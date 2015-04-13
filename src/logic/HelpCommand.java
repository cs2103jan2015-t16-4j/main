package logic;

import java.util.ArrayList;

import common.Task;

public class HelpCommand extends Command {
	private String commandType;

	public HelpCommand() {

	}

	public HelpCommand(String commandType) {
		this.commandType = commandType;
	}

	// TODO
	public ArrayList<Task> execute() {
		return null;
	}
}