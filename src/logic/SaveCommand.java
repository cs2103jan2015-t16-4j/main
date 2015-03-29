package logic;

import java.util.ArrayList;

import common.Task;

import database.Database;

//@author A0101045
public class SaveCommand extends Command{
	public SaveCommand(){
		
	}
	
	public ArrayList<Task> execute(){
		Database.getInstance().saveTasksToFile();
		return null;
	}
}
