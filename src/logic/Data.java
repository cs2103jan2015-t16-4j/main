package logic;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Settings;
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
	
	public static Settings getSettings(){
		Settings setting = Database.getInstance().getSetting();
		return setting;
	}
}
