package ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import common.Task;
import parser.Parser;
import logic.Data;
import database.Database;
//@author A0112734N-unused
// non GUI main class
public class AnyTask {
	private static final String MESSAGE_DISPLAY_NOT_EMPTY = "%-10d %-10s %-20s %-10s\n";
	private static final String MESSAGE_DISPLAY_EMPTY = "No result\n";
	private static final String MESSAGE_INVALID = "Invalid command\n";
	
	private static Scanner sc = new Scanner(System.in);


	public static void main(String[] args) {
		if (args.length == 0) {
			Display.displayMsgWelcome();
		} else if (args.length == 1) {
			Database db = Database.getInstance();
			db.setFilePath(args[0]);
			Display.displayMsgWelcome();
		} else {
			System.exit(0);
		}

		if (!Data.initTaskList()) {
			Display.displayMsgError();
			System.exit(0);
		}

		while (true) {
			Display.displayMsgPrompt();
			String command = sc.nextLine();
			processCommand(command);
		}
	}
	public static void processCommand(String command) {
		Parser p = Parser.getInstance();
		try {
			displayResults(p.parseInput(command));
		} catch (Exception e) {
			System.out.println("Invalid command");
		}
	}
	//@author A0119384Y-unused
	private static void displayResults(ArrayList<Task> taskList) {
		if (taskList == null){
		} else if (taskList.size() > 0) {
			System.out.printf("%-10s %-10s %-20s %-10s\n", "Task ID",
					"Task Name", "End Time", "Tags");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			for (int index = 0; index < taskList.size(); index++) {
				System.out.printf(
						MESSAGE_DISPLAY_NOT_EMPTY,
						taskList.get(index).getId(),
						taskList.get(index).getName(),
						(taskList.get(index).getEndTime() != null) ? format
								.format(taskList.get(index).getEndTime()
										.getTime()) : "none",
						taskList.get(index).getTags());
			}
		} else if (taskList.size() == 0){
			System.out.printf(MESSAGE_DISPLAY_EMPTY);
		}
		else{
			System.out.printf(MESSAGE_INVALID);
		}
	}
}
