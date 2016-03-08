import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.*;

public class HibernateUtil{
	private static final SessionFactory sessionfactory;
	
	static 
	{
		try{
			sessionfactory = new Configuration().configure().buildSessionFactory();
		
		}catch(HibernateException ex){
			throw new ExceptionInInitializerError(ex); 
		}
		
	}


	public static SessionFactory getSessionFactory(){
		return sessionfactory;
	}
}