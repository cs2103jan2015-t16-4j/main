package ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import common.Task;

import parser.Parser;
import logic.Data;
import database.Database;
import logic.Display;
import ui.Gui;

public class AnyTask {

	private static final String OPERATION_ADD = "add";
	private static final String OPERATION_DISPLAY = "display";
	private static final String OPERATION_EDIT = "edit";
	private static final String OPERATION_FILEPATH = "filepath";
	private static final String OPERATION_DELETE = "delete";
	private static final String OPERATION_EXIT = "exit";
	private static final String OPERATION_SAVE = "save";
	private static final String MESSAGE_DISPLAY_NOT_EMPTY = "%-10d %-10s %-20s %-10s\n";
	private static final String MESSAGE_DISPLAY_EMPTY = "No result\n";
	private static final String MESSAGE_INVALID = "Invalid command\n";
	
	private static Scanner sc = new Scanner(System.in);

	private static boolean isCorrectFormat = false;

	public static void executeUserCommand(String userCommandType,
			String userCommand) {

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

		if (!Data.initTaskList()) {
			Display.displayMsgError();
			System.exit(0);
		}

		while (isCorrectFormat) {
			Display.displayMsgPrompt();
			String command = sc.nextLine();
			processCommand(command);
		}
	}

	public static void formatCommand(String command) {
		executeUserCommand(getFirstWord(command).toLowerCase(),
				removeFirstWord(command));
	}

	public static void processCommand(String command) {
		// executeUserCommand(getFirstWord(command).toLowerCase(),
		// removeFirstWord(command));
		Parser p = new Parser(command);
		displayResults(p.parseInput());
	}

	private static void displayResults(ArrayList<Task> taskList) {
		if (taskList.size() > 0) {
			System.out.printf("%-10s %-10s %-20s %-10s\n", "Task ID",
					"Task Name", "End Time", "Tags");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			for (int index = 0; index < taskList.size(); index++) {
				System.out.printf(
						MESSAGE_DISPLAY_NOT_EMPTY,
						taskList.get(index).getId(),
						taskList.get(index).getName(),
						(taskList.get(index).getEndTime() != null) ? format
								.format(taskList.get(index).getEndTime()
										.getTime()) : "none",
						taskList.get(index).getTags());
			}
		} else if (taskList.size() == 0){
			System.out.printf(MESSAGE_DISPLAY_EMPTY);
		}
		else{
			System.out.printf(MESSAGE_INVALID);
		}
	}

	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}

	private static String removeFirstWord(String userCommand) {
		String[] userCommandString = userCommand.trim().split(" ", 2);
		if (userCommandString.length == 1) {
			return "";
		} else {
			return userCommandString[1];
		}
	}
}
