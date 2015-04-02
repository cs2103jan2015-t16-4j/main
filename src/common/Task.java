package common;

import java.util.ArrayList;
import java.util.Calendar;

import database.Database;

//@author A0119384Y
public class Task {
	private int id;
	private String name;
	private int recurringId = -1;
	private Calendar startTime, endTime;
	private ArrayList<String> tags = new ArrayList<String>();

	public Task(String name) {
		this.name = name;
		Database db = Database.getInstance();
		this.id = db.getId();
	}

	public Task(Task another) {
		this.name = another.name;
		this.id = another.id;
		if (another.startTime != null) {
			this.startTime = (Calendar) another.startTime.clone();
		}
		if (another.endTime != null) {
			this.endTime = (Calendar) another.endTime.clone();
		}
		this.tags = new ArrayList<String>(another.tags);
	}

	public void addTag(String newTag) {
		this.tags.add(newTag);
	}

	public void addTags(ArrayList<String> newTags) {
		this.tags.addAll(newTags);
	}

	// @author A0112734N
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Task other = (Task) obj;
		if (this.endTime == null) {
			if (other.endTime != null) {
				return false;
			}
		} else if (!this.endTime.equals(other.endTime)) {
			return false;
		}
		if (this.id != other.id) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.recurringId != other.recurringId) {
			return false;
		}
		if (this.startTime == null) {
			if (other.startTime != null) {
				return false;
			}
		} else if (!this.startTime.equals(other.startTime)) {
			return false;
		}
		if (this.tags == null) {
			if (other.tags != null) {
				return false;
			}
		} else if (!this.tags.equals(other.tags)) {
			return false;
		}
		return true;
	}

	// @author A0119384Y
	public Calendar getEndTime() {
		return this.endTime;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getRecurringId() {
		return this.recurringId;
	}

	public Calendar getStartTime() {
		return this.startTime;
	}

	public ArrayList<String> getTags() {
		return this.tags;
	}

	// @author A0112734N
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.endTime == null) ? 0 : this.endTime.hashCode());
		result = (prime * result) + this.id;
		result = (prime * result)
				+ ((this.name == null) ? 0 : this.name.hashCode());
		result = (prime * result) + this.recurringId;
		result = (prime * result)
				+ ((this.startTime == null) ? 0 : this.startTime.hashCode());
		result = (prime * result)
				+ ((this.tags == null) ? 0 : this.tags.hashCode());
		return result;
	}

	// @author A0119384Y
	public boolean isContainedTag(String tag) {
		return this.tags.contains(tag);
	}

	public boolean isDeadline() {
		return (this.startTime == null) && (this.endTime != null);
	}

	public boolean isDone() {
		return this.tags.contains("#done");
	}

	public boolean isDue() {
		Calendar now = Calendar.getInstance();
		return now.before(this.endTime);
	}

	public boolean isFloating() {
		return (this.startTime == null) && (this.endTime == null);
	}

	public boolean isRecurring() {
		return this.recurringId >= 0;
	}

	public boolean isScheduled() {
		return (this.startTime != null) && (this.endTime != null);
	}

	public void removeTag(String tag) {
		if (this.tags.contains(tag)) {
			this.tags.remove(tag);
		}
	}

	public void replaceTag(String oldTag, String newTag) {
		if (this.tags.contains(oldTag)) {
			this.tags.remove(oldTag);
			this.tags.add(newTag);
		}
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRecurringId(int precId) {
		this.recurringId = precId;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
}
