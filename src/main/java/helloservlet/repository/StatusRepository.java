package helloservlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import helloservlet.config.MysqlConfig;
import helloservlet.entity.JobsEntity;
import helloservlet.entity.StatusEntity;

public class StatusRepository {
	public List<StatusEntity> findAll() {
		List<StatusEntity> listRoles = new ArrayList<StatusEntity>();
//		System.out.println("findAll repo check 1");
		String query = "SELECT * FROM status;";
		
		
		try {
			//ket noi csdl
			Connection connection = MysqlConfig.getConnection();
			
			//thuc thi cau query
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			//tra ve ket qua kieu ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
			
			//tao instance RoleEntity (da thiet ke tu truoc) -> set attribute cho tun`g dong` query dc
			while(resultSet.next()) {
				StatusEntity statusEntity = new StatusEntity();
				
				statusEntity.setId(resultSet.getInt("id"));
				statusEntity.setName(resultSet.getString("name"));

				
				listRoles.add(statusEntity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Loi~ findAll status " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		if(listRoles.isEmpty()) {
			System.out.println("list status is empty");
		}
//		System.out.println("findAll repo check 1 in jobs");
		return listRoles;
	}
	
	
}
