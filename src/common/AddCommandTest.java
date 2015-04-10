package common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import logic.AddCommand;

import org.junit.Test;

//@author A0119384Y
public class AddCommandTest {

	/*
	 * Non recurring: AddCommand(String name, Calendar startTime, Calendar
	 * endTime)
	 * 
	 * Recurring: AddCommand(String name, String recurringCycle, Calendar
	 * startTime, Calendar endTime, Calendar endRecurringTimeCalendar)
	 */
	@Test
	public void addFloatingTaskTest() {
		AddCommand a = new AddCommand("update manual", null, null);
		ArrayList<Task> result = a.execute();
		assertEquals(1, result.size());
		assertEquals("update manual", result.get(0).getName());
		assertEquals(null, result.get(0).getStartTime());
		assertEquals(null, result.get(0).getEndTime());
		assertEquals(-1, result.get(0).getRecurringId());
	}

	@Test
	public void addTimeTaskTest() {
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		AddCommand a = new AddCommand("update manual", startTime, endTime);
		ArrayList<Task> result = a.execute();
		assertEquals(1, result.size());
		assertEquals("update manual", result.get(0).getName());
		assertEquals(startTime, result.get(0).getStartTime());
		assertEquals(endTime, result.get(0).getEndTime());
		assertEquals(-1, result.get(0).getRecurringId());
	}

	@Test
	public void addDeadlineTaskTest() {
		Calendar deadline = Calendar.getInstance();
		AddCommand a = new AddCommand("update manual", null, deadline);
		ArrayList<Task> result = a.execute();
		assertEquals(1, result.size());
		assertEquals("update manual", result.get(0).getName());
		assertEquals(null, result.get(0).getStartTime());
		assertEquals(deadline, result.get(0).getEndTime());
		assertEquals(-1, result.get(0).getRecurringId());
	}

	@Test
	public void addRecurringTimeTaskTest() {
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		Calendar endRecurringTime = Calendar.getInstance();
		AddCommand a = new AddCommand("update manual", "weekly", startTime,
				endTime, endRecurringTime);
		ArrayList<Task> result = a.execute();
		assertEquals(1, result.size());
		assertEquals("update manual", result.get(0).getName());
		assertEquals(startTime, result.get(0).getStartTime());
		assertEquals(endTime, result.get(0).getEndTime());
		assertTrue(result.get(0).getRecurringId() > 0);
	}

	@Test
	public void addRecurringDeadlineTaskTest() {
		Calendar deadLine = Calendar.getInstance();
		Calendar endRecurringTime = Calendar.getInstance();
		endRecurringTime.add(Calendar.DATE, 7);
		AddCommand a = new AddCommand("update manual", "daily", null, deadLine,
				endRecurringTime);
		ArrayList<Task> result = a.execute();
		assertEquals(8, result.size());
		assertEquals("update manual", result.get(0).getName());
		assertEquals(null, result.get(0).getStartTime());
		assertEquals(deadLine, result.get(0).getEndTime());
		assertTrue(result.get(0).getRecurringId() > 0);
	}

	@Test
	public void addNullTest() {
		/* This is a boundary case for the null value task name */
		AddCommand a = new AddCommand(null, null, null);
		ArrayList<Task> result = a.execute();
		assertEquals(null, result.get(0).getName());
	}

	@Test
	public void addEmptyNameTest() {
		/* This is a boundary case for the empty name task name */
		AddCommand a = new AddCommand("", null, null);
		ArrayList<Task> result = a.execute();
		assertEquals(1, result.size());
		assertEquals("", result.get(0).getName());
		assertEquals(null, result.get(0).getStartTime());
		assertEquals(null, result.get(0).getEndTime());
		assertEquals(-1, result.get(0).getRecurringId());
	}

	@Test(expected = NullPointerException.class)
	public void addNullRecurringTest() {
		/* This is a boundary case for the null value task name */
		AddCommand a = new AddCommand(null, null, null, null, null);
		a.execute();
	}
}
