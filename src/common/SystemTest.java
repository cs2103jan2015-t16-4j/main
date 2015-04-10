package common;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import parser.Parser;
import database.Database;

public class SystemTest {
	//@author A0112734N
	
	// create a backup list so that we can put back the data in the file after
	// the testing
	private ArrayList<Task> backupList = new ArrayList<Task>();
	private Database db = Database.getInstance();

	@Test
	public void addFloatingTaskTest() {
		this.db.fetchTasksFromFile();
		this.backupList.addAll(this.db.getTaskList());
		ArrayList<Task> taskList = this.db.getTaskList();

		// checks the adding of a new floating task, using the same input as a
		// user.
		Parser p = Parser.getInstance();
		p.parseInput("Add new floating task");
		// checks the adding of a new tag, using the same input as a user.
		p.parseInput("tag new floating task #tag");

		// test task name
		assertEquals("new floating task", taskList.get(taskList.size() - 1)
				.getName());

		// test tag
		assertEquals("#tag", taskList.get(taskList.size() - 1).getTags().get(0));

	}

	@Test
	public void editFloatingTaskTest() {
		this.db.fetchTasksFromFile();
		this.backupList.addAll(this.db.getTaskList());
		ArrayList<Task> taskList = this.db.getTaskList();
		int id;
		
		// add new task for checking of edits
		Parser p = Parser.getInstance();
		p.parseInput("Add new floating task name");
		p.parseInput("tag new floating task name #tag");
		id=taskList.get(taskList.size() - 1).getId();
		
		// test edit name using name (Includes keyword in name)
		// checks the editing of a task name, using the same input as a user.
		p.parseInput("edit new floating task name name to new floating task1");
		assertEquals("new floating task1", taskList.get(taskList.size() - 1).getName());
		
		// test edit tag using name
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("edit new floating task1 #tag to #newtag");
		assertEquals("#newtag", taskList.get(taskList.size() - 1).getTags().get(0));
		
		// test done using name
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("done new floating task1");
		assertEquals("#done", taskList.get(taskList.size() - 1).getTags().get(1));
		
		//test delete tag using name
		p.parseInput("delete new floating task1 #done");
		p.parseInput("delete new floating task1 #newtag");
		assertEquals(new ArrayList<String>(),taskList.get(taskList.size() - 1).getTags());
		
		//test add tag using ID
		p.parseInput("tag "+ Integer.toString(id)+" #newtagagain");
		assertEquals("#newtagagain", taskList.get(taskList.size() - 1).getTags().get(0));
		
		// test edit name using ID
		// checks the editing of a task name, using the same input as a user.
		p.parseInput("edit "+ Integer.toString(id)+" name to new floating 2");
		assertEquals("new floating 2", taskList.get(taskList.size() - 1).getName());

		// test edit tag using ID
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("edit "+ Integer.toString(id)+" #newtag to #newtagagain");
		assertEquals("#newtagagain", taskList.get(taskList.size() - 1).getTags().get(0));
		
		// test done using ID
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("done "+ Integer.toString(id));
		assertEquals("#done", taskList.get(taskList.size() - 1).getTags().get(1));
		
		//test delete tag using ID
		p.parseInput("delete "+ Integer.toString(id)+" #done");
		assertEquals("#newtagagain", taskList.get(taskList.size() - 1).getTags().get(0));
		
		//test delete task using ID
		ArrayList<Task> undoList= new ArrayList<Task>();
		undoList.addAll(this.db.getTaskList());
		p.parseInput("delete "+ Integer.toString(id));
		assertEquals(backupList,taskList);
		assertNotEquals(undoList,taskList);
		
		//test undo
		p.parseInput("undo");
		assertEquals(undoList,taskList);
		assertEquals("new floating 2", taskList.get(taskList.size() - 1).getName());
		
		//test delete task using name
		p.parseInput("delete new floating 2");
		assertEquals(backupList,taskList);
		assertNotEquals(undoList,taskList);
		
		this.db.setTaskList(this.backupList);
		this.db.saveTasksToFile();
	}
	
	@Test
	public void editDeadlineTaskTest() {
		this.db.fetchTasksFromFile();
		this.backupList.addAll(this.db.getTaskList());
		ArrayList<Task> taskList = this.db.getTaskList();
		int id;
		
		// add new task for checking of edits
		Parser p = Parser.getInstance();
		p.parseInput("Add new deadline task name by tomorrow");
		p.parseInput("tag new deadline task name #tag");
		id=taskList.get(taskList.size() - 1).getId();
		
		// test edit name using name (Includes keyword in name)
		// checks the editing of a task name, using the same input as a user.
		p.parseInput("edit new deadline task name name to new deadline task1");
		assertEquals("new deadline task1", taskList.get(taskList.size() - 1).getName());
		
		// test edit tag using name
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("edit new deadline task1 #tag to #newtag");
		assertEquals("#newtag", taskList.get(taskList.size() - 1).getTags().get(0));
		
		// test done using name
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("done new deadline task1");
		assertEquals("#done", taskList.get(taskList.size() - 1).getTags().get(1));
		
		//test delete tag using name
		p.parseInput("delete new deadline task1 #done");
		p.parseInput("delete new deadline task1 #newtag");
		assertEquals(new ArrayList<String>(),taskList.get(taskList.size() - 1).getTags());
		
		//test add tag using ID
		p.parseInput("tag "+ Integer.toString(id)+" #newtagagain");
		assertEquals("#newtagagain", taskList.get(taskList.size() - 1).getTags().get(0));
		
		// test edit name using ID
		// checks the editing of a task name, using the same input as a user.
		p.parseInput("edit "+ Integer.toString(id)+" name to new deadline 2");
		assertEquals("new deadline 2", taskList.get(taskList.size() - 1).getName());

		// test edit tag using ID
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("edit "+ Integer.toString(id)+" #newtag to #newtagagain");
		assertEquals("#newtagagain", taskList.get(taskList.size() - 1).getTags().get(0));
		
		// test done using ID
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("done "+ Integer.toString(id));
		assertEquals("#done", taskList.get(taskList.size() - 1).getTags().get(1));
		
		//test delete tag using ID
		p.parseInput("delete "+ Integer.toString(id)+" #done");
		assertEquals("#newtagagain", taskList.get(taskList.size() - 1).getTags().get(0));
		
		//test delete task using ID
		ArrayList<Task> undoList= new ArrayList<Task>();
		undoList.addAll(this.db.getTaskList());
		p.parseInput("delete "+ Integer.toString(id));
		assertEquals(backupList,taskList);
		assertNotEquals(undoList,taskList);
		
		//test undo
		p.parseInput("undo");
		assertEquals(undoList,taskList);
		assertEquals("new deadline 2", taskList.get(taskList.size() - 1).getName());
		
		//test delete task using name
		p.parseInput("delete new deadline 2");
		assertEquals(backupList,taskList);
		assertNotEquals(undoList,taskList);
		
		this.db.setTaskList(this.backupList);
		this.db.saveTasksToFile();
	}
	
	

}
