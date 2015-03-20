package logic;

import java.util.ArrayList;

import common.Task;
import database.Database;

public class DeleteCommand extends Command {
	private String name;
	private int taskId;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();
	
	DeleteCommand(String name) {
		// need to check name is not null.
		this.name = name;
	}

	DeleteCommand(int taskId) {
		this.taskId = taskId;
	}

	public void execute() {

		
		if (isWithId()) {
			executeWithId();
		} else {
			executeWithName();
		}
	}

	private boolean isWithId() {
		return name == null;
	}

	private void executeWithId() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getId() == taskId) {
				resultTaskIndexes.add(index);
				break;
			}
		}
		assert (resultTaskIndexes.size() == 0 || resultTaskIndexes.size() == 1);
		if (resultTaskIndexes.size() == 1) {
			taskList.remove(resultTaskIndexes.get(0));
			// return successful removal message
		} else {
			// return unsuccessful removal message
		}
	}

	private void executeWithName() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			taskList.remove(resultTaskIndexes.get(0));
			// return successful removal message
		} else {
			// return unsuccessful removal message
		}
	}
}
