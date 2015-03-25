package common;

import java.util.ArrayList;
import java.util.Calendar;

import database.Database;

//@author A0119384Y
public class Task {
	private int id;
	private String name;
	private Calendar startTime;
	private Calendar endTime;
	private ArrayList<String> tags = new ArrayList<String>();
	
	public Task(String name) {
		this.name = name;
		Database db = Database.getInstance();
		this.id = db.getId();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void addTag(String newTag) {
		tags.add(newTag);
	}

	public void addTags(ArrayList<String> newTags) {
		tags.addAll(newTags);
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void removeTag(String tag) {
		if (tags.contains(tag)) {
			tags.remove(tag);
		}
	}

	public void replaceTag(String oldTag, String newTag) {
		if (tags.contains(oldTag)) {
			tags.remove(oldTag);
			tags.add(newTag);
		}
	}

	public boolean isDone() {
		return tags.contains("#done");
	}

	public boolean isDue() {
		Calendar now = Calendar.getInstance();
		return now.before(endTime);
	}

	public boolean isFloating() {
		return (startTime == null) && (endTime == null);
	}

	public boolean isDeadline() {
		return (startTime == null) && (endTime != null);
	}

	public boolean isScheduled() {
		return (startTime != null) && (endTime != null);
	}

	public boolean isContainedTag(String tag) {
		return tags.contains(tag);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		
		Task other = (Task) obj;
		
		if (endTime == null) {
			if (other.endTime != null){
				return false;
			}
		} else if (!endTime.equals(other.endTime)){
			return false;
		}
		if (id != other.id){
			return false;
		}
		if (name == null) {
			if (other.name != null){
				return false;
			}
		} else if (!name.equals(other.name)){
			return false;
		}
		if (startTime == null) {
			if (other.startTime != null){
				return false;
			}
		} else if (!startTime.equals(other.startTime)){
			return false;
		}
		if (tags == null) {
			if (other.tags != null){
				return false;
			}
		} else if (!tags.equals(other.tags)){
			return false;
		}
		return true;
	}
}
