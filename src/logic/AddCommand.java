package logic;

import java.util.Calendar;

import common.Task;
import database.Database;

public class AddCommand extends Command{
	private Task task;
	public AddCommand(String name, Calendar startTime, Calendar endTime){
		assert name != null && name != "";
		this.task = new Task(name);
		if(startTime!=null){
			task.setStartTime(startTime);
		}
		if(endTime!=null){
			task.setEndTime(endTime);
		}
	}
	
	public Task getTask(){
		return task;
	}
	
	public void execute(){
		Database.getTaskList().add(task);
	}
	
}