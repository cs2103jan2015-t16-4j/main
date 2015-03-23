package ui;

import java.util.Calendar;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import javax.swing.table.AbstractTableModel;

import common.Task;

public class TaskTableModel extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"id","Name","Start","End","Tags"};
    private Class<?>[] columnTypes = {String.class,String.class,Calendar.class,Calendar.class,ArrayList.class};
    private ArrayList<Task> data;
    
    public static final int    ID_COL = 0;
    public static final int    NAME_COL = 1;
    public static final int    START_COL = 2;
    public static final int    END_COL = 3;
    public static final int    TAGS_COL = 4;

    public void setData(ArrayList<Task> data){
    	this.data=data;
    }
    
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
    	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm");;
    	
    	if(col==ID_COL){
    		return task.getId();
    		
    	} else if(col==NAME_COL){
    		return task.getName();
    		
    	} else if (col==START_COL){
    		String day="";
    		if(task.getStartTime() != null){
    			day= format.format(task.getStartTime().getTime());
    		}
    		return day;
    		
    	} else if (col==END_COL){
    		String day="";
    		if(task.getEndTime() != null){
    			day= format.format(task.getEndTime().getTime());
    		}
    		return day;
    		
    	} else if (col==TAGS_COL){
    		ArrayList<String> tags=task.getTags();
    		String tagString="";
    		if(tags!=null&&!tags.isEmpty()){
	    		for(int i=0; i<tags.size();i++){
	    			tagString+=tags.get(i);
	    		}
    		}
    		return tagString;
    	}
      
        return "";
    }
    
    public TaskTableModel(ArrayList<Task> taskList)
    {
        super();
        data = taskList;
    }
}
