package logic;

import java.util.ArrayList;

import common.Task;

import database.Database;

public class ExitCommand extends Command{
	public ExitCommand(){
		
	}
	
	public ArrayList<Task> execute(){
		Database.saveTasksToFile();
		System.exit(0);
		return null;
	}
}
