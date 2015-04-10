package logic;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ui.Display;
import common.Task;
import database.Database;

//@author A0112734N
public class Data {
	//@author A0112734N
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

	//@author A0112734N-unused
	public static void editTask(Task oldTask, Task newTask) {
		int index= taskList.indexOf(oldTask);
		taskList.set(index,newTask);
		//sort();
	}
	
	//@author A0112734N-unused
	public static void editTask(int id, Task newTask) {
//		for (int i = 0; i < taskList.size(); i++) {
//			if (taskList.get(i).getId().equals(id)) {
//				taskList.set(i,newTask);
//			}
//		}
		//sort();
	}
	//@author A0112734N-unused
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
	//@author A0112734N-unused
	public static void deleteAllTask() {
		taskList.clear();
	}
	//@author A0112734N-unused
	//for v0.1
	public static void addTask(String newTask) {
		taskList.add(new Task(newTask));
		Display.displayMsgAdd(newTask);
	}
	//@author A0112734N-unused
	//for v0.1
	public static void editTask(int taskID, String newName) {
		taskList.remove(taskID-1);
		taskList.add(taskID-1, new Task(newName));
	}
	//@author A0112734N-unused
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
