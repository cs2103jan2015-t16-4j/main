package common;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.AddCommand;

import org.junit.Test;

public class AddCommandTest {
	
	@Test
	public void addFloatingTaskTest() {
		AddCommand a = new AddCommand("update manual", null ,null);
		ArrayList<Task> result = a.execute();
		assertEquals("update manual",result.get(0).getName());
	}
	
	@Test
	public void addNullTest() {
		/* This is a boundary case for the null value task name*/
		AddCommand a = new AddCommand(null, null ,null);
		ArrayList<Task> result = a.execute();
		assertEquals(null, result.get(0).getName());
	}	

	@Test
	public void addEmptyNameTest() {
		/* This is a boundary case for the empty name task name*/
		AddCommand a = new AddCommand("", null ,null);
		ArrayList<Task> result = a.execute();
		assertEquals("", result.get(0).getName());
	}
}
