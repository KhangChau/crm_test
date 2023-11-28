package helloservlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Role;

import helloservlet.config.MysqlConfig;
import helloservlet.entity.RoleEntity;

public class RoleRepository {
	
	public List<RoleEntity> findAll() {
		List<RoleEntity> listRoles = new ArrayList<RoleEntity>();
		System.out.println("findAll repo check 1");
		String query = "SELECT * FROM roles r;";
		
		
		try {
			//ket noi csdl
			Connection connection = MysqlConfig.getConnection();
			
			//thuc thi cau query
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			//tra ve ket qua kieu ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
			
			//tao instance RoleEntity (da thiet ke tu truoc) -> set attribute cho tun`g dong` query dc
			while(resultSet.next()) {
				RoleEntity roleEntity = new RoleEntity();
				
				roleEntity.setId(resultSet.getInt("id"));
				roleEntity.setName(resultSet.getString("name"));
				roleEntity.setDesc(resultSet.getString("description"));
				
				listRoles.add(roleEntity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Loi~ findAll " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		if(listRoles.isEmpty()) {
			System.out.println("list roles is empty");
		}
		System.out.println("findAll repo check 1");
		return listRoles;
	}
	public int insertIntoRole(String name, String description) {
		int count = 0;
		//Buoc 2: Chuẩn bị câu query (truy vấn)
//	String query = "SELECT *\r\n"
//			+ " FROM users u \r\n"
//			+ " WHERE u.email = '"+email+"' AND u.password = '"+password+"'";
	String query = "INSERT INTO roles( name, description ) VALUES (?, ?);";
	
	//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
	Connection connection = MysqlConfig.getConnection();
	
	try {
		//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, description);
		
		//Buoc 5: thông báo cho CSDL biết và thực thi câu query
		//có 2 cách thực thi:
		//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
		//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
		count = preparedStatement.executeUpdate();

		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Loi insertIntoRole " + e.getLocalizedMessage());
		}
	return count;
	}
	public int deleteById(int id) throws SQLException {
		
		int count = 0;
		
		String query = "DELETE FROM roles r WHERE r.id = ?;";
		
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setInt(1, id);
		count = preparedStatement.executeUpdate();
		
		return count;
	}
	public String findRoleName (int id_role) {
		String s = "";
		return s;
	}
	public List<RoleEntity> findRoleById (int id_role) {
		List<RoleEntity> listRoles = new ArrayList<RoleEntity>();
		System.out.println("findAll repo check 1");
		String query = "SELECT * FROM roles r WHERE id = ?;";
		
		
		try {
			//ket noi csdl
			Connection connection = MysqlConfig.getConnection();
			
			//thuc thi cau query
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id_role);
			//tra ve ket qua kieu ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
			
			//tao instance RoleEntity (da thiet ke tu truoc) -> set attribute cho tun`g dong` query dc
			while(resultSet.next()) {
				RoleEntity roleEntity = new RoleEntity();
				
				roleEntity.setId(resultSet.getInt("id"));
				roleEntity.setName(resultSet.getString("name"));
				roleEntity.setDesc(resultSet.getString("description"));
				
				listRoles.add(roleEntity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Loi~ findAll " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		if(listRoles.isEmpty()) {
			System.out.println("list roles is empty");
		}
		System.out.println("findAll repo check 1");
		return listRoles;
	}

}
