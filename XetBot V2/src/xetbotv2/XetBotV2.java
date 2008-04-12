package xetbotv2;

import xetbotv2.modules.*;
import xetbotv2.libs.xAutojoinChannels;
import xetbotv2.libs.xIRCCommand;
import xetbotv2.libs.xCommandList;
import xetbotv2.libs.xIRCCommandTypeCode;
import xetbotv2.libs.xConfig;
import org.jibble.pircbot.*;
//import java.io.IOException;
//import java.util.StringTokenizer;

/**
	@version 2.0.10a
*/
public class XetBotV2 extends PircBot {
	private xetbotv2.modules.BotFunction[] plugins;
	private xConfig config;
	
	public XetBotV2(xConfig config){
    this.config = config;
    
		this.plugins = new xetbotv2.modules.BotFunction[6];
		this.plugins[0] = new XetBotTimeModule(config);
		this.plugins[1] = new XetBotQuoteModule(config);
		this.plugins[2] = new XetBotQuoteAddModule(config);
		this.plugins[3] = new XetBotOnTextModule(config);
		this.plugins[4] = new XetBotGoogleSearchModule(config);
		//this.plugins[5] = new XetBotPollModule(config);
		this.plugins[5] = new XetBotQuizModule(config);
		
		this.setName(config.get(xConfig.CONFIG_NICKNAME));
    this.setLogin(config.get(xConfig.CONFIG_LOGIN));
		
		this.setVerbose(true);
		this.setVersion("Xet|BOT| V2 0.10a build 36");
    
    boolean isFirstTime = true;
		
    while (!this.isConnected()) {
      try { this.connect(config.get(xConfig.CONFIG_SERVER)); } 
      catch(NickAlreadyInUseException e) {
        this.setName(config.get(xConfig.CONFIG_NICKNAME2));
        if(!isFirstTime) this.setAutoNickChange(true);
        isFirstTime = false;
      }
      catch(Exception e){e.printStackTrace();}
      
      this.autojoinChannels();
      
      if(!this.isConnected())
        try { Thread.sleep(10000); } catch(InterruptedException e) { e.printStackTrace(); }
    }
	}
  
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		//int length = this.plugins.length;
		xCommandList commands;
		for(int i = 0; i < plugins.length; i++){
			//System.out.println("i: "+i);//debug
			if(this.plugins[i]!=null){
				//System.out.println("il plugin "+i+" non e' nullo");//debug
				commands = this.plugins[i].onText(message);
				//String[] commands = XetBotV2.commandTokenizer(command);
				xIRCCommand command;
				
				for(int j=0; j<commands.length(); j++){
					command = (xIRCCommand)commands.getCommand(j);
          if(command==null)break;//just for check that current command is valid
					if(command.isType(xIRCCommandTypeCode.RESPONSE_TYPE_MSG_TO_CHANNEL)){
						//System.out.println("il plugin "+i+" e' msg2chan");//debug
						sendMessage(channel, 
							syntaxReplace(channel, sender, login, hostname, 
								command.getParams()));
					}
					else if(command.isType(xIRCCommandTypeCode.RESPONSE_TYPE_MSG_TO_USER)){
						//System.out.println("il plugin "+i+" e' msg2usr");//debug
						sendMessage(sender, 
							syntaxReplace(channel, sender, login, hostname, 
								command.getParams()));
					}
					else if(command.isType(xIRCCommandTypeCode.RESPONSE_TYPE_ACTION_TO_CHANNEL)){
						//System.out.println("il plugin "+i+" e' act2chan");//debug
						sendAction(channel, 
							syntaxReplace(channel, sender, login, hostname, 
								command.getParams()));
					}
					else if(command.isType(xIRCCommandTypeCode.RESPONSE_TYPE_ACTION_TO_USER)){
						//System.out.println("il plugin "+i+" e' act2usr");//debug
						sendAction(channel, 
							syntaxReplace(sender, sender, login, hostname, 
								command.getParams()));
					}
					else if(command.isType(xIRCCommandTypeCode.RESPONSE_TYPE_CTCP)){
						//System.out.println("il plugin "+i+" e' ctcp");//debug
						sendCTCPCommand(sender, 
							syntaxReplace(sender, sender, login, hostname, 
								command.getParams()));
					}
					else if(command.isType(xIRCCommandTypeCode.RESPONSE_TYPE_DELAY)){
						//System.out.println("il plugin "+i+" e' delay");//debug
						int delay = Integer.valueOf(command.getParameter(1));
						if(command.getParameter(2).compareTo("s")==0) delay = delay*1000;
						else if(command.getParameter(2).compareTo("m")==0) delay = delay*60000;
						else if(command.getParameter(2).compareTo("h")==0) delay = delay*3600000;
						try { Thread.sleep(delay); }
						catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}
			//System.out.println("======================");//debug
		}
	}
	
	/**
		authenticate the bot with qbot... bot're loving theirself!!
	*/
	public void onConnect(){
		sendMessage("q@cserve.quakenet.org", "auth xetbot texrulez");
	}
  
  public void onDisconnect(){
    while(!this.isConnected()) {
      try { this.connect(config.get(xConfig.CONFIG_SERVER)); } catch(Exception e){e.printStackTrace();}
      try { Thread.sleep(30000); } catch(InterruptedException e) { e.printStackTrace(); }
    }
		this.autojoinChannels();
  }
  
  /**
    try to get my nickname back when uman goes offline!!! muhahahah BOT OWNAGE!!!
  */
  public void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason){
    if(sourceNick.compareToIgnoreCase(config.get(xConfig.CONFIG_NICKNAME))==0)
      this.changeNick(config.get(xConfig.CONFIG_NICKNAME));
  }
  
  /**
    try to get my nickname back when uman change his nick!!! muhahahah BOT OWNAGE!!!
  */
  public void onNickChange(String oldNick, String login, String hostname, String newNick) {
    if(oldNick.compareToIgnoreCase(config.get(xConfig.CONFIG_NICKNAME))==0)
      this.changeNick(config.get(xConfig.CONFIG_NICKNAME));
  }
	/**
		replaces script syntax with corresponding value. 
		i.e. all occurrences of the string "$user" become the name of the user
		valid tags are: $chan, $user, $login, $hostname
	*/
	private String syntaxReplace(String channel, String sender, String login, String hostname, String message){
		String result = message.replace("$chan",channel );
		result = result.replace("$user", sender);
		result = result.replace("$n", "");
		result = result.replace("$login", login);
		return result.replace("$hostname", hostname);
	}
	/*
	private static String[] commandTokenizer(String command){
		StringTokenizer tkz = new StringTokenizer(command, "|");
		String[] result = new String[tkz.countTokens()];
		int i =0;
		while(tkz.hasMoreTokens()){
			result[i] = tkz.nextToken();
			i++;
		}
		
		return result;
			
	}*/
	
	/**
		retrives autojoin channel from database and join them all!
	*/
	private void autojoinChannels(){
		xAutojoinChannels channels = new xAutojoinChannels(config.get(xConfig.CONFIG_JDBCURL));//"jdbc:mysql://localhost/ircbot?user=root&password=texrulez");
		while(channels.hasResults()) this.joinChannel(channels.getChannel());
	}	
	
}