package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import beans.CrimeDataEntity;
import beans.CrimeUserDataEntity;
import tasks.CrowdSourcingFunctions;

/**
@author: Kalpana Sastry
 */

public class CrowdsourcingDataRetrievalServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void retrieve(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//String currentDate = request.getParameter("currentDate");
		//System.out.println(currentDate);

		CrowdSourcingFunctions retrieve = new CrowdSourcingFunctions();
		List<CrimeUserDataEntity> list = new LinkedList<CrimeUserDataEntity>();
//
//		SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yy");
//		Date dateFull = null;
//		try{
//			dateFull = formatterDate.parse(currentDate);
//			System.out.println(dateFull);
//		}
//
//		catch (ParseException e) {
//			e.printStackTrace();
//		}
		list = retrieve.getCrowdSubmittedCrimes();
		
		for(int i = 0; i< list.size() ; i++){
			System.out.println(list.get(i).getAddress());
		}

		Gson gson = new Gson();
		List<CrimeUserDataEntity> bean = new LinkedList<CrimeUserDataEntity>();
		bean = list;

		JsonElement element = gson.toJsonTree(bean, new TypeToken<List<CrimeUserDataEntity>>() {}.getType());
		JsonArray jsonArray = element.getAsJsonArray();	
		response.setContentType("application/json");
		response.getWriter().print(jsonArray);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		retrieve(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		retrieve(request,response);
	}

}