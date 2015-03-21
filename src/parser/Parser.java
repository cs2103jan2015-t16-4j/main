package parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import logic.*;
import logic.Command.CommandType;

public class Parser {
	private static final String KEYWORD_ADD_DEADLINE = "by";
	private static final String KEYWORD_ADD_SCHEDULED = "from";
	private static final String KEYWORD_ADD_SCHEDULED_2 = "to";
	private static final String KEYWORD_EDIT_NAME = "name to";
	private static final String KEYWORD_EDIT_DEADLINE = "deadline to";
	private static final String KEYWORD_EDIT_TAG = "to #";
	private static final String KEYWORD_DELETE_TAG = " #";
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
			String name = paras.split("\\s+" + KEYWORD_ADD_DEADLINE + "\\s+")[0];
			String deadlineString = paras.split("\\s+" + KEYWORD_ADD_DEADLINE
					+ "\\s+")[1];
			SimpleDateFormat deadlineSdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar deadlineCalendar = Calendar.getInstance();
			try {
				deadlineCalendar.setTime(deadlineSdf.parse(deadlineString));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new AddCommand(name, null, deadlineCalendar);
		} else if (paras.toLowerCase().contains(KEYWORD_ADD_SCHEDULED)) {
			String name = paras.split("\\s+" + KEYWORD_ADD_SCHEDULED + "\\s+")[0];
			String timeString = paras.split("\\s+" + KEYWORD_ADD_SCHEDULED
					+ "\\s+")[1];
			String beginTimeString = timeString.split("\\s+"
					+ KEYWORD_ADD_SCHEDULED_2 + "\\s+")[0];
			String endTimeString = timeString.split("\\s+"
					+ KEYWORD_ADD_SCHEDULED_2 + "\\s+")[1];

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new AddCommand(name, beginTimeCalendar, endTimeCalendar);
		}
		return new AddCommand(paras, null, null);
	}

	private Command deleteParser(String paras) {
		if (isNumeric(paras)) {
			return new DeleteCommand(Integer.parseInt(paras));
		} else if (paras.toLowerCase().contains(KEYWORD_DELETE_TAG)) {
			String name = paras.split(KEYWORD_DELETE_TAG)[0];
			String tag = "#" + paras.split(KEYWORD_DELETE_TAG)[1];
			return new DeleteCommand(name, tag);
		} else {
			return new DeleteCommand(paras);
		}
	}

	private Command editParser(String paras) {
		if (paras.toLowerCase().contains(KEYWORD_EDIT_NAME)) {
			String oldName = paras.split("\\s+" + KEYWORD_EDIT_NAME + "\\s+")[0];
			String newName = paras.split("\\s+" + KEYWORD_EDIT_NAME + "\\s+")[1];
			return new EditCommand(oldName, newName);
		} else if (paras.toLowerCase().contains(KEYWORD_EDIT_DEADLINE)) {
			String name = paras.split("\\s+" + KEYWORD_EDIT_DEADLINE + "\\s+")[0];
			String newDeadlineString = paras.split("\\s+"
					+ KEYWORD_EDIT_DEADLINE + "\\s+")[1];
			SimpleDateFormat newDeadlineSdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar newDeadlineCalendar = Calendar.getInstance();
			try {
				newDeadlineCalendar.setTime(newDeadlineSdf
						.parse(newDeadlineString));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new EditCommand(name, newDeadlineCalendar);
		} else if (paras.toLowerCase().contains(KEYWORD_EDIT_TAG)) {
			String name = paras.split("\\s+#", 2)[0];
			String oldTag = "#"
					+ paras.split("\\s+#", 2)[1].split("\\s+"
							+ KEYWORD_EDIT_TAG)[0];
			String newTag = "#"
					+ paras.split("\\s+#", 2)[1].split("\\s+"
							+ KEYWORD_EDIT_TAG)[1];
			return new EditCommand(name, oldTag, newTag);
		}
		return null;
	}

	private Command displayParser(String paras) {
		if (paras.length() == 0) {
			return new DisplayCommand();
		} else {
			return new DisplayCommand(paras);
		}
	}

	private Command tagParser(String paras) {
		String name = paras.split("\\s+")[0];
		String[] tags = paras.split("\\s+", 2)[1].split("\\s+");
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

	private Command invalidParser(String paras) {
		return new InvalidCommand();
	}

	private Command exitParser(String paras) {
		return new ExitCommand();
	}

	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}

	private static String removeFirstWord(String userCommand) {
		String[] userCommandString = userCommand.trim().split("\\s+", 2);
		if (userCommandString.length == 1) {
			return "";
		} else {
			return userCommandString[1];
		}
	}

	private static boolean isNumeric(String str) {
		try {
			int i = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
