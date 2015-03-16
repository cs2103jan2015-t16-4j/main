package database;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import logic.Display;
import common.Task;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class Database {
	private static String filepath = "anytasklist.txt";
	private static ArrayList<Task> taskList = new ArrayList<Task>();

	public static ArrayList<Task> getTaskList() {
		return taskList;
	}

	public static void addTask(Task newTask) {
		taskList.add(newTask);
	}

	public static void addTask(String newTask) {
		taskList.add(new Task(newTask));
		Display.displayMsgAdd(newTask);
	}

	public static boolean fetchTasksFromFile() {

		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			if (br.ready()) {
				try {
					Gson gson= new Gson();
					taskList = gson.fromJson(br, new TypeToken<ArrayList<Task>>(){}.getType());	
				}
				catch (JsonParseException e) {
					//file exists but file is not in json format
					Display.displayMsgError("The file content is in the wrong format."
							+ " Please check the file and try running AnyTask again");
					return false;
				}
			}
			br.close();
		} catch (IOException e) {
			// error
			return false;
		}
		return true;
	}

	public static void editTask(int taskID, String newName) {
		taskList.remove(taskID-1);
		taskList.add(taskID-1, new Task(newName));
	}

	public static void deleteTask(String name) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getName().equals(name)) {
				taskList.remove(i);
			}
		}
		Display.displayMsgDeleteLine(name);
	}

	public static void deleteAllTask() {
		taskList.clear();
	}

	public static boolean saveTasksToFile() {
		try {
			BufferedWriter bWrite = new BufferedWriter(new FileWriter(
					filepath, false));
			Gson gson= new Gson();
			String jsonTask = gson.toJson(taskList);
			bWrite.write(jsonTask);
			bWrite.close();

		} catch (IOException e) {
			// error
			return false;
		}
		return true;
	}

	public static void setFilePath(String userText) {
		saveTasksToFile();
		filepath = userText;
		taskList.clear();
		fetchTasksFromFile();
	}

}
