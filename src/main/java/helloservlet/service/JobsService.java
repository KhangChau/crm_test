package helloservlet.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import helloservlet.entity.DetailsJobsEntity;
import helloservlet.entity.JobsEntity;
import helloservlet.entity.RoleEntity;
import helloservlet.entity.TaskEntity;
import helloservlet.repository.JobsRepository;
import helloservlet.repository.RoleRepository;

public class JobsService {
	private JobsRepository jobsRepository = new JobsRepository();
	
//	public int insert(String name, String description) {
//		int count = jobsRepository.insertIntoRole(name, description);
//		return count;
//	}
	
	public List<JobsEntity> getAllJobs(){
		System.out.println("getAllRole service check ");
		return jobsRepository.findAll();
//		return null;
	}
	public JobsEntity getJobById(int id) {
		return jobsRepository.findJobById(id);
	}
	public int insert(String name, String startDate, String endDate) {
		int count = jobsRepository.insertIntoJobs(name, startDate, endDate);
		return count;
	}
	public int update(String name, String startDate, String endDate, int id) {
		int count = jobsRepository.updateIntoJobs(name, startDate, endDate, id);
		return count;
	}
	public boolean delete(int id) {
		int count=0;
		try {
			count = jobsRepository.deleteById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count > 0;
	}
	public String findNameById(int id) {
		return jobsRepository.findNameById(id);
	}
	public int[] calculateTaskPercent(int jobId){
		int sumListSize = 0;
		float percent100List[] = {0,0,0};
		int percentListResult[] = {0,0,0};
		
		for (int i = 0; i<3; i++) {
			int listSize = jobsRepository.findAllTaskByJobIdAndStatusId(jobId, i+1).size();
			System.out.println("kiem tra listSizeEach: " + listSize);
			percent100List[i] = listSize;
			sumListSize+=listSize;
		}
		if (sumListSize!=0) {
			// Calculate total size in arrayList
			for(int t=0; t< 3; t++) {
				percent100List[t] =  Math.round(percent100List[t]*100/sumListSize);
				percentListResult[t] = (int)percent100List[t];
				System.out.println("kiem tra percentEach: " + percentListResult[t]);
			}
			
		}
		
		return percentListResult;
	}
	public List<DetailsJobsEntity> findAllDetailsJobsEntityByJobsId(int jobId){
		
		List<DetailsJobsEntity> list = new ArrayList<DetailsJobsEntity>(); 
		
		//lay list user_id by job_id
		list = jobsRepository.findAllIdAndNameUserByJobId(jobId);
		
		// loop qua list tren -> add 3 list task theo status_id
		int size = list.size();
		for(int i = 0; i < size; i++) {
			int tempUserId = list.get(i).getUserId();
			List<TaskEntity> task1 = jobsRepository.findAllTaskByUserIdAndJobIdAndStatusId(tempUserId,jobId, 1);
			list.get(i).setTask1(task1);
			List<TaskEntity> task2 = jobsRepository.findAllTaskByUserIdAndJobIdAndStatusId(tempUserId,jobId, 2);
			list.get(i).setTask2(task2);
			List<TaskEntity> task3 = jobsRepository.findAllTaskByUserIdAndJobIdAndStatusId(tempUserId,jobId, 3);
			list.get(i).setTask3(task3);
		}
		//lan luot add vo list<DetailsJobsEntity>
		
		return list;
	}
}
