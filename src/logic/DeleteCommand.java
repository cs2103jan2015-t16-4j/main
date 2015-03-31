package logic;

import java.util.ArrayList;

import common.Task;
import database.Database;

//@author A0119384Y
public class DeleteCommand extends Command {
	private static final String KEYWORD_DEADLINE = "deadline";
	private static final String KEYWORD_END_TIME = "end time";
	private static final String KEYWORD_START_TIME = "start time";
	private static final String KEYWORD_HASHTAG = "#";
	private static final String[] KEYWORD_ATTRIBUTES = { KEYWORD_START_TIME,
			KEYWORD_END_TIME, KEYWORD_DEADLINE };

	private String name, tag, attribute;
	private int taskId = -1;
	private ArrayList<Task> taskList = Database.getInstance().getTaskList();
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

	public DeleteCommand(String name, String para) {
		if (isTag(para)) {
			this.name = name;
			this.tag = para;
		} else if (isAttribute(para)) {
			this.name = name;
			this.attribute = para.toLowerCase();
		} else {

		}
	}

	public DeleteCommand(int taskId, String para) {
		if (isTag(para)) {
			this.taskId = taskId;
			this.tag = para;
		} else if (isAttribute(para)) {
			this.taskId = taskId;
			this.attribute = para.toLowerCase();
		} else {

		}
	}

	public ArrayList<Task> execute() {

		if (isDeleteTaskWithId()) {
			return deleteTaskWithId();
		} else if (isDeleteTaskWithName()) {
			return deleteTaskWithName();
		} else if (isDeleteTagWithName()) {
			return deleteTagWithName();
		} else if (isDeleteTagWithId()) {
			return deleteTagWithId();
		} else if (isDeleteAttributeWithName()) {
			return deleteAttributeWithName();
		} else if (isDeleteAttributeWithId()) {
			return deleteAttributeWithId();
		} else {
			// return invalid
			return null;
		}
	}

	private boolean isAttribute(String para) {
		for (String i : KEYWORD_ATTRIBUTES) {
			if (para.toLowerCase() == i) {
				return true;
			}
		}
		return false;
	}

	private boolean isTag(String para) {
		return para.startsWith(KEYWORD_HASHTAG);
	}

	private boolean isDeleteTagWithName() {
		return taskId < 0 && name != null && tag != null && attribute == null;
	}

	private boolean isDeleteTagWithId() {
		return taskId >= 0 && name == null && tag != null && attribute == null;
	}

	private boolean isDeleteTaskWithName() {
		return taskId < 0 && name != null && tag == null && attribute == null;
	}

	private boolean isDeleteTaskWithId() {
		return taskId >= 0 && name == null && tag == null && attribute == null;
	}

	private boolean isDeleteAttributeWithName() {
		return taskId < 0 && name != null && tag == null && attribute != null;
	}

	private boolean isDeleteAttributeWithId() {
		return taskId >= 0 && name == null && tag == null && attribute != null;
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
		return withIdToWithName(deleteTag());
	}

	private ArrayList<Task> deleteTagWithName() {
		searchWithName();
		return deleteTag();
	}

	private ArrayList<Task> deleteAttributeWithId() {
		searchWithId();
		return withIdToWithName(deleteAttribute());
	}

	private ArrayList<Task> deleteAttributeWithName() {
		searchWithName();
		return deleteAttribute();
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

	private ArrayList<Task> deleteAttribute() {
		if (resultTaskIndexes.size() == 1) {
			ArrayList<Task> r = new ArrayList<Task>();
			if (attribute == KEYWORD_START_TIME) {
				taskList.get(resultTaskIndexes.get(0)).setStartTime(null);
			} else if (attribute == KEYWORD_END_TIME) {
				taskList.get(resultTaskIndexes.get(0)).setEndTime(null);
			} else if (attribute == KEYWORD_DEADLINE) {
				taskList.get(resultTaskIndexes.get(0)).setEndTime(null);
			} else {
				return null;
			}
			r.add(taskList.get(resultTaskIndexes.get(0)));
			return r;
		} else {
			return null;
		}
	}

	private ArrayList<Task> withIdToWithName(ArrayList<Task> result) {
		if (result == null) {
			name = String.valueOf(taskId);
			taskId = -1;
			return execute();
		} else {
			return result;
		}
	}
}
