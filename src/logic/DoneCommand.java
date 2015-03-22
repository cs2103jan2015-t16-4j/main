package logic;

import java.util.ArrayList;

import common.Task;

import database.Database;

//@author A0119384Y
public class DoneCommand extends Command {
	private final static String TAG_DONE = "#done";
	private String name;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();

	public DoneCommand(String name) {
		assert name != null && name != "";
		this.name = name;
	}

	public ArrayList<Task> execute() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			taskList.get(resultTaskIndexes.get(0)).addTag(TAG_DONE);
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
