package logic;

import database.Database;

public class SetpathCommand extends Command{
	private String path;
	public SetpathCommand(String path){
		this.path = path;
	}
	
	public void execute(){
		Database.setFilePath(path);
	}
}
