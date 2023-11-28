package helloservlet.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.cert.CertPathValidatorException.BasicReason;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import helloservlet.payload.BasicResponse;
import helloservlet.service.RoleService;

@WebServlet (name = "apiRoleController", urlPatterns = {"/api/role"})
public class ApiRoleControler extends HttpServlet {
	
	private RoleService roleService = new RoleService();
	private Gson gson = new Gson();
	/**
	 * {
	 * 	"statusCode" : 200,
	 *  "message" : "",
	 *  "data" : true => dynamic
	 * }
	 * 
	 * payload : request, response
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doDelete(req, resp);
		
		int id = Integer.parseInt(req.getParameter("id"));	
		boolean isSuccess;
		try {
			isSuccess = roleService.deleteRole(id);
			
			
			//Tra ve JSON--------------------------------------------------
			BasicResponse basicResponse = new BasicResponse();
			basicResponse.setStatusCode(200);;
			basicResponse.setMessage("");
			basicResponse.setData(isSuccess);
			
			String dataJson = gson.toJson(basicResponse);
			
			PrintWriter out = resp.getWriter();
	        resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
	        
	        out.print(dataJson);
	        out.flush();
			//-----------------------------------------------------------
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("kiem tra DELETE para id = " + id);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		
		System.out.println("Kiem tra GET");
	}
}
