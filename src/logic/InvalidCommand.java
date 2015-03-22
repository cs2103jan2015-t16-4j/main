package logic;

import java.util.ArrayList;

import common.Task;

public class InvalidCommand extends Command{
	private String userCommand;
	public InvalidCommand(String userCommand){
		this.userCommand = userCommand;
	}
	
	public ArrayList<Task> execute(){
		System.out.println("Command invalid: "+userCommand);
		return null;
	}
}
