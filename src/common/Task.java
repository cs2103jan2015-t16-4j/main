package common;

import java.util.ArrayList;
import java.util.Date;

public class Task {
	private int id;
	private String name;
	private Date startTime;
	private Date endTime;
	private ArrayList<String> tags;
	
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
	
	public Date getStartTime(){
		return startTime;
	}
	
	public Date getEndTime(){
		return endTime;
	}
	
	public void addTag(String newTag){
		tags.add(newTag);
	}
	
	public void addTags(ArrayList<String> newTags){
		tags.addAll(newTags);
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	
	public boolean isDone(){
		return tags.contains("done");
	}
	
	public boolean isDue(){
		Date now = new Date();
		return now.before(endTime);
	}
	
	public boolean isFloating(){
		return (startTime==null)&&(endTime==null);
	}
	
	public boolean isDeadline(){
		return (startTime==null)&&(endTime!=null);
	}
	
	public boolean isTentative(){
		return (startTime!=null)&&(endTime!=null);
	}
}
