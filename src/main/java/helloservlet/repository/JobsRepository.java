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
import helloservlet.entity.DetailsJobsEntity;
import helloservlet.entity.JobsEntity;
import helloservlet.entity.RoleEntity;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;
import helloservlet.entity.UserTableEntity;

public class JobsRepository {
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
	
 	public List<JobsEntity> findAll() {
		List<JobsEntity> listRoles = new ArrayList<JobsEntity>();
//		System.out.println("findAll repo check 1");
		String query = "SELECT * FROM jobs;";
		
		
		try {
			//ket noi csdl
			Connection connection = MysqlConfig.getConnection();
			
			//thuc thi cau query
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			//tra ve ket qua kieu ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
			
			//tao instance RoleEntity (da thiet ke tu truoc) -> set attribute cho tun`g dong` query dc
			while(resultSet.next()) {
				JobsEntity jobsEntity = new JobsEntity();
				
				jobsEntity.setId(resultSet.getInt("id"));
				jobsEntity.setName(resultSet.getString("name"));
				jobsEntity.setStartDate(resultSet.getDate("start_date"));
				jobsEntity.setEndDate(resultSet.getDate("end_date"));
				
				listRoles.add(jobsEntity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Loi~ findAll " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		if(listRoles.isEmpty()) {
			System.out.println("list jobs is empty");
		}
		System.out.println("findAll repo check 1 in jobs");
		return listRoles;
	}
	public int insertIntoJobs(String name, String startDate, String endDate) {
		int count = 0;
		//Buoc 2: Chuẩn bị câu query (truy vấn)
//	String query = "SELECT *\r\n"
//			+ " FROM users u \r\n"
//			+ " WHERE u.email = '"+email+"' AND u.password = '"+password+"'";
	String query = "INSERT INTO jobs( name, start_date, end_date ) VALUES (?, ?, ?);";
	
	//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
	Connection connection = MysqlConfig.getConnection();
	
	try {
		//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, this.convertDateCustom(startDate));
		preparedStatement.setString(3, this.convertDateCustom(endDate));
		
		//Buoc 5: thông báo cho CSDL biết và thực thi câu query
		//có 2 cách thực thi:
		//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
		//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
		count = preparedStatement.executeUpdate();

		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Loi insertIntoJobs " + e.getLocalizedMessage());
		}
	return count;
	}
	public JobsEntity findJobById(int id) {
		JobsEntity job = new JobsEntity();
		
		String query = "SELECT *\n"
				+ "				 FROM jobs j\n"
				+ "				 WHERE j.id = ?;"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Integer.toString(id));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				JobsEntity entity = new JobsEntity();
			    entity.setId(resultSet.getInt("id"));
			    entity.setName(resultSet.getString("name"));
			    entity.setStartDate(resultSet.getDate("start_date"));
			    entity.setEndDate(resultSet.getDate("end_date"));
				
				job = entity;
//				System.out.println("user check: " + entity.getFullname());
				return entity;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return job;
	}
	public int updateIntoJobs(String name, String start_date, String end_date, int id) {
		int count = 0;
		//Buoc 2: Chuẩn bị câu query (truy vấn)
	String query = "UPDATE jobs u\n"
			+ "SET u.name  = ?, "
			+ "u.start_date  = ?, "
			+ "u.end_date = ? "
			+ "WHERE u.id  = ?;"
			;
	
	//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
	Connection connection = MysqlConfig.getConnection();
	
	try {
		//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, this.convertDateCustom(start_date));
		preparedStatement.setString(3, this.convertDateCustom(end_date));
		
		preparedStatement.setString(4, Integer.toString(id));
		
		//Buoc 5: thông báo cho CSDL biết và thực thi câu query
		//có 2 cách thực thi:
		//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
		//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
		count = preparedStatement.executeUpdate();
		connection.close();
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Loi updateJobTable " + e.getLocalizedMessage());
		}
	return count;
	}
	
	public int deleteById(int id) throws SQLException {
		
		int count = 0;
		
		String query = "DELETE FROM jobs u WHERE u.id = ?;";
		
		Connection connection = MysqlConfig.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setInt(1, id);
		count = preparedStatement.executeUpdate();
		
		return count;
	}
	public String findNameById(int id) {
		
		String query = "SELECT name\n"
				+ "				 FROM jobs j\n"
				+ "				 WHERE j.id = ?;"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Integer.toString(id));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				return resultSet.getString("name");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("loi tim ten groupwork");
			e.printStackTrace();
		}
		
		return "";
	}
	
	public List<TaskEntity> findAllTaskByJobIdAndStatusId(int jobId, int statusId){
		List<TaskEntity> list = new ArrayList<TaskEntity>();
		
		String query = 
//				"SELECT t.name, j.name AS jobName, u.fullname As userName, t.start_date AS startDate, t.end_date AS endDate, s.name AS statusName, "
//				+ "t.user_id AS idUser, t.status_id AS idStatus, t.job_id as idJob,\n" 
//				+ "t.id\n" +
				"SELECT t.id, t.status_id as statusId\n" +
				 "FROM tasks t\n" +
				 "JOIN users u ON t.user_id = u.id\n" +
				 "JOIN status s ON t.status_id = s.id\n" +
				 "JOIN jobs j ON t.job_id = j.id\n" +
				 "WHERE t.job_id = ? AND t.status_id = ?;"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Integer.toString(jobId));
			preparedStatement.setString(2, Integer.toString(statusId));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				TaskEntity entity = new TaskEntity();
				entity.setId(Integer.parseInt(resultSet.getString("id")));
				entity.setIdStatus(Integer.parseInt(resultSet.getString("statusId")));				
				
//				entity.setName(resultSet.getString("name"));
//				entity.setJobName(resultSet.getString("jobName"));
//				entity.setUserName(resultSet.getString("userName"));
//				entity.setStartDate(resultSet.getDate("startDate"));
//				entity.setEndDate(resultSet.getDate("endDate"));
//				entity.setStatusName(resultSet.getString("statusName"));
//				
//				entity.setIdUser(resultSet.getInt("idUser"));
//				entity.setIdJob(resultSet.getInt("idJob"));
//				entity.setIdStatus(resultSet.getInt("idStatus"));
				
				list.add(entity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;		
	}
	public List<DetailsJobsEntity> findAllIdAndNameUserByJobId(int jobId) {
			
			//Buoc 2: Chuẩn bị câu query (truy vấn)
	//	String query = "SELECT *\r\n"
	//			+ " FROM users u \r\n"
	//			+ " WHERE u.email = '"+email+"' AND u.password = '"+password+"'";
		String query = "SELECT DISTINCT u.id, u.fullname AS userName \n"
				+ " FROM users u\n"
				+ "JOIN tasks t ON u.id = t.user_id\n"
				+ "JOIN jobs j ON t.job_id = j.id\n"
				+ "WHERE j.id = ?\n"
				+ "ORDER BY u.id\n"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		List<DetailsJobsEntity> listUserIdAndName = new ArrayList<DetailsJobsEntity>();
		
		try {
			//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Integer.toString(jobId));
			//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
			
			//Buoc 5: thông báo cho CSDL biết và thực thi câu query
			//có 2 cách thực thi:
			//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
			//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
			ResultSet resultSet = preparedStatement.executeQuery();
			
			
			//Buoc 6: Duyệt từng dòng dữ liệu query được và gán vào trong List<UserEntitty>
			while(resultSet.next()) {
				DetailsJobsEntity entity = new DetailsJobsEntity();
				entity.setUserId(resultSet.getInt("id"));
				entity.setUserName(resultSet.getString("userName"));			
				listUserIdAndName.add(entity);
			} 
			connection.close();	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Loi findAllIdAndNameUse " + e.getLocalizedMessage());
			}
		return listUserIdAndName;
	}
	public List<TaskEntity> findAllTaskByUserIdAndJobIdAndStatusId(int userId, int jobId, int statusId){
		
		List<TaskEntity> list = new ArrayList<TaskEntity>();
		
		String query = 
				"SELECT t.name, j.name AS jobName, u.fullname As userName, t.start_date AS startDate, t.end_date AS endDate, s.name AS statusName, "
				+ "t.user_id AS idUser, t.status_id AS idStatus, t.job_id as idJob,\n" 
				+ "t.id\n" +
//				"SELECT t.id, t.status_id as statusId\n" +
				 "FROM tasks t\n" +
				 "JOIN users u ON t.user_id = u.id\n" +
				 "JOIN status s ON t.status_id = s.id\n" +
				 "JOIN jobs j ON t.job_id = j.id\n" +
				 "WHERE t.user_id = ? AND t.job_id = ? AND t.status_id = ?;"
				;
		
		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Integer.toString(userId));
			preparedStatement.setString(2, Integer.toString(jobId));
			preparedStatement.setString(3, Integer.toString(statusId));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				TaskEntity entity = new TaskEntity();
				entity.setId(Integer.parseInt(resultSet.getString("id")));
//				entity.setIdStatus(Integer.parseInt(resultSet.getString("statusId")));				
				
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
