package ui;

import java.sql.Time;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import logic.Data;
import common.Task;

public class TaskTableModel extends AbstractTableModel {
    private String[] columnNames = {"id","Name","Start","End","Tags"};
    private Class<?>[] columnTypes = {String.class,String.class,Time.class,Time.class,ArrayList.class};
    private ArrayList<Task> data;
    
    public static final int    ID_COL = 0;
    public static final int    NAME_COL = 1;
    public static final int    START_COL = 2;
    public static final int    END_COL = 3;
    public static final int    TAGS_COL = 4;

    public Class<?> getColumnClass(int col) {
        return columnTypes[col];
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
    	return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
    	Task task =data.get(row);
    	 
        switch(col)
        {
        case ID_COL : return task.getId();
        case NAME_COL : return task.getName();
        case START_COL : return task.getStartTime();
        case END_COL : return task.getEndTime();
        case TAGS_COL : return task.getTags();
        }

        return "";
    }
    
    public TaskTableModel(ArrayList<Task> taskList)
    {
        super();

        data = taskList;
    }

    /*
    public Class<? extends Object> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    */
}
