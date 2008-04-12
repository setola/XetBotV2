package xetbotv2.modules;

/**
	describe basic irc function on wich bot is based
	DA EDITARE
*/
public interface BotFunctionOnJoin{
	/**
		returns a string if text is something the bot cares about
		@param text the message for the bot
		@returns the bot's response
	*/
	public String onJoin(String text);
	
	/**
	TODO: gestione DCC, on<Tutti gli eventi di irc>
	*/
}