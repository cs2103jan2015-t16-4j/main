package common;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.AddCommand;
import logic.DoneCommand;

import org.junit.Before;
import org.junit.Test;

import database.Database;

public class OtherTest {

	/*
	 * InvalidCommand, HelpCommand and ExitCommand unit tests are omitted.
	 * SaveCommand and SetpathCommand unit tests are included in either database unit tests or systematic testing.
	 * Parser unit tests are included in systematic testing.
	 * DoneCommand testing is less important since it reuses codes in TagCommand.
	 */
	
	@Before
	public void before() {
		Database.getInstance().getTaskList().clear();
	}
	
	@Test
	public void doneTest() {
		AddCommand a = new AddCommand("update manual P", null, null);
		ArrayList<Task> resultAdd = a.execute();
		assertEquals("update manual P", resultAdd.get(0).getName());

		DoneCommand d = new DoneCommand(resultAdd.get(0).getName());
		ArrayList<Task> resultTag = d.execute();
		assertEquals(1, resultTag.get(0).getTags().size());
		assertEquals("#done", resultTag.get(0).getTags().get(0));
	}
}
