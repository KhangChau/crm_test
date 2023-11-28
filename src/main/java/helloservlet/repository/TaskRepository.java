package helloservlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import helloservlet.config.MysqlConfig;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserTableEntity;

public class TaskRepository {
	public String convertDateCustom(String dateInput) {
		String inputDateString = dateInput;

        // Define input and output date formats
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");
        String outputDateString = dateInput;
        try {
            // Parse the input date string to a Date object
            Date date = inputFormat.parse(inputDateString);

            // Format the Date object to the desired output format
            outputDateString = outputFormat.format(date);

            // Print the result
//            System.out.println("Input date: " + inputDateString);
//            System.out.println("Output date: " + outputDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return outputDateString;
	}
	
	public List<TaskEntity> getAllTask(){
		List<TaskEntity> listTask = new ArrayList<TaskEntity>();
		
		String query = "SELECT t.id, t.name, j.name AS jobName, u.fullname As userName, t.start_date AS startDate, t.end_date AS endDate, s.name AS statusName, "
				+ "t.user_id AS idUser, t.status_id AS idStatus, t.job_id as idJob\n" +
				 "FROM tasks t\n" +
				 "JOIN users u ON t.user_id = u.id\n" +
				 "JOIN status s ON t.status_id = s.id\n" +
				 "JOIN jobs j ON t.job_id = j.id\n" +
				 "ORDER BY t.id;"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				TaskEntity entity = new TaskEntity();
				entity.setId(resultSet.getInt("id"));
				entity.setName(resultSet.getString("Name"));
				entity.setJobName(resultSet.getString("jobName"));
				entity.setUserName(resultSet.getString("userName"));
				entity.setStartDate(resultSet.getDate("startDate"));
				entity.setEndDate(resultSet.getDate("endDate"));
				entity.setStatusName(resultSet.getString("statusName"));
				
				entity.setIdUser(resultSet.getInt("idUser"));
				entity.setIdJob(resultSet.getInt("idJob"));
				entity.setIdStatus(resultSet.getInt("idStatus"));
				
				listTask.add(entity);
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listTask;
	}
	
	public TaskEntity getTaskById(int id){
		TaskEntity task = new TaskEntity();
		
		String query = "SELECT t.name, j.name AS jobName, u.fullname As userName, t.start_date AS startDate, t.end_date AS endDate, s.name AS statusName, "
				+ "t.user_id AS idUser, t.status_id AS idStatus, t.job_id as idJob\n" +
				 "FROM tasks t\n" +
				 "JOIN users u ON t.user_id = u.id\n" +
				 "JOIN status s ON t.status_id = s.id\n" +
				 "JOIN jobs j ON t.job_id = j.id\n" +
				 "WHERE t.id = ?;"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Integer.toString(id));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				TaskEntity entity = new TaskEntity();
				entity.setId(id);
				entity.setName(resultSet.getString("name"));
				entity.setJobName(resultSet.getString("jobName"));
				entity.setUserName(resultSet.getString("userName"));
				entity.setStartDate(resultSet.getDate("startDate"));
				entity.setEndDate(resultSet.getDate("endDate"));
				entity.setStatusName(resultSet.getString("statusName"));
				
				entity.setIdUser(resultSet.getInt("idUser"));
				entity.setIdJob(resultSet.getInt("idJob"));
				entity.setIdStatus(resultSet.getInt("idStatus"));
				
				return entity;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return task;
	}
	
	public int insertIntoTask( String name, String startDate, String endDate, int user_id, int job_id, int status_id) {
		int count = 0;
		//Buoc 2: Chuẩn bị câu query (truy vấn)
	String query = "INSERT INTO tasks( name, start_date, end_date, user_id, job_id, status_id) VALUES (?, ?, ?, ?, ?, ?);";
	
	//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
	Connection connection = MysqlConfig.getConnection();
	
	try {
		//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, this.convertDateCustom(startDate));
		preparedStatement.setString(3, this.convertDateCustom(endDate));
		preparedStatement.setInt(4, user_id);
		preparedStatement.setInt(5, job_id);
		preparedStatement.setInt(6, status_id);
		
		//Buoc 5: thông báo cho CSDL biết và thực thi câu query
		//có 2 cách thực thi:
		//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
		//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
		count = preparedStatement.executeUpdate();
		connection.close();
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Loi insertIntoTasks " + e.getLocalizedMessage());
		}
	return count;
	}
	
	public int updateTaskTable(int id, String name, String startDate, String endDate, int user_id, int job_id, int status_id) {
		int count = 0;
		//Buoc 2: Chuẩn bị câu query (truy vấn)
	String query = "UPDATE tasks\n"
			+ "SET name = ?, start_date = ?, end_date = ?, user_id = ?, job_id = ?, status_id = ?\n"
			+ "WHERE id  = ?;"
			;

	
	//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
	Connection connection = MysqlConfig.getConnection();
	
	try {
		//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, this.convertDateCustom(startDate));
		preparedStatement.setString(3, this.convertDateCustom(endDate));
		preparedStatement.setInt(4, user_id);
		preparedStatement.setInt(5, job_id);
		preparedStatement.setInt(6, status_id);
		preparedStatement.setInt(7, id);
		
		//Buoc 5: thông báo cho CSDL biết và thực thi câu query
		//có 2 cách thực thi:
		//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
		//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
		count = preparedStatement.executeUpdate();
		connection.close();
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Loi updateTasks " + e.getLocalizedMessage());
		}
	return count;
	}
	public int updateTaskStatusOnly(int id,  int status_id) {
		int count = 0;
		//Buoc 2: Chuẩn bị câu query (truy vấn)
	String query = "UPDATE tasks\n"
			+ "SET status_id = ?\n"
			+ "WHERE id  = ?;"
			;

	
	//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
	Connection connection = MysqlConfig.getConnection();
	
	try {
		//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query

		preparedStatement.setInt(1, status_id);
		preparedStatement.setInt(2, id);
		
		//Buoc 5: thông báo cho CSDL biết và thực thi câu query
		//có 2 cách thực thi:
		//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
		//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
		count = preparedStatement.executeUpdate();
		connection.close();
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Loi update Status At Task " + e.getLocalizedMessage());
		}
	return count;
	}
	
	public int deleteById(int id) throws SQLException {
		
		int count = 0;
		
		try {
			String query = "DELETE FROM tasks  WHERE id = ?;";
			
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			count = preparedStatement.executeUpdate();
			connection.close();
			
		}catch(Exception e) {
			System.out.println("Loi deleteTasks " + e.getLocalizedMessage());
		}
		
		return count;
	}
	
	public List<TaskEntity> findAllTaskByUserIdAndStatusId(int userId, int statusId) {
		List<TaskEntity> list = new ArrayList<TaskEntity>();
		
		String query = "SELECT t.name, j.name AS jobName, u.fullname As userName, t.start_date AS startDate, t.end_date AS endDate, s.name AS statusName, "
				+ "t.user_id AS idUser, t.status_id AS idStatus, t.job_id as idJob\n" +
				 "FROM tasks t\n" +
				 "JOIN users u ON t.user_id = u.id\n" +
				 "JOIN status s ON t.status_id = s.id\n" +
				 "JOIN jobs j ON t.job_id = j.id\n" +
				 "WHERE t.id = ? AND t.status_id = ?;"
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
