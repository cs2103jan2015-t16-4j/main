package common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import logic.AddCommand;
import logic.TagCommand;

import org.junit.Before;
import org.junit.Test;

import database.Database;
//@author A0119384Y
public class TagCommandTest {
	/*
	 * Non recurring: TagCommand(String name, String[] tags), TagCommand(int id,
	 * boolean isTagRecurring, String[] tags)
	 * 
	 * Recurring: TagCommand(int id, boolean isTagRecurring, String[] tags)
	 */
	
	@Before
	public void before() {
		Database.getInstance().getTaskList().clear();
	}
	
	@Test
	public void tagRecurringTest() {
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		Calendar endRecurringTime = Calendar.getInstance();
		endRecurringTime.add(Calendar.DATE, 7);
		AddCommand a = new AddCommand("update manual recurring", "daily",
				startTime, endTime, endRecurringTime);
		ArrayList<Task> resultAdd = a.execute();

		String[] tags = { "done" };
		TagCommand t = new TagCommand(resultAdd.get(0).getId(), true, tags);
		ArrayList<Task> resultTag = t.execute();
		for (Task task : resultTag) {
			assertEquals(1, task.getTags().size());
			assertEquals("#done", task.getTags().get(0));
		}
	}

	@Test
	public void tagTest() {
		AddCommand a = new AddCommand("update manual Q", null, null);
		ArrayList<Task> resultAdd = a.execute();
		assertEquals(1, resultAdd.size());
		assertEquals("update manual Q", resultAdd.get(0).getName());

		String[] tags = { "done" };
		TagCommand t = new TagCommand(resultAdd.get(0).getName(), tags);
		ArrayList<Task> resultTag = t.execute();
		assertEquals(1, resultTag.get(0).getTags().size());
		assertEquals("#done", resultTag.get(0).getTags().get(0));
	}

}
