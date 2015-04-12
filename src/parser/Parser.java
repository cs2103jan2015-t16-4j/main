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
	private static final String CONSTANT_ESCAPE = "'";

	private static final String CONSTANT_SPACE = " ";
	private static final String KEYWORD_ADD_DEADLINE = " by ";
	private static final String KEYWORD_ADD_SCHEDULED = " from ";
	private static final String KEYWORD_ADD_SCHEDULED_2 = " to ";
	private static final String KEYWORD_ADD_RECURRING_BEFORE = " before ";
	private static final String KEYWORD_EDIT_NAME = " name to ";
	private static final String KEYWORD_EDIT_DEADLINE = " deadline to ";
	private static final String KEYWORD_EDIT_TAG = " to #";
	private static final String KEYWORD_DELETE_TAG = " #";
	private static final String KEYWORD_EDIT_START_TIME = " start time to ";
	private static final String KEYWORD_EDIT_END_TIME = " end time to ";
	private static final String KEYWORD_DELETE_RECURRING = " recurring";
	private static final String KEYWORD_DELETE_DEADLINE = " deadline";
	private static final String KEYWORD_DELETE_END_TIME = " end time";
	private static final String KEYWORD_DELETE_START_TIME = " start time";	
	private static final String KEYWORD_DISPLAY_BEFORE = "before ";
	private static final String KEYWORD_DISPLAY_FROM = "from ";
	private static final String CONSTANT_HASHTAG = "#";
	private static final String KEYWORD_BY = "by";
	private static final String KEYWORD_TO = "to";
	private static final String KEYWORD_DEADLINE = "deadline";
	private static final String KEYWORD_END_TIME = "end time";
	private static final String KEYWORD_START_TIME = "start time";
	private static final String KEYWORD_BEFORE = "before";
	private static final String KEYWORD_RECURRING = "recurring";
	private static final String KEYWORD_FROM = "from";
	private static final String KEYWORD_DAILY = "daily";
	private static final String KEYWORD_WEEKLY = "weekly";
	private static final String KEYWORD_MONTHLY = "monthly";
	private static final String KEYWORD_ANNUALLY = "annually";
	private static final String[] CONSTANT_ALL = { CONSTANT_HASHTAG,
			KEYWORD_BY, KEYWORD_TO, KEYWORD_DEADLINE, KEYWORD_END_TIME,
			KEYWORD_START_TIME, KEYWORD_BEFORE, KEYWORD_RECURRING,
			KEYWORD_FROM, KEYWORD_DAILY, KEYWORD_WEEKLY, KEYWORD_MONTHLY,
			KEYWORD_ANNUALLY };
	private static final boolean BOOLEAN_NOT_RECURRING = false;
	private static final boolean BOOLEAN_RECURRING = true;

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
			return parseAdd(paras);
		case DELETE:
			return parseDelete(paras);
		case EDIT:
			return parseEdit(paras);
		case DISPLAY:
			return parseDisplay(paras);
		case TAG:
			return parseTag(paras);
		case DONE:
			return parseDone(paras);
		case UNDO:
			return parseUndo(paras);
		case SETPATH:
			return parseSetpath(paras);
		case HELP:
			return parseHelp(paras);
		case INVALID:
			return parseInvalid(paras);
		case EXIT:
			return parseExit(paras);
		case SAVE:
			return parseSave(paras);
		default:
			throw new Error("Unrecognized command type");
		}
	}

	private Command parseAdd(String paras) {
		backupTaskList();
		String lastWord = paras
				.substring(paras.lastIndexOf(CONSTANT_SPACE) + 1).toLowerCase();
		if (isAddRecurringTask(lastWord)) {
			String parasWithoutLastWord = paras.substring(0,
					paras.lastIndexOf(CONSTANT_SPACE));
			if (isAddTaskWithDeadline(parasWithoutLastWord)) {
				return addRecurringTaskWithDeadline(lastWord,
						parasWithoutLastWord);
			} else if (isAddTaskWithTime(parasWithoutLastWord)) {
				return addRecurringTaskWithTime(lastWord, parasWithoutLastWord);
			} else {
				return null;
			}
		} else {
			if (isAddTaskWithDeadline(paras)) {
				return addTaskWithDeadline(paras);
			} else if (isAddTaskWithTime(paras)) {
				return addTaskWithTime(paras);
			} else if (isAddFloatingTask(paras)) {
				return new AddCommand(recoverEscapeKeywords(paras), null, null);
			} else {
				return null;
			}
		}
	}

	private Command parseDelete(String paras) {
		backupTaskList();
		if (isDeleteTag(paras)) {
			return deleteTag(paras);
		} else if (isDeleteRecurringTag(paras)) {
			return deleteRecurringTag(paras);
		} else if (isDeleteAttribute(paras)) {
			return deleteAttribute(false, paras);
		} else if (isDeleteRecurring(paras)) {
			return deleteRecurring(paras);
		} else if (isDeleteTask(paras)) {
			return deleteTask(paras);
		} else {
			return null;
		}
	}

	private Command parseEdit(String paras) {
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

	private Command parseDisplay(String paras) {
		if (paras.length() == 0) {
			return new DisplayCommand();
		} else if (isDisplayWithEndTime(paras)) {
			return displayWithEndTime(paras);
		} else if (isDisplayWithTimePeriod(paras)) {
			return displayWithTimePeriod(paras);
		} else if (isDisplayWithTag(paras)){
			return new DisplayCommand(paras, true);
		} else {
			return new DisplayCommand(recoverEscapeKeywords(paras).split(CONSTANT_SPACE));
		}
	}

	private Command parseTag(String paras) {
		backupTaskList();
		if (isTagRecurring(paras)) {
			return tagRecurring(paras);
		} else {
			return tag(paras);
		}
	}

	private Command parseDone(String paras) {
		backupTaskList();
		if (isNumerical(paras)) {
			return new DoneCommand(Integer.parseInt(paras));
		} else {
			return new DoneCommand(recoverEscapeKeywords(paras));
		}
	}

	private Command parseUndo(String paras) {
		return new UndoCommand(taskListBackup);
	}

	private Command parseSetpath(String paras) {
		return new SetpathCommand(paras);
	}

	private Command parseHelp(String paras) {
		return new HelpCommand();
	}

	private Command parseInvalid(String userCommand) {
		return new InvalidCommand(commandString);
	}

	private Command parseExit(String paras) {
		return new ExitCommand();
	}

	private Command parseSave(String paras) {
		return new SaveCommand();
	}

	private Calendar parseDate(String dateString) {
		DateParser dateParser = new DateParser(dateString);
		return dateParser.parseDate().get(0);
	}

	private ArrayList<Calendar> parseDates(String dateString) {
		DateParser dateParser = new DateParser(dateString);
		return dateParser.parseDate();
	}

	private String recoverEscapeKeywords(String paras) {
		String newParas = new String(paras);
		for (String keyword : CONSTANT_ALL) {
			newParas = newParas.replaceAll(CONSTANT_ESCAPE + keyword, keyword);
		}
		return newParas;
	}

	private boolean isAddRecurringTask(String lastWord) {
		return lastWord.equals(KEYWORD_DAILY)
				|| lastWord.equals(KEYWORD_WEEKLY)
				|| lastWord.equals(KEYWORD_MONTHLY)
				|| lastWord.equals(KEYWORD_ANNUALLY);
	}

	private boolean isAddFloatingTask(String paras) {
		return paras != CONSTANT_EMPTY_STRING;
	}

	private boolean isAddTaskWithTime(String paras) {
		return paras.contains(KEYWORD_ADD_SCHEDULED)
				&& paras.contains(KEYWORD_ADD_SCHEDULED_2);
	}

	private boolean isAddTaskWithDeadline(String paras) {
		return paras.contains(KEYWORD_ADD_DEADLINE);
	}

	private boolean isDeleteTask(String paras) {
		return paras != CONSTANT_EMPTY_STRING;
	}

	private boolean isDeleteTag(String paras) {
		return paras.contains(KEYWORD_DELETE_TAG)
				&& !isTagRecurring(paras);
	}

	private boolean isDeleteRecurringTag(String paras) {
		return paras.contains(KEYWORD_DELETE_TAG)
				&& isTagRecurring(paras);
	}

	private boolean isDeleteAttribute(String paras) {
		return (paras.contains(KEYWORD_DELETE_START_TIME)
				|| paras.contains(KEYWORD_DELETE_END_TIME) || paras
					.contains(KEYWORD_DELETE_DEADLINE))
				&& !isTagRecurring(paras);
	}

	private boolean isDeleteRecurring(String paras) {
		return isTagRecurring(paras);
	}

	private boolean isDisplayWithEndTime(String paras) {
		return paras.startsWith(KEYWORD_DISPLAY_BEFORE);
	}

	private boolean isDisplayWithTimePeriod(String paras) {
		return paras.startsWith(KEYWORD_DISPLAY_FROM)
				&& paras.contains(KEYWORD_ADD_SCHEDULED_2);
	}

	private boolean isDisplayWithTag(String paras) {
		return paras.startsWith(CONSTANT_HASHTAG);
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
		return paras.contains(KEYWORD_EDIT_TAG);
	}

	private boolean isEditDeadline(String paras) {
		return paras.contains(KEYWORD_EDIT_DEADLINE);
	}

	private boolean isEditName(String paras) {
		return paras.contains(KEYWORD_EDIT_NAME);
	}

	private boolean isEditStartTime(String paras) {
		return paras.contains(KEYWORD_EDIT_START_TIME);
	}

	private boolean isEditEndTime(String paras) {
		return paras.contains(KEYWORD_EDIT_END_TIME);
	}

	private boolean isTagRecurring(String paras) {
		return paras.contains(KEYWORD_DELETE_RECURRING);
	}

	private Command addTaskWithTime(String paras) {
		String name = paras.split(KEYWORD_ADD_SCHEDULED)[0];
		String timeString = paras.split(KEYWORD_ADD_SCHEDULED)[1];
		ArrayList<Calendar> timeCalendarList = parseDates(timeString);
		Calendar beginTimeCalendar = timeCalendarList.get(0);
		Calendar endTimeCalendar = timeCalendarList.get(1);

		if (beginTimeCalendar != null && endTimeCalendar != null
				&& beginTimeCalendar.before(endTimeCalendar)) {
			return new AddCommand(recoverEscapeKeywords(name),
					beginTimeCalendar, endTimeCalendar);
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
			return new AddCommand(recoverEscapeKeywords(name), null,
					deadlineCalendar);
		} else {
			return null;
		}
	}

	private Command addRecurringTaskWithTime(String lastWord,
			String parasWithoutLastWord) {
		String name = parasWithoutLastWord.split(KEYWORD_ADD_SCHEDULED)[0];
		String timeString = parasWithoutLastWord.split(KEYWORD_ADD_SCHEDULED)[1]
				.split(KEYWORD_ADD_RECURRING_BEFORE)[0];
		String endRecurringTimeString = parasWithoutLastWord
				.split(KEYWORD_ADD_RECURRING_BEFORE)[1];

		ArrayList<Calendar> timeCalendarList = parseDates(timeString);
		Calendar beginTimeCalendar = timeCalendarList.get(0);
		Calendar endTimeCalendar = timeCalendarList.get(1);
		Calendar endRecurringTimeCalendar = parseDate(endRecurringTimeString);

		if (beginTimeCalendar != null && endTimeCalendar != null
				&& endRecurringTimeCalendar != null) {
			return new AddCommand(recoverEscapeKeywords(name), lastWord,
					beginTimeCalendar, endTimeCalendar,
					endRecurringTimeCalendar);
		} else {
			return null;
		}
	}

	private Command addRecurringTaskWithDeadline(String lastWord,
			String parasWithoutLastWord) {
		String name = parasWithoutLastWord.split(KEYWORD_ADD_DEADLINE)[0];
		String deadlineString = parasWithoutLastWord
				.split(KEYWORD_ADD_DEADLINE)[1]
				.split(KEYWORD_ADD_RECURRING_BEFORE)[0];
		String endRecurringTimeString = parasWithoutLastWord
				.split(KEYWORD_ADD_DEADLINE)[1]
				.split(KEYWORD_ADD_RECURRING_BEFORE)[1];

		Calendar deadlineCalendar = parseDate(deadlineString);
		Calendar endRecurringTimeCalendar = parseDate(endRecurringTimeString);

		if (deadlineCalendar != null && endRecurringTimeCalendar != null) {
			return new AddCommand(recoverEscapeKeywords(name), lastWord, null,
					deadlineCalendar, endRecurringTimeCalendar);
		} else {
			return null;
		}
	}

	private Command displayWithEndTime(String paras) {
		String endTimeString = paras.split(KEYWORD_BEFORE + CONSTANT_SPACE)[1];
		Calendar endTimeCalendar = parseDate(endTimeString);
		if (endTimeCalendar != null) {
			return new DisplayCommand(endTimeCalendar);
		} else {
			return null;
		}
	}

	private Command displayWithTimePeriod(String paras) {
		String timeString = paras.split(KEYWORD_FROM + CONSTANT_SPACE)[1];

		ArrayList<Calendar> timeCalendarList = parseDates(timeString);
		Calendar startTimeCalendar = timeCalendarList.get(0);
		Calendar endTimeCalendar = timeCalendarList.get(1);

		if (startTimeCalendar != null && endTimeCalendar != null) {
			return new DisplayCommand(startTimeCalendar, endTimeCalendar);
		} else {
			return null;
		}
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
				return new EditCommand(recoverEscapeKeywords(name), null,
						newEndTimeCalendar);
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
				return new EditCommand(recoverEscapeKeywords(name),
						newStartTimeCalendar, null);
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
				return new EditCommand(recoverEscapeKeywords(name), null,
						newDeadlineCalendar);
			}
		} else {
			return null;
		}
	}

	private Command editName(String paras) {
		String oldName = paras.split(KEYWORD_EDIT_NAME)[0];
		String newName = paras.split(KEYWORD_EDIT_NAME)[1];
		if (isNumerical(oldName)) {
			return new EditCommand(Integer.parseInt(oldName),
					recoverEscapeKeywords(newName));
		} else {
			return new EditCommand(recoverEscapeKeywords(oldName),
					recoverEscapeKeywords(newName));
		}
	}

	private Command editTag(String paras) {
		String name = paras.split(KEYWORD_DELETE_TAG, 2)[0];
		String oldTag = CONSTANT_HASHTAG
				+ paras.split(KEYWORD_DELETE_TAG, 2)[1].split(KEYWORD_EDIT_TAG)[0];
		String newTag = CONSTANT_HASHTAG
				+ paras.split(KEYWORD_DELETE_TAG, 2)[1].split(KEYWORD_EDIT_TAG)[1];

		if (isNumerical(name)) {
			return new EditCommand(Integer.parseInt(name), oldTag, newTag);
		} else {
			return new EditCommand(recoverEscapeKeywords(name), oldTag, newTag);
		}
	}

	private Command deleteTask(String paras) {
		if (isNumerical(paras)) {
			return new DeleteCommand(Integer.parseInt(paras),
					BOOLEAN_NOT_RECURRING);
		} else {
			return new DeleteCommand(recoverEscapeKeywords(paras),
					BOOLEAN_NOT_RECURRING);
		}
	}

	private Command deleteRecurring(String paras) {
		String name = paras.split(CONSTANT_SPACE + KEYWORD_RECURRING)[0];
		if (isNumerical(name)) {
			return new DeleteCommand(Integer.parseInt(name), BOOLEAN_RECURRING);
		} else {
			return null; // only receives id.
		}

	}

	private Command deleteTag(String paras) {
		String name = paras.split(KEYWORD_DELETE_TAG)[0];
		String tag = CONSTANT_HASHTAG + paras.split(KEYWORD_DELETE_TAG)[1];
		if (isNumerical(name)) {
			return new DeleteCommand(Integer.parseInt(name),
					BOOLEAN_NOT_RECURRING, tag);
		} else {
			return new DeleteCommand(recoverEscapeKeywords(name),
					BOOLEAN_NOT_RECURRING, tag);
		}
	}

	private Command deleteRecurringTag(String paras) {
		String parasWithoutLastWord = paras.substring(0,
				paras.lastIndexOf(CONSTANT_SPACE));
		String name = parasWithoutLastWord.split(KEYWORD_DELETE_TAG)[0];
		String tag = CONSTANT_HASHTAG + parasWithoutLastWord.split(KEYWORD_DELETE_TAG)[1];
		if (isNumerical(name)) {
			return new DeleteCommand(Integer.parseInt(name),
					BOOLEAN_RECURRING, tag);
		} else {
			return null; // only receives id.
		}
	}

	private Command deleteAttribute(boolean recurring, String paras) {
		if (paras.toLowerCase().contains(KEYWORD_START_TIME)) {
			String name = paras.split(CONSTANT_SPACE + KEYWORD_START_TIME)[0];
			if (isNumerical(name)) {
				return new DeleteCommand(Integer.parseInt(name),
						BOOLEAN_NOT_RECURRING, KEYWORD_START_TIME);
			} else {
				return new DeleteCommand(recoverEscapeKeywords(name),
						BOOLEAN_NOT_RECURRING, KEYWORD_START_TIME);
			}
		} else if (paras.toLowerCase().contains(KEYWORD_END_TIME)) {
			String name = paras.split(CONSTANT_SPACE + KEYWORD_END_TIME)[0];
			if (isNumerical(name)) {
				return new DeleteCommand(Integer.parseInt(name),
						BOOLEAN_NOT_RECURRING, KEYWORD_END_TIME);
			} else {
				return new DeleteCommand(recoverEscapeKeywords(name),
						BOOLEAN_NOT_RECURRING, KEYWORD_END_TIME);
			}
		} else if (paras.toLowerCase().contains(KEYWORD_DEADLINE)) {
			String name = paras.split(CONSTANT_SPACE + KEYWORD_DEADLINE)[0];
			if (isNumerical(name)) {
				return new DeleteCommand(Integer.parseInt(name),
						BOOLEAN_NOT_RECURRING, KEYWORD_DEADLINE);
			} else {
				return new DeleteCommand(recoverEscapeKeywords(name),
						BOOLEAN_NOT_RECURRING, KEYWORD_DEADLINE);
			}
		} else {
			return null;
		}
	}

	private Command tag(String paras) {
		String name = paras.split(KEYWORD_DELETE_TAG)[0];
		;
		String[] tags = (CONSTANT_HASHTAG + paras.split(KEYWORD_DELETE_TAG,
				2)[1]).split(CONSTANT_SPACE);
	
		if (isNumerical(name)) {
			return new TagCommand(Integer.parseInt(name), BOOLEAN_NOT_RECURRING, tags);
		} else {
			return new TagCommand(recoverEscapeKeywords(name), tags);
		}
	}

	private Command tagRecurring(String paras) {
		String parasWithoutLastWord = paras.substring(0,
				paras.lastIndexOf(CONSTANT_SPACE));
		String name = parasWithoutLastWord.split(KEYWORD_DELETE_TAG)[0];
		;
		String[] tags = (CONSTANT_HASHTAG + parasWithoutLastWord.split(
				KEYWORD_DELETE_TAG, 2)[1]).split(CONSTANT_SPACE);
	
		if (isNumerical(name)) {
			return new TagCommand(Integer.parseInt(name), BOOLEAN_RECURRING, tags);
		} else {
			return null; // only receives id.
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
