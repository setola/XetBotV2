package xetbotv2.libs;

/**
	this interface contains the values rapresenting all irc command
	is usefull to test if a xIRCCommand is a certin type of command
	i.e. 	<code>
		if(xIRCCommand.test.getType() == xIRCCommandTypeCode.RESPONSE_TYPE_MSG_TO_USER) 
			System.out.println("this is a message for somebody");
		</code>
*/
public interface xIRCCommandTypeCode{
	/** a general o unrecognized irc command */
	public static final int RESPONSE_TYPE_RAW_COMMAND = 0;
	/** a message to a channel */
	public static final int RESPONSE_TYPE_MSG_TO_CHANNEL = 1;
	/** a message to a user */
	public static final int RESPONSE_TYPE_MSG_TO_USER = 2;
	/** an action command i.e. /me rulez done in a public channel*/
	public static final int RESPONSE_TYPE_ACTION_TO_CHANNEL = 3;
	/** an action command i.e. /me rulez sended to a private chat*/
	public static final int RESPONSE_TYPE_ACTION_TO_USER = 4;
	/** a CTCP command i.e. /ctcp nick version*/
	public static final int RESPONSE_TYPE_CTCP = 5;
	/** a WAIT\SLEEP\DELAY command i.e. /delay 2 s*/
	public static final int RESPONSE_TYPE_DELAY = 6;
	/** called when someone give the correct answer to a quiz 
	 * i.e. /quiz_correct_answer nick 2  where 2 is the time for answering
	 */
	public static final int RESPONSE_TYPE_QUIZ_CORRECT_ANSWER = 7;
	
	/** 
		may be usefull a method that checks if 
		current command is of given type or not
		@param type the type of command you wanna check
		@returns true only if current object describes a command of this type
	*/
	public boolean isType(int type);
}