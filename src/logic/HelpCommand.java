package logic;
import java.util.ArrayList;

import common.Task;

public class HelpCommand extends Command {
	
	public ArrayList<Task> execute(){
		System.out.println("1. display : Displays the title and deadline of all tasks, sorted by deadline.
			2. add  : add tasks (floating, deadline task, recurring task) 
			3. edit : edit tasks that have already been added
			4. delete : delete tasks that have been added
			5. tag : tag tasks with description
			6. done : to tag tasks with done 
			7. undo : undo the previous execution
			8. help : display the commands and their functionality
			9. setpath : to specify a folder other than default one to store data from AnyTask ");

		return null;
	}

	public HelpCommand(){

	}
}