package musicddbb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import musicddbb.utils.Connection;

public class SuscripcionDAO {
	
	private static EntityManager manager;
    
    /**
    * Funcion que selecciona por id del usuario la subcripciones de la base de datos
    *
    * @param id_usuario id por lo que se filtra el select
    * @return devuelve una lista de subcripciones del usuario
    */
   public static List<Lista> selectAllListas(int id_usuario) {
       List<Lista> result = new ArrayList();

       try {
    	   
    	   manager = Connection.connectToMysql();
    	   manager.getTransaction().begin();
    	   
    	   
    	   Usuario u = manager.find(Usuario.class, id_usuario);
    	   
    	   result = u.getListas_suscrito();	
			
    	   manager.getTransaction().commit();
    	   
       } catch (Exception ex) {
           System.out.println(ex);
       }
       
       return result;
   }
   
    /**
    * Funcion que selecciona todos los usuarios que tiene una lista
    *
    * @param id_lista id por lo que se filtra el select
    * @return devuelve una lista de usuario
    */
   public static List<Usuario> selectAllUsuario(int id_lista) {
       List<Usuario> result = new ArrayList();

       try {
    	   manager = Connection.connectToMysql();
    	   manager.getTransaction().begin();

    	   
    	   Lista l = manager.find(Lista.class, id_lista);
    	   
    	   result = l.getUsuarios_suscritos();

	
			
    	   manager.getTransaction().commit();
       } catch (Exception ex) {
           System.out.println(ex);
       }
       return result;
   }
   
   
    /**
    * Funcion que guarda la subcripcion en la base de datos
    *
    * @param id_usuario,id_lista id por lo que se filtra el select
    */
   public static boolean guardarSuscripcion(int id_usuario, int id_lista){ 
	   boolean resultado = false;
	   
       try {
    	   
    	   manager = Connection.connectToMysql();
    	   manager.getTransaction().begin();
    	   
    	   Lista l = manager.find(Lista.class, id_lista);
    	   Usuario u = manager.find(Usuario.class, id_usuario);

    	   List<Lista> lista_suscritas = u.getListas_suscrito();
    	   
    	   if(enLista(lista_suscritas, l)) {
    		   lista_suscritas.add(l);
        	   u.setListas_suscrito(lista_suscritas);
        	   resultado = true;
    	   }
    	   
           
           manager.getTransaction().commit();
        
       }catch (Exception ex) {
           System.out.println(ex);
       }
       
       return resultado;
   }
    /**
    * Borra de la base de datos la subcripcion de un usuario
    *
    * @return -1 si no se ha borrado o el id de la lista de reproduccion borrada
    */
   
   public static void remove(int id_usuario, int id_lista){
       try{
    	   manager = Connection.connectToMysql();
    	   manager.getTransaction().begin();
    	   
    	   Lista l = manager.find(Lista.class, id_lista);
    	   Usuario u = manager.find(Usuario.class, id_usuario);

    	   List<Lista> lista_suscritas = u.getListas_suscrito();
    	   lista_suscritas.remove(l);
    	   u.setListas_suscrito(lista_suscritas);
           
           manager.getTransaction().commit();
       }catch (Exception ex) {
           System.out.println(ex);
       }
   }
   
   /**
    * Funcion para saber si una lista esta en un array de listas
    * @param lista, el array de la lista al que queremos añadir la lista
    * @param listaAnadir, la lista que queremos añadir al array
    * @return
    */
   public static boolean enLista(List<Lista> lista, Lista listaAnadir) {
	   boolean resultado = false;
	   
	   if(!lista.contains(listaAnadir)) {
		   resultado = true;
	   }
	   
	   return resultado;
   }
}
