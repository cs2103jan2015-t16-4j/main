package logic;

import java.util.ArrayList;

import common.Task;

import database.Database;

public class EditCommand extends Command {
	String oldName, newName;
	ArrayList<Task> taskList = Database.getTaskList();
	ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();
	
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
			// return successful edit message
		} else {
			// return unsuccessful edit message
		}
	}
}
