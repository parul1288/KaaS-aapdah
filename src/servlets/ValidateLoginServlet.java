package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tasks.UserProfileActions;

public class ValidateLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void loginValidation(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserProfileActions actions = new UserProfileActions();

		boolean validate = actions.loginValid(email, password);
		if(validate == true){
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			session.setMaxInactiveInterval(60 * 60);
			Cookie cookie = new Cookie("user", email);
			cookie.setMaxAge(30*60);
			response.addCookie(cookie);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", validate);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		loginValidation(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loginValidation(request, response);
	}

}
