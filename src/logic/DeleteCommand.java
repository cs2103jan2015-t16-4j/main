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
	private boolean isDeleteRecurring = false;

	public DeleteCommand(String name, boolean isDeleteRecurring) {
		assert name != null;
		assert name != "";
		this.name = name;
		this.isDeleteRecurring = isDeleteRecurring;
	}

	public DeleteCommand(int taskId, boolean isDeleteRecurring) {
		assert taskId >= 0;
		this.taskId = taskId;
		this.isDeleteRecurring = isDeleteRecurring;
	}

	public DeleteCommand(String name, boolean isDeleteRecurring, String para) {
		if (isTag(para)) {
			this.name = name;
			this.tag = para;
			this.isDeleteRecurring = isDeleteRecurring;
		} else if (isAttribute(para)) {
			this.name = name;
			this.attribute = para.toLowerCase();
		} else {

		}
	}

	public DeleteCommand(int taskId, boolean isDeleteRecurring, String para) {
		if (isTag(para)) {
			this.taskId = taskId;
			this.tag = para;
			this.isDeleteRecurring = isDeleteRecurring;
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
		} else if (isDeleteRecurringWithId()) {
			return deleteRecurringWithId();
		} else if (isDeleteRecurringTagWithId()) {
			return deleteRecurringTagWithId();
		} else {
			// return invalid
			return null;
		}
	}

	private boolean isAttribute(String para) {
		for (String i : KEYWORD_ATTRIBUTES) {
			if (para.toLowerCase().equals(i)) {
				return true;
			}
		}
		return false;
	}

	private boolean isTag(String para) {
		return para.startsWith(KEYWORD_HASHTAG);
	}

	private boolean isDeleteTagWithName() {
		return taskId < 0 && name != null && tag != null && attribute == null && isDeleteRecurring==false;
	}

	private boolean isDeleteTagWithId() {
		return taskId >= 0 && name == null && tag != null && attribute == null && isDeleteRecurring==false;
	}

	private boolean isDeleteTaskWithName() {
		return taskId < 0 && name != null && tag == null && attribute == null && isDeleteRecurring==false;
	}

	private boolean isDeleteTaskWithId() {
		return taskId >= 0 && name == null && tag == null && attribute == null && isDeleteRecurring==false;
	}

	private boolean isDeleteAttributeWithName() {
		return taskId < 0 && name != null && tag == null && attribute != null && isDeleteRecurring==false;
	}

	private boolean isDeleteAttributeWithId() {
		return taskId >= 0 && name == null && tag == null && attribute != null && isDeleteRecurring==false;
	}

	private boolean isDeleteRecurringWithId() {
		return taskId >= 0 && name == null && tag == null && attribute == null && isDeleteRecurring==true;
	}

	private boolean isDeleteRecurringTagWithId() {
		return taskId >= 0 && name == null && tag != null && attribute == null && isDeleteRecurring==true;
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

	private ArrayList<Task> deleteRecurringTagWithId() {
		searchWithId();
		searchRecurring();
		return withIdToWithName(deleteRecurringTag());
	}

	private ArrayList<Task> deleteAttributeWithId() {
		searchWithId();
		return withIdToWithName(deleteAttribute());
	}

	private ArrayList<Task> deleteAttributeWithName() {
		searchWithName();
		return deleteAttribute();
	}

	private ArrayList<Task> deleteRecurringWithId() {
		searchWithId();
		return withIdToWithName(deleteRecurring());
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
			return deleteRecurringTag();
		} else {
			return null;
		}
	}

	private ArrayList<Task> deleteRecurringTag() {
		ArrayList<Task> r = new ArrayList<Task>();
		for(int index = 0; index < resultTaskIndexes.size(); index++){
			r.add(taskList.get(resultTaskIndexes.get(index)));
			taskList.get(resultTaskIndexes.get(index)).removeTag(tag);
		}
		return r;
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

	private ArrayList<Task> deleteRecurring() {
		if (resultTaskIndexes.size() == 1) {
			ArrayList<Task> r = new ArrayList<Task>();
			int index = 0;
			if (taskList.get(resultTaskIndexes.get(0)).isRecurring()) {
				int recurringId = taskList.get(resultTaskIndexes.get(0)).getRecurringId();
				while(index < taskList.size()){
					if (taskList.get(index).getRecurringId() == recurringId){
						r.add(taskList.get(index));
						taskList.remove(index);
					}
					else{
						index += 1;
					}
				}
			} else {
				return null;
			}
			return r;
		} else {
			return null;
		}
	}
	
	private void searchRecurring() {
		if (resultTaskIndexes.size() == 1) {
			if(taskList.get(resultTaskIndexes.get(0)).isRecurring()){
				int recurringId = taskList.get(resultTaskIndexes.get(0)).getRecurringId();
				resultTaskIndexes.clear();
				for (int index = 0; index < taskList.size(); index++) {
					if (taskList.get(index).getRecurringId() == recurringId) {
						resultTaskIndexes.add(index);
					}
				}
			}
			else {
				throw new Error("Selected task is not recurring task.");
			}
		} else {
			throw new Error("More than 1 result.");
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
