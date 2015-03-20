package logic;

import java.util.ArrayList;

import common.Task;
import database.Database;

public class DeleteCommand extends Command {
	private String name;
	private int taskId;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();
	
	public DeleteCommand(String name) {
		//TODO: check name is not null.
		this.name = name;
	}

	public DeleteCommand(int taskId) {
		this.taskId = taskId;
	}

	public void execute() {

		
		if (isWithId()) {
			executeWithId(taskId);
		} else {
			executeWithName(name);
		}
	}

	private boolean isWithId() {
		return name == null;
	}

	private void executeWithId(int taskId) {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getId() == taskId) {
				resultTaskIndexes.add(index);
				break;
			}
		}
		assert (resultTaskIndexes.size() == 0 || resultTaskIndexes.size() == 1);
		if (resultTaskIndexes.size() == 1) {
			taskList.remove(resultTaskIndexes.get(0));
			//TODO: return successful removal message
		} else {
			//TODO: return unsuccessful removal message
		}
	}

	private void executeWithName(String name) {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			taskList.remove(resultTaskIndexes.get(0));
			//TODO: return successful removal message
		} else {
			//TODO: return unsuccessful removal message
		}
	}
}
