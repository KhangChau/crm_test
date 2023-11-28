package helloservlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import helloservlet.config.MysqlConfig;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;

public class ProfileRepository {
	public UserEntity findUserByEmailAndPassword(String email, String password) {
		
		//Buoc 2: Chuẩn bị câu query (truy vấn)
//	String query = "SELECT *\r\n"
//			+ " FROM users u \r\n"
//			+ " WHERE u.email = '"+email+"' AND u.password = '"+password+"'";
	String query = "SELECT id, fullname\r\n"
			+ " FROM users u \r\n"
			+ " WHERE u.email = ? AND u.password = ?";
	
	//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
	Connection connection = MysqlConfig.getConnection();

	
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
			entity.setFullname(resultSet.getString("fullname"));
			entity.setEmail(email);
			
			return entity;
		} 
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Loi findUserByEmailAndPassword " + e.getLocalizedMessage());
		}
	return new UserEntity();
}
	public List<TaskEntity> findAllTaskByUserIdOrderByJobId(int userId) {
		List<TaskEntity> list = new ArrayList<TaskEntity>();
		
		String query = "SELECT t.name, j.name AS jobName, u.fullname As userName, t.start_date AS startDate, t.end_date AS endDate, s.name AS statusName, \r\n"
				+ "				t.id, t.user_id AS idUser, t.status_id AS idStatus, t.job_id as idJob\r\n"
				+ "				 FROM tasks t\r\n"
				+ "				 JOIN users u ON t.user_id = u.id\r\n"
				+ "				 JOIN status s ON t.status_id = s.id\r\n"
				+ "				 JOIN jobs j ON t.job_id = j.id\r\n"
				+ "				 WHERE t.user_id = ?\r\n"
				+ "				 ORDER BY j.id;"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Integer.toString(userId));
			
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
		System.out.println("check listsize function: " + list.size());
		return list;
	}

}
