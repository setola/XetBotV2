package xetbotv2.libs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

/**
	classe che maneggia la connessione con mysql
	da tenere pulita e asettica con al massimo un runquery
	non deve contenere nessun query specifica
	deve avere un metodo che ritorni i risultati
	NON formattati.
	deve poter essere usata per qualunque query non solo per le quote.
	
	TODO: gestione Exception
*/
public class xSQL{
	/** jdbc-formatted url to db*/
	private String jdbcurl;
	/** maintain statement*/
	private Statement stmt;
	/** maintain rusults*/
	private ResultSet rs;
	
	/**
		standard constructor: set url for current object
		@param jdbcurl a jdbc-formatted url to database
	*/
	public xSQL(String jdbcurl){
		this.jdbcurl = jdbcurl;
		this.rs = null;
	}
	
	/**
		apre la connessione col db - meditare se public o private
	*/
	public void openConnection() {
		try{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager.getConnection(this.jdbcurl);
		stmt = conn.createStatement();
		}
		// throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException
		catch(Exception e){ e.printStackTrace(); }
	}
	
	/**
		execute a query to database
		@return true if query has returned at least one valid row, false if not.
	*/
	public boolean runQuery(String query)  {
		boolean result = false;
		//System.out.println("executing query: "+query);
		try{
			openConnection();
			rs = stmt.executeQuery(query);			
			result = rs.first();
		}
		//throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
		catch(Exception e){ e.printStackTrace(); }
		
		return result;
	}
	
	/**
		execute a query to database - doesn't save the resultset to this object
		@return true if query has returned at least one valid row, false if not or if it is an update count
	*/
	public boolean executeQuery(String query)  {
		boolean result = false;
		try{
			openConnection();
			result = stmt.execute(query);	
		}
		//throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    catch(MySQLIntegrityConstraintViolationException e){ return false; }
		catch(Exception e){ e.printStackTrace(); }
		
		return result;
	}
	
	/**
		returns current resultset with cursor moved on first row 
		@returns current resultset
	*/
	public ResultSet getResults(){
		return this.rs;
	}
	
	/**
		Retrieves the value of the designated column in the current row
		@param what the label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column
		@returns the column value; if the value is SQL NULL, the value returned is null
	*/
	public String getResultString(String what){
		String result = null;
		try { result = this.rs.getString(what); }
		catch(SQLException e){ e.printStackTrace(); }
		return result;
	}
	
	/**
		Retrieves the value of the designated column in the current row
		@param what the index for the column specified 
		@returns the column value; if the value is SQL NULL, the value returned is empty string
	*/
	public String getResultString(int what){
		String result = "";
		try { result = this.rs.getString(what); }
		catch(SQLException e){ e.printStackTrace(); }
		return result;
	}
	
	/**
		returns current jdbc-formatted url to database
		@returns current jdbc url
	*/
	public String getJDBCurl(){
		return this.jdbcurl;
	}
	
	/**
		relases resurces used for opening and managing connection
	*/
	public void relaseResources() {
		try{
			if (rs != null) {
				rs.close(); 
				if (stmt != null) {
					stmt.close(); 
				}
			}
		}
		//throws SQLException
		catch(SQLException e){ e.printStackTrace(); }
	}
	
	/**
		finds and change eventual char that can corrupt
		sql syntax. i.e. ' . changes them with escape+char
		i.e. \'
	*/
	public static String checkSQLSyntax(String query){
		//System.out.println("replacing: "+query);//debug
		//System.out.println("with: "+query.replace("\'","\\\'"));//debug
		return query.replace("\'","\\\'");//debug
	}
	
	
	
	
	
	
	
	
	/**
		example of main - just for debug
	*/	
	public static void main(String[] args) throws Exception{
		
		xSQL asd = new xSQL("jdbc:mysql://localhost/ircbot?user=root&password=texrulez"); 
		
		asd.openConnection();
		asd.runQuery("Select * From quote order by rand() limit 0,1");
		ResultSet lol = asd.getResults();
		
		System.out.println("id: " + lol.getString("id"));
		System.out.println("author: " + lol.getString("author"));
		System.out.println("quote: " + lol.getString("quote"));
		System.out.println("timestamp: " + lol.getString("timestamp"));
		
		System.out.println("----------------------------------------");
		
		asd.runQuery("Select * From quote order by rand() limit 0,1");
		lol = asd.getResults();
		
		System.out.println("id: " + lol.getString("id"));
		System.out.println("author: " + lol.getString("author"));
		System.out.println("quote: " + lol.getString("quote"));
		System.out.println("timestamp: " + lol.getString("timestamp"));
		
		asd.relaseResources();
	
	}
	
	
	
}