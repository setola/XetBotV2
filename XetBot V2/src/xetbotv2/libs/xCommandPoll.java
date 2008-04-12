package xetbotv2.libs;

import java.util.StringTokenizer;

/**
  describes a tipical poll command
  something like:
  !poll <number> to show the poll correspondenting to <number>
  !vote <number> to vote for option <number>
  !addpoll <question> | <opt1> | ... | <optn> to starti a new poll
*/
public class xCommandPoll extends xCommand{
  
  public static final int ON_TEXT_POLL_NEW = 0;
  public static final int ON_TEXT_POLL_VOTE = 1;
  public static final int ON_TEXT_POLL_INFO = 2;
  
  
  private int typeOfCommandRequest;
  
  public xCommandPoll(String command){ init(command); }
  
  /**
		inizializes variables and tokenizes command
	*/
	protected void init(String command){
		StringTokenizer st = new StringTokenizer(command);
		
		this.numberOfTokens = xetbotv2.modules.XetBotPollModule.MAX_NUMBER_OF_OPTIONS + 2;
		this.tokens = new String[this.numberOfTokens];
		
		String currentToken;
		int tokenNumber = 0;
		
		/*inizialize the entire array or cannot concatenate strings
		in the while belowe: looks like null!Command nullArgument1 ...*/
		for(int i = 0; i < this.numberOfTokens; i++) this.tokens[i] = "";
		
    if(command.startsWith(xetbotv2.modules.XetBotPollModule.ON_TEXT_POLL_NEW)){
      typeOfCommandRequest = ON_TEXT_POLL_NEW;
      
      while (st.hasMoreTokens()) {
        currentToken = st.nextToken();
        
        //System.out.println("token "+tokenNumber+" "+currentToken);//DEBUG
        
        if(currentToken.compareTo("|")==0) {
          /*this is just for remove the first <space> from the second param of the command*/
          tokens[tokenNumber] = tokens[tokenNumber].substring(1);
          tokenNumber++; 
        }
        else {
          tokens[tokenNumber] += " " + currentToken;
          if(tokenNumber==0) {
            /*this is just for remove the first <space> from the second param of the command*/
            tokens[tokenNumber] = tokens[tokenNumber].substring(1);
            tokenNumber++;
          }
        }
      }
    }
    
    /*usin only one else just because ON_TEXT_POLL_VOTE and ON_TEXT_POLL_INFO
    have to be tokenized in the same way*/
    else {
      if(command.startsWith(xetbotv2.modules.XetBotPollModule.ON_TEXT_POLL_VOTE))
        typeOfCommandRequest = ON_TEXT_POLL_VOTE;
      else if(command.startsWith(xetbotv2.modules.XetBotPollModule.ON_TEXT_POLL_INFO))
        typeOfCommandRequest = ON_TEXT_POLL_INFO;
      while(st.hasMoreTokens()){
        tokens[tokenNumber] = st.nextToken();
        tokenNumber++; 
      }
    }
    
    /*else if(command.startsWith(xetbotv2.modules.XetBotPoll.ON_TEXT_POLL_INFO)){
      
    }*/
	}
  
  public String getQuestion(){ return this.getParameter(1); }
  public int getPollNumber(){ return (new Integer(this.getParameter(1))).intValue(); }
  public int getOptionNumber(){ return (new Integer(this.getParameter(2))).intValue(); }
  
  public boolean isGetPoll(){ return typeOfCommandRequest == ON_TEXT_POLL_INFO && 
                  xetbotv2.libs.xCommandQuote.isNumber(getParameter(1)); }
                  
  public boolean isSearch(){ return typeOfCommandRequest == ON_TEXT_POLL_INFO && 
                  !xetbotv2.libs.xCommandQuote.isNumber(getParameter(1)); }
  
  
  
  	
	/** just for test */
	
	public static void main(String[] args){
		xCommandPoll asd = new xCommandPoll(xetbotv2.modules.XetBotPollModule.ON_TEXT_POLL_NEW+" Ciccio del dio cana rulla? | si | no | boh |");
		System.out.println(asd.getQuestion());
    asd.replaceCommand(xetbotv2.modules.XetBotPollModule.ON_TEXT_POLL_INFO+" 1");
		System.out.println(asd);
    asd.replaceCommand(xetbotv2.modules.XetBotPollModule.ON_TEXT_POLL_INFO+" 1");
		System.out.println(asd.isGetPoll());
		System.out.println(asd.isSearch());
    asd.replaceCommand(xetbotv2.modules.XetBotPollModule.ON_TEXT_POLL_INFO+" prova");
		System.out.println(asd.isGetPoll());
		System.out.println(asd.isSearch());
    asd.replaceCommand(xetbotv2.modules.XetBotPollModule.ON_TEXT_POLL_VOTE+" 1 2");
		System.out.println(asd);
  }
  
  
  
}