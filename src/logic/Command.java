package logic;

public abstract class Command {
	static enum CommandType{
		ADD, DELETE, EDIT, DISPLAY, TAG, DONE, UNDO, SETPATH, INVALID, EXIT
	}
	
	public void execute(){
		
	}
}
