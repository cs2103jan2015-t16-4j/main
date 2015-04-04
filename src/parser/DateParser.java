package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

import com.joestelmach.natty.*;

//@author A0119384Y
public class DateParser {
	private String args;
	public DateParser(String args){
		this.args = args;
	}
	
	public ArrayList<Calendar> parseDate() {
		List<Date> dates;
		com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
		List<DateGroup> groups = parser.parse(args);
		try {
			dates = groups.get(0).getDates();
		} catch (Exception e) {
			return null;
		}
		return dateToCalendar(dates);
	}
	private ArrayList<Calendar> dateToCalendar(List<Date> dates){ 
		ArrayList<Calendar> cals = new ArrayList<Calendar>();
		for(Date date: dates){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cals.add(cal);
		}
		return cals;
	}
}
