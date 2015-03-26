package ui.messages;

import common.Settings;

//@author A0112734N
public class GeneralMessages {
	private static final String MESSAGE_ADD = "Added %s \n";
	private static final String MESSAGE_DELETE = "Deleted %s \n";
	private static final String MESSAGE_DISPLAY_EMPTY = "No results\n";
	private static final String MESSAGE_DISPLAY_SUCCESS = "Displaying all %s tasks\n";
	private static final String MESSAGE_DONE = "Task %s set to done\n";
	private static final String MESSAGE_EDIT = "Edited %s \n";
	private static final String MESSAGE_ERROR = "An Error has occured. Check the log file for more details \n";
	private static final String MESSAGE_INVALID = "Invalid command\n";
	private static final String MESSAGE_PATH = "%s is ready for use\n";
	private static final String MESSAGE_PROMPT = "Command:";
	private static final String MESSAGE_SAVE = "%s saved successfully\n";
	private static final String MESSAGE_TAG = "Added tag to %s \n";
	private static final String MESSAGE_UNDO = "Previous command undo success";
	private static final String MESSAGE_WELCOME = "Welcome to AnyTask. %s is ready for use\n";

	public static String getMsgAdd(String userText) {
		return String.format(MESSAGE_ADD, userText);
	}

	public static String getMsgDelete(String line) {
		return String.format(MESSAGE_DELETE, line);
	}

	public static String getMsgDisplay(String line) {
		return String.format(MESSAGE_DISPLAY_SUCCESS, line);
	}

	public static String getMsgDisplayEmpty() {
		return String.format(MESSAGE_DISPLAY_EMPTY);
	}

	public static String getMsgDone(String line) {
		return String.format(MESSAGE_DONE, line);
	}

	public static String getMsgEdit(String line) {
		return String.format(MESSAGE_EDIT, line);
	}

	public static String getMsgEmpty() {
		return String.format(MESSAGE_DISPLAY_EMPTY);
	}

	public static String getMsgError() {
		return String.format(MESSAGE_ERROR, Settings.getFilePath());
	}

	public static String getMsgInvalid() {
		return String.format(MESSAGE_INVALID);
	}

	public static String getMsgPath() {
		return String.format(MESSAGE_PATH, Settings.getFilePath());
	}

	public static String getMsgPrompt() {
		return String.format(MESSAGE_PROMPT);
	}

	public static String getMsgSave() {
		return String.format(MESSAGE_SAVE);
	}

	public static String getMsgTag(String line) {
		return String.format(MESSAGE_TAG, line);
	}

	public static String getMsgUndo() {
		return String.format(MESSAGE_UNDO);
	}

	public static String getMsgWelcome() {
		return String.format(MESSAGE_WELCOME, Settings.getFilePath());
	}
}
