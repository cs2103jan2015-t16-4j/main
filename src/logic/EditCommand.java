package logic;

import java.util.ArrayList;
import java.util.Calendar;

import common.Task;
import database.Database;

//@author A0119384Y
public class EditCommand extends Command {
	private String name, newName, oldTag, newTag;
	int taskId = -1;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();
	private Calendar newDeadline, newStartTime, newEndTime;

	public EditCommand(String oldName, String newName) {
		this.name = oldName;
		this.newName = newName;
	}

	public EditCommand(String name, Calendar newDeadline) {
		this.name = name;
		this.newDeadline = newDeadline;
	}

	public EditCommand(String name, Calendar newStartTime, Calendar newEndTime) {
		this.name = name;
		this.newStartTime = newStartTime;
		this.newEndTime = newEndTime;
	}

	public EditCommand(String name, String oldTag, String newTag) {
		this.name = name;
		this.oldTag = oldTag;
		this.newTag = newTag;
	}

	public EditCommand(int taskId, String newName) {
		this.taskId = taskId;
		this.newName = newName;
	}

	public EditCommand(int taskId, Calendar newDeadline) {
		this.taskId = taskId;
		this.newDeadline = newDeadline;
	}

	public EditCommand(int taskId, Calendar newStartTime, Calendar newEndTime) {
		this.taskId = taskId;
		this.newStartTime = newStartTime;
		this.newEndTime = newEndTime;
	}

	public EditCommand(int taskId, String oldTag, String newTag) {
		this.taskId = taskId;
		this.oldTag = oldTag;
		this.newTag = newTag;
	}

	public ArrayList<Task> execute() {
		assert (name != null);
		if (isEditNameWithName()) {
			return editNameWithName();
		} else if (isEditDeadlineWithName()) {
			return editDeadlineWithName();
		} else if (isEditTagWithName()) {
			return editTagWithName();
		} else if (isEditStartTimeWithName()) {
			return editStartTimeWithName();
		} else if (isEditEndTimeWithName()) {
			return editEndTimeWithName();
		} else if (isEditNameWithId()) {
			return editNameWithId();
		} else if (isEditDeadlineWithId()) {
			return editDeadlineWithId();
		} else if (isEditTagWithId()) {
			return editTagWithId();
		} else if (isEditStartTimeWithId()) {
			return editStartTimeWithId();
		} else if (isEditEndTimeWithId()) {
			return editEndTimeWithId();
		} else {
			return null;
		}

	}

	private boolean isEditEndTimeWithName() {
		return name != null && taskId < 0 && newName == null && oldTag == null
				&& newTag == null && newDeadline == null
				&& newStartTime == null && newEndTime != null;
	}

	private boolean isEditStartTimeWithName() {
		return name != null && taskId < 0 && newName == null && oldTag == null
				&& newTag == null && newDeadline == null
				&& newStartTime != null && newEndTime == null;
	}

	private boolean isEditTagWithName() {
		return name != null && taskId < 0 && newName == null && oldTag != null
				&& newTag != null && newDeadline == null
				&& newStartTime == null && newEndTime == null;
	}

	private boolean isEditDeadlineWithName() {
		return name != null && taskId < 0 && newName == null && oldTag == null
				&& newTag == null && newDeadline != null
				&& newStartTime == null && newEndTime == null;
	}

	private boolean isEditNameWithName() {
		return name != null && taskId < 0 && newName != null && oldTag == null
				&& newTag == null && newDeadline == null
				&& newStartTime == null && newEndTime == null;
	}

	private boolean isEditEndTimeWithId() {
		return name == null && taskId >= 0 && newName == null && oldTag == null
				&& newTag == null && newDeadline == null
				&& newStartTime == null && newEndTime != null;
	}

	private boolean isEditStartTimeWithId() {
		return name == null && taskId >= 0 && newName == null && oldTag == null
				&& newTag == null && newDeadline == null
				&& newStartTime != null && newEndTime == null;
	}

	private boolean isEditTagWithId() {
		return name == null && taskId >= 0 && newName == null && oldTag != null
				&& newTag != null && newDeadline == null
				&& newStartTime == null && newEndTime == null;
	}

	private boolean isEditDeadlineWithId() {
		return name == null && taskId >= 0 && newName == null && oldTag == null
				&& newTag == null && newDeadline != null
				&& newStartTime == null && newEndTime == null;
	}

	private boolean isEditNameWithId() {
		return name == null && taskId >= 0 && newName != null && oldTag == null
				&& newTag == null && newDeadline == null
				&& newStartTime == null && newEndTime == null;
	}

	private ArrayList<Task> editEndTimeWithId() {
		searchWithId();
		return withIdToWithName(editEndTime());
	}

	private ArrayList<Task> editStartTimeWithId() {
		searchWithId();
		return withIdToWithName(editStartTime());
	}

	private ArrayList<Task> editTagWithId() {
		searchWithId();
		return withIdToWithName(editTag());
	}

	private ArrayList<Task> editDeadlineWithId() {
		searchWithId();
		return withIdToWithName(editDeadline());
	}

	private ArrayList<Task> editNameWithId() {
		searchWithId();
		return withIdToWithName(editName());
	}

	private ArrayList<Task> editEndTimeWithName() {
		searchWithName();
		return editEndTime();
	}

	private ArrayList<Task> editStartTimeWithName() {
		searchWithName();
		return editStartTime();
	}

	private ArrayList<Task> editTagWithName() {
		searchWithName();
		return editTag();
	}

	private ArrayList<Task> editDeadlineWithName() {
		searchWithName();
		return editDeadline();
	}

	private ArrayList<Task> editNameWithName() {
		searchWithName();
		return editName();
	}

	private ArrayList<Task> editDeadline() {
		if (resultTaskIndexes.size() == 1) {
			taskList.get(resultTaskIndexes.get(0)).setEndTime(newDeadline);
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			return r;
		} else {
			return null;
		}
	}

	private ArrayList<Task> editName() {
		if (resultTaskIndexes.size() == 1) {
			taskList.get(resultTaskIndexes.get(0)).setName(newName);
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			return r;
		} else {
			return null;
		}
	}

	private ArrayList<Task> editTag() {
		if (resultTaskIndexes.size() == 1) {
			if (taskList.get(resultTaskIndexes.get(0)).isContainedTag(oldTag)) {
				taskList.get(resultTaskIndexes.get(0)).replaceTag(oldTag,
						newTag);
				ArrayList<Task> r = new ArrayList<Task>();
				r.add(taskList.get(resultTaskIndexes.get(0)));
				return r;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private ArrayList<Task> editEndTime() {
		if (resultTaskIndexes.size() == 1) {
			// TODO: return successful edit message;
			taskList.get(resultTaskIndexes.get(0)).setEndTime(newEndTime);
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			return r;
		} else {
			// TODO: return unsuccessful edit message
			return null;
		}
	}

	private ArrayList<Task> editStartTime() {
		if (resultTaskIndexes.size() == 1) {
			// TODO: return successful edit message;
			taskList.get(resultTaskIndexes.get(0)).setStartTime(newStartTime);
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			return r;
		} else {
			// TODO: return unsuccessful edit message
			return null;
		}
	}

	private void searchWithName() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
	}
	
	private void searchWithId() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getId() == taskId) {
				resultTaskIndexes.add(index);
				break;
			}
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
