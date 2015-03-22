package logic;

import java.util.ArrayList;

import common.Task;

import database.Database;

public class SetpathCommand extends Command{
	private String path;
	public SetpathCommand(String path){
		this.path = path;
	}
	
	public ArrayList<Task> execute(){
		Database.setFilePath(path);
		return null;
	}
}
