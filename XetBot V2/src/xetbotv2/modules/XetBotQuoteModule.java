package xetbotv2.modules;

import xetbotv2.libs.xQuote;
import xetbotv2.libs.xCommandQuote;
import xetbotv2.libs.xIRCCommand;
import xetbotv2.libs.xCommandList;
import xetbotv2.libs.xConfig;


public class XetBotQuoteModule implements BotFunction {
	
	public static final String ON_TEXT_QUOTE = "!cita";
	public static final String ON_TEXT_QUOTE_NUMBER = "!numerocitazioni";
	public static final String ON_TEXT_QUOTE_LAST = "!ultimacitazione";
	public static final int DEFAULT_NUMBER_OF_QUOTES = 1;
	public static final int MAX_NUMBER_OF_QUOTES = 3;
  
  private String jdbcurl;
	
	public XetBotQuoteModule(xConfig config){
    this.jdbcurl = config.get(xConfig.CONFIG_JDBCURL);
  }
  
	public xCommandList onText(String text){
		if (text.startsWith(ON_TEXT_QUOTE)) {
			xCommandQuote command = new xCommandQuote(text);
			xQuote quote = new xQuote(jdbcurl);
			
			boolean author = command.hasAuthor(), keyword = command.hasKeyword();
			
			String number;
			int integerValueOfNumber;
			xCommandList result;
			
			if(command.hasNumber()) {
				number = command.getNumber();
				integerValueOfNumber = (new Integer(number)).intValue();
				
			}
			else {
				number = String.valueOf(XetBotQuoteModule.DEFAULT_NUMBER_OF_QUOTES);
				integerValueOfNumber = XetBotQuoteModule.DEFAULT_NUMBER_OF_QUOTES;
			}
			
			if(integerValueOfNumber > XetBotQuoteModule.MAX_NUMBER_OF_QUOTES) {
				number = String.valueOf(XetBotQuoteModule.MAX_NUMBER_OF_QUOTES);
				integerValueOfNumber = XetBotQuoteModule.DEFAULT_NUMBER_OF_QUOTES;
				result = new xCommandList(integerValueOfNumber+1);
				result.add(new xIRCCommand("/msg spiacente ma il numero massimo di citazioni e' "+
					XetBotQuoteModule.MAX_NUMBER_OF_QUOTES+" per volta. Resetto a "+
					XetBotQuoteModule.DEFAULT_NUMBER_OF_QUOTES));
			}
			else
				result = new xCommandList(integerValueOfNumber);
			
			if(author){
				if(keyword) {
					quote.getQuoteFromAuthorAndKey(command.getAuthor(), command.getKeyword(), number);
				}
				else {
					quote.getQuoteFromAuthor(command.getAuthor(), number);
				}
			}
			else if(keyword) {
				quote.getQuoteFromKey(command.getKeyword(), number);
			}
			else {
				quote.getNRandomQuote(number);
			}
			
      if(!quote.hasResults()) 
        result.add(new xIRCCommand("/msg Spiacente ma nessuno ha detto nulla di clamoroso al riguardo"));
      else{
			for(int i = 0; i < integerValueOfNumber; i++) 
				result.add(new xIRCCommand("/msg# "+ quote.getResult(i)));
      }
			
			return result;
		}
    else if(text.startsWith(ON_TEXT_QUOTE_NUMBER)){
			xQuote quote = new xQuote(jdbcurl);
      
			xCommandList result = new xCommandList(1);
			result.add(new xIRCCommand("/msg# Il mio database contiene al momento "+ quote.numberOfQuotes() +" citazioni"));
      
			return result;
    }
    else if(text.startsWith(ON_TEXT_QUOTE_LAST)){
			xQuote quote = new xQuote(jdbcurl);
			quote.getLastQuote();
			
			xCommandList result = new xCommandList(1);
      result.add(new xIRCCommand("/msg# "+quote.getResult(0)));
      
			return result;
    }
		
		return new xCommandList(0);
	}	
}