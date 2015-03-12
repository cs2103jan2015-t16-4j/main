import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
	private static String FILE_NAME="anytasklist.txt";
	private static ArrayList<Task> taskList=new ArrayList<Task>();
	
	public static ArrayList<Task> getTaskList(){
		return taskList;
	}
	
	public static void addTask(Task newTask){
		taskList.add(newTask);
	}
	
	public static void addTask(String newTask){
		taskList.add(new Task(newTask));
	}
	
	public static void fetchTasksFromFile(){
		String line="";
		try{
			BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
			line=br.readLine();
			if (line== null) {
			    //no tasks
			}
			else{
				do{
					// for floating tasks only
					Task newTask=new Task(line);
					addTask(newTask);
				}while((line=br.readLine())!=null);
			}
			br.close();
		}
		catch(IOException e){
		    //error
		}
	}
	
	public static void editTask(String oldName,String newName){
		for(int i=0; i<taskList.size();i++) {
	    	if(taskList.get(i).getName().equals(oldName)){
	    		taskList.remove(i);
	    		taskList.add(i,new Task(newName));
	    	}
	    }
	}
	
	public static void deleteTask(String name){
		for(int i=0; i<taskList.size();i++) {
	    	if(taskList.get(i).getName().equals(name)){
	    		taskList.remove(i);
	    	}
	    }
	}
	
	public static void deleteAllTask(){
		taskList.clear();
	}

	public static void saveTasksToFile(){
		try{
		    BufferedWriter bWrite = new BufferedWriter(new FileWriter(FILE_NAME,true));
		    for(Task task: taskList) {
		    	bWrite.write(task.getName());
		    }
		    bWrite.close();
		    
		}
		catch(IOException e){
			//error
		}
	}

	public static void alterFilePath(String userText) {
		saveTasksToFile();
		FILE_NAME = userText;
		taskList.clear();
		fetchTasksFromFile();
	}
	

}
