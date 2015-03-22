package parser;

//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import common.Task;
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
	private String commandString;

	public Parser(String cmd) {
		this.commandString = cmd;
	}

	public ArrayList<Task> parseInput() {
		CommandType commandType = CommandType
				.fromString(getFirstWord(commandString));
		Command cmd = this.parseInput(commandType,
				removeFirstWord(commandString));
		return cmd.execute();
	}

	private Command parseInput(CommandType commandType, String paras) {
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

		if (isAddTaskWithDeadline(paras)) {
			return addTaskWithDeadline(paras);
		} else if (isAddTaskWithTime(paras)) {
			return addTaskWithTime(paras);
		} else if (isAddFloatingTask(paras)) {
			return new AddCommand(paras, null, null);
		} else {
			return null;
			// return new InvalidCommand(commandString);
		}

	}

	private Command deleteParser(String paras) {
		if (isDeleteTaskWithId(paras)) {
			return new DeleteCommand(Integer.parseInt(paras));
		} else if (isDeleteTagWithName(paras)) {
			String name = paras.split(KEYWORD_TAG)[0];
			String tag = CONSTANT_HASHTAG + paras.split(KEYWORD_TAG)[1];
			return new DeleteCommand(name, tag);
		} else if (isDeleteTaskWithName(paras)) {
			return new DeleteCommand(paras);
		} else {
			return null;
			// return new InvalidCommand(commandString);
		}
	}

	private Command editParser(String paras) {
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
			// return new InvalidCommand(commandString);
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

		name = paras.split(KEYWORD_TAG)[0];
		tags = (CONSTANT_HASHTAG + paras.split(KEYWORD_TAG, 2)[1])
				.split(CONSTANT_SPACE);

		return new TagCommand(name, tags);
	}

	private Command doneParser(String paras) {
		return new DoneCommand(paras);
	}

	private Command undoParser(String paras) {
		// TODO: to be completed
		return null;
	}

	private Command setpathParser(String paras) {
		// TODO: check correct path
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

	private Calendar parseDate(String dateString) {
		return DateParser.datePaser(dateString);
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
			// return new InvalidCommand(commandString);
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
			// return new InvalidCommand(commandString);
		}
	}

	private boolean isDeleteTaskWithName(String paras) {
		return paras != CONSTANT_EMPTY_STRING;
	}

	private boolean isDeleteTagWithName(String paras) {
		return paras.toLowerCase().contains(KEYWORD_TAG);
	}

	private boolean isDeleteTaskWithId(String str) {
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
		String name;
		String newEndTimeString;

		name = paras.split(KEYWORD_EDIT_END_TIME)[0];
		newEndTimeString = paras.split(KEYWORD_EDIT_END_TIME)[1];

		Calendar newEndTimeCalendar = parseDate(newEndTimeString);
		if (newEndTimeCalendar != null) {
			return new EditCommand(name, null, newEndTimeCalendar);
		} else {
			return null;
			// return new InvalidCommand(commandString);
		}
	}

	private Command editStartTime(String paras) {
		String name;
		String newStartTimeString;

		name = paras.split(KEYWORD_EDIT_START_TIME)[0];
		newStartTimeString = paras.split(KEYWORD_EDIT_START_TIME)[1];

		Calendar newStartTimeCalendar = parseDate(newStartTimeString);
		if (newStartTimeCalendar != null) {
			return new EditCommand(name, newStartTimeCalendar, null);
		} else {
			return null;
			// return new InvalidCommand(commandString);
		}
	}

	private Command editDeadline(String paras) {
		String name;
		String newDeadlineString;

		name = paras.split(KEYWORD_EDIT_DEADLINE)[0];
		newDeadlineString = paras.split(KEYWORD_EDIT_DEADLINE)[1];

		Calendar newDeadlineCalendar = parseDate(newDeadlineString);
		if (newDeadlineCalendar != null) {
			return new EditCommand(name, null, newDeadlineCalendar);
		} else {
			return null;
			// return new InvalidCommand(commandString);
		}
	}

	private Command editName(String paras) {
		String oldName;
		String newName;

		oldName = paras.split(KEYWORD_EDIT_NAME)[0];
		newName = paras.split(KEYWORD_EDIT_NAME)[1];

		return new EditCommand(oldName, newName);
	}

	private Command editTag(String paras) {
		String name;
		String oldTag;
		String newTag;

		name = paras.split(KEYWORD_TAG, 2)[0];
		oldTag = CONSTANT_HASHTAG
				+ paras.split(KEYWORD_TAG, 2)[1].split(KEYWORD_EDIT_TAG)[0];
		newTag = CONSTANT_HASHTAG
				+ paras.split(KEYWORD_TAG, 2)[1].split(KEYWORD_EDIT_TAG)[1];

		return new EditCommand(name, oldTag, newTag);
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
