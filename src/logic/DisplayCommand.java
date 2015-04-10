package logic;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import common.Task;
import database.Database;

//@author A0119384Y
public class DisplayCommand extends Command {
	private static final String CONSTANT_HASHTAG = "#";
	private static final String CONSTANT_ESCAPE = "'";

	private static final String KEYWORD_FLOATING = "floating";
	private static final String KEYWORD_DONE = "done";
	private static final String KEYWORD_ALL = "all";
	private static final String KEYWORD_RECURRING = "recurring";
	private static final String KEYWORD_DUE = "due";
	private static final String KEYWORD_SORT= "sort";
	
	private static final String[] CONSTANT_ALL = { CONSTANT_HASHTAG,
		KEYWORD_FLOATING, KEYWORD_DONE, KEYWORD_ALL, KEYWORD_RECURRING,
		KEYWORD_DUE, KEYWORD_SORT};
	

	private String keyword;
	private String[] keywords;
	private Calendar startTimeCalendar, endTimeCalendar;
	private ArrayList<Task> taskList = Database.getInstance().getTaskList();
	private ArrayList<Task> resultTasklist = new ArrayList<Task>();
	private boolean isDisplayWithTag = false;

	public DisplayCommand() {

	}

	public DisplayCommand(String keyword) {
		this.keyword = keyword;
	}
	
	public DisplayCommand(String[] keywords) {
		this.keywords = keywords;
	}
	
	public DisplayCommand(String tag, boolean isDisplayWithTag){
		this.keyword = tag;
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
		} else if (isDisplayRecurring()) {
			return displayRecurring();
		} else if (isDisplayDue()) {
			return displayDue();
		} else if (isDisplaySort()) {
			return displaySort();
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
		return isDisplayWithTag;
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

	private boolean isDisplayRecurring() {
		return keyword.equalsIgnoreCase(KEYWORD_RECURRING);
	}
	
	private boolean isDisplayDue() {
		return keyword.equalsIgnoreCase(KEYWORD_DUE);
	}
	
	private boolean isDisplaySort() {
		return keyword.equalsIgnoreCase(KEYWORD_SORT);
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
		keyword = recoverEscapeKeywords(keyword);
		for (int index = 0; index < taskList.size(); index++) {
			for (String keyword: keywords){
				if (taskList.get(index).getName().toLowerCase()
						.contains(keyword.toLowerCase())) {
					resultTasklist.add(taskList.get(index));
					break;
				}
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

	private ArrayList<Task> displayRecurring() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).isRecurring()) {
				resultTasklist.add(taskList.get(index));
			}
		}
		return resultTasklist;
	}

	private ArrayList<Task> displaySort() {
		Collections.sort(taskList);
		return taskList;
	}

	private ArrayList<Task> displayWithEndTime() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getEndTime() != null) {
				if (!endTimeCalendar.before(taskList.get(index).getEndTime())) {
					resultTasklist.add(taskList.get(index));
				}
			}
		}
		return resultTasklist;
	}

	private ArrayList<Task> displayDue() {
		endTimeCalendar = Calendar.getInstance();
		return displayWithEndTime();
	}

	private ArrayList<Task> displayWithTimePeriod() {
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getEndTime() != null) {
				if (!startTimeCalendar.after(taskList.get(index).getEndTime())
						&& !endTimeCalendar.before(taskList.get(index)
								.getEndTime())) {
					resultTasklist.add(taskList.get(index));
				}
			}
		}
		return resultTasklist;
	}
	
	private String recoverEscapeKeywords(String paras) {
		String newParas = new String(paras);
		for (String keyword : CONSTANT_ALL) {
			newParas = newParas.replaceAll(CONSTANT_ESCAPE + keyword, keyword);
		}
		return newParas;
	}
}
