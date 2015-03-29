package logic;

import java.util.ArrayList;

import common.Task;

//@author A0119384Y
public abstract class Command {
	public static enum CommandType {
		SAVE("save"), ADD("add"), DELETE("delete"), EDIT("edit"), DISPLAY("display"), TAG(
				"tag"), DONE("done"), UNDO("undo"), SETPATH("setpath"), INVALID(
				"invalid"), EXIT("exit"), HELP("help");

		private String commandType;

		CommandType(String commandType) {
			this.commandType = commandType;
		}

		public String getText() {
			return this.commandType;
		}

		public static CommandType fromString(String text) {
			if (text != null) {
				for (CommandType b : CommandType.values()) {
					if (text.equalsIgnoreCase(b.commandType)) {
						return b;
					}
				}
			}
			return INVALID;
		}
	}

	public abstract ArrayList<Task> execute();
}
