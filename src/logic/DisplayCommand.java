package logic;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;

import common.Task;
import database.Database;

//@author A0119384Y
public class DisplayCommand extends Command {
	private static final String CONSTANT_HASHTAG = "#";
	// private static final String MESSAGE_DISPLAY_NOT_EMPTY =
	// "%-10d %-10s %-20s %-10s\n";
	// private static final String MESSAGE_DISPLAY_EMPTY =
	// "no result for keyword %s.\n";
	private static final String KEYWORD_FLOATING = "floating";
	private static final String KEYWORD_DONE = "done";
	private static final String KEYWORD_ALL = "all";

	private String keyword;
	private ArrayList<Task> taskList = Database.getInstance().getTaskList();
	private ArrayList<Task> resultTasklist = new ArrayList<Task>();

	public DisplayCommand() {

	}

	public DisplayCommand(String keyword) {
		assert keyword != null && keyword != "";
		this.keyword = keyword;
	}

	public ArrayList<Task> execute() {
		if (isDisplayUndone()) {
			return displayUndone();
		} else if (isDisplayAll()) {
			return displayAll();
		} else if (isDisplayFloating()) {
			return displayFloating();
		} else if (isDisplayDone()) {
			return displayDone();
		} else if (isDisplayWithTag()) {
			return displayWithTag();
		} else {
			return displayWithKeyword();
		}
	}

	private ArrayList<Task> displayAll() {
		return taskList;
	}

	private boolean isDisplayWithTag() {
		return keyword.startsWith(CONSTANT_HASHTAG);
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

	private boolean isDisplayAll() {
		return keyword.equalsIgnoreCase(KEYWORD_ALL);
	}

	private ArrayList<Task> displayWithKeyword() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().toLowerCase()
					.contains(keyword.toLowerCase())) {
				resultTasklist.add(taskList.get(index));
			}
		}
		return resultTasklist;
	}

	private ArrayList<Task> displayWithTag() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getTags().contains(keyword.toLowerCase())) {
				resultTasklist.add(taskList.get(index));
			}
		}

		return resultTasklist;
	}

	private ArrayList<Task> displayFloating() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).isFloating()) {
				resultTasklist.add(taskList.get(index));
			}
		}
		return resultTasklist;
	}

	private ArrayList<Task> displayDone() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).isDone()) {
				resultTasklist.add(taskList.get(index));
			}
		}
		return resultTasklist;
	}

	private ArrayList<Task> displayUndone() {
		for (int index = 0; index < taskList.size(); index++) {
			if (!taskList.get(index).isDone()) {
				resultTasklist.add(taskList.get(index));
			}
		}
		return resultTasklist;
		// displayResults(resultTaskIndexes, keyword);
	}

	// private void displayResults(ArrayList<Integer> resultTaskIndexes,
	// String keyword) {
	// if (resultTaskIndexes.size() > 0) {
	// System.out.printf("%-10s %-10s %-20s %-10s\n", "Task ID",
	// "Task Name", "End Time", "Tags");
	// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	//
	// for (int index = 0; index < resultTaskIndexes.size(); index++) {
	// System.out.printf(
	// MESSAGE_DISPLAY_NOT_EMPTY,
	// taskList.get(resultTaskIndexes.get(index)).getId(),
	// taskList.get(resultTaskIndexes.get(index)).getName(),
	// (taskList.get(resultTaskIndexes.get(index))
	// .getEndTime() != null) ? format.format(taskList
	// .get(resultTaskIndexes.get(index)).getEndTime()
	// .getTime()) : "none",
	// taskList.get(resultTaskIndexes.get(index)).getTags());
	// }
	// } else {
	// System.out.printf(MESSAGE_DISPLAY_EMPTY, keyword);
	// }
	// }
}
