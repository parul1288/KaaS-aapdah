package servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.CrimeDataEntity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import tasks.RetrieveTweets;

public class RetrieveRealTimeTweetsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private void retrieve(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		List<String> list = new LinkedList<String>();
		
		RetrieveTweets tweets = new RetrieveTweets();
		
		list = tweets.retrieveRealTimeTweets();
		
		for(int i =0 ; i <list.size(); i++){
			System.out.println(list.get(i));
		}
		Gson gson = new Gson();
		List<String> bean = new LinkedList<String>();
		bean = list;

		JsonElement element = gson.toJsonTree(bean, new TypeToken<List<String>>() {}.getType());
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
