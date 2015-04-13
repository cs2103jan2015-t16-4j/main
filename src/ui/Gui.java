package ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import logic.Command.CommandType;
import logic.Data;
import parser.Parser;
import ui.messages.GeneralMessages;
import ui.messages.HelpMessages;
import common.Task;

//@author A0112734N
public class Gui {
	private static final String CONSTANT_SPACE = " ";
	private static final String CONSTANT_INIT_COMMAND = "display";
	private static JFrame frame;
	private static final int idCol = 5;
	private static TaskTableModel model;
	// kept here for testing from console
	private static Scanner sc = new Scanner(System.in);
	private static JScrollPane taskScrollPane;

	private static JTextPane textArea;

	private static void displayResults(String command, ArrayList<Task> taskList) {
		String commandType = Parser.getInstance().parseCommandType(command);
		switch (CommandType.fromString(commandType)) {
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
			if (taskList == null) {
			} else if (taskList.size() > 0) {
				model.setData(taskList);
				updateTable();
				setTextArea(GeneralMessages.getMsgDisplay(Parser.getInstance()
						.getCommandInfo(command)));
			} else if (taskList.size() == 0) {
				model.setData(taskList);
				updateTable();
				setTextArea(GeneralMessages.getMsgDisplayEmpty());
			} else {
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
			model.setData(Data.getTaskList());
			updateTable();
			setTextArea(GeneralMessages.getMsgPath());
			break;
		case HELP:
			// TODO: Help Command Display
			setTextArea(HelpMessages.getMsgHelp((Parser.getInstance().getCommandInfo(command))));
			break;
		case SAVE:
			setTextArea(GeneralMessages.getMsgSave());
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

	private static JPanel initCommandFieldPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Command: ");
		JTextField textField = new JTextField();
		label.setLabelFor(textField);
		panel.add(label, BorderLayout.WEST);
		panel.add(textField, BorderLayout.CENTER);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processCommand(((JTextField) e.getSource()).getText());
				((JTextField) e.getSource()).setText("");
			}
		});
		label.setDisplayedMnemonic(KeyEvent.VK_N);
		return panel;
	}

	private static void initFrame() {
		ArrayList<Task> commandType = Parser.getInstance().parseInput(CONSTANT_INIT_COMMAND);
		frame = new JFrame("AnyTask");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(initTablePane(commandType));
		frame.add(initTextArea(), BorderLayout.BEFORE_FIRST_LINE);
		frame.add(initCommandFieldPanel(), BorderLayout.SOUTH);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	public static void initSystemTray() {
		if (!SystemTray.isSupported()) {
			return;
		}
		Image image = Toolkit.getDefaultToolkit().getImage("src/ui/icon.jpg");
		PopupMenu trayPopupMenu = new PopupMenu();
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		trayPopupMenu.add(exit);

		TrayIcon trayIcon = new TrayIcon(image, "AnyTask", trayPopupMenu);
		SystemTray systemTray = SystemTray.getSystemTray();
		try {
			systemTray.add(trayIcon);
		} catch (AWTException awtException) {
			awtException.printStackTrace();
		}
	}

	private static JScrollPane initTablePane(ArrayList<Task> list) {
		model = new TaskTableModel(list);
		JTable table = new JTable(model);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(2).setMinWidth(110);
		table.getColumnModel().getColumn(2).setMaxWidth(110);
		table.getColumnModel().getColumn(3).setMinWidth(110);
		table.getColumnModel().getColumn(3).setMaxWidth(110);
		table.getColumnModel().getColumn(5).setMinWidth(0);
		table.getColumnModel().getColumn(5).setMaxWidth(0);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
				model);
		table.setRowSorter(sorter);
		taskScrollPane = new JScrollPane(table);
		return taskScrollPane;
	}

	private static JScrollPane initTextArea() {
		textArea = new JTextPane();
		textArea.setEditable(false);
		
		textArea.setContentType("text/html");
		textArea.setText(" ");
		textArea.setPreferredSize( new Dimension(800,60) );
		JScrollPane scrollPane = new JScrollPane(textArea);
		return scrollPane;
	}
	
	//@author A0119384Y-reused
	private static boolean isNumerical(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	//@author A0112734N
	public static void main(String[] args) {
		if (!Data.initTaskList()) {
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

	private static void processCommand(String command) {
		Parser p = Parser.getInstance();
		try {
			if (isNumerical(p.getCommandInfo(command).split(CONSTANT_SPACE, 2)[0])) {
				String paras[] = p.getCommandInfo(command).split(
						CONSTANT_SPACE, 2);
				// create new command string using ID.
				String newCommand = p.parseCommandType(command);
				// col 5 holds unique ID of task. It is hidden in the gui.
				newCommand += " "
						+ model.getValueAt((Integer.parseInt(paras[0]) - 1),
								idCol) + " ";
				if (paras.length == 2) {
					newCommand += paras[1];
				}
				displayResults(newCommand, p.parseInput(newCommand));
			} else {
				displayResults(command, p.parseInput(command));
			}
		} catch (Exception e) {
			setTextArea(GeneralMessages.getMsgInvalid());
		}
	}

	public static void setTextArea(String toDisplay) {
		textArea.setText(toDisplay);
	}

	public static void updateTable() {
		model.fireTableDataChanged();
	}

	public Gui() {
		initFrame();
		initSystemTray();
		setTextArea(GeneralMessages.getMsgWelcome());
	}
}
