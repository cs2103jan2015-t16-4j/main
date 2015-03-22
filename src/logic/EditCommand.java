package logic;

import java.util.ArrayList;
import java.util.Calendar;

import common.Task;
import database.Database;

public class EditCommand extends Command {
	private String name, newName, oldTag, newTag;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();
	private Calendar newDeadline;

	public EditCommand(String oldName, String newName) {
		assert oldName != null && oldName !="";
		assert newName != null && newName !="";
		
		this.name = oldName;
		this.newName = newName;
	}

	public EditCommand(String name, Calendar newDeadline) {
		assert name != null && name !="";
		assert newDeadline != null;
		
		this.name = name;
		this.newDeadline = newDeadline;
	}

	public EditCommand(String name, String oldTag, String newTag) {
		assert name != null && name !="";
		assert oldTag != null && oldTag !="";
		assert newTag != null && newTag !="";
		
		this.name = name;
		this.oldTag = oldTag;
		this.newTag = newTag;
	}

	public ArrayList<Task> execute() {
		assert (name != null);
		if (isEditName()) {
			return editName(name, newName);
		} else if (isEditDeadline()) {
			return editDeadline(name, newDeadline);
		} else if (isEditTag()) {
			return editTag(name, oldTag, newTag);
		} else {
			// return invalid
			return null;
		}

	}

	private boolean isEditTag() {
		return newTag != null;
	}

	private boolean isEditDeadline() {
		return newDeadline != null;
	}

	private boolean isEditName() {
		return newName != null;
	}

	private ArrayList<Task> editTag(String name, String oldTag, String newTag) {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			if (taskList.get(resultTaskIndexes.get(0)).isContainedTag(oldTag)) {
				taskList.get(resultTaskIndexes.get(0)).replaceTag(oldTag,
						newTag);
				ArrayList<Task> r = new ArrayList<Task>();
				r.add(taskList.get(resultTaskIndexes.get(0)));
				return r;
				// TODO: return successful edit message
			} else {
				// TODO: return unsuccessful edit message
				return null;
			}
		} else {
			// TODO: return unsuccessful edit messager
			return null;
		}
	}

	private ArrayList<Task> editDeadline(String name, Calendar newDeadline) {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			// TODO: return successful edit message;
			taskList.get(resultTaskIndexes.get(0)).setEndTime(newDeadline);
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			return r;
		} else {
			// TODO: return unsuccessful edit message
			return null;
		}
	}

	private ArrayList<Task> editName(String oldName, String newName) {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(oldName)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			taskList.get(resultTaskIndexes.get(0)).setName(newName);
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			return r;
			// TODO: return successful edit message
		} else {
			// TODO: return unsuccessful edit message
			return null;
		}
	}
}
