package helloservlet.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailsJobsEntity {
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<TaskEntity> getTask1() {
		return task1;
	}
	public void setTask1(List<TaskEntity> task1) {
		this.task1 = task1;
	}
	public List<TaskEntity> getTask2() {
		return task2;
	}
	public void setTask2(List<TaskEntity> task2) {
		this.task2 = task2;
	}
	public List<TaskEntity> getTask3() {
		return task3;
	}
	public void setTask3(List<TaskEntity> task3) {
		this.task3 = task3;
	}
	private int userId;
	private String userName;
	private List<TaskEntity> task1; //status_id = 1;
	private List<TaskEntity> task2; //status_id = 2;
	private List<TaskEntity> task3; //status_id = 3;
	
	
	 

	
	
}
