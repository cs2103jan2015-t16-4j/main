import java.util.Scanner;

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

		if (userCommandType.equalsIgnoreCase(OPERATION_ADD)) {
			Database.addTask(userCommand);
		} else if (userCommandType.equalsIgnoreCase(OPERATION_DISPLAY)) {
			Display.displayAll();
		} else if (userCommandType.equalsIgnoreCase(OPERATION_EDIT)) {
			int taskID = Integer.parseInt(getFirstWord(userCommand));
			String newName = removeFirstWord(userCommand);
			Database.editTask(taskID, newName);
		} else if (userCommandType.equalsIgnoreCase(OPERATION_DELETE)) {
			Database.deleteTask(userCommand);
		} else if (userCommandType.equalsIgnoreCase(OPERATION_FILEPATH)) {
			Database.alterFilePath(userCommand);
		} else if (userCommandType.equalsIgnoreCase(OPERATION_SAVE)) {
			Database.saveTasksToFile();
		} else if (userCommandType.equalsIgnoreCase(OPERATION_EXIT)) {
			System.exit(0);
		} else {
			Display.displayMsgInvalid();
		}
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			isCorrectFormat = true;
			Display.displayMsgWelcome();
		} else if (args.length == 1) {
			Database.alterFilePath(args[0]);
			isCorrectFormat = true;
			Display.displayMsgWelcome();
		} else {
			System.exit(0);
		}

		Database.fetchTasksFromFile();

		while (isCorrectFormat) {
			Display.displayMsgPrompt();
			String command = sc.nextLine();
			executeUserCommand(getFirstWord(command), removeFirstWord(command));
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
