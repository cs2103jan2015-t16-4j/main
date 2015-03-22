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

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;

import logic.Data;
import logic.Display;
import common.Task;
import ui.AnyTask;
import ui.Messages.GeneralMessages;

public class Gui {
	private static TaskTableModel model;
	private static JTextArea textArea;
	//kept here for testing from console
	private static Scanner sc = new Scanner(System.in);
	private static JScrollPane taskScrollPane;
	private static JFrame frame;
	
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
				AnyTask.processCommand(((JTextField) e.getSource()).getText());
				((JTextField) e.getSource()).setText("");
				updateVisual();				
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
	    frame.setSize(500, 300);
	    frame.setVisible(true);
	}
	
	private static void updateVisual(){
		setTextArea("command received");
		//model.setData(new ArrayList<Task>());
		updateTable();
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
