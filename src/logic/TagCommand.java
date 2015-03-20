package logic;

import java.util.ArrayList;

import common.Task;

import database.Database;

public class TagCommand extends Command{
	private String name;
	private ArrayList<String> tags = new ArrayList<String>();
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();
	
	public TagCommand(String name, String[] tags){
		this.name = name;
		// TODO: check correct tag format
		for (String tag: tags){
			this.tags.add(tag.toLowerCase());
		}
	}
	
	public void execute(){
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			taskList.get(resultTaskIndexes.get(0)).addTags(tags);
			//TODO: return successful tag message
		} else {
			//TODO: return unsuccessful tag message
		}
	}
}
