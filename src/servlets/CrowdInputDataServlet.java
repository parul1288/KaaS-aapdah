package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tasks.CrowdSourcingFunctions;

public class CrowdInputDataServlet extends HttpServlet {
	/*
	 * This method receives form data from CrowdInputData.jsp and send it to the model to perform necessary action
	 */
	private static final long serialVersionUID = 1L;

	public void reportCrime(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		String crimeType = request.getParameter("crimeType");
		String dateValue = request.getParameter("date");
		String timeValue = request.getParameter("time");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String description = request.getParameter("description");
		
//		System.out.println(crimeType);
//		System.out.println(dateValue);
//		System.out.println(city);
		
		SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
		Date dateFull = null;
		Date timeFull = null;
		try{
			dateFull = (Date)formatterDate.parse(dateValue);
			timeFull = (Date)formatterTime.parse(timeValue);
		}

		catch (ParseException e) {
			e.printStackTrace();
		}

		
		CrowdSourcingFunctions userData = new CrowdSourcingFunctions();
		
		String message = null;
		message = userData.insertUserData(crimeType, dateFull, timeFull, address, city, description);
		System.out.println(message);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", message);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(data));

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		reportCrime(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reportCrime(request, response);
	}

}
