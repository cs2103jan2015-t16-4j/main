import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList; 

public class AnyTaskTest {

	@Test
	public void test() {				
		Database.deleteAllTask();
		Database.addTask("a");
		Database.addTask("b");
		Database.addTask("c");

		Database.deleteTask("b");
		
		ArrayList<Task> taskList=Database.getTaskList();
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

