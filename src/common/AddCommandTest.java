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

}
