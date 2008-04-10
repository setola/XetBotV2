package xetbotv2.libs;

import java.util.StringTokenizer;

/**
	describes a tipical OnText command i.e. something like
	!rulla Setola in modo spaventoso
	where !rulla is the main command and rest of string are arguments
*/
public class xCommandOnText extends xCommand{
	String allParam = "";
	String miscParam = "";
	
	/**
		inizialize current object with given command line
		@param command the command with all his arguments
	*/
	public xCommandOnText(String command){ this.init(command); }
	
	/**
		inizializes variables and tokenizes command
	*/
	protected void init(String command){
		StringTokenizer st = new StringTokenizer(command);
		
		this.numberOfTokens = st.countTokens() ;
		this.tokens = new String[this.numberOfTokens];
		
		String currentToken;
		int tokenNumber = 0;
		
		/*inizialize the entire array or cannot concatenate strings
		in the while belowe: looks like null!Command nullArgument1 ...*/
		//for(int i = 0; i < this.numberOfTokens; i++) this.tokens[i] = "";
		
		while (st.hasMoreTokens()) {
			currentToken = st.nextToken();
			//System.out.println("token"+tokenNumber+": "+currentToken);
			
			this.tokens[tokenNumber] = currentToken;
			if(tokenNumber>1)
				miscParam += " "+currentToken;
			if(tokenNumber>0 && !currentToken.startsWith("$")) 
				allParam += " "+currentToken;
			
			tokenNumber++;
		}
		/*this is just for remove the first <space> from the second param of the command*/
		//if(this.hasAllParam()) this.tokens[2] = this.tokens[2].substring(1);
	}
	
	/**
		returns the misc param
		@returns the misc param
	*/
	public String getMiscParam(){ return this.miscParam; }
	
	/**
		returns the all param
		@returns the all param
	*/
	public String getAllParam() {  return this.allParam; }
	
	/**
		returns the objective
		@returns the keyword
	*/
	public String getObjective() { return this.tokens[1]; }
	
	/**
		returns the var
		@returns the var
	*/
	public String getVar(int index) {
		if(index<this.numberOfTokens) 
			return this.tokens[index]; 
		return "";
	}
		
	/**
		tests if param corresponding to "the sender" is set
		@returns true only if current object has the second param	
	*/
	public boolean hasObjective(){ return this.hasParameter(1); }
	
	
		
	
	/** just for test */
	
	public static void main(String[] args){
		xCommandOnText asd = new xCommandOnText("!prova uno due tre quattro cinque sei");
		System.out.println(asd);
		//System.out.println("numberOfVars: "+asd.getNumberOfVars());
	}
		
	
}