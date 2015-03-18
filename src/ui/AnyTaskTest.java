package ui;

import common.Task;
import static org.junit.Assert.*;

import org.junit.Test;

import database.Database;
import logic.Data;

import java.util.ArrayList; 

public class AnyTaskTest {

	@Test
	public void test() {				
		Data.deleteAllTask();
		Data.addTask("a");
		Data.addTask("b");
		Data.addTask("c");
		Data.deleteTask("b");
		ArrayList<Task> taskList=Data.getTaskList();
		ArrayList<String> taskListName=new ArrayList<String>();
		for(Task task: taskList) {
			taskListName.add(task.getName());
	    }
		ArrayList<String> testList=new ArrayList<String>();
		testList.add("a");
		testList.add("c");

		assertEquals(taskListName,testList);
	}

}

