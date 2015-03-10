package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tasks.UserProfileActions;

public class UserProfileSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void saveProfile(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String emailNotification = request.getParameter("enotification");
		String popUpNotification = request.getParameter("pnotification");
		System.out.println(email);
		System.out.println(password);
		UserProfileActions profile = new UserProfileActions();

		String message = profile.saveUserProfile(firstName, lastName, city, state, email, password, emailNotification, popUpNotification);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", message);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(data));

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		saveProfile(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		saveProfile(request, response);
	}
}