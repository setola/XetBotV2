package xetbotv2.libs;

import java.util.StringTokenizer;

/**
	describes a tipical quote-add command
	such as "!addcitazione Ciccio tribes rulla proprio abbestia"
	where "!addcitazione is the command
	"Ciccio" is the author, "tribes rulla proprio abbestia" is the quote to add
*/
public class xCommandQuoteAdd extends xCommand{
	
	/**
		inizialize current object with given command line
		@param command the command with all his arguments
	*/
	public xCommandQuoteAdd(String command){ this.init(command); }
	
	/**
		inizializes variables and tokenizes command
	*/
	protected void init(String command){
		StringTokenizer st = new StringTokenizer(command);
		
		this.numberOfTokens = 3;
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
					this.tokens[2] += " " + currentToken;
				break;
			}
			tokenNumber++;
		}
		/*this is just for remove the first <space> from the second param of the command*/
		if(this.hasParameter(2)) this.tokens[2] = this.tokens[2].substring(1);
	}
	
	/**
		returns the author
		@returns the author
	*/
	public String getAuthor() { return this.tokens[1]; }
	
	/**
		returns the quote
		@returns the quote
	*/
	public String getQuote() { return this.tokens[2]; }	
	
	
	
	
	
	/** just for test */
	public static void main(String[] args){
		xCommandQuoteAdd asd = new xCommandQuoteAdd("!addcitazione Ciccio server tribes 3 rulla!");
		System.out.println(asd);
		System.out.println(asd.getAuthor());
		System.out.println(asd.getQuote());
		
		System.out.println("----------------------------------");
		
		asd.replaceCommand("!addcitazione Dio porco maledetto");
		System.out.println(asd);
		System.out.println(asd.getAuthor());
		System.out.println(asd.getQuote());
	}
}