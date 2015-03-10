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

/**
 * Servlet implementation class AutoCompleteCrimeTypeServlet
 */

public class AutoCompleteCrimeTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	final List<String> crimesList = new ArrayList<>();
	crimesList.add("Assault");
	crimesList.add("Assault With Deadly Weapon");
	crimesList.add("Breaking & Entering");
	crimesList.add("Homicide");
	crimesList.add("Sexual Offense");
	crimesList.add("Robbery");
	crimesList.add("Theft");
	crimesList.add("Theft from Vehicle");
	crimesList.add("Theft of Vehicle");
	crimesList.add("Vehicle Recovery");
	
	Collections.sort(crimesList);
	response.setContentType("application/json");
	final String param = request.getParameter("term");
	final List<AutoCompleteEntity> result = new ArrayList<>();
	
	for(final String crimeType: crimesList){
		 if (crimeType.toLowerCase().startsWith(param.toLowerCase())) {
             result.add(new AutoCompleteEntity(crimeType, crimeType));
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