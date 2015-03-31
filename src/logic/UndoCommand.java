package logic;

import java.util.ArrayList;

import common.Task;
import database.Database;

public class UndoCommand extends Command{
	private ArrayList<Task> taskListBackup;
	public UndoCommand(ArrayList<Task> taskListBackup){
		this.taskListBackup = taskListBackup;
	}

	public ArrayList<Task> execute() {
		ArrayList<Task> taskList = Database.getInstance().getTaskList();
		ArrayList<Task> temp = new ArrayList<Task>();
		for(Task t: taskList){
			temp.add(new Task(t));
		}
		taskList.clear();
		for(Task t: taskListBackup){
			taskList.add(new Task(t));
		}
		taskListBackup.clear();
		for(Task t: temp){
			taskListBackup.add(new Task(t));
		}
		return taskList;
	}

}
