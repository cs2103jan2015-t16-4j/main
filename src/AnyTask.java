import java.util.Scanner;

public class AnyTask {
	static enum COMMAND_TYPE {
		ADD, DISPLAY, EDIT, DELETE, SEARCH, SAVE, INVALID, EXIT
	};
	private static final String OPERATION_ADD = "add";
	private static final String OPERATION_DISPLAY = "display";
	private static final String OPERATION_EDIT = "edit";
	private static final String OPERATION_DELETE = "delete";
	private static final String OPERATION_EXIT = "exit";
	private static final String OPERATION_SAVE = "save";

	private static boolean isCorrectFormat = false;

	private static Scanner sc = new Scanner(System.in);
	
	public static void executeUserCommand(String userCommand) {
		
		if (userCommand.equals(OPERATION_ADD)) {
			String userText = sc.nextLine();
			Database.addTask(userText);
		} else if (userCommand.equals(OPERATION_DISPLAY)) {
			Display.displayAll();
		} else if (userCommand.equals(OPERATION_EDIT)) {
			String oldName = sc.nextLine();
			String newName = sc.nextLine();
			Database.editTask(oldName, newName);
		} else if (userCommand.equals(OPERATION_DELETE)) {
			String userText = sc.nextLine();
			Database.deleteTask(userText);
		} else if (userCommand.equals(OPERATION_SAVE)) {
			Database.saveTasksToFile();
		} else if (userCommand.equals(OPERATION_EXIT)) {
			System.exit(0);
		} else {
			throw new Error("Unrecognized command type");
		}

	}

	public static void main(String[] args) {
		if (args.length == 0) {
			isCorrectFormat = true;
			Display.displayMsgWelcome();
		} else {
			System.exit(0);
		}
		
		Database.fetchTasksFromFile();

		while (isCorrectFormat) {
			Display.displayMsgPrompt();
			String command = sc.next();
			command = command.toLowerCase();
			executeUserCommand(command.trim());
		}
	}
}
