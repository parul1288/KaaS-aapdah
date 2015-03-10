package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConfigSession {

	public static final SessionFactory sessionFactory = buildSessionFactory();

	public static SessionFactory buildSessionFactory(){
		try{
			//			Configuration  configuration = new Configuration().configure("/home/kalpana/Desktop/CMPE295B/CMPE295B/WebContent/WEB-INF/classes/hibernate.cfg.xml");
			return  new Configuration().configure().buildSessionFactory();
		}
		catch(Throwable e){
			System.err.println("Initial session factory creation failed!");
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}

	public static Session openSession(){
		return sessionFactory.openSession();
	}

}
