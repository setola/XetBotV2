package xetbotv2.libs;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
	manage sensible text from database
*/
public class xOnText {
	/** this is the current ResultSet */
	private ResultSet rs;
	/** mantain connection with db */
	private xSQL db;
	/** this is true if the last executed query has given at least one row of results */
	private boolean hasResults;
	
	/**
		default constructor: creates connection with given db
		@param jdbcurl a jdbc-formatted url to your database
	*/
	public xOnText(String jdbcurl){
		this.db = new xSQL(jdbcurl);
		this.db.openConnection();
		this.hasResults = false;
	}
	
	/**
		tests if given text is included in db or not
		@returns true only if text is in database
	*/
	public boolean isSensibleText(String text){
		String query = "select text from ontext where text='" + xSQL.checkSQLSyntax(new xCommandOnText(text).getCommand()) + "' limit 0,1";
		//String query = "select text from ontext where match(text) against('" + xSQL.checkSQLSyntax(text) + "' in boolean mode) limit 0,1";
		return this.db.runQuery(query);
	}
	
	/**
		returns the command corresponding to given text
		@param text sensible text
		@return command for given text
	*/
	public String getCommand(String text){
		xCommandOnText onText = new xCommandOnText(text);
		String query = "select command from ontext where text='" +xSQL.checkSQLSyntax(onText.getCommand()) + "' order by rand() limit 0,1";
		//String query = "select command from ontext where match(text) against('" + xSQL.checkSQLSyntax(text) + "' in boolean mode) limit 0,1";
		this.hasResults = this.db.runQuery(query);
		this.rs = db.getResults();
		
		String result = "";
			
		while(this.hasResults){
			try{
				result += rs.getString("command");
				this.hasResults = rs.next();
				
				if(onText.hasObjective()) result = result.replace("$user", onText.getObjective());
				
				for(int i=0; i<onText.getNumberOfTokens(); i++){
					//System.out.println("replacing: $"+String.valueOf(i)+" con "+onText.getParameter(i));
					result = result.replace("$"+String.valueOf(i), onText.getParameter(i));
				}
				result = result.replace("$a", onText.getAllParam());
				result = result.replace("$n", onText.getMiscParam());
				
			}
			catch(SQLException e){ e.printStackTrace(); }
		}
		
		return result;
	}
	
	/**
		extract and format the resultset. Edit this if you wann translate your quote-bot
		@returns a human-readable string of current results
	*/
	/*private String formatResults(){
		if(!this.hasResults) return "";
		
		String result = "";
		
		while(this.hasResults){
			try{
				result += rs.getString("command");
				this.hasResults = rs.next();
			}
			catch(SQLException e){ e.printStackTrace(); }
		}
		
		return result;
	}*/
	
	
	
	
	
	
	
	
	
	public static void main(String[] args){
		xOnText asd = new xOnText("jdbc:mysql://localhost/ircbot?user=root&password=texrulez");
		
		//System.out.println(asd.isSensibleText("!prova"));
		//System.out.println("---");
		System.out.println(asd.getCommand("!rulla"));
		System.out.println("---");
		System.out.println(asd.getCommand("!rulla setola proprio abbestia"));
	}
	
}