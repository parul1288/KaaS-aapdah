package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import beans.CrimeDataEntity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import tasks.RetrieveDataFromDB;

public class MapAddressRetrieveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void addressRetrieve(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		String city = request.getParameter("city");
		System.out.println(city);

		RetrieveDataFromDB retrieve = new RetrieveDataFromDB();

		//Retrieve only address
		List<String> list = new LinkedList<String>();
		list = retrieve.getAddressByCity(city);
		System.out.println(list.size());
		Gson gson = new Gson();
		JsonElement element = gson.toJsonTree(list, new TypeToken<List<CrimeDataEntity>>() {}.getType());

		JsonArray jsonArray = element.getAsJsonArray();	
		response.setContentType("application/json");
		response.getWriter().print(jsonArray);

		//Retrieve both address and crime type
//		List<CrimeDataEntity> list = new ArrayList<>();
//		list = retrieve.getdetailsForMaps(city);
//
//		Gson gson = new Gson();
//		List<CrimeDataEntity> bean = new ArrayList<>();
//		bean = list;
//
//		JsonElement element = gson.toJsonTree(bean, new TypeToken<List<CrimeDataEntity>>() {}.getType());
//
//		JsonArray jsonArray = element.getAsJsonArray();	
//		response.setContentType("application/json");
//		response.getWriter().print(jsonArray);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		addressRetrieve(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addressRetrieve(request, response);
	}
}
