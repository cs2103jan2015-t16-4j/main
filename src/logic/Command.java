package logic;

public abstract class Command {
	public static enum CommandType{
		ADD, DELETE, EDIT, DISPLAY, TAG, DONE, UNDO, SETPATH, INVALID, EXIT
	}
	
	public abstract void execute();
}
