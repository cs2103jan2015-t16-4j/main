package common;

import java.util.ArrayList;
import java.util.Calendar;

public class Task {
	private int id;
	private String name;
	private Calendar startTime;
	private Calendar endTime;
	private ArrayList<String> tags = new ArrayList<String>();
	
	public Task(String name){
		this.name = name;
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<String> getTags(){
		return tags;
	}
	
	public Calendar getStartTime(){
		return startTime;
	}
	
	public Calendar getEndTime(){
		return endTime;
	}
	
	public void addTag(String newTag){
		tags.add(newTag);
	}
	
	public void addTags(ArrayList<String> newTags){
		tags.addAll(newTags);
	}
	
	public void setStartTime(Calendar startTime){
		this.startTime = startTime;
	}
	
	public void setEndTime(Calendar endTime){
		this.endTime = endTime;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean isDone(){
		return tags.contains("#done");
	}
	
	public boolean isDue(){
		Calendar now = Calendar.getInstance();
		return now.before(endTime);
	}
	
	public boolean isFloating(){
		return (startTime==null)&&(endTime==null);
	}
	
	public boolean isDeadline(){
		return (startTime==null)&&(endTime!=null);
	}
	
	public boolean isScheduled(){
		return (startTime!=null)&&(endTime!=null);
	}
}
