package xetbotv2.modules;

import xetbotv2.libs.xCommandList;
/**
	describe basic irc function on wich bot is based
*/
public interface BotFunction{
	/**
		returns a string if text is something the bot cares about
		@param text the message for the bot
		@returns the bot's response
	*/
	public xCommandList onText(String text);
	
	/**
	TODO: gestione DCC, on<Tutti gli eventi di irc>
	*/
}