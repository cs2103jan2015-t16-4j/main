package ui.Messages;

import java.util.ArrayList;

import logic.Data;

import common.Task;
import common.Settings;

public class GeneralMessages {
	private static final String MESSAGE_WELCOME = "Welcome to AnyTask. %s is ready for use\n";
	private static final String MESSAGE_ADD = "Added %s \n";
	private static final String MESSAGE_DELETELINE = "Deleted %s \n";
	private static final String MESSAGE_DISPLAY_SUCCESS = "%d. %s \n";
	private static final String MESSAGE_DISPLAY_EMPTY = "There are no tasks to display \n";
	private static final String MESSAGE_DISPLAY_INVALID = "Command invalid \n";
	private static final String MESSAGE_PROMPT = "Command:";
	private static final String MESSAGE_ERROR = "An Error has occured. Check the log file for more details \n";

	private static String output = "";

	private static void print() {
		System.out.printf(output);
	}

	public static String getMsgWelcome() {
		return String.format(MESSAGE_WELCOME, Settings.getFilePath());
	}

	public static void getMsgAdd(String userText) {
		output = String.format(MESSAGE_ADD, userText);
		print();
	}

	public static void getMsgDeleteLine(String line) {
		output = String.format(MESSAGE_DELETELINE, line);
		print();
	}

	public static void getMsgDisplay(int i, String line) {
		output = String.format(MESSAGE_DISPLAY_SUCCESS, i, line);
		print();
	}

	public static void getMsgEmpty() {
		output = String.format(MESSAGE_DISPLAY_EMPTY);
		print();
	}

	public static void getMsgPrompt() {
		output = String.format(MESSAGE_PROMPT);
		print();
	}
	
	public static void getMsgError() {
		output = String.format(MESSAGE_ERROR);
		print();
	}
	
	public static void displayAll() {
		ArrayList<Task> taskList = Data.getTaskList();
		int i = 1;
		if (taskList.isEmpty()) {
			getMsgEmpty();
		} else {
			for (Task task : taskList) {
				getMsgDisplay(i, task.getName());
				i++;
			}
		}
	}

	public static void getMsgInvalid() {
		output = String.format(MESSAGE_DISPLAY_INVALID);
		print();
	}
}
