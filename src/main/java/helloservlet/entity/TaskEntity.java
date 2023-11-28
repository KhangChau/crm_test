package helloservlet.entity;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TaskEntity {
	private int id; 
	private String name;
	private Date startDate;
	private Date endDate;
	private int idUser;
	private int idJob;
	private int idStatus;
	
	private String userName;
	private String jobName;
	private String statusName;
	
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
	
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getIdJob() {
		return idJob;
	}
	public void setIdJob(int idJob) {
		this.idJob = idJob;
	}
	public int getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}
	
}
