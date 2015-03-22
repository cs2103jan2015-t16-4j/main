package parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import logic.*;
import logic.Command.CommandType;

public class Parser {
	private static final String CONSTANT_EMPTY_STRING = "";
	private static final String CONSTANT_HASHTAG = "#";
	private static final String CONSTANT_SPACE = "\\s+";
	private static final String KEYWORD_ADD_DEADLINE = "\\s+by\\s";
	private static final String KEYWORD_ADD_SCHEDULED = "\\s+from\\s+";
	private static final String KEYWORD_ADD_SCHEDULED_2 = "\\s+to\\s+";
	private static final String KEYWORD_EDIT_NAME = "\\s+name to\\s+";
	private static final String KEYWORD_EDIT_DEADLINE = "\\s+deadline to\\s+";
	private static final String KEYWORD_EDIT_TAG = "\\s+to #";
	private static final String KEYWORD_TAG = "\\s+#";
	private String commandString;

	public Parser(String cmd) {
		this.commandString = cmd;
	}

	public void parseInput() {
		CommandType commandType = CommandType
				.fromString(getFirstWord(commandString));
		Command cmd = this.parseInput(commandType,
				removeFirstWord(commandString));
		cmd.execute();
	}

	// private CommandType determineCommandType(String commandTypeString) {
	// if (commandTypeString.equalsIgnoreCase("add")) {
	// return CommandType.ADD;
	// } else if (commandTypeString.equalsIgnoreCase("delete")) {
	// return CommandType.DELETE;
	// } else if (commandTypeString.equalsIgnoreCase("edit")) {
	// return CommandType.EDIT;
	// } else if (commandTypeString.equalsIgnoreCase("display")) {
	// return CommandType.DISPLAY;
	// } else if (commandTypeString.equalsIgnoreCase("tag")) {
	// return CommandType.TAG;
	// } else if (commandTypeString.equalsIgnoreCase("done")) {
	// return CommandType.DONE;
	// } else if (commandTypeString.equalsIgnoreCase("undo")) {
	// return CommandType.UNDO;
	// } else if (commandTypeString.equalsIgnoreCase("setpath")) {
	// return CommandType.SETPATH;
	// } else if (commandTypeString.equalsIgnoreCase("help")) {
	// return CommandType.HELP;
	// } else if (commandTypeString.equalsIgnoreCase("exit")) {
	// return CommandType.EXIT;
	// } else {
	// return CommandType.INVALID;
	// }
	// }

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

		if (paras.toLowerCase().contains(KEYWORD_ADD_DEADLINE)) {
			String name;
			String deadlineString;
			try {
				name = paras.split(KEYWORD_ADD_DEADLINE)[0];
				deadlineString = paras.split(KEYWORD_ADD_DEADLINE)[1];
			} catch (Exception e) {
				return new InvalidCommand(commandString);
			}
			SimpleDateFormat deadlineSdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar deadlineCalendar = Calendar.getInstance();
			try {
				deadlineCalendar.setTime(deadlineSdf.parse(deadlineString));
			} catch (ParseException e) {
				return new InvalidCommand(commandString);
			}
			return new AddCommand(name, null, deadlineCalendar);
		} else if (paras.toLowerCase().contains(KEYWORD_ADD_SCHEDULED)) {
			String name;
			String beginTimeString;
			String endTimeString;
			try {
				name = paras.split(KEYWORD_ADD_SCHEDULED)[0];
				String timeString = paras.split(KEYWORD_ADD_SCHEDULED)[1];
				beginTimeString = timeString.split(KEYWORD_ADD_SCHEDULED_2)[0];
				endTimeString = timeString.split(KEYWORD_ADD_SCHEDULED_2)[1];
			} catch (Exception e1) {
				return new InvalidCommand(commandString);
			}

			SimpleDateFormat beginTimeSdf = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm");
			SimpleDateFormat endTimeSdf = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm");

			Calendar beginTimeCalendar = Calendar.getInstance();
			Calendar endTimeCalendar = Calendar.getInstance();

			try {
				beginTimeCalendar.setTime(beginTimeSdf.parse(beginTimeString));
				endTimeCalendar.setTime(endTimeSdf.parse(endTimeString));
			} catch (ParseException e) {
				return new InvalidCommand(commandString);
			}
			return new AddCommand(name, beginTimeCalendar, endTimeCalendar);
		} else if (paras != CONSTANT_EMPTY_STRING) {
			return new AddCommand(paras, null, null);
		} else {
			return new InvalidCommand(commandString);
		}

	}

	private Command deleteParser(String paras) {
		if (isNumeric(paras)) {
			return new DeleteCommand(Integer.parseInt(paras));
		} else if (paras.toLowerCase().contains(KEYWORD_TAG)) {
			String name = paras.split(KEYWORD_TAG)[0];
			String tag = CONSTANT_HASHTAG + paras.split(KEYWORD_TAG)[1];
			return new DeleteCommand(name, tag);
		} else if (paras != CONSTANT_EMPTY_STRING) {
			return new DeleteCommand(paras);
		} else {
			return new InvalidCommand(commandString);
		}
	}

	private Command editParser(String paras) {
		if (paras.toLowerCase().contains(KEYWORD_EDIT_NAME)) {
			String oldName;
			String newName;
			try {
				oldName = paras.split(KEYWORD_EDIT_NAME)[0];
				newName = paras.split(KEYWORD_EDIT_NAME)[1];
			} catch (Exception e) {
				return new InvalidCommand(commandString);
			}
			return new EditCommand(oldName, newName);
		} else if (paras.toLowerCase().contains(KEYWORD_EDIT_DEADLINE)) {
			String name;
			String newDeadlineString;
			try {
				name = paras.split(KEYWORD_EDIT_DEADLINE)[0];
				newDeadlineString = paras.split(KEYWORD_EDIT_DEADLINE)[1];
			} catch (Exception e1) {
				return new InvalidCommand(commandString);
			}
			SimpleDateFormat newDeadlineSdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar newDeadlineCalendar = Calendar.getInstance();
			try {
				newDeadlineCalendar.setTime(newDeadlineSdf
						.parse(newDeadlineString));
			} catch (ParseException e) {
				return new InvalidCommand(commandString);
			}
			return new EditCommand(name, newDeadlineCalendar);
		} else if (paras.toLowerCase().contains(KEYWORD_EDIT_TAG)) {
			String name;
			String oldTag;
			String newTag;
			try {
				name = paras.split(KEYWORD_TAG, 2)[0];
				oldTag = CONSTANT_HASHTAG
						+ paras.split(KEYWORD_TAG, 2)[1].split(KEYWORD_EDIT_TAG)[0];
				newTag = CONSTANT_HASHTAG
						+ paras.split(KEYWORD_TAG, 2)[1].split(KEYWORD_EDIT_TAG)[1];
			} catch (Exception e) {
				return new InvalidCommand(commandString);
			}
			return new EditCommand(name, oldTag, newTag);
		} else {
			return new InvalidCommand(commandString);
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
		try {
			name = paras.split(KEYWORD_TAG)[0];
			tags = (CONSTANT_HASHTAG + paras.split(KEYWORD_TAG, 2)[1]).split(CONSTANT_SPACE);
		} catch (Exception e) {
			return new InvalidCommand(commandString);
		}
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
		return new InvalidCommand(userCommand);
	}

	private Command exitParser(String paras) {
		return new ExitCommand();
	}

	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split(CONSTANT_SPACE)[0];
		return commandTypeString;
	}

	private static String removeFirstWord(String userCommand) {
		String[] userCommandString = userCommand.trim().split(CONSTANT_SPACE, 2);
		if (userCommandString.length == 1) {
			return CONSTANT_EMPTY_STRING;
		} else {
			return userCommandString[1];
		}
	}

	private static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
