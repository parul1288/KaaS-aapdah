package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tasks.Predictor;

public class PredictorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void predictCrime(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String city = request.getParameter("city");

		Map<String, Double> map = new HashMap<String, Double>();

		Predictor predict = new Predictor();
		try{
			map =  predict.predictCrime(city);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		predictCrime(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		predictCrime(request, response);
	}

}
