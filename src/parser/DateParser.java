package parser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

//@author A0119384Y
public class DateParser {
	public static Calendar datePaser(String args) {
		Date date;
		com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
		List<DateGroup> groups = parser.parse(args);
		try {
			date = groups.get(0).getDates().get(0);
		} catch (Exception e) {
			return null;
		}
		return dateToCalendar(date);
		
	}
	private static Calendar dateToCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
	}
}
