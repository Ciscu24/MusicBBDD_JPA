package musicddbb.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connection {
	private static EntityManagerFactory emf = null;
	private static EntityManager manager = null;
    
    public static EntityManager connectToMysql(){
        if(emf==null) {
        	emf=Persistence.createEntityManagerFactory("mysql");
        	manager = emf.createEntityManager();
        }
        
        return manager; 
    }
    
    public static EntityManager connectToH2(){
        if(emf==null) {
        	emf=Persistence.createEntityManagerFactory("aplicacion");
        	manager = emf.createEntityManager();
        }
        
        return manager; 
    }
    
    /**
     * Metodo que cierra la conexion a la base de datos
     */
    public static void closeConnection(){
    	if(emf!=null) {
    		emf.close();
    		manager.close();
    	}
    }
}
