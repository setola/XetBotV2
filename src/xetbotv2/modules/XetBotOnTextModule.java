package xetbotv2.modules;

import xetbotv2.libs.xOnText;
import xetbotv2.libs.xIRCCommand;
import xetbotv2.libs.xCommandList;
import xetbotv2.libs.xConfig;

/**
	here we have the class that manage events onText by searching
	text in the database and retriving - if exist - the command the bot
	have to run when someone say sensible text
*/
public class XetBotOnTextModule implements BotFunction {
  
  private String jdbcurl;
  
  public XetBotOnTextModule(xConfig config){
    this.jdbcurl = config.get(xConfig.CONFIG_JDBCURL);
  }

	public xCommandList onText(String text){
		xOnText onText = new xOnText(jdbcurl);
		
		xCommandList result = new xCommandList(1);
		result.add(new xIRCCommand(onText.getCommand(text)));
		
		return result;
	}
}