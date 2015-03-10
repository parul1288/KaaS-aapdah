package tests;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import beans.CrimeDataEntity;
import beans.CrimeUserDataEntity;
import tasks.CrowdSourcingFunctions;
import tasks.InsertExcelSQL;
import tasks.RetrieveDataFromDB;


public class test {

	public static void main(String args[]){

		//Insert data - excel
		InsertExcelSQL excel = new InsertExcelSQL();
		try{
			String message = excel.loadExcelData("/home/kalpana/Documents/sanjose .xlsx");
			System.out.println(message);

		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}


		//Retrieve data from database																																										
		//		RetrieveDataFromDB re = new RetrieveDataFromDB();
		//
		//		try{
		//			List<CrimeDataEntity> list = re.retrieveSQLData("sex");
		//			int size = list.size();
		//			System.out.println(size);
		//			for(int i= 0; i< size; i++ ){
		//				System.out.println(list.get(i).getAddress());
		//			}
		//		}
		//		catch(Exception e){
		//			System.out.println(e.getMessage());
		//			e.printStackTrace();
		//		}


		//Insert data crowd sourcing
		//		InsertUserData userData = new InsertUserData();
		//		String message = userData.insertUserData("Theft", "03/17/2014", "12:48PM", "439 S 4th St", "San Jose", "");
		//		System.out.println(message);


		//Retrieve all the addresses in a map
		//		RetrieveDataFromDB retrieve = new RetrieveDataFromDB();
		//		HashMap<String, ArrayList<String>> map = retrieve.getAllAddress();
		//		
		//		ArrayList<String> addresses = map.get("San Jose");
		//		
		//		for( int i = 0; i< addresses.size() ; i++){
		//			System.out.println(addresses.get(i));
		//		}


		//Return all distinct addresses of a particular city
		//		RetrieveDataFromDB retrieve = new RetrieveDataFromDB();
		//		List<String> addresses = new LinkedList<>();
		//		addresses = retrieve.getAddressByCity("San Francisco");
		//
		//		for( int i = 0; i< addresses.size() ; i++){
		//			System.out.println(addresses.get(i));
		//		}
		//
		//		RetrieveDataFromDB retrieve = new RetrieveDataFromDB();
		//		Map<String, String> map = retrieve.getdetailsForMaps("Palo Alto");
		//
		//		//		for(int i =0; i< map.size(); i++){
		//		//			System.out.println(map.get(i));
		//		//		}
		//
		//		Set<String> keys = map.keySet();
		//
		//		Iterator i = keys.iterator();
		//		System.out.println("Key                             "+        "Value                             ");
		//		System.out.println("============================="+ "       ============================");
		//		while(i.hasNext()){
		//
		//			String key = (String) i.next();
		//			String value = (String) map.get(key);
		//			
		//			
		//			System.out.println(key+"                " + value);
		//		}

		//		CrowdSourcingFunctions retrieve = new CrowdSourcingFunctions();
		//		List<CrimeUserDataEntity> val = retrieve.getCrowdSubmittedCrimes();
		//		for(int i = 0; i< val.size(); i++){
		//			val.get(i).getAddress();
		//		}
	}
}

