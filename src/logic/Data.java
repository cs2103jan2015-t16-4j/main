package logic;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Task;
import database.Database;

//@author A0112734N
public class Data {
	
	private static ArrayList<Task> taskList= new ArrayList<Task>();
	final static Logger logger = LoggerFactory.getLogger(Database.class);
	
	public static boolean initTaskList(){
		Database db = Database.getInstance();
		if(db.fetchTasksFromFile()){
			taskList=db.getTaskList();
			return true;
		}
		return false;
	}
	
	public static ArrayList<Task> getTaskList(){
		Database db = Database.getInstance();
		return db.getTaskList();
	}
	
	public static void setTaskList(ArrayList<Task> newTaskList){
		taskList=newTaskList;
	}
	
	public static void addTask(Task newTask) {
		taskList.add(newTask);
	}
	
	public static void editTask(Task oldTask, Task newTask) {
		int index= taskList.indexOf(oldTask);
		taskList.set(index,newTask);
		//sort();
	}
	
	public static void editTask(int id, Task newTask) {
//		for (int i = 0; i < taskList.size(); i++) {
//			if (taskList.get(i).getId().equals(id)) {
//				taskList.set(i,newTask);
//			}
//		}
		//sort();
	}
	
	public static void deleteTask(Task task) {
		taskList.remove(task);
	}
	
	public static void deleteTask(int id) {
//		for (int i = 0; i < taskList.size(); i++) {
//			if (taskList.get(i).getId().equals(id)) {
//				taskList.remove(i);
//			}
//		}
	}
	
	public static void deleteAllTask() {
		taskList.clear();
	}
	
	//for v0.1
	public static void addTask(String newTask) {
		taskList.add(new Task(newTask));
		Display.displayMsgAdd(newTask);
	}
	
	//for v0.1
	public static void editTask(int taskID, String newName) {
		taskList.remove(taskID-1);
		taskList.add(taskID-1, new Task(newName));
	}
	
	//for v0.1
	public static void deleteTask(String name) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getName().equals(name)) {
				taskList.remove(i);
				Display.displayMsgDeleteLine(name);
			}
		}
	}
}
