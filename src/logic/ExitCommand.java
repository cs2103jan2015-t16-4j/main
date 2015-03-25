package logic;

import java.util.ArrayList;

import common.Task;

import database.Database;

//@author A0119384Y
public class ExitCommand extends Command{
	public ExitCommand(){
		
	}
	
	public ArrayList<Task> execute(){
		Database.getInstance().saveTasksToFile();
		System.exit(0);
		return null;
	}
}
