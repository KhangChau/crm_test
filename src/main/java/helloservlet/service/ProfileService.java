package helloservlet.service;

import java.util.List;

import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;
import helloservlet.repository.ProfileRepository;
import helloservlet.repository.UserRepository;

public class ProfileService {
	ProfileRepository profileRepository = new ProfileRepository();
	UserRepository userRepository = new UserRepository();
	
	public UserEntity findUserByEmailAndPassword(String email, String password) {
		return profileRepository.findUserByEmailAndPassword(email, password);
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
	public List<TaskEntity> findAllTaskByUserIdOrderByJobId(int userId) {
		return profileRepository.findAllTaskByUserIdOrderByJobId(userId);
	}
}
