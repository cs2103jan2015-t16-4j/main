package common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import logic.AddCommand;
import logic.DeleteCommand;
import logic.TagCommand;

import org.junit.Test;

import database.Database;
//@author A0119384Y
public class DeleteCommandTest {
	/* Delete task: DeleteCommand(String name, boolean isDeleteRecurring) */
	/*
	 * Delete others: DeleteCommand(String name, boolean isDeleteRecurring,
	 * String para)
	 */
	@Test
	public void deleteTaskIdTest() {
		AddCommand a = new AddCommand("update manual 1", null, null);
		ArrayList<Task> resultAdd = a.execute();
		assertEquals(1, resultAdd.size());
		assertEquals("update manual 1", resultAdd.get(0).getName());

		DeleteCommand d = new DeleteCommand(resultAdd.get(0).getId(), false);
		ArrayList<Task> resultDelete = d.execute();
		assertEquals(1, resultDelete.size());
		assertTrue(resultDelete.get(0).equals(resultDelete.get(0)));
	}

	@Test
	public void deleteTaskNameTest() {
		AddCommand a = new AddCommand("update manual 2", null, null);
		ArrayList<Task> resultAdd = a.execute();
		assertEquals(1, resultAdd.size());
		assertEquals("update manual 2", resultAdd.get(0).getName());

		DeleteCommand d = new DeleteCommand(resultAdd.get(0).getName(), false);
		System.out.println(Database.getInstance().getTaskList());
		ArrayList<Task> resultDelete = d.execute();
		assertEquals(1, resultDelete.size());
		// assertTrue(resultDelete.get(0).equals(resultDelete.get(0)));
	}

	@Test
	public void deleteAttributeIdTest() {
		Calendar endTime = Calendar.getInstance();
		AddCommand a = new AddCommand("update manual 3", null, endTime);
		ArrayList<Task> resultAdd = a.execute();
		assertEquals(1, resultAdd.size());
		assertEquals("update manual 3", resultAdd.get(0).getName());

		System.out.println(resultAdd);

		DeleteCommand d = new DeleteCommand(resultAdd.get(0).getId(), false,
				"end time");
		ArrayList<Task> resultDelete = d.execute();
		assertEquals(1, resultDelete.size());
		assertEquals(null, resultDelete.get(0).getEndTime());
	}

	@Test
	public void deleteAttributeNameTest() {
		AddCommand a = new AddCommand("update manual 4", null, null);
		ArrayList<Task> resultAdd = a.execute();
		assertEquals(1, resultAdd.size());
		assertEquals("update manual 4", resultAdd.get(0).getName());

		String[] tags = { "done" };
		TagCommand t = new TagCommand(resultAdd.get(0).getId(), false, tags);
		ArrayList<Task> resultTag = t.execute();
		assertEquals(1, resultTag.get(0).getTags().size());

		DeleteCommand d = new DeleteCommand(resultAdd.get(0).getName(), false,
				"#done");
		ArrayList<Task> resultDelete = d.execute();
		assertEquals(1, resultDelete.size());
		assertEquals(0, resultDelete.get(0).getTags().size());
	}
	
	@Test
	public void deleteRecurringTagTest() {
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		Calendar endRecurringTime = Calendar.getInstance();
		endRecurringTime.add(Calendar.DATE, 7); 
		AddCommand a = new AddCommand("update manual recurring", "daily", startTime ,endTime, endRecurringTime);
		ArrayList<Task> resultAdd = a.execute();

		String[] tags = { "done" };
		TagCommand t = new TagCommand(resultAdd.get(0).getId(), true, tags);
		ArrayList<Task> resultTag = t.execute();
		assertEquals(1, resultTag.get(0).getTags().size());

		DeleteCommand d = new DeleteCommand(resultAdd.get(0).getId(), true,
				"#done");
		ArrayList<Task> resultDelete = d.execute();
		assertEquals(8, resultDelete.size());
		for (Task task: resultDelete){
			assertEquals(0, task.getTags().size());
		}
	}
	
	@Test
	public void deleteRecurringTaskTest() {
		Calendar deadLine = Calendar.getInstance();
		Calendar endRecurringTime = Calendar.getInstance();
		endRecurringTime.add(Calendar.DATE, 7); 
		AddCommand a = new AddCommand("update manual recurring", "daily", null ,deadLine, endRecurringTime);
		ArrayList<Task> resultAdd = a.execute();

		DeleteCommand d = new DeleteCommand(resultAdd.get(0).getId(), true);
		ArrayList<Task> resultDelete = d.execute();
		assertEquals(8, resultDelete.size());
		assertEquals(0, Database.getInstance().getTaskList().size());
	}
}
