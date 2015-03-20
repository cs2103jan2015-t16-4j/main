package logic;

import java.util.ArrayList;

import common.Task;

import database.Database;

public class EditCommand extends Command {
	private String oldName, newName;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();
	
	EditCommand(String oldName, String newName) {
		this.oldName = oldName;
		this.newName = newName;
	}

	public void execute() {

		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(oldName)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			taskList.get(resultTaskIndexes.get(0)).setName(newName);
			//TODO: return successful edit message
		} else {
			//TODO: return unsuccessful edit message
		}
	}
}
