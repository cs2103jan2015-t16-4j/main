package common;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import parser.Parser;
import database.Database;

public class SystemTest {

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

		// add new task for checking of edits
		Parser p = Parser.getInstance();
		p.parseInput("Add new floating task");
		p.parseInput("tag new floating task #tag");

		// checks the editing of a task name, using the same input as a user.
		p.parseInput("edit new floating task name to new floating task");
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("edit new floating task #tag to #newtag");

		// test task name
		assertEquals("new floating task", taskList.get(taskList.size() - 1)
				.getName());

		// test tag
		assertEquals("#newtag", taskList.get(taskList.size() - 1).getTags()
				.get(0));

		this.db.setTaskList(this.backupList);
		this.db.saveTasksToFile();
	}

}
