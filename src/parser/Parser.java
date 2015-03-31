package parser;

//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import common.Task;
import database.Database;
import logic.*;
import logic.Command.CommandType;

//@author A0119384Y
public class Parser {
	private static final String CONSTANT_EMPTY_STRING = "";
	private static final String CONSTANT_HASHTAG = "#";
	private static final String CONSTANT_SPACE = " ";
	private static final String KEYWORD_ADD_DEADLINE = " by ";
	private static final String KEYWORD_ADD_SCHEDULED = " from ";
	private static final String KEYWORD_ADD_SCHEDULED_2 = " to ";
	private static final String KEYWORD_EDIT_NAME = " name to ";
	private static final String KEYWORD_EDIT_DEADLINE = " deadline to ";
	private static final String KEYWORD_EDIT_TAG = " to #";
	private static final String KEYWORD_TAG = " #";
	private static final String KEYWORD_EDIT_START_TIME = " start time to ";
	private static final String KEYWORD_EDIT_END_TIME = " end time to ";
	private static final String KEYWORD_DELETE_DEADLINE = "deadline";
	private static final String KEYWORD_DELETE_END_TIME = "end time";
	private static final String KEYWORD_DELETE_START_TIME = "start time";
	private ArrayList<Task> taskListBackup = new ArrayList<Task>();

	private static Parser parserInstance = new Parser();

	private String commandString;

	private Parser() {

	}

	public static Parser getInstance() {
		return parserInstance;
	}

	public ArrayList<Task> parseInput(String commandString) {
		this.commandString = commandString;
		Command cmd = parseInfo(
				CommandType.fromString(parseCommandType(commandString)),
				getCommandInfo(commandString));
		return cmd.execute();
	}

	public String getCommandInfo(String commandString) {
		return removeFirstWord(commandString);
	}

	public String parseCommandType(String commandString) {
		return getFirstWord(commandString);
	}

	private Command parseInfo(CommandType commandType, String paras) {
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
		case SAVE:
			return saveParser(paras);
		default:
			throw new Error("Unrecognized command type");
		}
	}

	private Command addParser(String paras) {
		backupTaskList();
		if (isAddTaskWithDeadline(paras)) {
			return addTaskWithDeadline(paras);
		} else if (isAddTaskWithTime(paras)) {
			return addTaskWithTime(paras);
		} else if (isAddFloatingTask(paras)) {
			return new AddCommand(paras, null, null);
		} else {
			return null;
		}

	}

	private Command deleteParser(String paras) {
		backupTaskList();
		if (isDeleteTag(paras)) {
			return deleteTag(paras);
		} else if (isDeleteAttribute(paras)) {
			return deleteAttribute(paras);
		} else if (isDeleteTask(paras)) {
			return deleteTask(paras);
		} else {
			return null;
		}
	}

	private Command editParser(String paras) {
		backupTaskList();
		if (isEditName(paras)) {
			return editName(paras);
		} else if (isEditDeadline(paras)) {
			return editDeadline(paras);
		} else if (isEditStartTime(paras)) {
			return editStartTime(paras);
		} else if (isEditEndTime(paras)) {
			return editEndTime(paras);
		} else if (isEditTag(paras)) {
			return editTag(paras);
		} else {
			return null;
		}
	}

	private Command displayParser(String paras) {
		if (paras.length() == 0) {
			return new DisplayCommand();
		} else {
			return new DisplayCommand(paras);
		}
	}

	private Command tagParser(String paras) {
		String name;
		String[] tags;
		backupTaskList();
		name = paras.split(KEYWORD_TAG)[0];
		tags = (CONSTANT_HASHTAG + paras.split(KEYWORD_TAG, 2)[1])
				.split(CONSTANT_SPACE);
		if (isNumerical(name)) {
			return new TagCommand(Integer.parseInt(name), tags);
		} else {
			return new TagCommand(name, tags);
		}

	}

	private Command doneParser(String paras) {
		backupTaskList();
		if (isNumerical(paras)) {
			return new DoneCommand(Integer.parseInt(paras));
		} else {
			return new DoneCommand(paras);
		}
	}

	private Command undoParser(String paras) {
		return new UndoCommand(taskListBackup);
	}

	private Command setpathParser(String paras) {
		return new SetpathCommand(paras);
	}

	private Command helpParser(String paras) {
		// if (paras.length() == 0){
		// return new HelpCommand();
		// } else {
		// return new HelpCommand(paras);
		// }
		return null;
	}

	private Command invalidParser(String userCommand) {
		return new InvalidCommand(commandString);
	}

	private Command exitParser(String paras) {
		return new ExitCommand();
	}

	private Command saveParser(String paras) {
		return new SaveCommand();
	}

	private Calendar parseDate(String dateString) {
		DateParser dateParser = new DateParser(dateString);
		return dateParser.parseDate();
		// SimpleDateFormat dateSdf = new SimpleDateFormat("dd/MM/yyyy");
		// Calendar dateCalendar = Calendar.getInstance();
		// try {
		// dateCalendar.setTime(dateSdf.parse(dateString));
		// } catch (ParseException e) {
		// return null;
		// }
		// return dateCalendar;
	}

	private boolean isAddFloatingTask(String paras) {
		return paras != CONSTANT_EMPTY_STRING;
	}

	private boolean isAddTaskWithTime(String paras) {
		return paras.toLowerCase().contains(KEYWORD_ADD_SCHEDULED);
	}

	private boolean isAddTaskWithDeadline(String paras) {
		return paras.toLowerCase().contains(KEYWORD_ADD_DEADLINE);
	}

	private Command addTaskWithTime(String paras) {
		String name;
		String beginTimeString;
		String endTimeString;

		name = paras.split(KEYWORD_ADD_SCHEDULED)[0];
		String timeString = paras.split(KEYWORD_ADD_SCHEDULED)[1];
		beginTimeString = timeString.split(KEYWORD_ADD_SCHEDULED_2)[0];
		endTimeString = timeString.split(KEYWORD_ADD_SCHEDULED_2)[1];

		Calendar beginTimeCalendar = parseDate(beginTimeString);
		Calendar endTimeCalendar = parseDate(endTimeString);

		if (beginTimeCalendar != null && endTimeCalendar != null
				&& beginTimeCalendar.before(endTimeCalendar)) {
			return new AddCommand(name, beginTimeCalendar, endTimeCalendar);
		} else {
			return null;
		}
	}

	private Command addTaskWithDeadline(String paras) {
		String name;
		String deadlineString;

		name = paras.split(KEYWORD_ADD_DEADLINE)[0];
		deadlineString = paras.split(KEYWORD_ADD_DEADLINE)[1];

		Calendar deadlineCalendar = parseDate(deadlineString);
		if (deadlineCalendar != null) {
			return new AddCommand(name, null, deadlineCalendar);
		} else {
			return null;
		}
	}

	private boolean isDeleteTask(String paras) {
		return paras != CONSTANT_EMPTY_STRING;
	}

	private boolean isDeleteTag(String paras) {
		return paras.toLowerCase().contains(KEYWORD_TAG);
	}

	private boolean isDeleteAttribute(String paras) {
		return paras.toLowerCase().contains(KEYWORD_DELETE_START_TIME)
				|| paras.toLowerCase().contains(KEYWORD_DELETE_END_TIME)
				|| paras.toLowerCase().contains(KEYWORD_DELETE_DEADLINE);
	}

	private boolean isNumerical(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private boolean isEditTag(String paras) {
		return paras.toLowerCase().contains(KEYWORD_EDIT_TAG);
	}

	private boolean isEditDeadline(String paras) {
		return paras.toLowerCase().contains(KEYWORD_EDIT_DEADLINE);
	}

	private boolean isEditName(String paras) {
		return paras.toLowerCase().contains(KEYWORD_EDIT_NAME);
	}

	private boolean isEditStartTime(String paras) {
		return paras.toLowerCase().contains(KEYWORD_EDIT_START_TIME);
	}

	private boolean isEditEndTime(String paras) {
		return paras.toLowerCase().contains(KEYWORD_EDIT_END_TIME);
	}

	private Command editEndTime(String paras) {
		String name = paras.split(KEYWORD_EDIT_END_TIME)[0];
		String newEndTimeString = paras.split(KEYWORD_EDIT_END_TIME)[1];

		Calendar newEndTimeCalendar = parseDate(newEndTimeString);
		if (newEndTimeCalendar != null) {
			if (isNumerical(name)) {
				return new EditCommand(Integer.parseInt(name), null,
						newEndTimeCalendar);
			} else {
				return new EditCommand(name, null, newEndTimeCalendar);
			}
		} else {
			return null;
		}
	}

	private Command editStartTime(String paras) {
		String name = paras.split(KEYWORD_EDIT_START_TIME)[0];
		String newStartTimeString = paras.split(KEYWORD_EDIT_START_TIME)[1];

		Calendar newStartTimeCalendar = parseDate(newStartTimeString);
		if (newStartTimeCalendar != null) {
			if (isNumerical(name)) {
				return new EditCommand(Integer.parseInt(name),
						newStartTimeCalendar, null);
			} else {
				return new EditCommand(name, newStartTimeCalendar, null);
			}
		} else {
			return null;
		}
	}

	private Command editDeadline(String paras) {
		String name = paras.split(KEYWORD_EDIT_DEADLINE)[0];
		String newDeadlineString = paras.split(KEYWORD_EDIT_DEADLINE)[1];

		Calendar newDeadlineCalendar = parseDate(newDeadlineString);
		if (newDeadlineCalendar != null) {
			if (isNumerical(name)) {
				return new EditCommand(Integer.parseInt(name), null,
						newDeadlineCalendar);
			} else {
				return new EditCommand(name, null, newDeadlineCalendar);
			}
		} else {
			return null;
		}
	}

	private Command editName(String paras) {
		String oldName = paras.split(KEYWORD_EDIT_NAME)[0];
		String newName = paras.split(KEYWORD_EDIT_NAME)[1];
		if (isNumerical(oldName)) {
			return new EditCommand(Integer.parseInt(oldName), newName);
		} else {
			return new EditCommand(oldName, newName);
		}
	}

	private Command editTag(String paras) {
		String name = paras.split(KEYWORD_TAG, 2)[0];
		String oldTag = CONSTANT_HASHTAG
				+ paras.split(KEYWORD_TAG, 2)[1].split(KEYWORD_EDIT_TAG)[0];
		String newTag = CONSTANT_HASHTAG
				+ paras.split(KEYWORD_TAG, 2)[1].split(KEYWORD_EDIT_TAG)[1];

		if (isNumerical(name)) {
			return new EditCommand(Integer.parseInt(name), oldTag, newTag);
		} else {
			return new EditCommand(name, oldTag, newTag);
		}
	}

	private Command deleteTask(String paras) {
		if (isNumerical(paras)) {
			return new DeleteCommand(Integer.parseInt(paras));
		} else {
			return new DeleteCommand(paras);
		}
	}

	private Command deleteTag(String paras) {
		String name = paras.split(KEYWORD_TAG)[0];
		String tag = CONSTANT_HASHTAG + paras.split(KEYWORD_TAG)[1];
		if (isNumerical(name)) {
			return new DeleteCommand(Integer.parseInt(name), tag);
		} else {
			return new DeleteCommand(name, tag);
		}
	}

	private Command deleteAttribute(String paras) {
		if(paras.toLowerCase().contains(KEYWORD_DELETE_START_TIME)){
			String name = paras.split(CONSTANT_SPACE+KEYWORD_DELETE_START_TIME)[0];
			if (isNumerical(name)) {
				return new DeleteCommand(Integer.parseInt(name), KEYWORD_DELETE_START_TIME);
			} else {
				return new DeleteCommand(name, KEYWORD_DELETE_START_TIME);
			}
		} else if (paras.toLowerCase().contains(KEYWORD_DELETE_END_TIME)){
			String name = paras.split(CONSTANT_SPACE+KEYWORD_DELETE_END_TIME)[0];
			if (isNumerical(name)) {
				return new DeleteCommand(Integer.parseInt(name), KEYWORD_DELETE_END_TIME);
			} else {
				return new DeleteCommand(name, KEYWORD_DELETE_END_TIME);
			}
		} else if (paras.toLowerCase().contains(KEYWORD_DELETE_DEADLINE)){
			String name = paras.split(CONSTANT_SPACE+KEYWORD_DELETE_DEADLINE)[0];
			if (isNumerical(name)) {
				return new DeleteCommand(Integer.parseInt(name), KEYWORD_DELETE_DEADLINE);
			} else {
				return new DeleteCommand(name, KEYWORD_DELETE_DEADLINE);
			}
		} else {
			return null;
		}
	}

	private void backupTaskList() {
		taskListBackup.clear();
		for (Task t : Database.getInstance().getTaskList()) {
			taskListBackup.add(new Task(t));
		}
	}

	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split(CONSTANT_SPACE)[0];
		return commandTypeString;
	}

	private static String removeFirstWord(String userCommand) {
		String[] userCommandString = userCommand.trim()
				.split(CONSTANT_SPACE, 2);
		if (userCommandString.length == 1) {
			return CONSTANT_EMPTY_STRING;
		} else {
			return userCommandString[1];
		}
	}
}
