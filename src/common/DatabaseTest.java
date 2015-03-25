package common;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Test;


import database.Database;
import common.Task;

public class DatabaseTest {
	
	private Database db = Database.getInstance();
	//create a backup list so that we can put back the data in the file after the testing
	private ArrayList<Task> backupList=new ArrayList<Task>();;
	@Test
	public void testClearSaveFile() {
		db.fetchTasksFromFile();
		backupList.addAll(db.getTaskList());
		
		ArrayList<Task> taskList=db.getTaskList();
		
		//Empty list to check against the taskList
		ArrayList<Task> checkList=new ArrayList<Task>();
		
		//Make sure there is at least one task in task list.
		Task task = new Task("task name 1");
		taskList.add(task);
		task = new Task("task name 2");
		taskList.add(task);
		db.saveTasksToFile();
		//Clear the saved file.
		db.clearFile();
		
		//Refetch the task list from saved file
		db.fetchTasksFromFile();
		taskList=db.getTaskList();
		
		db.setTaskList(backupList);
		db.saveTasksToFile();
		

		assertEquals(checkList,taskList);
	}
	
	@Test
	public void testSaveAndFetch() {
		db.fetchTasksFromFile();
		backupList.addAll(db.getTaskList());
		
		db.clearFile();		
		db.fetchTasksFromFile();
		ArrayList<Task> taskList=db.getTaskList();
		
		ArrayList<Task> checkList=new ArrayList<Task>();
		
		//add tasks to both lists
		Task task = new Task("Task name 1");
		db.getTaskList().add(task);
		checkList.add(task);
		
		task = new Task("Task name 2");
		db.getTaskList().add(task);
		checkList.add(task);
		
		db.saveTasksToFile();
		db.fetchTasksFromFile();
		
		//rewrite tasklist with tasks that were saved to file
		taskList=db.getTaskList();
		
		db.setTaskList(backupList);
		db.saveTasksToFile();
		
		assertEquals(checkList,taskList);
	}
	
}

