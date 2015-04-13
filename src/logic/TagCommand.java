package logic;

import java.util.ArrayList;

import common.Task;
import database.Database;

//@author A0119384Y
public class TagCommand extends Command {
	private String name;
	private int taskId = -1;
	private boolean isTagRecurring = false;
	private ArrayList<String> tags = new ArrayList<String>();
	private ArrayList<Task> taskList = Database.getInstance().getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();

	public TagCommand(String name, String[] tags) {
		this.name = name;
		for (String tag : tags) {
			tag = formatTag(tag);
			this.tags.add(tag.toLowerCase());
		}
	}

	public TagCommand(int id, boolean isTagRecurring, String[] tags) {
		this.taskId = id;
		this.isTagRecurring = isTagRecurring;
		for (String tag : tags) {
			tag = formatTag(tag);
			this.tags.add(tag.toLowerCase());
		}
		
	}

	private String formatTag(String tag) {
		if (!tag.startsWith("#")) {
			tag = "#" + tag;
		}
		return tag;
	}

	public ArrayList<Task> execute() {
		if (isTagRecurringWithName()) {
			return tagRecurringWithName();
		} else if (isTagRecurringWithId()) {
			return tagRecurringWithId();
		} else if (isTagWithName()) {
			return tagWithName();
		} else if (isTagWithId()) {
			return tagWithId();
		} else {
			return null;
		}

	}

	private boolean isTagRecurringWithId() {
		return taskId >= 0 && isTagRecurring == true;
	}

	private boolean isTagRecurringWithName() {
		return name != null && isTagRecurring == true;
	}

	private boolean isTagWithId() {
		return taskId >= 0 && isTagRecurring == false;
	}

	private boolean isTagWithName() {
		return name != null && isTagRecurring == false;
	}

	private ArrayList<Task> tagWithName() {
		searchWithName();
		return addTag();
	}

	private ArrayList<Task> tagWithId() {
		searchWithId();
		return withIdToWithName(addTag());
	}

	private ArrayList<Task> tagRecurringWithId() {
		searchWithId();
		searchRecurring();
		return withIdToWithName(addTagRecurring());
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

	private ArrayList<Task> tagRecurringWithName() {
		searchWithName();
		searchRecurring();
		return addTagRecurring();
	}

	private ArrayList<Task> addTag() {
		if (resultTaskIndexes.size() == 1) {
			return addTagRecurring();
		} else {
			return null;
		}
	}

	private ArrayList<Task> addTagRecurring() {
		ArrayList<Task> r = new ArrayList<Task>();
		for(int index = 0; index < resultTaskIndexes.size(); index++){
			taskList.get(resultTaskIndexes.get(index)).addTags(tags);
			r.add(taskList.get(resultTaskIndexes.get(index)));
		}
		return r;
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
	
	/*
	 * Input number may be not a task id but a task name. After failure of
	 * executing with id, this class will execute again treating input number as
	 * a task name.
	 */
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
