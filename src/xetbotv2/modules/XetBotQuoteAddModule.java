package xetbotv2.modules;

import xetbotv2.libs.xQuote;
import xetbotv2.libs.xCommandQuoteAdd;
import xetbotv2.libs.xIRCCommand;
import xetbotv2.libs.xCommandList;
import xetbotv2.libs.xConfig;


public class XetBotQuoteAddModule implements BotFunction {
	
	public static final String ON_TEXT_QUOTE_ADD = "!addcitazione";
	
  private String jdbcurl;
  
  public XetBotQuoteAddModule(xConfig config){
    this.jdbcurl = config.get(xConfig.CONFIG_JDBCURL);
  }
	
	public xCommandList onText(String text){
		if (text.startsWith(ON_TEXT_QUOTE_ADD)) {
			xCommandQuoteAdd command = new xCommandQuoteAdd(text);
			xQuote quote = new xQuote(this.jdbcurl);
			quote.addQuote(command.getAuthor(), command.getQuote());
			xCommandList result = new xCommandList(1);
			result.add(new xIRCCommand("/msg la citazione e' stata inserita correttamente."));
		}
		
		return new xCommandList(0);
	}	
}