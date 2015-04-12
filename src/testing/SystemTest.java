package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import common.Task;

import parser.DateParser;
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
		assertTrue(taskList.get(taskList.size() - 1).isFloating());
		
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
	public void deadlineTaskTest() {
		this.db.fetchTasksFromFile();
		this.backupList.addAll(this.db.getTaskList());

		ArrayList<Task> taskList = this.db.getTaskList();
		int id;
		
		// add new task for checking of edits
		Parser p = Parser.getInstance();
		p.parseInput("Add new deadline task name by tomorrow");
		p.parseInput("tag new deadline task name #tag");
		id=taskList.get(taskList.size() - 1).getId();
		assertTrue(taskList.get(taskList.size() - 1).isDeadline());
		
		// test edit name using name (Includes keyword in name)
		// checks the editing of a task name, using the same input as a user.
		p.parseInput("edit new deadline task name name to new deadline task1");
		assertEquals("new deadline task1", taskList.get(taskList.size() - 1).getName());
		
		// test edit deadline using name
		// checks the editing of a task name, using the same input as a user.
		Calendar deadline=parseDate("tuesday 12pm");
		p.parseInput("edit new deadline task1 deadline to tuesday 12pm");
		assertEquals(deadline, taskList.get(taskList.size() - 1).getEndTime());
		
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
		p.parseInput("edit "+ Integer.toString(id)+" name to new task 2");
		assertEquals("new task 2", taskList.get(taskList.size() - 1).getName());
		
		// test edit deadline using ID
		// checks the editing of a task name, using the same input as a user.
		Calendar deadline2=parseDate("31 may 7pm");
		p.parseInput("edit "+ Integer.toString(id)+" deadline to 31 may 7pm");
		assertEquals(deadline2, taskList.get(taskList.size() - 1).getEndTime());

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
		assertEquals("new task 2", taskList.get(taskList.size() - 1).getName());
		
		//test delete task using name
		p.parseInput("delete new task 2");
		assertEquals(backupList,taskList);
		assertNotEquals(undoList,taskList);
		
		this.db.setTaskList(this.backupList);
		this.db.saveTasksToFile();
	}
	
	@Test
	public void scheduledTaskTest() {
		this.db.fetchTasksFromFile();
		this.backupList.addAll(this.db.getTaskList());
		ArrayList<Task> taskList = this.db.getTaskList();
		int id;
		
		// add new task for checking of edits
		Parser p = Parser.getInstance();
		p.parseInput("Add new scheduled task name from tuesday 3pm to tuesday 5pm");
		p.parseInput("tag new scheduled task name #tag");
		id=taskList.get(taskList.size() - 1).getId();
		assertTrue(taskList.get(taskList.size() - 1).isScheduled());
		
		// test edit name using name (Includes keyword in name)
		// checks the editing of a task name, using the same input as a user.
		p.parseInput("edit new scheduled task name name to new scheduled task1");
		assertEquals("new scheduled task1", taskList.get(taskList.size() - 1).getName());
		
		// test edit start time using name
		// checks the editing of a task name, using the same input as a user.
		Calendar scheduled=parseDate("monday 12am");
		p.parseInput("edit new scheduled task1 start time to monday 12am");
		assertEquals(scheduled, taskList.get(taskList.size() - 1).getStartTime());
		
		// test edit end time using name
		// checks the editing of a task name, using the same input as a user.
		scheduled=parseDate("tuesday 12pm");
		p.parseInput("edit new scheduled task1 end time to tuesday 12pm");
		assertEquals(scheduled, taskList.get(taskList.size() - 1).getEndTime());

		// test edit tag using name
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("edit new scheduled task1 #tag to #newtag");
		assertEquals("#newtag", taskList.get(taskList.size() - 1).getTags().get(0));
		
		// test done using name
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("done new scheduled task1");
		assertEquals("#done", taskList.get(taskList.size() - 1).getTags().get(1));
		
		//test delete tag using name
		p.parseInput("delete new scheduled task1 #done");
		p.parseInput("delete new scheduled task1 #newtag");
		assertEquals(new ArrayList<String>(),taskList.get(taskList.size() - 1).getTags());
		
		//test add tag using ID
		p.parseInput("tag "+ Integer.toString(id)+" #newtagagain");
		assertEquals("#newtagagain", taskList.get(taskList.size() - 1).getTags().get(0));
		
		// test edit name using ID
		// checks the editing of a task name, using the same input as a user.
		p.parseInput("edit "+ Integer.toString(id)+" name to new task 2");
		assertEquals("new task 2", taskList.get(taskList.size() - 1).getName());
		
		// test edit start time using ID
		// checks the editing of a task name, using the same input as a user.
		scheduled=parseDate("2:00pm");
		p.parseInput("edit "+ Integer.toString(id)+" start time to 14:00");
		assertEquals(scheduled, taskList.get(taskList.size() - 1).getStartTime());
		
		// test edit end time using ID
		// checks the editing of a task name, using the same input as a user.
		scheduled=parseDate("2pm monday 2 weeks later");
		p.parseInput("edit "+ Integer.toString(id)+" end time to 14:00:00 monday 2 weeks later ");
		assertEquals(scheduled, taskList.get(taskList.size() - 1).getEndTime());

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
		assertEquals("new task 2", taskList.get(taskList.size() - 1).getName());
		
		//test delete task using name
		p.parseInput("delete new task 2");
		assertEquals(backupList,taskList);
		assertNotEquals(undoList,taskList);
		
		this.db.setTaskList(this.backupList);
		this.db.saveTasksToFile();
	}
	
	@Test
	public void recurDeadlineTaskTest() {
		this.db.fetchTasksFromFile();
		this.backupList.addAll(this.db.getTaskList());
		this.db.clearFile();
		this.db.fetchTasksFromFile();
		ArrayList<Task> taskList = this.db.getTaskList();

		// add new task for checking of edits
		Parser p = Parser.getInstance();
		p.parseInput("Add first day by 1 Jan 2015 before next year monthly");
		p.parseInput("tag 1 #newmonth recurring");
		assertTrue(taskList.get(taskList.size() - 1).isDeadline());
		
		// test edit name using ID (Includes keyword in name)
		// checks the editing of a task name, using the same input as a user.
		p.parseInput("edit 1 name to first day of the month");
		assertEquals("first day of the month", taskList.get(0).getName());
		
		// test edit name using ID (Includes keyword in name)
		// checks the editing of a task name, using the same input as a user.
		p.parseInput("edit 12 name to first day of the last month");
		assertEquals("first day of the last month", taskList.get(11).getName());
		
		// test edit deadline using ID
		// checks the editing of a task name, using the same input as a user.
		Calendar deadline=parseDate("2 Feb 2015 00:01");
		p.parseInput("edit 2 deadline to 2 Feb 2015 00:01");
		assertEquals(deadline, taskList.get(1).getEndTime());
		
		// test edit tag using ID
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("edit 1 #newmonth to #firstNewMonth");
		assertEquals("#firstNewMonth", taskList.get(0).getTags().get(0));
		
		// test done using ID
		// checks the editing of a new tag, using the same input as a user.
		p.parseInput("done 1");
		assertEquals("#done", taskList.get(0).getTags().get(1));
		
		//test delete tag using ID
		p.parseInput("delete 1 #done");
		p.parseInput("delete 1 #firstNewMonth");
		assertEquals(new ArrayList<String>(),taskList.get(0).getTags());

		//test delete all recurr task using ID
		ArrayList<Task> undoList= new ArrayList<Task>();
		undoList.addAll(taskList);
		p.parseInput("delete 1 recurring");
		assertEquals(new ArrayList<Task>(),taskList);
		assertNotEquals(undoList,taskList);
		
		//test undo
		p.parseInput("undo");
		assertEquals(undoList,taskList);
		assertEquals("first day of the month", taskList.get(0).getName());
		
		//test delete task using ID
		p.parseInput("delete 1");
		assertNotEquals("first day of the month", taskList.get(0).getName());
		assertEquals("first day", taskList.get(0).getName());
		
		//test delete task using ID
		p.parseInput("delete 2 #newmonth recurring");
		for(int i=0; i<taskList.size();i++){
			assertEquals(new ArrayList<String>(),taskList.get(i).getTags());
		}

		this.db.setTaskList(this.backupList);
		this.db.saveTasksToFile();
	}
	
	private Calendar parseDate(String dateString) {
		DateParser dateParser = new DateParser(dateString);
		return dateParser.parseDate().get(0);
	}
}
