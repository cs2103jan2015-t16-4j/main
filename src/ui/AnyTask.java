package ui;
import java.util.Scanner;

import logic.Data;
import database.Database;
import logic.Display;

public class AnyTask {

	private static final String OPERATION_ADD = "add";
	private static final String OPERATION_DISPLAY = "display";
	private static final String OPERATION_EDIT = "edit";
	private static final String OPERATION_FILEPATH = "filepath";
	private static final String OPERATION_DELETE = "delete";
	private static final String OPERATION_EXIT = "exit";
	private static final String OPERATION_SAVE = "save";

	private static boolean isCorrectFormat = false;

	private static Scanner sc = new Scanner(System.in);

	public static void executeUserCommand(String userCommandType, String userCommand) {

		if (userCommandType.equals(OPERATION_ADD)) {
			Data.addTask(userCommand);
		} else if (userCommandType.equals(OPERATION_DISPLAY)) {
			Display.displayAll();
		} else if (userCommandType.equals(OPERATION_EDIT)) {
			int taskID = Integer.parseInt(getFirstWord(userCommand));
			String newName = removeFirstWord(userCommand);
			Data.editTask(taskID, newName);
		} else if (userCommandType.equals(OPERATION_DELETE)) {
			Data.deleteTask(userCommand);
		} else if (userCommandType.equals(OPERATION_FILEPATH)) {
			Database.setFilePath(userCommand);
		} else if (userCommandType.equals(OPERATION_SAVE)) {
			Database.saveTasksToFile();
		} else if (userCommandType.equals(OPERATION_EXIT)) {
			System.exit(0);
			Database.saveTasksToFile();
		} else {
			Display.displayMsgInvalid();
		}
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			isCorrectFormat = true;
			Display.displayMsgWelcome();
		} else if (args.length == 1) {
			Database.setFilePath(args[0]);
			isCorrectFormat = true;
			Display.displayMsgWelcome();
		} else {
			System.exit(0);
		}

		if(!Data.initTaskList()){
			Display.displayMsgError();
			System.exit(0);			
		}

		while (isCorrectFormat) {
			Display.displayMsgPrompt();
			String command = sc.nextLine();
			executeUserCommand(getFirstWord(command).toLowerCase(), removeFirstWord(command));
		}
	}
	
	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
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
}
