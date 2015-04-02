package ui.messages;

public class HelpMessages {
	/*private static final String MESSAGE_HELP ="	1. display : Displays the title and deadline of all tasks, sorted by deadline.\m"
			+ "	2. add  : add tasks (floating, deadline task, recurring task) \n"
			+ "	3. edit : edit tasks that have already been added\n"
			+ "	4. delete : delete tasks that have been added\n"
			+ "	5. tag : tag tasks with description\n"
			+ "	6. done : to tag tasks with done \n"
			+ "	7. undo : undo the previous execution\n"
			+ "	8. help : display the commands and their functionality\n"
			+ "	9. setpath : to specify a folder other than default one to store data from AnyTask \n";*/
	
	private static final String MESSAGE_HELP ="The following commands are available: add, edit, delete, tag, done, undo, setpath. \n Type \"Help <command>\" for more infomation regarding the command.";
	
	public static String getMsgHelp(String line) {
		// TODO: Help Command Display
		return String.format(MESSAGE_HELP);
	}
}
