package helloservlet.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;

import helloservlet.config.MysqlConfig;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;
import helloservlet.entity.UserTableEntity;
/**
 * Chứa tất cả các câu query liên quan đến user
 * -----------------------------------
 * select: đại diện cho chữ "find"
 * where: đại diện bởi chữ "by"
 */
public class UserRepository {
	//select *
	//from users s
	//where s.email = '' and s.password = '';
	public UserRepository () {
		
	}
	
	public List<UserEntity> findByEmailAndPassword(String email, String password) {
		
			//Buoc 2: Chuẩn bị câu query (truy vấn)
	//	String query = "SELECT *\r\n"
	//			+ " FROM users u \r\n"
	//			+ " WHERE u.email = '"+email+"' AND u.password = '"+password+"'";
		String query = "SELECT *\r\n"
				+ " FROM users u \r\n"
				+ " WHERE u.email = ? AND u.password = ?";
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		List<UserEntity> listUser = new ArrayList<UserEntity>();
		
		try {
			//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			
			//Buoc 5: thông báo cho CSDL biết và thực thi câu query
			//có 2 cách thực thi:
			//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
			//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
			ResultSet resultSet = preparedStatement.executeQuery();
						
			//Buoc 6: Duyệt từng dòng dữ liệu query được và gán vào trong List<UserEntitty>
			while(resultSet.next()) {
				UserEntity entity = new UserEntity();
				entity.setId(resultSet.getInt("id"));
				entity.setRole_id(resultSet.getInt("role_id"));
				entity.setFullname(resultSet.getString("fullname"));
				
				listUser.add(entity);
			} 
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Loi findByEmailAndPassword " + e.getLocalizedMessage());
			}
		return listUser;
	}
	public List<UserEntity> findAllIdAndNameUser() {
		
		//Buoc 2: Chuẩn bị câu query (truy vấn)
//	String query = "SELECT *\r\n"
//			+ " FROM users u \r\n"
//			+ " WHERE u.email = '"+email+"' AND u.password = '"+password+"'";
	String query = "SELECT id, fullname\r\n"
			+ " FROM users u \r\n"
			;
	
	//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
	Connection connection = MysqlConfig.getConnection();
	
	List<UserEntity> listUser = new ArrayList<UserEntity>();
	
	try {
		//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
		
		//Buoc 5: thông báo cho CSDL biết và thực thi câu query
		//có 2 cách thực thi:
		//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
		//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
		ResultSet resultSet = preparedStatement.executeQuery();
		
		
		//Buoc 6: Duyệt từng dòng dữ liệu query được và gán vào trong List<UserEntitty>
		while(resultSet.next()) {
			UserEntity entity = new UserEntity();
			entity.setId(resultSet.getInt("id"));
			entity.setFullname(resultSet.getString("fullname"));			
			listUser.add(entity);
		} 
		connection.close();	
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Loi findByEmailAndPassword " + e.getLocalizedMessage());
		}
	return listUser;
}
	
	public List<UserTableEntity> findAllIdAndEmailAndFullnameWithRole(){
		List<UserTableEntity> listUser = new ArrayList<UserTableEntity>();
		
		String query = "SELECT u.id, u.email, u.fullname, r.name as role\n" +
				 "FROM users u\n" +
				 "JOIN roles r ON u.role_id = r.id\n" +
				 "ORDER BY u.id;"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				UserTableEntity entity = new UserTableEntity();
				entity.setId(resultSet.getInt("id"));
				entity.setFullname(resultSet.getString("fullname"));
				entity.setEmail(resultSet.getString("email"));
//				entity.setRole_id(resultSet.getInt("role_id"));
				entity.setRole(resultSet.getString("role"));
				
				listUser.add(entity);
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listUser;
	}
	
	public UserTableEntity getUserById(int id){
		UserTableEntity user = new UserTableEntity();
		
		String query = "SELECT *\n"
				+ "				 FROM users u\n"
				+ "				 WHERE u.id = ?;"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Integer.toString(id));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				UserTableEntity entity = new UserTableEntity();
				entity.setId(resultSet.getInt("id"));
				entity.setFullname(resultSet.getString("fullname"));
				entity.setEmail(resultSet.getString("email"));
				entity.setPassword(resultSet.getString("password"));
				entity.setRole_id(resultSet.getInt("role_id"));
				
				user = entity;
//				System.out.println("user check: " + entity.getFullname());
				return entity;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	public int insertIntoUserTable(String fullname, String email, String password, int role_id) {
		int count = 0;
		//Buoc 2: Chuẩn bị câu query (truy vấn)
	String query = "INSERT INTO users( email, password, fullname , role_id  ) VALUES (?, ?, ?, ?);";
	
	//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
	Connection connection = MysqlConfig.getConnection();
	
	try {
		//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
		preparedStatement.setString(1, email);
		preparedStatement.setString(2, password);
		preparedStatement.setString(3, fullname);
		preparedStatement.setInt(4, role_id);
		
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
	
	public int updateUserTable(String fullname, String email, String password, int role_id, int id) {
		int count = 0;
		//Buoc 2: Chuẩn bị câu query (truy vấn)
	String query = "UPDATE users u\n"
			+ "SET u.fullname  = ?, "
			+ "email  = ?, "
			+ "u.password = ?, "
			+ "u.role_id = ?\n"
			+ "WHERE u.id  = ?;"
			;
	
	//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
	Connection connection = MysqlConfig.getConnection();
	
	try {
		//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
		preparedStatement.setString(1, fullname);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, password);
		
		preparedStatement.setString(4, Integer.toString(role_id));
		preparedStatement.setString(5, Integer.toString(id));
		
		//Buoc 5: thông báo cho CSDL biết và thực thi câu query
		//có 2 cách thực thi:
		//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
		//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
		count = preparedStatement.executeUpdate();
		connection.close();
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Loi updateUserTable " + e.getLocalizedMessage());
		}
	return count;
	}

	public int deleteById(int id) throws SQLException {
		
		int count = 0;
		
		String query = "DELETE FROM users u WHERE u.id = ?;";
		
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setInt(1, id);
		count = preparedStatement.executeUpdate();
		
		return count;
	}
	public List<TaskEntity> findAllTaskByUserIdAndStatusId(int userId, int statusId) {
		List<TaskEntity> list = new ArrayList<TaskEntity>();
		
		String query = "SELECT t.name, j.name AS jobName, u.fullname As userName, t.start_date AS startDate, t.end_date AS endDate, s.name AS statusName, "
				+ "t.user_id AS idUser, t.status_id AS idStatus, t.job_id as idJob,\n" 
				+ "t.id\n" +
				 "FROM tasks t\n" +
				 "JOIN users u ON t.user_id = u.id\n" +
				 "JOIN status s ON t.status_id = s.id\n" +
				 "JOIN jobs j ON t.job_id = j.id\n" +
				 "WHERE t.user_id = ? AND t.status_id = ?;"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Integer.toString(userId));
			preparedStatement.setString(2, Integer.toString(statusId));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				TaskEntity entity = new TaskEntity();
				entity.setId(Integer.parseInt(resultSet.getString("id")));
				entity.setName(resultSet.getString("name"));
				entity.setJobName(resultSet.getString("jobName"));
				entity.setUserName(resultSet.getString("userName"));
				entity.setStartDate(resultSet.getDate("startDate"));
				entity.setEndDate(resultSet.getDate("endDate"));
				entity.setStatusName(resultSet.getString("statusName"));
				
				entity.setIdUser(resultSet.getInt("idUser"));
				entity.setIdJob(resultSet.getInt("idJob"));
				entity.setIdStatus(resultSet.getInt("idStatus"));
				
				list.add(entity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
}

	