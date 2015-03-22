package logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import common.Task;
import database.Database;

public class DisplayCommand extends Command {
	private static final String CONSTANT_HASHTAG = "#";
	private static final String MESSAGE_DISPLAY_NOT_EMPTY = "%-10d %-10s %-20s %-10s\n";
	private static final String MESSAGE_DISPLAY_EMPTY = "no result for keyword %s.\n";
	private static final String KEYWORD_FLOATING = "floating";
	private static final String KEYWORD_DONE = "done";

	private String keyword;
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();

	public DisplayCommand() {

	}

	public DisplayCommand(String keyword) {
		assert keyword != null && keyword !="";
		this.keyword = keyword;
	}

	public void execute() {
		if (isDisplayUndone()) {
			displayUndone();
		} else if (isDisplayFloating()) {
			displayFloating();
		} else if (isDisplayDone()) {
			displayDone();
		} else if (isDisplayWithTag()) {
			displayWithTag(keyword);
		} else {
			displayWithKeyword(keyword);
		}
	}

	private boolean isDisplayWithTag() {
		return keyword.substring(0, 0) == CONSTANT_HASHTAG;
	}

	private boolean isDisplayDone() {
		return keyword.equalsIgnoreCase(KEYWORD_DONE);
	}

	private boolean isDisplayFloating() {
		return keyword.equalsIgnoreCase(KEYWORD_FLOATING);
	}

	private boolean isDisplayUndone() {
		return keyword == null;
	}

	private void displayWithKeyword(String keyword) {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().toLowerCase()
					.contains(keyword.toLowerCase())) {
				resultTaskIndexes.add(index);
			}
		}
		displayResults(resultTaskIndexes, keyword);
	}

	private void displayWithTag(String keyword) {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getTags().contains(keyword.toLowerCase())) {
				resultTaskIndexes.add(index);
			}
		}

		displayResults(resultTaskIndexes, keyword);
	}

	private void displayFloating() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).isFloating()) {
				resultTaskIndexes.add(index);
			}
		}
		displayResults(resultTaskIndexes, keyword);
	}

	private void displayDone() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).isDone()) {
				resultTaskIndexes.add(index);
			}
		}
		displayResults(resultTaskIndexes, keyword);
	}

	private void displayUndone() {
		for (int index = 0; index < taskList.size(); index++) {
			if (!taskList.get(index).isDone()) {
				resultTaskIndexes.add(index);
			}
		}
		displayResults(resultTaskIndexes, keyword);
	}

	private void displayResults(ArrayList<Integer> resultTaskIndexes,
			String keyword) {
		if (resultTaskIndexes.size() > 0) {
			System.out.printf("%-10s %-10s %-20s %-10s\n", "Task ID",
					"Task Name", "End Time", "Tags");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			for (int index = 0; index < resultTaskIndexes.size(); index++) {
				System.out.printf(
						MESSAGE_DISPLAY_NOT_EMPTY,
						taskList.get(resultTaskIndexes.get(index)).getId(),
						taskList.get(resultTaskIndexes.get(index)).getName(),
						(taskList.get(resultTaskIndexes.get(index))
								.getEndTime() != null) ? format.format(taskList
								.get(resultTaskIndexes.get(index)).getEndTime()
								.getTime()) : "none",
						taskList.get(resultTaskIndexes.get(index)).getTags());
			}
		} else {
			System.out.printf(MESSAGE_DISPLAY_EMPTY, keyword);
		}
	}
}
