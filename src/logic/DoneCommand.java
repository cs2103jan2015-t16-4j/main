package logic;

import java.util.ArrayList;

import common.Task;

//@author A0119384Y
public class DoneCommand extends Command {
	private final static String TAG_DONE = "#done";
	private static final boolean BOOLEAN_NOT_RECURRING = false;
	private String name;
	int taskId  =-1;

	public DoneCommand(String name) {
		this.name = name;
	}
	
	public DoneCommand(int taskId) {
		this.taskId = taskId;
	}

	public ArrayList<Task> execute() {
		String[] s = {TAG_DONE};
		if(isDoneWithId()){
			Command c = new TagCommand(taskId, BOOLEAN_NOT_RECURRING, s);
			return c.execute();
		}else if(isDoneWithName()){
			Command c = new TagCommand(name, s);
			return c.execute();
		}else{
			return null;
		}

	}
	
	private boolean isDoneWithName() {
		return name!=null && taskId<0;
	}

	private boolean isDoneWithId() {
		return name==null && taskId>=0;
	}

}
