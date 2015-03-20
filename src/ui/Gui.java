package ui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.util.Scanner;

import logic.Data;
import logic.Display;
import ui.AnyTask;

public class Gui {
	private static JTextField textField;
	private static TaskTableModel model;
	private static Scanner sc = new Scanner(System.in);
	
	public Gui(){
		JScrollPane scrollPane = new JScrollPane(initTable());
		JFrame frame = new JFrame("AnyTask");

	    frame.add(initCommandField(), BorderLayout.SOUTH);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(scrollPane);
	    frame.setSize(500, 300);
	    frame.setVisible(true);
		
	}

	private static JTable initTable(){

	    model = new TaskTableModel(Data.getTaskList());
	    
	    JTable table = new JTable(model);
	    
	    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
	    table.setRowSorter(sorter); 
	    return table;
	}
	
	public static void update(){
		model.fireTableDataChanged();
	}
	
	public static JPanel initCommandField(){
		JPanel panel = new JPanel(new BorderLayout());
	    JLabel label = new JLabel("Command: ");
	    textField = new JTextField();
	    label.setLabelFor(textField);
	    panel.add(label, BorderLayout.WEST);
	    panel.add(textField, BorderLayout.CENTER);
	    textField.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	AnyTask.processCommand(textField.getText());
	        	textField.setText("");
	        }
	      });
	    
	    label.setDisplayedMnemonic(KeyEvent.VK_N);
		return panel;
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
			update();
		}
	}
}
