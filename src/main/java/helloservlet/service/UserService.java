package helloservlet.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import helloservlet.entity.TaskEntity;
//import helloservlet.entity.RoleEntity;
import helloservlet.entity.UserEntity;
import helloservlet.entity.UserTableEntity;
import helloservlet.repository.RoleRepository;
import helloservlet.repository.UserRepository;

public class UserService {
	private UserRepository userRepository =  new UserRepository();
	
	public List<UserTableEntity> getAllIdAndEmailAndFullnameWithRole(){
		System.out.println("getAllUser service check ");
		
		return userRepository.findAllIdAndEmailAndFullnameWithRole();
		//		return null;
	}
	
	public int insert(String fullname, String email, String password, int role_id) {
		int count = userRepository.insertIntoUserTable(fullname, email, password, role_id);
		return count;
	}
	public int update(String fullname, String email, String password, int role_id, int id) {
		int count = userRepository.updateUserTable(fullname, email, password, role_id, id);
		return count;
	}
	public boolean deleteUser(int id) throws SQLException {
		
		return userRepository.deleteById(id) > 0;
	}
	
	public UserTableEntity getUserById(int id) {
			return userRepository.getUserById(id);
	}
	public List<UserEntity> findAllIdAndNameUser(){
		return userRepository.findAllIdAndNameUser();
	}
	public List<TaskEntity> findAllTaskByUserIdAndStatusId(int userId, int statusId) {
		return userRepository.findAllTaskByUserIdAndStatusId(userId, statusId);
	}
	
	public int[] calculateTaskPercent(int userId){
		int sumListSize = 0;
		float percent100List[] = {0,0,0};
		int percentListResult[] = {0,0,0};
		
		for (int i = 0; i<3; i++) {
			int listSize = userRepository.findAllTaskByUserIdAndStatusId(userId, i+1).size();
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
}
