package ui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import parser.Parser;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;

import logic.Data;
import logic.Display;
import logic.Command.CommandType;

import common.Task;
import ui.messages.GeneralMessages;

public class Gui {
	private static TaskTableModel model;
	private static JTextArea textArea;
	//kept here for testing from console
	private static Scanner sc = new Scanner(System.in);
	private static JScrollPane taskScrollPane;
	private static JFrame frame;
	private static final String CONSTANT_SPACE = " ";
	
	public Gui(){
		initFrame();
		setTextArea(GeneralMessages.getMsgWelcome());
	}

	public static void updateTable(){
		model.fireTableDataChanged();
	}

	public static void setTextArea(String toDisplay){
		textArea.setText(toDisplay);
	}
	
	private static JScrollPane initTablePane(ArrayList<Task> list){
	    model = new TaskTableModel(list);	 
	    JTable table = new JTable(model);
	    table.getColumnModel().getColumn(0).setMaxWidth(50);
	    table.getColumnModel().getColumn(2).setMinWidth(110);
	    table.getColumnModel().getColumn(2).setMaxWidth(110);
	    table.getColumnModel().getColumn(3).setMinWidth(110);
	    table.getColumnModel().getColumn(3).setMaxWidth(110);;
	    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
	    table.setRowSorter(sorter);
	    taskScrollPane = new JScrollPane(table);
	    return taskScrollPane;
	}
	
	private static JPanel initCommandFieldPanel(){
		JPanel panel = new JPanel(new BorderLayout());
	    JLabel label = new JLabel("Command: ");
	    JTextField textField = new JTextField();
	    label.setLabelFor(textField);
	    panel.add(label, BorderLayout.WEST);
	    panel.add(textField, BorderLayout.CENTER);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processCommand(((JTextField) e.getSource()).getText());
				((JTextField) e.getSource()).setText("");			
			}
		});
	    label.setDisplayedMnemonic(KeyEvent.VK_N);
		return panel;
	}	
	
	private static JScrollPane initTextArea(){
	  textArea = new JTextArea("" ,2,1);
	  textArea.setLineWrap(true);
	  textArea.setEditable(false);
	  JScrollPane scrollPane = new JScrollPane(textArea);  
	  return scrollPane;
	}

	private static void initFrame(){
		frame = new JFrame("AnyTask");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(initTablePane(Data.getTaskList()));
	    frame.add(initTextArea(),BorderLayout.BEFORE_FIRST_LINE);
	    frame.add(initCommandFieldPanel(), BorderLayout.SOUTH);
	    frame.setSize(800, 600);
	    frame.setVisible(true);
	}

	
	private static void processCommand(String command) {
		Parser p = Parser.getInstance();
		try {
			displayResults(command,p.parseInput(command));
		} catch (Exception e) {
			setTextArea(GeneralMessages.getMsgInvalid());
		}
	}
	
	private static CommandType getCommandType(String userCommand) {
		return CommandType.fromString(userCommand.trim().split(CONSTANT_SPACE)[0]);
	}
	
	private static String getCommandInfo(String userCommand) {
		String[] userCommandString = userCommand.trim()
				.split(CONSTANT_SPACE, 2);
		if (userCommandString.length == 1) {
			return "";
		} else {
			return userCommandString[1];
		}
	}
	
	private static void displayResults(String command, ArrayList<Task> taskList) {
		CommandType commandType=getCommandType(command);
		switch (commandType) {
		case ADD:
			model.setData(Data.getTaskList());
			updateTable();
			setTextArea(GeneralMessages.getMsgAdd(taskList.get(0).getName()));
			break;
		case DELETE:
			model.setData(Data.getTaskList());
			updateTable();
			setTextArea(GeneralMessages.getMsgDelete(taskList.get(0).getName()));
			break;
		case EDIT:
			model.setData(Data.getTaskList());
			updateTable();
			setTextArea(GeneralMessages.getMsgEdit(taskList.get(0).getName()));
			break;
		case DISPLAY:
			if (taskList == null){
			} else if (taskList.size() > 0) {
				model.setData(taskList);
				updateTable();
				setTextArea(GeneralMessages.getMsgDisplay(getCommandInfo(command)));
			} else if (taskList.size() == 0){
				model.setData(taskList);
				updateTable();
				setTextArea(GeneralMessages.getMsgDisplayEmpty());
			} else{
				model.setData(Data.getTaskList());
				updateTable();
				setTextArea(GeneralMessages.getMsgInvalid());
			}
			break;
		case TAG:
			model.setData(Data.getTaskList());
			updateTable();
			setTextArea(GeneralMessages.getMsgTag(taskList.get(0).getName()));
			break;
		case DONE:
			model.setData(Data.getTaskList());
			updateTable();
			setTextArea(GeneralMessages.getMsgDone(taskList.get(0).getName()));
			break;
		case UNDO:
			model.setData(Data.getTaskList());
			updateTable();
			setTextArea(GeneralMessages.getMsgUndo());
			break;
		case SETPATH:
			updateTable();
			setTextArea(GeneralMessages.getMsgPath());
			break;
		case HELP:
			//TODO: Help Command Display
			break;
		case INVALID:
			setTextArea(GeneralMessages.getMsgInvalid());
			break;
		case EXIT:
			break;
		default:
			throw new Error("Unrecognized command type");
		}	
	}
	
	public static void main(String[] args) {
		if(!Data.initTaskList()){
			Display.displayMsgError();
			System.exit(0);			
		}
		new Gui();
		while (true) {
			Display.displayMsgPrompt();
			String command = sc.nextLine();
			AnyTask.processCommand(command);
			updateTable();
		}
	}
}
