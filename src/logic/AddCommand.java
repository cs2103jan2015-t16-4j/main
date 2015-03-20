package logic;

import java.util.Date;

import common.Task;
import database.Database;

public class AddCommand extends Command{
	private Task task;
	AddCommand(String name, Date startTime, Date endTime){
		//need to check name is not null.
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