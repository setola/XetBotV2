package xetbotv2.libs;

//import java.util.Vector;
import java.util.StringTokenizer;


/**
	describes a tipical command line 
	es: !cita Ciccio tribes 3
	once create the object you can access to the single 
	param of the line by calling getParameter(int)
	
	this is a basic class. is a good idea to extend it
	with a subclass that describes a particular command such as {@link xCommandQuote}
*/
public class xCommand{
	protected String[] tokens;
	protected int numberOfTokens;
	
	/**
		default useless consctructor ;)
	*/
	public xCommand(){}
	
	/**
		inizialize object tokinizing the give command
		@param command the line command to tokenize
	*/
	public xCommand(String command){ this.init(command); }
	
	/**
		inizializes variables and tokenizes command
	*/
	protected void init(String command){
		StringTokenizer st = new StringTokenizer(command);
		
		this.numberOfTokens = st.countTokens();
		this.tokens = new String[this.numberOfTokens];
		
		int tokenNumber = 0;
		
		while (st.hasMoreTokens()) {
			tokens[tokenNumber] = st.nextToken();
			tokenNumber++;
		}
	}
	
	/**
		returns the parameter correspondentin to given number: 0 is usually the main command
		if param doesn't exist returns null
		@param number the number of the param
		@returns desired param
	*/
	public String getParameter(int number){
		if(number >= 0 && number < this.numberOfTokens)
			return tokens[number];
		return null;
	}
	
	/**
		returns how many params has this command
		@returns the number of params this command has
	*/
	public int getNumberOfTokens(){ return numberOfTokens; }
	
	/**
		returns the main command. The same as getParameter(0);
		@returns the main command.
	*/
	public String getCommand(){ 
		if(this.numberOfTokens>0) return tokens[0]; 
		return "";
	}
	
	/**
		returns all params, but not the command
		@returns all params, but not the command
	*/
	public String getParams(){
		String result = "";
		for( int i = 1; i<numberOfTokens; i++)
			result += getParameter(i) + " ";
		return result;
	}
	
	/**
		tests if the param number exists
		@return true if and only if the param corresponding to number is not empty string ("")
	*/
	public boolean hasParameter(int number){
		if(number >= 0 && number < this.numberOfTokens)
			//return (tokens[number] != null) || (tokens[number].compareTo("") == 0);
			return !tokens[number].isEmpty();
		return false;
	}
	
	/**
		changes the command line to new
		@param new string rapresenting a newCommand command for this obj
	*/
	public void replaceCommand(String newCommand){ this.init(newCommand); }
	
	/**
		returns a string rapresenting the command and all params divided by "|"
		@returns a string rapresenting the command and all params divided by "|"
	*/
	public String toString(){
		String result = "";
		for( int i = 0; i<numberOfTokens; i++)
			//result += tokens[i] + "|";
			result += getParameter(i) + "|";
		
		return result;
	}
	
	/** just for test */
	public static void main(String[] args){
		xCommand asd = new xCommand("!cita Ciccio server tribes 3");
		System.out.println(asd);
		System.out.println(asd.getNumberOfTokens());
		System.out.println(asd.getCommand());
		System.out.println(asd.getParameter(1));
		System.out.println(asd.hasParameter(1));
		System.out.println(asd.hasParameter(10));
		
		System.out.println("----------------------------------");
		
		asd.replaceCommand("/cita Dio porco");
		System.out.println(asd);
		System.out.println(asd.getNumberOfTokens());
		System.out.println(asd.getCommand());
		System.out.println(asd.getParameter(1));
		System.out.println(asd.hasParameter(3));
		System.out.println(asd.hasParameter(10));
	}
}