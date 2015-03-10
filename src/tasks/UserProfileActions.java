package tasks;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.hibernate.Query;
import org.hibernate.Session;

import beans.UserProfileInfoEntity;

import util.ConfigSession;

public class UserProfileActions {
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	/*
	 * This method takes the personal details from user and creates a separate profile for each user so that 
	 * in future the returning users can access their profile and create customized alerts. 
	 */
	public String saveUserProfile(String firstName, String lastName, String city, String state, 
			String email, String password, String emailNotification, String popUpNotification)
	{
		String message = null;
		Session session = ConfigSession.openSession();
		// a list to keep track of all the mail 
		List<String> existingEmailList = null;
		UserProfileInfoEntity profile = new UserProfileInfoEntity();
		try{
			lock.writeLock().lock();
			session.getSessionFactory().getCurrentSession();

			//get a list of all the emails from the database
			String str = "Select email from UserProfileInfoEntity ORDER BY email";
			Query query = session.createQuery(str);

			//a list to keep track of all the existing emails in the database
			existingEmailList = new LinkedList<String>();

			Iterator<String> iter = query.iterate();
			while(iter.hasNext()){
				String existingEmail = iter.next(); 

				existingEmailList.add(existingEmail);
			}

			//if the current email is not in the existing emails list add the profile information to the profile object
			if(!existingEmailList.contains(email)){
				profile.setFirstName(firstName);
				profile.setLastName(lastName);
				profile.setCity(city);
				profile.setState(state);
				profile.setEmail(email);
				profile.setPassword(password);
				profile.setEmailNotification(emailNotification);
				profile.setPopUpNotification(popUpNotification);

				try{
					session.beginTransaction();

					//the save the profile object to the session thus saving it to the database
					session.save(profile);
					session.getTransaction().commit();
					message = "Profile created";
				}
				catch(Exception e){
					message = "Profile not created";
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			//if the email is in the existing list then do nothing and return this message
			else{
				message = "Email already exists";
			}
		}
		catch(Exception e){
			message = "Profile not created";
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally{
			session.close();
			lock.writeLock().unlock();
		}
		return message;
	}

	public boolean loginValid(String email, String password){
		Session session = ConfigSession.openSession();
		boolean isValid = false;

		try{
			lock.readLock().lock();
			session.getSessionFactory().getCurrentSession();
			String str = "from UserProfileInfoEntity where email = :email and password = :pwd";

			Query query = session.createQuery(str);
			query.setParameter("email", email);
			query.setParameter("pwd", password);

			Iterator iter = query.iterate();

			while(iter.hasNext()){
				UserProfileInfoEntity profile = (UserProfileInfoEntity)iter.next();

				if(profile != null){
					isValid = true;
				}
			}
			session.flush();
			session.close();
		}

		catch(Exception e){
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally{

			lock.readLock().unlock();
		}
		return isValid;
	}


	public List<Object> getUserDataByLogin(String email, String password){
		Session session = ConfigSession.openSession();

		List<Object> userDataList = null;

		try{
			lock.writeLock().lock();
			session.getSessionFactory().getCurrentSession();


			String str = "Select firstName, lastName, city, state from UserProfileInfoEntity where email = :email and password = :pwd ORDER BY email";
			Query query = session.createQuery(str);
			query.setParameter("email", email);
			query.setParameter("pwd", password);

			userDataList = new LinkedList<Object>();

			Iterator iter = query.iterate();

			while(iter.hasNext()){
				UserProfileInfoEntity profile = (UserProfileInfoEntity)iter.next();
				String firstName = profile.getFirstName();
				String lastName = profile.getLastName();
				String city = profile.getCity();
				String state = profile.getState();
				String emailNotification = profile.getEmailNotification();
				String popUpNotification = profile.getPopUpNotification();

				userDataList.add(firstName);
				userDataList.add(lastName);
				userDataList.add(city);
				userDataList.add(state);
				userDataList.add(email);
				userDataList.add(emailNotification);
				userDataList.add(popUpNotification);
			}
			session.flush();
			session.close();
		}

		catch(Exception e){
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally{

			lock.readLock().unlock();
		}
		return userDataList;
	}

	public String editProfile(String firstName, String lastName, String city, String state, 
			String email, String emailNotification, String popUpNotification)
	{
		String message = null;
		Session session = ConfigSession.openSession();

		try{
			lock.writeLock().lock();
			session.getSessionFactory().getCurrentSession();

			String str = "Update UserProfileInfoEntity set" +
					"firstName = :fName" +
					"lastName = :lName" +
					"city = :cityVal" +
					"state = :stateVal" +
					"email = :emailId"
					+ "eNotification = :emailNotification"
					+ "pNotification = :popUpNotification";

			Query query = session.createQuery(str);

			query.setParameter("fName", firstName);
			query.setParameter("lName", lastName);
			query.setParameter("cityVal", city);
			query.setParameter("stateVal", state);
			query.setParameter("emailId", email);
			query.setParameter("eNotification", emailNotification);
			query.setParameter("pNotification", popUpNotification);

			int count = query.executeUpdate();

			if(count == 1){
				message = "Profile's updated";
			}
			else{
				message = "Profile's not updated";
			}

			session.flush();
			session.close();

		}
		catch(Exception e){
			message = "Profile not updated for the following reason" +e.getMessage();
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally{
			session.close();
			lock.writeLock().unlock();
		}

		return message;
	}
}
