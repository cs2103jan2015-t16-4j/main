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

	public ArrayList<Task> execute() {

		if (isDeleteTaskWithId()) {
			return executeWithId();
		} else if (isDeleteTaskWithName()) {
			return executeWithName();
		} else if (isDeleteTagWithName()){
			return executeWithTag();
		} else {
			//return invalid
			return null;
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

	private ArrayList<Task> executeWithId() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getId() == taskId) {
				resultTaskIndexes.add(index);
				break;
			}
		}
		assert (resultTaskIndexes.size() == 0 || resultTaskIndexes.size() == 1);
		if (resultTaskIndexes.size() == 1) {
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			taskList.remove(taskList.get(resultTaskIndexes.get(0)));
			return r;
			// TODO: return successful removal message
		} else {
			name = String.valueOf(taskId);
			return executeWithName();
		}
	}

	private ArrayList<Task> executeWithName() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			taskList.remove(taskList.get(resultTaskIndexes.get(0)));
			return r;
			// TODO: return successful removal message
		} else {
			// TODO: return unsuccessful removal message
			return null;
		}
	}

	private ArrayList<Task> executeWithTag() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			taskList.get(resultTaskIndexes.get(0)).removeTag(tag);
			return r;
			// TODO: return successful removal message
		} else {
			return null;
			// TODO: return unsuccessful removal message
		}
	}
}
