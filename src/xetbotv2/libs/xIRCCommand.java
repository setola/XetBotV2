package xetbotv2.libs;

/**
	describe all irc commands tokenizing the command line
	@version 0.2
*/
public class xIRCCommand extends xCommand implements xIRCCommandTypeCode{
	
	/** contains the current command type */
	private int type;
	
	/** 
		default builder, inizialize object parsing command param 
		@param command string form of the command line i.e. /mem lols on your funnyes
	*/
	public xIRCCommand(String command){ this.init(command); }
	
	/**
		inizializes variables, tokenizes command and retrives the current command's type
		@param command string form of the command line i.e. /mem lols on your funnyes
	*/
	protected void init(String command){
		super.init(command);
		this.retriveCommandTipe();
	}
	
	/**
		returns true only if given type is the same as this object's type
		@param type int value rapresenting IRC command type
		@returns true only if current command is of givent type
	*/
	public boolean isType(int type){ return this.type == type; }
	
	/** 
		returns type of command 
		@returns type of command 
	*/
	public int getType(){ return this.type; }
	
	/** 
		check what type of command is and sets this.type
	*/
	private void retriveCommandTipe(){
		if(this.getCommand().compareToIgnoreCase("/msg#") == 0) 
			this.type = xIRCCommand.RESPONSE_TYPE_MSG_TO_CHANNEL;
			
		else if(this.getCommand().compareToIgnoreCase("/msg") == 0) 
			this.type = xIRCCommand.RESPONSE_TYPE_MSG_TO_USER;
		
		else if(this.getCommand().compareToIgnoreCase("/action") == 0)
			this.type = xIRCCommand.RESPONSE_TYPE_ACTION_TO_USER;
		
		else if(this.getCommand().compareToIgnoreCase("/action#") == 0)
			this.type = xIRCCommand.RESPONSE_TYPE_ACTION_TO_CHANNEL;

		else if(this.getCommand().compareToIgnoreCase("/ctcp") == 0)
			this.type = xIRCCommand.RESPONSE_TYPE_CTCP;
		
		else if(this.getCommand().compareToIgnoreCase("/delay") == 0)
			this.type = xIRCCommand.RESPONSE_TYPE_DELAY;		
		
		else this.type = xIRCCommand.RESPONSE_TYPE_RAW_COMMAND;
	}
	
	
	
	
	
	
	/** just for test*/
	public static void main(String[] args){
		xIRCCommand asd = new xIRCCommand("/ctcp Ciccio server tribes 3");
		System.out.println(asd);
		System.out.println(asd.getCommand());
		System.out.println(asd.getParameter(1));
		System.out.println(asd.getType());
		
		System.out.println("----------------------------------");
		
		asd.replaceCommand("/msg# Dio porco");
		System.out.println(asd);
		System.out.println(asd.getCommand());
		System.out.println(asd.getParameter(1));
		System.out.println(asd.getType());
	}
}
