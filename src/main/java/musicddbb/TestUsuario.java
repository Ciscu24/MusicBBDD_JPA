package musicddbb;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import musicddbb.model.Lista;
import musicddbb.model.Usuario;

public class TestUsuario {

	private static EntityManager manager;
	private static EntityManagerFactory emf;
	
	public static void main(String[] args) {
		
		/*emf=Persistence.createEntityManagerFactory("aplicacion");
        manager=emf.createEntityManager();
        Usuario usuario = new Usuario(1, "ciscu6@gmail.com", "Ciscu", "ciscu24.png");
        Lista l1 = new Lista(1, "PlayList24", "Esta playlist es de ciscu24");
        Lista l2 = new Lista(2, "Music24", "Esta es otra playlist de ciscu24");
        
        List<Lista> playlist = new ArrayList<Lista>();
        playlist.add(l1);
        playlist.add(l2);
        
        usuario.setListas_creadas(playlist);
        
        manager.getTransaction().begin();
        manager.persist(usuario);
        manager.getTransaction().commit();
        
        manager.getTransaction().begin();
        Usuario ux = manager.find(Usuario.class, 1);
        System.out.println(ux.toStringWithListas_Creadas());
        List<Lista> misListas = ux.getListas_creadas();
        for(Lista lx:misListas) {
        	System.out.println(lx);
        }
		manager.getTransaction().commit();*/
        
		
		emf = Persistence.createEntityManagerFactory("mysql");
		manager = emf.createEntityManager();
		Usuario usuario = new Usuario("ciscu6@gmail.com", "Ciscu", "ciscu24.png");
		
		manager.getTransaction().begin();
        manager.persist(usuario);
        manager.getTransaction().commit();
        
    }
	
	public static void list() {
		EntityManager manager=emf.createEntityManager();
		List<Usuario> usuarios=manager.createQuery("FROM Usuario").getResultList();
		System.out.println("Usuarios: "+usuarios.size());
		for(Usuario a:usuarios) {
			System.out.println(a);
		}
		manager.close();
	}

}
