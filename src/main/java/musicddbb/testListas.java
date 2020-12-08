package musicddbb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;
import musicddbb.model.Cancion;
import musicddbb.model.Lista;
import musicddbb.model.ListaDAO;

public class testListas {
	

	private static EntityManager manager;
	private static EntityManagerFactory emf;
	
	public static void main(String[] args) {
	

		emf=Persistence.createEntityManagerFactory("mysql");
        manager=emf.createEntityManager();
    
      /*  Lista l1 = new Lista(1, "PlayList24", "Esta playlist es de ciscu24");
        Lista l2 = new Lista(2, "Music24", "Esta es otra playlist de ciscu24");
        Cancion cancion1 = new Cancion(1,"cancioncita",200);
        Cancion cancion2 = new Cancion(2,"ciscuparty",200);
        Cancion cancion3 = new Cancion(3,"Padarle",200);
        l1.setCanciones(cancion1);
        l1.setCanciones(cancion2);
   */
        
     //   manager.getTransaction().begin();
   //     manager.persist(l1);
    //    manager.getTransaction().commit();
        
       /* manager.getTransaction().begin();
        Lista aux = manager.find(Lista.class, 1);
        System.out.println(aux.toStringWithCanciones());
        List<Cancion> misListas = aux.getCanciones();
        for(Cancion lx:misListas) {
        	System.out.println(lx);
        }
		manager.getTransaction().commit();
	
	
	
	
*/	Lista L1 = new Lista("prueba","prueba");
        ListaDAO L = new ListaDAO(L1);
        
       
	}

}
