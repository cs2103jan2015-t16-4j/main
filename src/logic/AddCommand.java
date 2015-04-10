package logic;

import java.util.ArrayList;
import java.util.Calendar;

import common.Task;
import database.Database;

//@author A0119384Y
public class AddCommand extends Command {
	private static final String KEYWORD_ANNUALLY = "annually";
	private static final String KEYWORD_MONTHLY = "monthly";
	private static final String KEYWORD_WEEKLY = "weekly";
	private static final String KEYWORD_DAILY = "daily";
	private Task task;
	private ArrayList<Task> tasks;

	public AddCommand(String name, Calendar startTime, Calendar endTime) {
		assert name != null && name != "";
		this.task = new Task(name);
		if (startTime != null) {
			task.setStartTime(startTime);
		}
		if (endTime != null) {
			task.setEndTime(endTime);
		}
	}

	public AddCommand(String name, String recurringCycle, Calendar startTime,
			Calendar endTime, Calendar endRecurringTimeCalendar) {
		tasks = new ArrayList<Task>();
		ArrayList<Integer> recurringId = new ArrayList<Integer>();
		while (!endTime.after(endRecurringTimeCalendar)) {
			Task t = new Task(name);
			if (startTime != null) {
				t.setStartTime(startTime);
			}
			recurringId.add(t.getId());
			t.setEndTime(endTime);
			t.setRecurringId(recurringId.get(0));
			tasks.add(t);
			if (recurringCycle.equals(KEYWORD_DAILY)) {
				if (startTime != null) {
					startTime = (Calendar) endTime.clone();
					startTime.add(Calendar.DATE, 1);
				}
				endTime = (Calendar) endTime.clone();
				endTime.add(Calendar.DATE, 1);
			} else if (recurringCycle.equals(KEYWORD_WEEKLY)) {
				if (startTime != null) {
					startTime = (Calendar) endTime.clone();
					startTime.add(Calendar.DATE, 7);
				}
				endTime = (Calendar) endTime.clone();
				endTime.add(Calendar.DATE, 7);
			} else if (recurringCycle.equals(KEYWORD_MONTHLY)) {
				if (startTime != null) {
					startTime = (Calendar) endTime.clone();
					startTime.add(Calendar.MONTH, 1);
				}
				endTime = (Calendar) endTime.clone();
				endTime.add(Calendar.MONTH, 1);
			} else if (recurringCycle.equals(KEYWORD_ANNUALLY)) {
				if (startTime != null) {
					startTime = (Calendar) endTime.clone();
					startTime.add(Calendar.YEAR, 1);
				}
				endTime = (Calendar) endTime.clone();
				endTime.add(Calendar.YEAR, 1);
			} else {
				break;
			}
		}
	}

	public Task getTask() {
		return task;
	}

	public ArrayList<Task> execute() {
		if (task != null) {
			Database db = Database.getInstance();
			db.getTaskList().add(task);
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(task);
			return r;
		} else if (tasks != null) {
			Database db = Database.getInstance();
			db.getTaskList().addAll(tasks);
			ArrayList<Task> r = new ArrayList<Task>();
			r.addAll(tasks);
			return r;
		} else {
			return null;
		}
	}

}