package logic;

import java.util.ArrayList;

import common.Task;
import database.Database;

//@author A0119384Y
public class TagCommand extends Command {
	private String name;
	private int taskId = -1;
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

	public TagCommand(int id, String[] tags) {
		this.taskId = id;
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
		if (isTagWithName()) {
			return tagWithName();
		} else if (isTagWithId()) {
			return tagWithId();
		} else {
			return null;
		}

	}

	private boolean isTagWithId() {
		return taskId >= 0;
	}

	private boolean isTagWithName() {
		return name != null;
	}

	private ArrayList<Task> tagWithName() {
		searchWithName();
		return addTag();
	}

	private ArrayList<Task> tagWithId() {
		searchWithId();
		return withIdToWithName(addTag());
	}

	private ArrayList<Task> addTag() {
		if (resultTaskIndexes.size() == 1) {
			taskList.get(resultTaskIndexes.get(0)).addTags(tags);
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			return r;
		} else {
			return null;
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

	private void searchWithName() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
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
