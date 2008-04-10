package xetbotv2.libs;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jibble.pircbot.Colors;

public class xPollSQL{
  
	/** this is the current ResultSet */
	private ResultSet rs;
	/** mantain connection with db */
	private xSQL db;
	/** this is true if the last executed query has given at least one row of results */
	private boolean hasResults;
	/** maintain poll */
  private xPoll poll;
  
  public xPollSQL(String jdbcurl){
		this.db = new xSQL(jdbcurl);
		this.db.openConnection();
		this.hasResults = false;
  }
  
  public xPoll getPoll(int id){
		this.hasResults = false;
    String query = "SELECT * FROM `poll` WHERE `id`="+id+" LIMIT 0 , 1";
    runquery(query);
    resultSetToXPoll();
    
    return poll;
  }
  
  public boolean searchPoll(String question){
		this.hasResults = false;
    String query = "SELECT * FROM `poll` WHERE `question` LIKE '%" + question + "%' LIMIT 0 , 1";
    runquery(query);
    resultSetToXPoll();
    
    return hasResults;
  }
  
	/**
		adds current poll to db
	*/
	public boolean addPoll(){
    
    int numberOfOptions = poll.getNumberOfOptions();
    String query = "INSERT INTO `poll` (`author`, `question`";
    
    for(int i=1; i <= numberOfOptions; i++)
      query += ", `option" + i + "`, `votes" + i + "`";
    
    query += ") VALUES ('" + xSQL.checkSQLSyntax(poll.getAuthor()) + "', '" + xSQL.checkSQLSyntax(poll.getQuestion()) + "'";
    
    for(int i=1; i <= numberOfOptions; i++)
      query += ", '" + xSQL.checkSQLSyntax(poll.getOption(i-1)) + "', '" + poll.getVotes(i-1) + "'";
    
    query += ")";
    
		boolean result = this.db.executeQuery(query);
    System.out.println(result);//debug
    return result;
    
	}
  
  /**
    adds give poll to db
  */
  public boolean addPoll(xPoll poll){
    this.poll = poll;
    return this.addPoll();
  }
  
  /**
    adds a single vote for given poll to given choice
    @param id the poll id
    @index the poll choice
  */
  public boolean vote(int id, int index){
		this.hasResults = false;
    int votes = getVotes(id,index)+1;
    String query = "UPDATE `poll` SET `votes"+index+"` = '"+votes+"' WHERE `poll`.`id` ="+id+" LIMIT 1 ;";
    
    return db.executeQuery(query);
  }
  
  /**
    retrives number of votes for index choice of id poll
    @param id the poll id
    @index the poll choice
    @returns number of votes for give choice
  */
  public int getVotes(int id, int index){
    String query = "SELECT `votes" + index +"` FROM `poll` WHERE `id`="+id+" LIMIT 0 , 1";
    runquery(query);
    int result = 0;
    try{ result = rs.getInt("votes"+index); }
    catch(SQLException e){ e.printStackTrace(); }
    
    return result;
  }
  
  private void resultSetToXPoll(){
    if(!hasResults) return;
    
		this.poll = new xPoll();
		
		int i = 0;
    boolean hasMoreResults = true;
		while(hasMoreResults){
			try{
				
				this.poll.setID(rs.getInt("id"));
				this.poll.setAuthor(rs.getString("author"));
				this.poll.setQuestion(rs.getString("question"));
				this.poll.setTimestamp(rs.getTimestamp("startinDate"));
        
        String currentOption = null;
        for(int j = 1; j < xetbotv2.modules.XetBotPollModule.MAX_NUMBER_OF_OPTIONS; j++){
          currentOption = rs.getString("option"+j);
          
          //System.out.println("option"+j+" "+currentOption);//debug
          
          if(currentOption != null)
            this.poll.addOption(new VoteOption(currentOption,rs.getInt("votes"+j)));
        }
				
				
				hasMoreResults = rs.next();
				i++;
			}
			catch(SQLException e){ e.printStackTrace(); }
		}
  }
	
	/**
		runs the given query
		@param query the SQL query
	*/
	private void runquery(String query){
		//System.out.println("query: " + query); //DEBUG
		this.hasResults = this.db.runQuery(query);
		this.rs = db.getResults();
	}
  
  public void setPoll(xPoll poll){ this.poll = poll; }
  public xPoll getPoll(){ return poll; }
  
  public String[] getFormattedPollInfo(){
    if(poll==null)return null;
    
    
    String[] result = new String[xetbotv2.modules.XetBotPollModule.MAX_NUMBER_OF_OPTIONS+3];
    result[0] = Colors.NORMAL;
    result[0] += "Sondaggio numero: " + Colors.BOLD + Colors.RED + poll.getID();
    result[1] = Colors.NORMAL;
    result[1] += "Domanda: " + Colors.BOLD + Colors.REVERSE + poll.getQuestion();
    
    int numberOfOptions = poll.getNumberOfOptions();
    int i;
    for(i=0; i<numberOfOptions; i++)
      if(poll.getOption(i)!=null) 
        result[i+2] = (i+1) + " - (" + Colors.RED + poll.getVotes(i) + Colors.NORMAL + " voti - " + 
            Colors.RED + ((double)(((int)(poll.getPercentual(i)*100)))/100) + 
            Colors.NORMAL + "%): " + poll.getOption(i);
    
    result[i+3] = "Voti Totali: " + Colors.RED + String.valueOf(poll.getNumberOfVotes()) + Colors.NORMAL;
    
    return result;
  }
  
  /*public String[] getFormattedPollInfo(int index){
    return getPoll(index).getFormattedPollInfo();
  }*/
  
  
  
  public static void main(String[] args){
    xPollSQL asd = new xPollSQL("jdbc:mysql://localhost/ircbot?user=root&password=texrulez");
    
    xPoll lol = new xPoll(3);
    lol.setAuthor("fetola");
    lol.setQuestion("Ciccio e' gay?");
    lol.addOption(new VoteOption("FI"));
    lol.addOption(new VoteOption("NO"));
    lol.addOption(new VoteOption("solo quando succhia i cazzi"));
    
    asd.searchPoll("bot");
    //System.out.println(asd.getVotes(1,2));
    
    //asd.vote(1,2);
    //System.out.println(asd.getVotes(1,2));
    
    //System.out.println(lol);
    //asd.setPoll(lol);
    //System.out.println("----------------");
    //System.out.println(asd.getPoll());
    //System.out.println("----------------");
    //asd.addPoll();
    
    
    //System.out.println(searchPoll("ciccio e' gay?"));
    System.out.println(asd.getPoll());
    
  }
  
}