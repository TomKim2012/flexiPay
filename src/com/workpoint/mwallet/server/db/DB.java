package com.workpoint.mwallet.server.db;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.workpoint.mwallet.server.actionhandlers.BaseActionHandler;
import com.workpoint.mwallet.server.dao.ErrorDaoImpl;
import com.workpoint.mwallet.server.dao.UserGroupDaoImpl;

/**
 * <p>
 * This class provides utility methods for 
 * beginning/committing & rolling back user transactions
 * 
 * <p>
 * Further it provides utility methods for retrieving {@link EntityManagerFactory}
 *  and the {@link EntityManager} 
 * 
 * <P>
 * Whenever an entity manager is requested, a corresponding {@link UserTransaction} has to have been
 * started/ began
 * 
 * <p>
 * A problem scenario that arises from this is one where {@link JBPMHelper} which initializes
 * an {@link Environment} variable and {@link TaskHandler} using a {@link EntityManagerFactory}
 * generates an {@link EntityManager} without a {@link UserTransaction} - since the UserTransaction
 * is application managed. In this case, an exception is thrown with a 'no active transaction' message.
 * 
 * <p>
 * To mitigate the above error, a {@link UserTransaction} transaction will be started for every
 * request and committed at the end of the request -see {@link BaseActionHandler}  
 * 
 * @author duggan
 *
 */
public class DB{
	
	private static Logger log = Logger.getLogger( DB.class );

    private static final ThreadLocal<EntityManager> entityManagers = new ThreadLocal<EntityManager>();

	private static ThreadLocal<DaoFactory> daoFactory = new ThreadLocal<>();
	
    
    private DB(){}
    

	private static EntityManagerFactory emf;
    
    public static EntityManager getEntityManager(){
    	
    	EntityManager em = entityManagers.get();
    	
        if (em != null && !em.isOpen()) {
            em = null;
        }
        if (em == null) {
        	
        	synchronized(entityManagers){        		
	            if (emf == null) {    
	            	emf = getEntityManagerFactory(); //(serviceRegistry);	            	
	            }
        	}
        	
        	//beginTransaction();
            em = emf.createEntityManager();
            entityManagers.set(em);
        }

        return em;
    }

	/**
	 * This must be called before XA transaction is started
	 * @return
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		
		synchronized (log) {
			if(emf==null){
				try{				
					emf = Persistence.createEntityManagerFactory("mwallet-pu");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		return emf; 
	}


	public static void closeSession(){
		try{
			
			EntityManager em = (EntityManager) entityManagers.get();
	        if(em==null)
	        	return;	        
	        
		}catch(Exception e){
			
			try{
				rollback();
			}catch(Exception ex){}
			
			throw new RuntimeException(e);
		}finally{
			clearSession();
			
			closeFactory();
		}
		
	}
    

	/**
     * Close the single hibernate em instance.
     *
     * @throws HibernateException
     */
    private static void clearSession(){
        EntityManager em = (EntityManager) entityManagers.get();
        if(em==null)
        	return;
        
        entityManagers.set(null);
       
        if (em != null) {
            em.close();
        }
        
    }

    /**
     * Begin a {@link UserTransaction}
     * 
     * <p>
     * This is called whenever a new entity manager is requested
     */
    public static void beginTransaction() {
    	try{    	
    		getUserTrx().begin();
    	}catch(Exception e){
    		throw new RuntimeException(e);
    	}
    	
	}

    /**
     * This method commits a {@link UserTransaction}
     * <p>
     * A transaction is always generated whenever an entity manager is requested
     */
	public static void commitTransaction() {
		try{						
			//if(entityManagers.get()!=null)
			
			/*STATUS_ACTIVE               0
			STATUS_COMMITTED            3
			STATUS_COMMITTING           8
			STATUS_MARKED_ROLLBACK      1
			STATUS_NO_TRANSACTION       6
			STATUS_PREPARED             2
			STATUS_PREPARING            7
			STATUS_ROLLEDBACK           4
			STATUS_ROLLING_BACK         9
			STATUS_UNKNOWN              5*/
			
			//JBPM engine marks transactions for rollback everytime
			//something goes wrong - it does'nt necessarily throw an exception 
//			if(getUserTrx().getRollbackOnly()){
//				getUserTrx().rollback();
//			}else{
//				getUserTrx().commit();
//			}
			getUserTrx().commit();
    	}catch(Exception e){
    		throw new RuntimeException(e);
    	}
		
	}

	/**
	 * Rollback a {@link UserTransaction}
	 */
	public static void rollback(){
		try{
			
			//if(entityManagers.get()!=null)
			getUserTrx().rollback();
			
    	}catch(Exception e){
    		e.printStackTrace();
    		//throw new RuntimeException(e);
    	}
	}
	
	public static EntityTransaction getUserTrx() throws NamingException {
		
		return getEntityManager().getTransaction();
	}
	
	private static DaoFactory factory(){
		DaoFactory factory=daoFactory.get();
		
		if(factory==null){
			factory = new DaoFactory();
			daoFactory.set(factory);
		}
		
		return factory;
	}
	
	private static void closeFactory() {
		if(daoFactory.get()==null)
			return;
		
		daoFactory.set(null);
	}

	public static boolean hasActiveTrx() throws NamingException{
		return getUserTrx().isActive();
	}
	
	public static UserGroupDaoImpl getUserGroupDao(){
		return factory().getUserGroupDaoImpl(getEntityManager());
	}
	
	public static ErrorDaoImpl getErrorDao() {
		return factory().getErrorDao(getEntityManager());
	}

}
