package com.healthctrl.servlets;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.fitrax.nutritionix4j.client.dto.NutritionixProxyTest;
import com.fitrax.nutritionix4j.client.dto.TestApi;
import com.healthctrl.objects.UserDetails;
import com.healthctrl.utils.AnalyzeUtils;
import com.healthctrl.utils.UserDetailsUtils;

/**
 * Servlet implementation class UserRegistrationServlet
 */
@WebServlet("/UserRegistrationServlet")
public class UserRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDetailsUtils userUtils;
	public String user_id;
	public UserDetails user;
	@Resource(name="jdbc/sadna")
	private DataSource dataSource;
	HttpSession session;

	@Override
	public void init() throws ServletException {
		super.init();
		//create user details db util..and pass the conn pool / data source
		try{
			userUtils = new UserDetailsUtils(dataSource);
		}
		catch (Exception e){
			throw new ServletException(e);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get the data and set the attribute
		String command = request.getParameter("command");
		try {
			switch (command) {
			case "Login":
				authenticateUser(request,response);
				break;
			case "Register":
				addUserDetails(request, response);
				break;
			case "Logout":
				logout(request, response);
				break;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ServletException(e);
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		session=request.getSession();  
		session.invalidate();  

		response.sendRedirect("homePage.jsp");

	}
	private void authenticateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		user = userUtils.getUserDetails(email);
		if( userUtils.authenticateUser(email, password)){
			HttpSession session = request.getSession();
			user = userUtils.getUserDetails(email);
			session.setAttribute("user_id", user.getUser_id());
			//add user to request object
			request.setAttribute("user",user.getName());
			TrackingServlet tracking = new TrackingServlet(dataSource);
			tracking.getAllTests(request, response);
		}
		else {
			response.sendRedirect("login.jsp");
		}
	}

	private void addUserDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String birthday_year = request.getParameter("birthday_year");
		//create a user object
		if (name!=null && gender!=null && lastName!=null && 
				gender!=null && birthday_year!=null && password!=null){
			UserDetails user = new UserDetails(name, lastName, email,password,gender, birthday_year);
			//add user to db
			boolean success = userUtils.addUser(user);
			if (success){
				RequestDispatcher rqDisp = request.getRequestDispatcher("blood_test_form.jsp");
				rqDisp.forward(request, response);
			}
		}
		else {
			RequestDispatcher rqDisp = request.getRequestDispatcher("login.jsp");
			rqDisp.forward(request, response);
		}
	}
	//	private void addUserFacebookDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
	//		String name = request.getParameter("first_name");
	//		String lastName = request.getParameter("last_name");
	//		String gender = request.getParameter("gender");
	//		String email = request.getParameter("email");
	//		String age = request.getParameter(request.getParameter("birthday"));
	//		
	//		UserDetails user = new UserDetails(name, lastName, email,"0",gender,age);
	//		boolean success = false;
	//		try {
	//			success = userUtils.addFbUser(user);
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			System.out.println(e.getMessage());
	//		}
	//		if (success){
	//		HttpSession session = request.getSession();
	//		user = userUtils.getUserDetails(email);
	//		session.setAttribute("user_id", user.getUser_id());
	//		response.sendRedirect("homePage.jsp");
	//		}
	//		else {
	//			response.sendRedirect("login.jsp");
	//		}
	//	}
	//	
	//	
	//	private void listUserDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
	//	//	List <ComponentResult> componentsResult = dbUtils.getBloodTestDetails();
	//		//add test components to request
	//	//	request.setAttribute("component_list", componentsResult);
	////		//send JSO page (view)
	////		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-testResult.jsp");
	////		dispatcher.forward(request, response);
	//	}

}
