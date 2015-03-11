import java.util.ArrayList;

public class Display {
	
	public static final String MESSAGE_WELCOME="Welcome to AnyTask.\n";
	public static final String MESSAGE_ADD="Added %s \n";
	public static final String MESSAGE_DELETELINE="Deleted %s \n";
	public static final String MESSAGE_DISPLAY_SUCCESS="%d. %s \n";
	public static final String MESSAGE_DISPLAY_EMPTY="There are no tasks to display \n";
	public static final String MESSAGE_PROMPT="Command: \n";
	public static final String MESSAGE_ERROR="ERROR: \n";

	private static String output ="";
	
	private static void print(){
		System.out.printf(output);
	}

	public static void displayMsgWelcome() {
		output = String.format(MESSAGE_WELCOME);
		print();
	}

	public static void displayMsgAdd(String userText){
    	output = String.format(MESSAGE_ADD,userText);
    	print();
    }
    
	public static void displayMsgDeleteLine(String line){
    	output = String.format(MESSAGE_DELETELINE, line);
    	print();
    }
    
	public static void displayMsgDisplay(int i,String line){
    	output = String.format(MESSAGE_DISPLAY_SUCCESS, i, line);
    	print();
    }
    
	public static void displayMsgEmpty(){
    	output = String.format(MESSAGE_DISPLAY_EMPTY);
    	print();
    }
    
	public static void displayMsgPrompt(){
    	output = String.format(MESSAGE_PROMPT);
    	print();
    }
	
	public static void displayAll(){
		ArrayList<Task> taskList=Database.getTaskList();
		int i=1;
		
		if (taskList.isEmpty()) {
		    displayMsgEmpty();
		}
		else{
			for(Task task: taskList) {
				displayMsgDisplay(i,task.getName());
		    }
			i++;
		}
	}
}
