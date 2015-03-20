package database;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Task;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

//@author A0112734N
public class Database  {
	private static String filepath = "anytasklist.txt";
	private static ArrayList<Task> taskList = new ArrayList<Task>();

	final static Logger logger = LoggerFactory.getLogger(Database.class);
	
	public static ArrayList<Task> getTaskList() {
		assert (taskList!=null);
		return taskList;
	}
	public static void setTaskList(ArrayList<Task> newTaskList){
		saveTasksToFile();
		taskList=newTaskList;
	}
	//@author insert Zirui's admin here
	public static void setFilePath(String userText) {
		saveTasksToFile();
		filepath = userText;
		taskList.clear();
		fetchTasksFromFile();
	}
	
	public static boolean clearFile() {
		try {
			BufferedWriter bWrite = new BufferedWriter(new FileWriter(
					filepath, false));
			bWrite.write("");
			bWrite.close();
			return true;
		} catch (IOException e) {
			logger.error("Error writing to {}",filepath, e);
			return false;
		}
	}
	
	public static boolean fetchTasksFromFile() {
		boolean isFileRead=false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			if (br.ready()) {
				isFileRead= readJsonFile(br);
				br.close();		
				return isFileRead;
			} else {
				br.close();
				return true;
			}
		} catch (IOException e) {	
			logger.info("Error reading {}",filepath, e);
			return true;
		}
	}

	private static boolean readJsonFile(BufferedReader br){
		try {
			Gson gson= new Gson();
			taskList = gson.fromJson(br, new TypeToken<ArrayList<Task>>(){}.getType());	
			if(taskList==null){
				taskList=new ArrayList<Task>();
			}
			return true;
		} catch (JsonParseException e) {
			logger.error("Error reading {}: file exists but is not in json format",filepath, e);
			return false;		
		}
	}
	
	public static boolean saveTasksToFile() {
		try {
			BufferedWriter bWrite = new BufferedWriter(new FileWriter(
					filepath, false));
			Gson gson= new Gson();
			String jsonTask = gson.toJson(taskList);
			bWrite.write(jsonTask);
			bWrite.close();
			return true;
		} catch (IOException e) {
			logger.error("Error writing {}",filepath, e);
			return false;
		}
	}
}
