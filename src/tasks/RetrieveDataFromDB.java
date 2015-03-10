package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.hibernate.Query;
import org.hibernate.Session;

import beans.CrimeDataEntity;
import util.ConfigSession;

public class RetrieveDataFromDB {

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public List<CrimeDataEntity> retrieveSQLData(String crimeType){


		List<CrimeDataEntity> list = null;

		try{

			lock.readLock().lock();

			Session session = ConfigSession.openSession();
			session.getSessionFactory().getCurrentSession();
			session.beginTransaction();


			String str = "from CrimeDataEntity where crimeType LIKE :str1 ORDER BY agency, address";

			Query query = session.createQuery(str);
			query.setString("str1", "%" +crimeType + "%" );
			list = new ArrayList<CrimeDataEntity>();

			Iterator iter = query.iterate();

			while(iter.hasNext()){
				CrimeDataEntity crimes = new CrimeDataEntity();
				CrimeDataEntity cde = (CrimeDataEntity)iter.next();


				crimes.setDateValue(cde.getDateValue());
				crimes.setTimeValue(cde.getTimeValue());
				crimes.setAddress(cde.getAddress());
				crimes.setAgency(cde.getAgency());
				//System.out.println(cde.getAddress());
				list.add(crimes);


			}
			//System.out.println(list.get(1).getAddress());

			session.flush();
			session.close();

		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally{

			lock.readLock().unlock();
		}
		return list;
	}

	public HashMap<String, ArrayList<String>> getAllAddress(){
		HashMap<String, ArrayList<String>> map = null;
		ArrayList<String> addresses = null;


		String PATTERN = "(County|Sheriff's|Sheriff|Office|Police|Department|of|Public|Safety)\\b";

		Session session = ConfigSession.openSession();

		try{

			lock.readLock().lock();


			session.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			String str = "from CrimeDataEntity ORDER BY agency, address";
			Query query = session.createQuery(str);

			map = new HashMap<>();
			addresses = new ArrayList<>();

			Iterator iter = query.iterate();

			while(iter.hasNext()){
				CrimeDataEntity cde = (CrimeDataEntity)iter.next();

				String agency = cde.getAgency();
				String city = agency.replaceAll(PATTERN, "");
				//System.out.println(city);

				String address = cde.getAddress();

				if(!map.containsKey(city)){
					addresses.add(address);

				}
				else{
					ArrayList<String> existingAddresses = map.get(city);

					for(int i = 0; i< existingAddresses.size(); i++){
						if(existingAddresses.get(i) != address)
							addresses.add(address);
						else
							continue;
					}
				}

				map.put(city, addresses);



			}
			session.flush();
			session.close();
		}

		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally{

			lock.readLock().unlock();
		}

		return map;
	}

	public List<String> getAddressByCity(String city){

		List<String> addresses = null;

		Session session = ConfigSession.openSession();

		try{

			lock.readLock().lock();


			session.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			String str = "Select DISTINCT address from CrimeDataEntity where agency LIKE :str1  GROUP BY address, crimeType";
			Query query = session.createQuery(str);
			query.setString("str1", "%" +city.trim() + "%" );

			addresses = new LinkedList<>();

			Iterator iter = query.iterate();

			while(iter.hasNext()){
				

				String address = (String) iter.next();
				if(!addresses.contains(address)){
					addresses.add(address);
				}				
			}
			session.flush();
			session.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally{

			lock.readLock().unlock();
		}

		return addresses;

	}

	public List<CrimeDataEntity> getdetailsForMaps(String city){
		List<CrimeDataEntity> list = null;

		try{

			lock.readLock().lock();

			Session session = ConfigSession.openSession();
			session.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			String str = "from CrimeDataEntity where agency LIKE :str1 and dateValue LIKE '2%' ORDER BY address, crimeType";
			Query query = session.createQuery(str);
			query.setString("str1", "%" +city + "%" );

			list = new LinkedList<CrimeDataEntity>();
			Iterator iter = query.iterate();

			while(iter.hasNext()){
				CrimeDataEntity cde = (CrimeDataEntity)iter.next();
				CrimeDataEntity crimes = (CrimeDataEntity)iter.next();

				String address = cde.getAddress();
				String crimeType = cde.getCrimeType();

				crimes.setAddress(cde.getAddress());
				crimes.setCrimeType(cde.getCrimeType());
				
				list.add(crimes);
			}
			session.flush();
			session.close();	

		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally{

			lock.readLock().unlock();
		}
		return list;
	}

	public List<String> getAddressForMaps(String city){
		List<String> addresses = null;

		Session session = ConfigSession.openSession();

		try{

			lock.readLock().lock();


			session.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			String str = "from CrimeDataEntity where agency LIKE :str1 and dateValue LIKE '2%' ORDER BY address, crimeType";
			Query query = session.createQuery(str);
			query.setString("str1", "%" +city + "%" );

			addresses = new LinkedList<String>();

			Iterator iter = query.iterate();

			while(iter.hasNext()){
				CrimeDataEntity cde = (CrimeDataEntity)iter.next();

				String address = cde.getAddress();

				if(!addresses.contains(address)){
					addresses.add(address);
				}
				else{
					continue;
				}
			}
			session.flush();
			session.close();	

		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally{

			lock.readLock().unlock();
		}
		return addresses;

	}
}
