package xetbotv2.libs;

import java.util.Calendar;

public class xCalendar {
	private Calendar cal;
	
	public xCalendar(){
		cal = Calendar.getInstance();
	}
	
	public xCalendar(long millis){
		cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
	}
	
	public String getFormattedDate(){
		String minutes = "", hours = "";
		
		if(cal.get(Calendar.MINUTE)<10) minutes += "0";
			minutes += String.valueOf(cal.get(Calendar.MINUTE));
			
		if(cal.get(Calendar.HOUR_OF_DAY)<10) hours += "0";
			hours += String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		
		return hours+":"+minutes;
	}
	
	public String getFormattedTime(){
		String day = "", month = "";
				
		if(cal.get(Calendar.DAY_OF_MONTH)<10) day += "0";
		day += String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
				
		if(cal.get(Calendar.MONTH)<9) month += "0";
		month += String.valueOf(cal.get(Calendar.MONTH)+1);
		
		return day+"-"+month+"-"+cal.get(Calendar.YEAR);
		
	}
	
	
	
	
	
	
	public static void main(String[] args){
		xCalendar asd = new xCalendar();
		System.out.println(asd.getFormattedDate());
		System.out.println(asd.getFormattedTime());
	}
}