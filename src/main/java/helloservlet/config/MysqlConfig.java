package helloservlet.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class dùng để khai báo thông itn cấu hình tạo kết nối tới CSDL
 */
public class MysqlConfig {
	public static Connection getConnection() {
		
		try {
			//khai báo driver sử dụng cho JDBC (từ khoá: <tên drive> class.forname)
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Khai báo thông tin csdl mà JDBC sẽ kết nối tới
			return DriverManager.getConnection("jdbc:mysql://localhost:3307/crm_app", "root", "admin123");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Loi ket noi toi CSDL " + e.getLocalizedMessage());
		}
		
		return null;
	}
}
