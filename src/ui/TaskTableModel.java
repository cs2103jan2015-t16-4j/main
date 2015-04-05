package ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

import common.Task;

//@author A0112734N
public class TaskTableModel extends AbstractTableModel {
	private static final int END_COL = 3;
	private static final int ID_COL = 0;
	private static final int NAME_COL = 1;
	private static final long serialVersionUID = 1L;	
	private static final int START_COL = 2;
	private static final int TAGS_COL = 4;
	private static final int UID_COL = 5;
	private String[] columnNames = { "id", "Name", "Start", "End", "Tags","UID" };
	private Class<?>[] columnTypes = { String.class, String.class,
			Calendar.class, Calendar.class, ArrayList.class,String.class };
	private ArrayList<Task> data;

	public TaskTableModel(ArrayList<Task> taskList) {
		super();
		this.data = taskList;
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return this.columnTypes[col];
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public String getColumnName(int col) {
		return this.columnNames[col];
	}

	@Override
	public int getRowCount() {
		return this.data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Task task = this.data.get(row);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (col == ID_COL) {
			return row+1;
			//return task.getId();
		} else if (col == NAME_COL) {
			return task.getName();
		} else if (col == START_COL) {
			String day = "";
			if (task.getStartTime() != null) {
				day = format.format(task.getStartTime().getTime());
			}
			return day;
		} else if (col == END_COL) {
			String day = "";
			if (task.getEndTime() != null) {
				day = format.format(task.getEndTime().getTime());
			}
			return day;
		} else if (col == TAGS_COL) {
			ArrayList<String> tags = task.getTags();
			String tagString = "";
			if ((tags != null) && !tags.isEmpty()) {
				for (int i = 0; i < tags.size(); i++) {
					tagString += tags.get(i);
				}
			}
			return tagString;
		}if (col == UID_COL) {
			return task.getId();
		}
		return "";
	}

	public void setData(ArrayList<Task> data) {
		this.data = data;
	}
}
