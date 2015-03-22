package logic;

import java.util.ArrayList;
import java.util.Calendar;

import common.Task;
import database.Database;

//@author A0119384Y
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
	
	public ArrayList<Task> execute(){
		Database.getTaskList().add(task);
		ArrayList<Task> r = new ArrayList<Task>();
		r.add(task);
		return r;
	}
	
}