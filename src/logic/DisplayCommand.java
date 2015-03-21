package logic;

import java.util.ArrayList;

import common.Task;
import database.Database;

public class DisplayCommand extends Command {
	private static final String MESSAGE_DISPLAY_NOT_EMPTY = "%d. %s. %s. %s\n";
	private static final String MESSAGE_DISPLAY_EMPTY = "no result for keyword \"%s\".\n";

	private String keyword;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();

	public DisplayCommand() {

	}

	public DisplayCommand(String keyword) {
		this.keyword = keyword;
	}

	public void execute() {
		if (keyword == null) {
			displayWithNothing();
		} else {
			displayWithKeyword(keyword);
		}
	}

	private void displayWithNothing() {
		displayWithKeyword("");
	}

	private void displayWithKeyword(String keyword) {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().toLowerCase()
					.contains(keyword.toLowerCase())) {
				resultTaskIndexes.add(index);
			}
		}

		if (resultTaskIndexes.size() > 0) {
			System.out.print("Task ID. Task Name. IsDone. Tags\n");

			for (int index = 0; index < resultTaskIndexes.size(); index++) {
				System.out.printf(MESSAGE_DISPLAY_NOT_EMPTY,
						taskList.get(resultTaskIndexes.get(index)).getId(),
						taskList.get(resultTaskIndexes.get(index)).getName(),
						taskList.get(resultTaskIndexes.get(index)).isDone(),
						taskList.get(resultTaskIndexes.get(index)).getTags()
								.toString());
			}
		} else {
			System.out.printf(MESSAGE_DISPLAY_EMPTY, keyword);
		}
	}

}
