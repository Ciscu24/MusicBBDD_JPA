package musicddbb;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import musicddbb.model.Cancion;
import musicddbb.model.CancionDAO;
import musicddbb.model.Disco;
import musicddbb.model.DiscoDAO;
import musicddbb.model.Lista;
import musicddbb.model.ListaDAO;
import musicddbb.model.SuscripcionDAO;
import musicddbb.model.Usuario;
import musicddbb.model.UsuarioDAO;
import musicddbb.utils.Connection;

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
        
		
		//manager = Connection.connectToMysql();
		/*
		Usuario usuario = new Usuario("ciscu6@gmail.com", "Ciscu", "ciscu24.png");
		Usuario usuario2 = new Usuario("edu@gmail.com", "Edu", "Edu.png");
		
		Lista l1 = new Lista("PlayList24", "Esta playlist es de ciscu24", usuario);
        Lista l2 = new Lista("Music24", "Esta es otra playlist de ciscu24", usuario);
        
        List<Lista> listasdelistas = new ArrayList<Lista>();
        listasdelistas.add(l1);
        listasdelistas.add(l2);
		
		
        manager.getTransaction().begin();
		manager.getTransaction().commit();
		
	    UsuarioDAO u1=new UsuarioDAO(usuario2);
	    u1.save();
	    */
	    Disco disco=new Disco("Colores","Colores.png",Date.valueOf("2020-10-20"));
	    Disco disco1=new Disco("Prueba", "prueba.png", Date.valueOf("2020-10-20"));	    
	    DiscoDAO d1=new DiscoDAO(disco1); 
	    
	    
	    Disco disco2=new Disco("","",Date.valueOf("2020-10-20"));
	    DiscoDAO d2=new DiscoDAO(disco2); 
	   // System.out.println(d2.save());
	    
	    Cancion c1=new Cancion("Cancion con Yandel", 180,new Disco(6));
	    CancionDAO c=new CancionDAO(c1);
	    c.save();
	    
	    //System.out.println(UsuarioDAO.selectAllForID(1));
	    //d1.save();
		//System.out.println(SuscripcionDAO.selectAllListas(1));
		//System.out.println(SuscripcionDAO.selectAllUsuario(4));
		
		//SuscripcionDAO.guardarSuscripcion(2, 4);
		//SuscripcionDAO.remove(2, 4);
		
		
        //list();
        
    }
	
	public static void list() {
		List<Usuario> usuarios = Connection.connectToMysql().createQuery("FROM Usuario").getResultList();
		System.out.println("Usuarios: "+usuarios.size());
		for(Usuario a:usuarios) {
			System.out.println(a);
		}
		manager.close();
	}

}
