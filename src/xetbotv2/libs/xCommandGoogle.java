package xetbotv2.libs;

import java.util.StringTokenizer;

public class xCommandGoogle extends xCommand{
	
	public xCommandGoogle(String command){ this.init(command); }
	
	/**
		inizializes variables and tokenizes command
	*/
	protected void init(String command){
		//super(command);
		StringTokenizer st = new StringTokenizer(command);
		
		this.numberOfTokens = 2;
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
				
				default:
					this.tokens[1] += " "+currentToken;
				break;
			}
			tokenNumber++;
		}
		/*this is just for remove the first <space> from the second param of the command*/
		if(this.hasSearchString()) this.tokens[1] = this.tokens[1].substring(1);
	}	
	
	/**
		returns the search string
		@returns the search string
	*/
	public String getSearchString() { return this.tokens[1]; }
	
	/**
		tests if param corresponding to keyword is set
		@returns true only if current object has the second param	
	*/
	public boolean hasSearchString(){ return this.hasParameter(1); }
	
	
	
	
	
		/** just for test */
	public static void main(String[] args){
		xCommandGoogle asd = new xCommandGoogle("!google Ciccio server tribes 3");
		System.out.println(asd);
	}
}