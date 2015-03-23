package logic;

import java.util.ArrayList;

import common.Task;
import database.Database;

//@author A0119384Y
public class DeleteCommand extends Command {
	private String name, tag;
	private int taskId = -1;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();

	public DeleteCommand(String name) {
		assert name != null;
		assert name != "";
		this.name = name;
	}

	public DeleteCommand(int taskId) {
		assert taskId >= 0;
		this.taskId = taskId;
	}

	public DeleteCommand(String name, String tag) {
		assert name != null && name != "";
		assert tag != null && tag != "";
		this.name = name;
		this.tag = tag;
	}

	public DeleteCommand(int taskId, String tag) {
		assert taskId >= 0;
		assert tag != null && tag != "";
		this.taskId = taskId;
		this.tag = tag;
	}

	public ArrayList<Task> execute() {

		if (isDeleteTaskWithId()) {
			return deleteTaskWithId();
		} else if (isDeleteTaskWithName()) {
			return deleteTaskWithName();
		} else if (isDeleteTagWithName()) {
			return deleteTagWithName();
		} else if (isDeleteTagWithId()){
			return deleteTagWithId();
		} else {
			// return invalid
			return null;
		}
	}

	private boolean isDeleteTagWithName() {
		return taskId < 0 && name != null && tag != null;
	}

	private boolean isDeleteTagWithId() {
		return taskId >= 0 && name == null && tag != null;
	}

	private boolean isDeleteTaskWithName() {
		return taskId < 0 && name != null && tag == null;
	}

	private boolean isDeleteTaskWithId() {
		return taskId >= 0 && name == null && tag == null;
	}

	private ArrayList<Task> deleteTaskWithId() {
		searchWithId();
		return withIdToWithName(deleteTask());
	}

	private ArrayList<Task> deleteTaskWithName() {
		searchWithName();
		return deleteTask();
	}

	private ArrayList<Task> deleteTagWithId() {
		searchWithId();
		return deleteTag();
	}

	private ArrayList<Task> deleteTagWithName() {
		searchWithName();
		return deleteTag();
	}

	private void searchWithId() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getId() == taskId) {
				resultTaskIndexes.add(index);
				break;
			}
		}
	}

	private void searchWithName() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
	}

	private ArrayList<Task> deleteTask() {
		if (resultTaskIndexes.size() == 1) {
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			taskList.remove(taskList.get(resultTaskIndexes.get(0)));
			return r;
		} else {
			return null;
		}
	}

	private ArrayList<Task> deleteTag() {
		if (resultTaskIndexes.size() == 1) {
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			taskList.get(resultTaskIndexes.get(0)).removeTag(tag);
			return r;
		} else {
			return null;
		}
	}

	private ArrayList<Task> withIdToWithName(ArrayList<Task> result) {
		if(result==null){
			name = String.valueOf(taskId);
			taskId = -1;
			return execute();
		} else {
			return result;
		}
	}
}
