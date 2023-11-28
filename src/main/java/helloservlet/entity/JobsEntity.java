package helloservlet.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JobsEntity {
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	

	
	public String getStringStartDate() {
		String stringDate="";
		if(this.startDate!=null) {
			stringDate = dateFormat.format(this.startDate);
		}
			
		return stringDate;
	}
	
	public String getStringEndDate() {
		String stringDate ="";
		if(this.endDate!=null) {
			stringDate = dateFormat.format(this.endDate);
		}
		return stringDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
