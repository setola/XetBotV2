package xetbotv2.libs;

public class xCommandList {
	private xCommand[] list;
	private int length;
	private int currentIndex;
	
	public xCommandList(int maxIndex){
		length = maxIndex;
		list = new xCommand[length];
	}
	
	public boolean add(xCommand command){
		if(!isAddable()) return false;
		
		list[currentIndex] = command;
		currentIndex++;
		return true;
	}
	
	public boolean isAddable(){ return currentIndex < length; }
	
	public boolean exist(int index){ return currentIndex > index; }
	
	public boolean hasCommands(){ return list[0]!=null; }
	
	public int length() { return length; }
	
	public int currentIndex() { return currentIndex; }
	
	public xCommand getCommand(int index){ 
		if(exist(index)) return list[index]; 
		return null;
	}
}