package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import common.Settings;
import common.Task;

//@author A0112734N
public class Database {
	private static class DatabaseLoader {
		private static final Database INSTANCE = new Database();
	}

	private static String filepath = "anytasklist.txt";
	private static int id = -1;

	final static Logger logger = LoggerFactory.getLogger(Database.class);

	private static ArrayList<Task> taskList = new ArrayList<Task>();

	public static Database getInstance() {
		return DatabaseLoader.INSTANCE;
	}

	private Database() {
		if (DatabaseLoader.INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
	}

	public boolean clearFile() {
		try {
			BufferedWriter bWrite = new BufferedWriter(new FileWriter(filepath,
					false));
			bWrite.write("");
			bWrite.close();
			return true;
		} catch (IOException e) {
			logger.error("Error writing to {}", filepath, e);
			return false;
		}
	}

	public boolean fetchTasksFromFile() {
		boolean isFileRead = false;
		Settings.setFilePath(filepath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			if (br.ready()) {
				isFileRead = this.readJsonFile(br);
				br.close();
				return isFileRead;
			} else {
				br.close();
				return true;
			}
		} catch (IOException e) {
			logger.info("Error reading {}", filepath, e);
			return true;
		}
	}

	public int getId() {
		id++;
		return id;
	}

	public ArrayList<Task> getTaskList() {
		assert (taskList != null);
		return taskList;
	}

	private boolean readJsonFile(BufferedReader br) {
		try {
			Gson gson = new Gson();
			taskList = gson.fromJson(br, new TypeToken<ArrayList<Task>>() {
			}.getType());
			if (taskList == null) {
				taskList = new ArrayList<Task>();
			}
			this.setIdFromList();
			return true;
		} catch (JsonParseException e) {
			logger.error(
					"Error reading {}: file exists but is not in json format",
					filepath, e);
			return false;
		}
	}

	public boolean saveTasksToFile() {
		try {
			BufferedWriter bWrite = new BufferedWriter(new FileWriter(filepath,
					false));
			Gson gson = new Gson();
			String jsonTask = gson.toJson(taskList);
			bWrite.write(jsonTask);
			bWrite.close();
			return true;
		} catch (IOException e) {
			logger.error("Error writing {}", filepath, e);
			return false;
		}
	}

	// @author A0119384Y
	public void setFilePath(String userText) {
		this.saveTasksToFile();
		filepath = userText;
		taskList.clear();
		if (this.fetchTasksFromFile()) {
			Settings.setFilePath(filepath);
		}
	}

	public void setId(int newId) {
		id = newId;
	}

	private void setIdFromList() {
		int taskid;
		id = 0;
		for (int i = 0; i < taskList.size(); i++) {
			taskid = taskList.get(i).getId();
			if (id < taskList.get(i).getId()) {
				id = taskid;
			}
		}
	}

	public void setTaskList(ArrayList<Task> newTaskList) {
		this.saveTasksToFile();
		taskList = newTaskList;
	}

}
