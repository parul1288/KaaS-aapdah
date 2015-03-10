package tasks;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.hibernate.Query;
import org.hibernate.Session;

import util.ConfigSession;
import beans.CrimeUserDataEntity;

/**
@author: Kalpana Sastry
 */

public class CrowdSourcingFunctions {

	private ReadWriteLock lock = new ReentrantReadWriteLock(); 

	public String insertUserData(String crimeType, Date dateValue, Date timeValue, String address, String city, 
			String description){
		Session session = ConfigSession.openSession();
		CrimeUserDataEntity crimes = new CrimeUserDataEntity();
		String message = null;
		try{
			lock.writeLock().lock();
			session.getSessionFactory().getCurrentSession();

			crimes.setCrimeType(crimeType);
			crimes.setDateValue(dateValue);
			crimes.setTimeValue(timeValue);
			crimes.setAddress(address);
			crimes.setCity(city);
			crimes.setDescription(description);
			try{
				session.beginTransaction();
				session.save(crimes);
				session.getTransaction().commit();
				message = "Reported";
			}

			catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
				message = "Not reported";
			}		
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = "Crime not reported";
		}

		finally{
			session.close();
			lock.writeLock().unlock();
		}

		return message;
	}

	public List<CrimeUserDataEntity> getCrowdSubmittedCrimes(){
		Session session = ConfigSession.openSession();
		CrimeUserDataEntity crimes = new CrimeUserDataEntity();

		List<CrimeUserDataEntity> list = null;

		try{
			lock.writeLock().lock();

			session.getSessionFactory().getCurrentSession();
			session.beginTransaction();

//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//			Calendar endDate = Calendar.getInstance();
//			Calendar startDate = (Calendar)endDate.clone();
//			startDate.add(Calendar.DAY_OF_MONTH, -7);
//			
//			String formattedStart = new String();
//			formattedStart = dateFormat.format(startDate.getTime());
//			String formattedEnd = new String();
//			formattedEnd = dateFormat.format(endDate.getTime());	
//
//			Date start = null;
//			Date end = null;
//			try {
//				start = new Date(dateFormat.parse(formattedStart).getTime());
//				end = new Date(dateFormat.parse(formattedEnd).getTime());
//				
//			} catch (ParseException ex) {
//				System.out.println("Parse Exception");
//			}
//			System.out.println("Formatted Start Date = " + start);
//			System.out.println("Formatted End Date = " + end);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");

			Calendar endDate = Calendar.getInstance();
			Calendar startDate = (Calendar)endDate.clone();
			startDate.add(Calendar.DAY_OF_MONTH, -7);
			
			String start = new String();
			start = dateFormat.format(startDate.getTime());	
			System.out.println("Formatted Start Date = " + start);

			String end = new String();
			end = dateFormat.format(endDate.getTime());	
			System.out.println("Formatted End Date = " + end);
			
			String str = "from CrimeUserDataEntity where dateValue BETWEEN '" + start  + "' AND '" +end +"'";

			Query query = session.createQuery(str);
			//query.setParameter("currDate", end);
			//query.setParameter("startDate", start);

			list = new LinkedList<CrimeUserDataEntity>();

			Iterator<CrimeUserDataEntity> iter = query.iterate();

			while(iter.hasNext()){
				CrimeUserDataEntity userCrimes = iter.next();
				CrimeUserDataEntity entity = new CrimeUserDataEntity();

				entity.setCrimeType(userCrimes.getCrimeType());
				entity.setAddress(userCrimes.getAddress());
				System.out.println(entity.getAddress());
				entity.setCity(userCrimes.getCity());
				entity.setDateValue(userCrimes.getDateValue());
				entity.setTimeValue(userCrimes.getTimeValue());
				entity.setDescription(userCrimes.getDescription());	

				list.add(entity);
			}



			session.flush();
			session.close();

		}

		catch(Exception e){
			e.printStackTrace();
		}

		finally{
			//session.close();
			lock.writeLock().unlock();
		}

		return list;
	}

}