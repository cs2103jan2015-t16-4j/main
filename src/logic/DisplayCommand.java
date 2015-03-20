package logic;

import java.util.ArrayList;

import common.Task;
import database.Database;

public class DisplayCommand extends Command {
	private static final String MESSAGE_DISPLAY_NOT_EMPTY = "%d. %s\n";
	private static final String MESSAGE_DISPLAY_EMPTY = "no result for keyword \"%s\".\n";

	private String keyword;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();

	DisplayCommand() {

	}

	DisplayCommand(String keyword) {
		this.keyword = keyword;
	}

	public void execute() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().contains(keyword)) {
				resultTaskIndexes.add(index);
			}
		}

		if (resultTaskIndexes.size() > 0) {
			System.out.print("Task ID. Task Name\n");
			
			for (int index = 0; index < resultTaskIndexes.size(); index++) {
				System.out.printf(MESSAGE_DISPLAY_NOT_EMPTY,
						taskList.get(resultTaskIndexes.get(index)).getId(),
						taskList.get(resultTaskIndexes.get(index)).getName());
			}
		} else {
			System.out.printf(MESSAGE_DISPLAY_EMPTY, keyword);
		}
	}

}
