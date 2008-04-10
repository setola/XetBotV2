package xetbotv2.modules;

import xetbotv2.libs.xIRCCommand;
import xetbotv2.libs.xCalendar;
import org.jibble.pircbot.Colors;
import xetbotv2.libs.xCommandList;
import xetbotv2.libs.xConfig;

public class XetBotTimeModule implements BotFunction{
	
	public static final String ON_TEXT_TIME = "!time";
  
  public XetBotTimeModule(xConfig config){
    //manage here any configuration extracted from xConfig.config
  }
	
	public xCommandList onText(String text){
		if (text.equalsIgnoreCase(XetBotTimeModule.ON_TEXT_TIME)) {
			xCalendar cal = new xCalendar();
			
			xCommandList result = new xCommandList(2);
			result.add(new xIRCCommand("/msg# sono le " + 
				Colors.BOLD + Colors.RED +  cal.getFormattedDate() + Colors.NORMAL));
			result.add(new xIRCCommand("/msg#  del "+ Colors.BOLD + Colors.CYAN +  cal.getFormattedTime()));
			
			
			/*return new xIRCCommand("/msg# sono le " + 
				Colors.BOLD + Colors.RED +  cal.getFormattedDate() + Colors.NORMAL +  
				" del " + Colors.BOLD + Colors.CYAN +  cal.getFormattedTime());*/
			
			return result;
		}
		return new xCommandList(0);
		//return new xIRCCommand("");
	}
}
