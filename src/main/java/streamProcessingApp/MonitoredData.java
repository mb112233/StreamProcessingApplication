package streamProcessingApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitoredData {
	
	private Date startTime;
	private Date endTime;
	private String activityType;
	
	
	
	public MonitoredData(String startTime, String endTime, String activityType) {
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.startTime = s.parse(startTime);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			this.endTime = s.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.activityType = activityType;
		
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	public String toString() {
		return startTime+" "+endTime+" "+activityType;
	}
	
}
