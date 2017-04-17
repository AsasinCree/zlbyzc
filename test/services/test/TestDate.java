package services.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {

	
    public static void main(String[] args) {
    	String string = "1994-03-21";
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date();
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
    }
}
