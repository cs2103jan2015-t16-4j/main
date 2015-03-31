package logic;

import java.util.ArrayList;
import java.util.Calendar;

import common.Task;
import database.Database;

//@author A0119384Y
public class AddCommand extends Command {
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
		int precId = -1;
		while (!endTime.after(endRecurringTimeCalendar)) {
			Task t = new Task(name);
			if (startTime != null) {
				t.setStartTime(startTime);
			}
			t.setEndTime(endTime);
			t.setPrecId(precId);
			tasks.add(t);
			precId = t.getId();
			if (recurringCycle.equals("daily")) {
				if (startTime != null) {
					startTime = (Calendar) endTime.clone();
					startTime.add(Calendar.DATE, 1);
				}
				endTime = (Calendar) endTime.clone();
				endTime.add(Calendar.DATE, 1);
			} else if (recurringCycle.equals("weekly")) {
				if (startTime != null) {
					startTime = (Calendar) endTime.clone();
					startTime.add(Calendar.DATE, 7);
				}
				endTime = (Calendar) endTime.clone();
				endTime.add(Calendar.DATE, 7);
			} else if (recurringCycle.equals("monthly")) {
				if (startTime != null) {
					startTime = (Calendar) endTime.clone();
					startTime.add(Calendar.MONTH, 1);
				}
				endTime = (Calendar) endTime.clone();
				endTime.add(Calendar.MONTH, 1);
			} else if (recurringCycle.equals("annually")) {
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