package parser;

import logic.*;
import logic.Command.CommandType;

public class Parser {
	private String commandString;
	
	public Parser(String cmd){
		this.commandString = cmd;
	}
	
	public void parseInput(){
		CommandType commandType = this.determineCommandType(getFirstWord(commandString));
		Command cmd = this.parseInput(commandType, removeFirstWord(commandString));
		cmd.execute();
	}
	
	private CommandType determineCommandType(String commandTypeString) {
		if (commandTypeString.equalsIgnoreCase("add")) {
			return CommandType.ADD;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return CommandType.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("edit")) {
			return CommandType.EDIT;
		} else if (commandTypeString.equalsIgnoreCase("display")) {
			return CommandType.DISPLAY;
		} else if (commandTypeString.equalsIgnoreCase("tag")) {
			return CommandType.TAG;
		} else if (commandTypeString.equalsIgnoreCase("done")) {
			return CommandType.DONE;
		} else if (commandTypeString.equalsIgnoreCase("undo")) {
			return CommandType.UNDO;
		} else if (commandTypeString.equalsIgnoreCase("setpath")) {
			return CommandType.SETPATH;
		} else if (commandTypeString.equalsIgnoreCase("help")) {
			return CommandType.HELP;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
			return CommandType.EXIT;
		} else {
			return CommandType.INVALID;
		}
	}
	
	private Command parseInput(CommandType commandType, String paras){
		switch (commandType) {
		case ADD:
			return addParser(paras);
		case DELETE:
			return deleteParser(paras);
		case EDIT:
			return editParser(paras);
		case DISPLAY:
			return displayParser(paras);
		case TAG:
			return tagParser(paras);
		case DONE:
			return doneParser(paras);
		case UNDO:
			return undoParser(paras);
		case SETPATH:
			return setpathParser(paras);
		case HELP:
			return helpParser(paras);
		case INVALID:
			return invalidParser(paras);
		case EXIT:
			return exitParser(paras);
		default:
			throw new Error("Unrecognized command type");
		}
	}


	private Command addParser(String paras) {
		return new AddCommand(paras, null, null);
	}

	private Command deleteParser(String paras) {
		if (isNumeric(paras)){
			return new DeleteCommand(Integer.parseInt(paras));
		} else {
			return new DeleteCommand(paras);
		}
	}

	private Command editParser(String paras) {
		String oldName = paras.split(",")[0];
		String newName = paras.split(",")[1];
		return new EditCommand(oldName, newName);
	}

	private Command displayParser(String paras) {
		if (paras.length() == 0){
			return new DisplayCommand();
		} else {
			return new DisplayCommand(paras);
		}
	}

	private Command tagParser(String paras) {
		String name = paras.split(" ")[0];
		String[] tags = paras.split(" ",2)[1].split(" ");
		return new TagCommand(name, tags);
	}

	private Command doneParser(String paras) {
		return new DoneCommand(paras);
	}

	private Command undoParser(String paras) {
		//TODO: to be completed
		return null;
	}

	private Command setpathParser(String paras) {
		//TODO: check correct path
		return new SetpathCommand(paras);
	}

	private Command helpParser(String paras) {
//		if (paras.length() == 0){
//			return new HelpCommand();
//		} else {
//			return new HelpCommand(paras);
//		}
		return null;
	}

	private Command invalidParser(String paras) {
		return new InvalidCommand();
	}

	private Command exitParser(String paras) {
		return new ExitCommand();
	}

	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split(" ")[0];
		return commandTypeString;
	}
	
	private static String removeFirstWord(String userCommand) {
		String[] userCommandString = userCommand.trim().split(" ",2);
		if (userCommandString.length == 1){
			return "";
		} else {
			return userCommandString[1];
		}
	}	
	
	private static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    int i = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
