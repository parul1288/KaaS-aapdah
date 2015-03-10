package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.json.JSONArray;




import net.sf.json.JSON;
import net.sf.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import tasks.RetrieveDataFromDB;
import beans.CrimeDataEntity;

public class RetrieveSqlDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void retrieve(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<CrimeDataEntity> list = new ArrayList<>();
		RetrieveDataFromDB retrieve = new RetrieveDataFromDB();

		String crimeType = request.getParameter("crimeType");
		System.out.println("CrimeType selected in :" +crimeType);

		if(crimeType!=null){
			list = retrieve.retrieveSQLData(crimeType);
			System.out.println(list.get(1).getAddress());
		}

		Gson gson = new Gson();
		List<CrimeDataEntity> bean = new ArrayList<>();
		bean = list;

		JsonElement element = gson.toJsonTree(bean, new TypeToken<List<CrimeDataEntity>>() {}.getType());
		//		Type listType = new TypeToken<ArrayList<CrimeDataEntity>>() {
		//		}.getType();
		//		JsonElement element = gson.toJsonTree(bean, listType);

		//		try{
		//			JsonArray jsonArray = element.getAsJsonArray();
		//			response.setContentType("application/json");
		//			response.getWriter().print(jsonArray);
		//			//response.setStatus(HttpServletResponse.SC_OK);
		//			response.flushBuffer();
		//		}
		//
		//		catch(Exception e){
		//			System.out.println(e.getMessage());
		//			e.printStackTrace();
		//		}

		JsonArray jsonArray = element.getAsJsonArray();	
		System.out.println(jsonArray.toString());
		//		response.setContentType("application/json");
		//		response.getWriter().print(jsonArray);
		request.setAttribute("list", list);
		RequestDispatcher view = request.getRequestDispatcher("/abc.jsp");
		view.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		retrieve(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		retrieve(request,response);
	}

}
