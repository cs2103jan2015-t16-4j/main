package logic;

import java.util.ArrayList;

import common.Task;
import database.Database;

public class DeleteCommand extends Command {
	private String name, tag;
	private int taskId;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();

	public DeleteCommand(String name) {
		assert(name == null);
		this.name = name;
	}

	public DeleteCommand(int taskId) {
		this.taskId = taskId;
	}

	public DeleteCommand(String name, String tag) {
		this.name = name;
		this.tag = tag;
	}

	public void execute() {

		if (name == null) {
			executeWithId(taskId);
		} else if (tag == null) {
			executeWithName(name);
		} else {
			executeWithTag(name, tag);
		}
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
			taskList.remove(taskList.remove(resultTaskIndexes.get(0)));
			// TODO: return successful removal message
		} else {
			// TODO: return unsuccessful removal message
		}
	}

	private void executeWithName(String name) {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			taskList.remove(taskList.get(resultTaskIndexes.get(0)));
			// TODO: return successful removal message
		} else {
			// TODO: return unsuccessful removal message
		}
	}

	private void executeWithTag(String name, String tag) {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			taskList.get(resultTaskIndexes.get(0)).removeTag(tag);
			// TODO: return successful removal message
		} else {
			// TODO: return unsuccessful removal message
		}
	}
}
