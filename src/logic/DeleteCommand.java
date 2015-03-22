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
		assert name != null;
		assert name != "";
		this.name = name;
	}

	public DeleteCommand(int taskId) {
		assert taskId > 0;
		this.taskId = taskId;
	}

	public DeleteCommand(String name, String tag) {
		assert name != null && name != "";
		assert tag != null && tag != "";
		this.name = name;
		this.tag = tag;
	}

	public void execute() {

		if (isDeleteTaskWithId()) {
			executeWithId(taskId);
		} else if (isDeleteTaskWithName()) {
			executeWithName(name);
		} else if (isDeleteTagWithName()){
			executeWithTag(name, tag);
		} else {
			//return invalid
		}
	}

	private boolean isDeleteTagWithName() {
		return name != null && tag == null;
	}

	private boolean isDeleteTaskWithName() {
		return tag == null;
	}

	private boolean isDeleteTaskWithId() {
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
			taskList.remove(taskList.get(resultTaskIndexes.get(0)));
			// TODO: return successful removal message
		} else {
			executeWithName(String.valueOf(taskId));
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
