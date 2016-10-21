package co.edu.udea.Iw.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import co.edu.udea.Iw.exception.IWDaoException;

public class HibernateSessionFactory {

	private static HibernateSessionFactory instancia=null;
	private static SessionFactory sessionFactory=null;
	private static Configuration configuration= new Configuration();
	
	private HibernateSessionFactory(){
		
	}
	
	public static HibernateSessionFactory getInstancia(){
		if(instancia==null){
			//System.out.println("entes de sesion factory");
			instancia = new HibernateSessionFactory();
		}
		return instancia;
	}
	
	public Session getSession() throws IWDaoException{
		
		try{
			if(sessionFactory==null){
				//System.out.println("entes de configuration");
				configuration.configure("co/edu/udea/Iw/dao/cnf/hibernate.cfg.xml");
				//System.out.println("entes de configuration factory");
				sessionFactory = configuration.buildSessionFactory();
			}
			return sessionFactory.openSession();
			
		}catch(HibernateException e){
			throw new IWDaoException(e);	
		}
	}
}
