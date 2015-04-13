package ui.messages;

import logic.Command.CommandType;
//@author A0101045
public class HelpMessages {
	/*private static final String MESSAGE_HELP ="	1. display : Displays the title and deadline of all tasks, sorted by deadline.\m"
			+ "	2. add  : add tasks (floating, deadline task, recurring task) <br>"
			+ "	3. edit : edit tasks that have already been added<br>"
			+ "	4. delete : delete tasks that have been added<br>"
			+ "	5. tag : tag tasks with description<br>"
			+ "	6. done : to tag tasks with done <br>"
			+ "	7. undo : undo the previous execution<br>"
			+ "	8. help : display the commands and their functionality<br>"
			+ "	9. setpath : to specify a folder other than default one to store data from AnyTask <br>";*/
	
	//@author A0112734N
	private static final String MESSAGE_HELP ="The following commands are available: add, edit, delete, tag, done, undo, setpath. <br> "+
	"Type \"Help &lt;command&gt;\" for more infomation regarding the command. use \' \' if you want to include keywords in your task names.";
	private static final String MESSAGE_ADD ="<b>Floating Task:</b> add &lt;name&gt; <br> "
			+ "<b>Timed Task:</b> add &lt;name&gt; from &lt;start&gt; to &lt;end&gt; <br> <b>Deadline Task:</b> add &lt;name&gt; by &lt;deadline&gt;<br> "
			+ "<b>Recurring Task:</b> add ... before &lt;end recurring time&gt; [daily|weekly|monthly|annually]";
	private static final String MESSAGE_DELETE ="<b>Delete name:</b> delete &lt;id|name&gt;<br> "
			+"<b>Delete respective attribute:</b> delete &lt;id|name&gt; [start time|end time|deadline] <br>"
			+"<b>Delete tag:</b> delete &lt;id|name&gt; &lt;tag&gt; <br>"
			+"<b>Delete all recurring task:</b> delete &lt;id&gt; recurring<br> "
			+"<b>Delete all recurring tag:</b> delete &lt;id&gt; &lt;tag&gt; recurring";
	private static final String MESSAGE_EDIT ="<b>Edit respective attributes:</b>  edit &lt;id|name&gt; [start time|end time|deadline|name] to &lt;new value&gt; <br> "
			+ "<b>Edit Tag:</b>  edit &lt;id|name&gt; &lt;old tag&gt; to &lt;new tag&gt;";
	private static final String MESSAGE_DISPLAY ="<b>Display uncompleted:</b> display<br> "
			+ "<b>Display both completed and uncompleted:</b> display all<br> "
			+ "<b>Display uncompleted, sorted by date:</b> display sort<br> "
			+ "<b>Display done tasks only:</b> display done<br> "
			+ "<b>Display by tag:</b> display &lt;tag&gt;<br> "
			+ "<b>Display by keywords:</b> display &lt;keyword(s)&gt;<br> "
			+ "<b>Display within time period:</b> display from &lt;start&gt; to &lt;end&gt; <br> "
			+ "<b>Display tasks before certain time:</b> display before &lt;end&gt;<br> "
			+ "<b>Display overdue tasks:</b> display due<br> "
			+ "<b>Displays all recurring tasks:</b> display recurring<br> "
			+ "<b>Displays all floating tasks:</b> display floating";
	private static final String MESSAGE_TAG ="<b>Tag Task:</b> tag &lt;id|name&gt; &lt;tag(s)&gt;<br> "
			+ "<b>Tag recurring tasks:</b> tag &lt;id|name&gt; &lt;tag(s)&gt; recurring";
	private static final String MESSAGE_DONE ="<b>Mark task as done:</b> done &lt;id|name&gt;";
	private static final String MESSAGE_UNDO ="<b>Undo the last command (only once):</b> undo";
	private static final String MESSAGE_SETPATH ="<b>Specify new file location to store tasks:</b> setpath &lt;new path&gt;";
	private static final String MESSAGE_SAVE ="<b>Save the tasks into specified filed location:</b> save";
	private static final String MESSAGE_EXIT ="<b>Save and quit the program :</b> exit";


	public static String getMsgHelp(String line) {		
		switch (CommandType.fromString(line)) {
			case ADD:
				return String.format(MESSAGE_ADD);
			case DELETE:
				return String.format(MESSAGE_DELETE);
			case EDIT:
				return String.format(MESSAGE_EDIT);
			case DISPLAY:
				return String.format(MESSAGE_DISPLAY);
			case TAG:
				return String.format(MESSAGE_TAG);
			case DONE:
				return String.format(MESSAGE_DONE);
			case UNDO:
				return String.format(MESSAGE_UNDO);
			case SETPATH:
				return String.format(MESSAGE_SETPATH);
			case HELP:
				return String.format(MESSAGE_HELP);
			case SAVE:
				return String.format(MESSAGE_SAVE);
			case INVALID:
				return String.format(MESSAGE_HELP);
			case EXIT:
				return String.format(MESSAGE_EXIT);
			default:
				return String.format(MESSAGE_HELP);
		}
	}
}
