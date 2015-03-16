package logic;
import java.util.ArrayList;

import common.Task;
import database.Database;

public class Display {

	private static final String MESSAGE_WELCOME = "Welcome to AnyTask.\n";
	private static final String MESSAGE_ADD = "Added %s \n";
	private static final String MESSAGE_DELETELINE = "Deleted %s \n";
	private static final String MESSAGE_DISPLAY_SUCCESS = "%d. %s \n";
	private static final String MESSAGE_DISPLAY_EMPTY = "There are no tasks to display \n";
	private static final String MESSAGE_DISPLAY_INVALID = "Command invalid \n";
	private static final String MESSAGE_PROMPT = "Command:";
	private static final String MESSAGE_ERROR = "ERROR: %s \n";

	private static String output = "";

	private static void print() {
		System.out.printf(output);
	}

	public static void displayMsgWelcome() {
		output = String.format(MESSAGE_WELCOME);
		print();
	}

	public static void displayMsgAdd(String userText) {
		output = String.format(MESSAGE_ADD, userText);
		print();
	}

	public static void displayMsgDeleteLine(String line) {
		output = String.format(MESSAGE_DELETELINE, line);
		print();
	}

	public static void displayMsgDisplay(int i, String line) {
		output = String.format(MESSAGE_DISPLAY_SUCCESS, i, line);
		print();
	}

	public static void displayMsgEmpty() {
		output = String.format(MESSAGE_DISPLAY_EMPTY);
		print();
	}

	public static void displayMsgPrompt() {
		output = String.format(MESSAGE_PROMPT);
		print();
	}
	
	public static void displayMsgError(String line) {
		output = String.format(MESSAGE_ERROR, line);
		print();
	}
	
	public static void displayAll() {
		ArrayList<Task> taskList = Database.getTaskList();
		int i = 1;

		if (taskList.isEmpty()) {
			displayMsgEmpty();
		} else {
			for (Task task : taskList) {
				displayMsgDisplay(i, task.getName());
				i++;
			}
		}
	}

	public static void displayMsgInvalid() {
		output = String.format(MESSAGE_DISPLAY_INVALID);
		print();
	}
}
