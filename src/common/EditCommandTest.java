package common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import logic.AddCommand;
import logic.EditCommand;
import logic.TagCommand;

import org.junit.Before;
import org.junit.Test;

import database.Database;
//@author A0119384Y
public class EditCommandTest {
	/* Edit name: EditCommand(String oldName/int id, String newName) */
	/* Edit deadline: EditCommand(String name/int id, Calendar newDeadline) */
	/*
	 * Edit startTime/endTime: EditCommand(String name/int id, Calendar
	 * newStartTime, Calendar newEndTime)
	 */
	/*
	 * Edit one tag: EditCommand(String name/int id, String oldTag, String
	 * newTag)
	 */
	
	@Before
	public void before() {
		Database.getInstance().getTaskList().clear();
	}
	
	@Test
	public void editNameTest() {
		AddCommand a = new AddCommand("update manual 1", null, null);
		ArrayList<Task> resultAdd = a.execute();
		assertEquals(1, resultAdd.size());
		assertEquals("update manual 1", resultAdd.get(0).getName());

		EditCommand e = new EditCommand(resultAdd.get(0).getName(),
				"new update manual 1");
		ArrayList<Task> resultEdit = e.execute();
		assertEquals(1, resultEdit.size());
		assertEquals("new update manual 1", resultAdd.get(0).getName());
	}

	@Test
	public void editNameDuplicateTest() {
		AddCommand a = new AddCommand("update manual 1", null, null);
		a.execute();
		ArrayList<Task> resultAdd = a.execute();
		assertEquals(1, resultAdd.size());
		assertEquals("update manual 1", resultAdd.get(0).getName());

		EditCommand e = new EditCommand(resultAdd.get(0).getName(),
				"new update manual 1");
		ArrayList<Task> resultEdit = e.execute();
		assertEquals(null, resultEdit);
	}

	@Test
	public void editDeadlineIdTest() {
		Calendar deadline = Calendar.getInstance();
		AddCommand a = new AddCommand("update manual 3", null, deadline);
		ArrayList<Task> resultAdd = a.execute();

		Calendar newDeadline = Calendar.getInstance();
		EditCommand e = new EditCommand(resultAdd.get(0).getId(), newDeadline);
		ArrayList<Task> resultEdit = e.execute();
		assertEquals(1, resultEdit.size());
		assertEquals(newDeadline, resultAdd.get(0).getEndTime());
		assertTrue(deadline != resultAdd.get(0).getEndTime());
	}

	@Test
	public void editStartTimeIdTest() {
		Calendar startTime = Calendar.getInstance();
		AddCommand a = new AddCommand("update manual 7", null, startTime);
		ArrayList<Task> resultAdd = a.execute();

		Calendar newStartTime = Calendar.getInstance();
		EditCommand e = new EditCommand(resultAdd.get(0).getName(),
				newStartTime, null);
		ArrayList<Task> resultEdit = e.execute();
		assertEquals(1, resultEdit.size());
		assertEquals(newStartTime, resultAdd.get(0).getStartTime());
		assertTrue(startTime != resultAdd.get(0).getStartTime());
	}

	@Test
	public void editTagIdTest() {
		Calendar startTime = Calendar.getInstance();
		AddCommand a = new AddCommand("update manual 10", null, startTime);
		ArrayList<Task> resultAdd = a.execute();

		String[] tags = { "#done" };
		TagCommand t = new TagCommand(resultAdd.get(0).getId(), false, tags);
		ArrayList<Task> resultTag = t.execute();
		assertEquals(1, resultTag.get(0).getTags().size());

		EditCommand e = new EditCommand(resultAdd.get(0).getName(), "#done",
				"#urgent");
		ArrayList<Task> resultEdit = e.execute();
		assertEquals(1, resultEdit.size());
		assertTrue(resultAdd.get(0).getTags().contains("#urgent"));
		assertTrue(!resultAdd.get(0).getTags().contains("#done"));
	}

	@Test
	public void editNameIsNumeric() {
		/*
		 * The name of some tasks is numeric. If the name is numeric, first try
		 * to search as Id, then search as name if the preceding search is
		 * unsuccessfully executed,
		 */
		AddCommand a = new AddCommand("one", null, null);
		a.execute();
		a.execute();
		a = new AddCommand("13", null, null);
		ArrayList<Task> resultAdd = a.execute();
		assertEquals(1, resultAdd.size());

		EditCommand e = new EditCommand(resultAdd.get(0).getName(), "23");
		ArrayList<Task> resultEdit = e.execute();
		assertEquals(1, resultEdit.size());
		assertEquals("23", resultAdd.get(0).getName());
		assertEquals("23", resultEdit.get(0).getName());
	}

}
