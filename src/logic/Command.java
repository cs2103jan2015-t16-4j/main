package logic;

import java.util.ArrayList;

import common.Task;

//@author A0119384Y
public abstract class Command {
	private static final String[] KEYWORD_SAVE = { "save" };
	private static final String[] KEYWORD_ADD = { "add","create" };
	private static final String[] KEYWORD_DELETE = { "delete","remove" };
	private static final String[] KEYWORD_EDIT = { "edit","update" };
	private static final String[] KEYWORD_DISPLAY = { "display","search" };
	private static final String[] KEYWORD_TAG = { "tag","mark" };
	private static final String[] KEYWORD_DONE = { "done" };
	private static final String[] KEYWORD_UNDO = { "undo" };
	private static final String[] KEYWORD_SETPATH = { "setpath","path" };
	private static final String[] KEYWORD_INVALID = { "invalid" };
	private static final String[] KEYWORD_EXIT = { "exit" };
	private static final String[] KEYWORD_HELP = { "help" };

	public static enum CommandType {
		SAVE(KEYWORD_SAVE), ADD(KEYWORD_ADD), DELETE(KEYWORD_DELETE), EDIT(
				KEYWORD_EDIT), DISPLAY(KEYWORD_DISPLAY), TAG(KEYWORD_TAG), DONE(
				KEYWORD_DONE), UNDO(KEYWORD_UNDO), SETPATH(KEYWORD_SETPATH), INVALID(
				KEYWORD_INVALID), EXIT(KEYWORD_EXIT), HELP(KEYWORD_HELP);

		private String[] commandTypeDescs;

		CommandType(String[] commandType) {
			this.commandTypeDescs = commandType;
		}

		public String getText() {
			return this.commandTypeDescs[0];
		}

		public static CommandType fromString(String text) {
			if (text != null) {
				for (CommandType b : CommandType.values()) {
					for (String desc : b.commandTypeDescs) {
						if (text.equalsIgnoreCase(desc)) {
							return b;
						}
					}
				}
			}
			return INVALID;
		}
	}

	public abstract ArrayList<Task> execute();
}
