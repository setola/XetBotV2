package xetbotv2.libs;

import java.sql.Timestamp;

/**
  describes a list of VoteOption
  orders and calculates stats
  
  TODO:
  
  
    se hai voglia di sbatterti con l'ascii art magari pensare una o due barre stile grafico
*/
public class xPoll {
  public static final int SORT_ASCENDING = 0;
  public static final int SORT_DESCENDING = 1;
  
  private VoteOption[] list;
  private int numberOfOptions;
  
  private int id;
  private String author;
  private Timestamp timestamp;
  private String question;
  
  public xPoll(int maxNumberOfElemnts){
    list = new VoteOption[maxNumberOfElemnts];
    numberOfOptions = 0;
  }
  
  public xPoll(){ this(xetbotv2.modules.XetBotPollModule.MAX_NUMBER_OF_OPTIONS); }
  
  
  /**
    adds an option to current list
    @param v VoteOption description of option
    @returns true if add was successfull
  */
  public boolean addOption(VoteOption v){
    if(!hasMoreFreeSpaces()) return false;
    
    list[numberOfOptions] = v;
    numberOfOptions++;
    return true;
  }
  
  /**
    adds an option to current poll with 0 votes
    @param option description of option
    @returns true if add was successfull
  */
  public boolean addOption(String option){
    return addOption(new VoteOption(option, 0));
  }
  
  /**
    adds a single vote for the given index option
    @param index the number of poll option you wanna vote for
  */
  public void addVote(int index){
    if(isValidIndex(index)) {
      //votes[index]++;
      list[index].addVote();
      //numberOfVotes++;
    }
  }
  
  /**
    returns the number of votes for given poll option
    @param index the index of poll option
    @returns number of votes
  */
  public int getVotes(int index){
    if(isValidIndex(index)) 
      return list[index].getVotes();
      //return votes[index];
    return 0;
  }
  
  /**
    returns the description of given poll option
    @param index the index of poll option
    @returns string description of option
  */
  public String getOption(int index){
    if(isValidIndex(index)) 
      return list[index].getOption();
    return "";
  }
  
  /**
    calculates and returns percentual of given poll option
    @param index the index of poll option
    @returns percentual for given option
  */
  public double getPercentual(int index){
    double result = 0.0;
    if(isValidIndex(index)) {
      if(getVotes(index)!=0)
        result = ((double)getVotes(index)/getNumberOfVotes())*100;
    }
    return result;
  }
  
  /**
    returns a sorted array of current options
    @param by sorting type
    @return sorted list
  */
  public VoteOption[] getSorted(int by){
    switch(by){
      case xPoll.SORT_ASCENDING:
        return sortAscending();
      //break;
      
      case xPoll.SORT_DESCENDING:
        return sortDescending();
      //break;
      
      default:
        return sortAscending();
      //break;
    }
  }
  
  public int getNumberOfOptions(){ return numberOfOptions; }
  public int getNumberOfVotes(){ 
    int result = 0;
    
    for(int i=0; i<getNumberOfOptions(); i++)
      result += list[i].getVotes();
    
    return result; 
  }
  
  public int getID(){ return id; }
  public String getAuthor(){ return author; }
  public String getQuestion(){ return question; }
  public Timestamp getTimestamp(){ return timestamp; }
  
  public void setID(int id){ this.id = id; }
  public void setAuthor(String author){ this.author = author; }
  public void setQuestion(String question){ this.question = question; }
  public void setTimestamp(Timestamp timestamp){ this.timestamp = timestamp; }
  
  public String toString(){
    String result = "";
    result += "Sondaggio numero: " + String.valueOf(id) + "\n";
    result += "domanda: " + getQuestion() + "\n";
    
    int i = 0;
    while(i < numberOfOptions)
      result += "opzione: " + (i+1) + " " + getOption(i) + 
                " - voti: " + getVotes(i) + 
                " percentuale: " + getPercentual(i++) + "\n";
    
    
    result += "numero totale di voti: " + getNumberOfVotes();
    return result;
  }
  
  private VoteOption[] sortAscending(){ return list; }
  private VoteOption[] sortDescending(){ return list; }
  
  private boolean isValidIndex(int index){ return index < list.length; }
  private boolean hasMoreFreeSpaces(){ return isValidIndex(numberOfOptions); }
  
  
  
  
  
  
  
  
  
  
    
  /* just fot test */
  public static void main(String[] args){
    xPoll asd = new xPoll(3);
    asd.setQuestion("Ciccio e' gay?");
    
    boolean addiction = true;
    
    addiction = addiction && asd.addOption(new VoteOption("FI",0));
    addiction = addiction && asd.addOption(new VoteOption("NO"));
    addiction = addiction && asd.addOption(new VoteOption("solo quando succhia i cazzi"));
    
    for(int i = 0; i < 17; i++)
      asd.addVote(0);
    
    
    for(int i = 0; i < 19; i++)
      asd.addVote(1);
    
    for(int i = 0; i < 31; i++)
      asd.addVote(2);
    
    if(addiction)System.out.println(asd);
    
  }
  
  
  
  
  
  
}





