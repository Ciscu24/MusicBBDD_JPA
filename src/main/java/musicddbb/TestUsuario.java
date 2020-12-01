package musicddbb;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import musicddbb.model.Usuario;

public class TestUsuario {

	private static EntityManager manager;
	private static EntityManagerFactory emf;
	
	public static void main(String[] args) {
        
        //AppControllerUsuario.ejecutar();
		
		emf=Persistence.createEntityManagerFactory("aplicacion");
        manager=emf.createEntityManager();
        Usuario usuario = new Usuario(1, "ciscu6@gmail.com", "Ciscu", "ciscu24.png");
        manager.getTransaction().begin();
        manager.persist(usuario);
        manager.getTransaction().commit();
        
        list();
        
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
