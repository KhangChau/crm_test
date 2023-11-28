package helloservlet.service;

import java.sql.SQLException;
import java.util.List;

//import javax.management.relation.Role;

import helloservlet.entity.RoleEntity;
import helloservlet.repository.RoleRepository;

public class RoleService {
	private RoleRepository roleRepository = new RoleRepository();
	
	public int insert(String name, String description) {
		int count = roleRepository.insertIntoRole(name, description);
		return count;
	}
	
	public List<RoleEntity> getAllRole(){
		System.out.println("getAllRole service check ");
		return roleRepository.findAll();
//		return null;
	}
	
	public boolean deleteRole(int id) throws SQLException {
		
		return roleRepository.deleteById(id) > 0;
	}
}
