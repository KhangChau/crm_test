package helloservlet.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;

import helloservlet.entity.TaskEntity;
import helloservlet.repository.TaskRepository;

public class TaskService {
	private TaskRepository taskRepository = new TaskRepository();
	
	public List<TaskEntity> findAllTask() {
		return taskRepository.getAllTask();
	}
	public TaskEntity getTaskById(int id) {
		return taskRepository.getTaskById(id);
	}
	public int insert(String name, String startDate, String endDate, int user_id, int job_id, int status_id) {
		int count = 0;
		count = taskRepository.insertIntoTask(name, startDate, endDate, user_id, job_id, status_id);
		return count;
	}
	
	public Boolean update(int id, String name, String startDate, String endDate, int user_id, int job_id, int status_id){
		int count = 0;
		taskRepository.updateTaskTable(id, name, startDate, endDate, user_id, job_id, status_id);
		return count > 0;
	}
	
	public Boolean delete(int id) {
		int count = 0;
		 try {
			count = taskRepository.deleteById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("loi delete task ");
			e.printStackTrace();
		}
		return count > 0;
	}
	
	public List<TaskEntity> findAllTaskByUserIdAndStatusId(int userId, int statusId) {
		return taskRepository.findAllTaskByUserIdAndStatusId(userId, statusId);
	}
}
