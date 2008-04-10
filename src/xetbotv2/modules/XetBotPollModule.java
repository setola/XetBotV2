package xetbotv2.modules;

import xetbotv2.libs.xPoll;
import xetbotv2.libs.xPollSQL;
import xetbotv2.libs.xConfig;
import xetbotv2.libs.xCommandPoll;
import xetbotv2.libs.xCommandList;
import xetbotv2.libs.xIRCCommand;
import xetbotv2.libs.VoteOption;

public class XetBotPollModule implements BotFunction {
  
  public static final String ON_TEXT_POLL_INFO = "!sondaggio";
  public static final String ON_TEXT_POLL_VOTE = "!vota";
  public static final String ON_TEXT_POLL_NEW = "!addsondaggio";
  public static final int MAX_NUMBER_OF_OPTIONS = 5;
  
  private xPollSQL poll;
  	
	public XetBotPollModule(xConfig config){
    poll = new xPollSQL(config.get(xConfig.CONFIG_JDBCURL));
  }
  
  public xCommandList onText(String text){
    
		xCommandList result = new xCommandList(10);    
    xCommandPoll command = new xCommandPoll(text);
    
		if (command.getCommand().startsWith(ON_TEXT_POLL_INFO)) {
      if(command.isSearch()){
        System.out.println("search");//debug
        poll.searchPoll(command.getParameter(1));
      //result.add(new xIRCCommand("/msg# "+poll.getPoll()));
      }
      if(command.isGetPoll()){
        System.out.println("get");//debug
        poll.getPoll(command.getPollNumber());
      }
      String[] tempPollString = poll.getFormattedPollInfo();
      if(tempPollString!=null){
        for(int i=0; i<tempPollString.length; i++)
          if(tempPollString[i]!=null) 
            result.add(new xIRCCommand("/msg# "+tempPollString[i]));
      }
      else
        result.add(new xIRCCommand("/msg non ho trovato nulla di simile nei sondaggi."));
        
    }
    else if(command.getCommand().startsWith(ON_TEXT_POLL_VOTE)){
      poll.vote(command.getPollNumber(), command.getOptionNumber());
      //result.add(new xIRCCommand("/msg il tuo voto e' stato registrato con successo."));
    }
    else if(command.getCommand().startsWith(ON_TEXT_POLL_NEW)){
      xPoll tempPoll = new xPoll();
      tempPoll.setAuthor("ignoto");
      tempPoll.setQuestion(command.getQuestion());
      
      for(int i=0; i<MAX_NUMBER_OF_OPTIONS; i++){
        if(command.getParameter(i+2).compareTo("")!=0)
          tempPoll.addOption(new VoteOption(command.getParameter(i+2)));
      }
      /*
      TODO trovare un modo per dire se il sondaggio e' stato inserito o no
      */
      //if()
      poll.addPoll(tempPoll);
      //result.add(new xIRCCommand("/msg sondaggio aggiunto con successo."));
      //else 
        //result.add(new xIRCCommand("/msg sondaggio gia' presente nel database. vaffanculo."));
    }
    
    return result;
  }
  
}