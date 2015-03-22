package logic;

import java.util.ArrayList;

import common.Task;

import database.Database;

//@author A0119384Y
public class TagCommand extends Command{
	private String name;
	private ArrayList<String> tags = new ArrayList<String>();
	private ArrayList<Task> taskList = Database.getTaskList();
	private ArrayList<Integer> resultTaskIndexes = new ArrayList<Integer>();
	
	public TagCommand(String name, String[] tags){
		this.name = name;
		for (String tag: tags){
			tag = formatTag(tag);
			this.tags.add(tag.toLowerCase());
		}
	}

	private String formatTag(String tag) {
		if(!tag.startsWith("#")){
			tag = "#" + tag;
		}
		return tag;
	}
	
	public ArrayList<Task> execute(){
		for (int index = 0; index < taskList.size(); index++) {
			if (taskList.get(index).getName().equalsIgnoreCase(name)) {
				resultTaskIndexes.add(index);
			}
		}
		if (resultTaskIndexes.size() == 1) {
			taskList.get(resultTaskIndexes.get(0)).addTags(tags);
			ArrayList<Task> r = new ArrayList<Task>();
			r.add(taskList.get(resultTaskIndexes.get(0)));
			return r;
			//TODO: return successful tag message
		} else {
			//TODO: return unsuccessful tag message
			return null;
		}
	}
}
