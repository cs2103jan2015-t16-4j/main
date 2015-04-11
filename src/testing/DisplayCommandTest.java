package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import logic.AddCommand;
import logic.DisplayCommand;
import logic.TagCommand;

import org.junit.Before;
import org.junit.Test;

import common.Task;

import database.Database;
//@author A0119384Y
public class DisplayCommandTest {
	
	private Task taskOne, taskTwo, taskThree, taskFour;

	@Before
	public void before() {
		Database.getInstance().getTaskList().clear();
		AddCommand a; 
		a = new AddCommand("task one", null, null);
		taskOne = a.execute().get(0);
		
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		a = new AddCommand("task two", startTime, endTime);
		taskTwo =  a.execute().get(0);
		String[] tags = { "#done" };
		TagCommand t = new TagCommand("task two", tags);
		t.execute();
		
		
		Calendar endRecurringTime = Calendar.getInstance();
		endRecurringTime.add(Calendar.DATE, 1);
		a = new AddCommand("task three", "daily", startTime, endTime, endRecurringTime);
		ArrayList<Task> r = a.execute();
		taskThree = r.get(0);
		taskFour = r.get(1);
	}
	
	@Test
	public void displayFloatingTest(){
		DisplayCommand d = new DisplayCommand("floating");
		ArrayList<Task> result = d.execute();
		assertEquals(1, result.size());
		assertEquals(taskOne, result.get(0));
	}
	
	@Test
	public void displayAll(){
		DisplayCommand d = new DisplayCommand("all");
		ArrayList<Task> result = d.execute();
		assertEquals(4, result.size());
	}
	
	@Test
	public void displayDone(){
		DisplayCommand d = new DisplayCommand("done");
		ArrayList<Task> result = d.execute();
		assertEquals(1, result.size());
		assertEquals(taskTwo, result.get(0));
	}
	
	@Test
	public void displayDue(){
		DisplayCommand d = new DisplayCommand("due");
		ArrayList<Task> result = d.execute();
		assertEquals(2, result.size());
		assertTrue(result.contains(taskTwo));
		assertTrue(result.contains(taskThree));
	}
	
	@Test
	public void displayKeyword(){
		String[] keywords = { "one", "two" };
		DisplayCommand d = new DisplayCommand(keywords);
		ArrayList<Task> result = d.execute();
		assertEquals(2, result.size());
		assertTrue(result.contains(taskTwo));
		assertTrue(result.contains(taskOne));
	}
	
	@Test
	public void displayTag(){
		DisplayCommand d = new DisplayCommand("done", true);
		ArrayList<Task> result = d.execute();
		assertEquals(1, result.size());
		assertTrue(result.contains(taskTwo));
	}
	
	@Test
	public void displayTimePeriod(){
		Calendar searchStartTime = Calendar.getInstance();
		Calendar searchEndTime = Calendar.getInstance();
		searchStartTime.add(Calendar.DATE, 1);
		searchEndTime.add(Calendar.DATE, 1);
		DisplayCommand d = new DisplayCommand(searchStartTime, searchEndTime);
		ArrayList<Task> result = d.execute();
		assertEquals(1, result.size());
		assertTrue(result.contains(taskFour));
	}
	
	@Test
	public void displayDeadline(){
		Calendar searchDeadline = Calendar.getInstance();
		searchDeadline.add(Calendar.DATE, 1);
		DisplayCommand d = new DisplayCommand(searchDeadline);
		ArrayList<Task> result = d.execute();
		assertEquals(3, result.size());
		assertTrue(result.contains(taskTwo));
		assertTrue(result.contains(taskThree));
		assertTrue(result.contains(taskFour));
	}
	
	@Test
	public void displayRecurring(){
		DisplayCommand d = new DisplayCommand("recurring");
		ArrayList<Task> result = d.execute();
		assertEquals(2, result.size());
		assertTrue(result.contains(taskThree));
		assertTrue(result.contains(taskFour));
	}

}
