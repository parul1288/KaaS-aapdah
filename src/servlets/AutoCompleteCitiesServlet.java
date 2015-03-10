package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.AutoCompleteEntity;

import com.google.gson.Gson;

public class AutoCompleteCitiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final List<String> citiesList = new ArrayList<>();
		citiesList.add("San Jose");
		citiesList.add("Santa Clara");
		citiesList.add("San Francisco");
		citiesList.add("Palo Alto");
		citiesList.add("Mountain View");
		citiesList.add("San Mateo");
		citiesList.add("Los Altos");
		citiesList.add("Menlo Park");
		citiesList.add("Sunnyvale");
		citiesList.add("Los Angeles");
		citiesList.add("Burlingame");
		citiesList.add("Alexander City");
		

		Collections.sort(citiesList);
		response.setContentType("application/json");
		final String param = request.getParameter("term");
		final List<AutoCompleteEntity> result = new ArrayList<>();

		for(final String city: citiesList){
			if (city.toLowerCase().startsWith(param.toLowerCase())) {
				result.add(new AutoCompleteEntity(city, city));
			}
		}

		response.getWriter().write(new Gson().toJson(result));

	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

