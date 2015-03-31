package logic;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import common.Task;
import database.Database;

//@author A0119384Y
public class DisplayCommand extends Command {
	private static final String CONSTANT_HASHTAG = "#";
	private static final String KEYWORD_FLOATING = "floating";
	private static final String KEYWORD_DONE = "done";
	private static final String KEYWORD_ALL = "all";

	private String keyword;
	private Calendar startTimeCalendar, endTimeCalendar;
	private ArrayList<Task> taskList = Database.getInstance().getTaskList();
	private ArrayList<Task> resultTasklist = new ArrayList<Task>();

	public DisplayCommand() {

	}

	public DisplayCommand(String keyword) {
		this.keyword = keyword;
	}

	public DisplayCommand(Calendar endTimeCalendar) {
		this.endTimeCalendar = endTimeCalendar;
	}

	public DisplayCommand(Calendar startTimeCalendar, Calendar endTimeCalendar) {
		this.startTimeCalendar = startTimeCalendar;
		this.endTimeCalendar = endTimeCalendar;
	}

	public ArrayList<Task> execute() {
		if (isDisplayWithEndTime()) {
			return displayWithEndTime();
		} else if (isDisplayWithTimePeriod()) {
			return displayWithTimePeriod();
		}else if (isDisplayUndone()) {
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

	private boolean isDisplayWithEndTime() {
		return startTimeCalendar == null && endTimeCalendar != null;
	}

	private boolean isDisplayWithTimePeriod() {
		return startTimeCalendar != null && endTimeCalendar != null;
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
	}

	private ArrayList<Task> displayWithEndTime() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getEndTime() != null) {
				if (endTimeCalendar.after(taskList.get(index).getEndTime())) {
					resultTasklist.add(taskList.get(index));
				}
			}
		}
		return resultTasklist;
	}

	private ArrayList<Task> displayWithTimePeriod() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getEndTime() != null) {
				if (startTimeCalendar.before(taskList.get(index).getEndTime())
						&& endTimeCalendar.after(taskList.get(index)
								.getEndTime())) {
					resultTasklist.add(taskList.get(index));
				}
			}
		}
		return resultTasklist;
	}
}
