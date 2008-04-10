package xetbotv2.libs;

import java.util.StringTokenizer;

/**
	describes a tipical quote-request command
	such as "!cita Ciccio tribes server 3"
	where "!cita! is the command
	"Ciccio" is the author, "tribes server" is the keyword
	and "3" is the number of quotes requested
*/
public class xCommandQuote extends xCommand{
	
	/**
		inizialize current object with given command line
		@param command the command with all his arguments
	*/
	public xCommandQuote(String command){ this.init(command); }
	
	/**
		inizializes variables and tokenizes command
	*/
	protected void init(String command){
		//super(command);
		StringTokenizer st = new StringTokenizer(command);
		
		this.numberOfTokens = 4;
		this.tokens = new String[this.numberOfTokens];
		
		String currentToken;
		int tokenNumber = 0;
		
		/*inizialize the entire array or cannot concatenate strings
		in the while belowe: looks like null!Command nullArgument1 ...*/
		for(int i = 0; i < this.numberOfTokens; i++) this.tokens[i] = "";
		
		while (st.hasMoreTokens()) {
			currentToken = st.nextToken();
			
			switch (tokenNumber){
				case 0:
					this.tokens[0] += currentToken;
				break;
				
				case 1:
					this.tokens[1] += currentToken;
				break;
				
				default:
					if(!st.hasMoreTokens() && isNumber(currentToken)) 
						this.tokens[3] += currentToken;
					else this.tokens[2] += " " + currentToken;
				break;
			}
			tokenNumber++;
		}
		/*this is just for remove the first <space> from the second param of the command*/
		if(this.hasKeyword()) this.tokens[2] = this.tokens[2].substring(1);
	}
	
	/**
		returns the author
		@returns the author
	*/
	public String getAuthor() { return this.tokens[1]; }
	
	/**
		returns the keyword
		@returns the keyword
	*/
	public String getKeyword() { return this.tokens[2]; }
		
	/**
		returns the number of quotes requested
		@returns the number of quotes requested
	*/
	public String getNumber() { return this.tokens[3]; }
	
	/**
		tests if param corresponding to author is set
		@returns true only if current object has the first param
	*/
	//public boolean hasAuthor(){ return this.hasParameter(1); }
	public boolean hasAuthor(){ return this.hasParameter(1) && this.tokens[1].compareTo("*") != 0; }
	
	/**
		tests if param corresponding to keyword is set
		@returns true only if current object has the second param	
	*/
	public boolean hasKeyword(){ return this.hasParameter(2); }
	
	/**
		tests if param corresponding to number is set
		@returns true only if current object has the third param
	*/
	public boolean hasNumber(){ return this.hasParameter(3); }
	
	/**
		changes the command line to new
		@param new string rapresenting a newCommand command for this obj
	*/
	public void replaceCommand(String newCommand){ this.init(newCommand); }
	
	/**
		tests if the given string s is made only by numbers
		@returns true if and only if s is made only by numbers
	*/
	public static boolean isNumber(String s) {
		for (int j = 0; j < s.length(); j++) {
			if (!Character.isDigit(s.charAt(j))) { return false; }
		}
		return true;
	}
	
	
	
	
	
	/** just for test */
	/*
	public static void main(String[] args){
		xCommandQuote asd = new xCommandQuote("!cita Ciccio server tribes 3");
		System.out.println(asd);
		System.out.println(asd.hasAuthor());
		System.out.println(asd.hasKeyword());
		System.out.println(asd.hasNumber());
		
		System.out.println("----------------------------------");
		
		asd.replaceCommand("!cita Dio porco maledetto");
		System.out.println(asd);
		System.out.println(asd.hasAuthor());
		System.out.println(asd.hasKeyword());
		System.out.println(asd.hasNumber());
	}*/
	
}