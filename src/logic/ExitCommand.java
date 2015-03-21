package logic;

import database.Database;

public class ExitCommand extends Command{
	public ExitCommand(){
		
	}
	
	public void execute(){
		Database.saveTasksToFile();
		System.exit(0);
	}
}
