package xetbotv2.modules;

import xetbotv2.libs.xGoogle;
import xetbotv2.libs.xCommandGoogle;
import xetbotv2.libs.xIRCCommand;
import xetbotv2.libs.xCommandList;
import org.jibble.pircbot.Colors;
import xetbotv2.libs.xConfig;

/**
	here we have the class that manage events onText by searching
	text in the database and retriving - if exist - the command the bot
	have to run when someone say sensible text
*/
public class XetBotGoogleSearchModule implements BotFunction {
	
	public static final String ON_TEXT_GOOGLE = "!gugl";
   
  public XetBotGoogleSearchModule(xConfig config){
    //manage here any configuration extracted from xConfig.config
  }
	
	public xCommandList onText(String text){
		if (text.startsWith(XetBotGoogleSearchModule.ON_TEXT_GOOGLE)){
			xCommandGoogle gSearch = new xCommandGoogle(text);
			xGoogle gQuery;
			
			if(gSearch.hasSearchString()){
				gQuery = new xGoogle(gSearch.getSearchString());
				
				xCommandList result = new xCommandList(4);
				result.add(new xIRCCommand("/msg# risultati per '" + Colors.RED + gSearch.getSearchString() + Colors.NORMAL +"' su google.it"));
				result.add(new xIRCCommand("/msg# "+gQuery.getResult(1)));
				result.add(new xIRCCommand("/msg# "+gQuery.getResult(2)));
				result.add(new xIRCCommand("/msg# "+gQuery.getResult(3)));
				
				return result;
			}		
		}
		return new xCommandList(0);
	}
}