package musicddbb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import musicddbb.utils.Connection;

public class CancionDAO extends Cancion {
	private boolean persist;
	private static EntityManager manager;

	private CancionDAO(int id, String nombre, int duracion, Disco disco_contenedor, List<Lista> listas) {
		super(id, nombre, duracion, disco_contenedor, listas);
		// TODO Auto-generated constructor stub
		persist = false;
	}

	private CancionDAO(int id, String nombre, int duracion) {
		super(id, nombre, duracion);
		// TODO Auto-generated constructor stub
		persist = false;
	}

	private CancionDAO(String nombre, int duracion, Disco disco_contenedor, List<Lista> listas) {
		super(nombre, duracion, disco_contenedor, listas);
		// TODO Auto-generated constructor stub
		persist = false;
	}

	private CancionDAO(String nombre, int duracion, Disco disco_contenedor) {
		super(nombre, duracion, disco_contenedor);
		// TODO Auto-generated constructor stub
		persist = false;
	}

	public CancionDAO() {
		super();
		persist = false;
	}

	public void persist() {
		persist = true;
	}

	public CancionDAO(Cancion c) {
		this.id = c.id;
		this.nombre = c.nombre;
		this.duracion = c.duracion;
		 this.disco_contenedor= c.disco_contenedor;
		this.listas = c.listas;
	}

	public void detatch() {
		persist = false;
	}

	public boolean isPersist() {
		return persist;
	}

	public void setPersist(boolean persist) {
		this.persist = persist;
	}

	public void setId(int id) {
		super.setId(id);
		if (persist) {
			save();
		}
	}

	public void setNombre(String nombre) {
		super.setNombre(nombre);
		if (persist) {
			save();
		}
	}

	public void setDuracion(int duracion) {
		super.setDuracion(duracion);
		if (persist) {
			save();
		}
		;
	}

	public void setListas(Lista lista) {
		super.setListas(lista);
		if (persist) {
			save();
		}
		;
	}
	public void setDisco_Contenedor(Disco disco_contenedor) {
		super.setDisco_contenedor(disco_contenedor);
		if(persist) {
			save();
		}
	}
	

	/**
	 * Metodo que guarda una cancion en la base de datos
	 *
	 * @return -1 en caso de que no haga nada o el id de la cancion que hayamos
	 *         agregado o editado
	 */

	public int save() {
		int result = 0;

		try {
			manager = Connection.connectToMysql();

			if (this.id > 0) {
				// UPDATE
				manager.getTransaction().begin();
				result=manager.createNativeQuery("UPDATE Cancion SET nombre = ?, duracion = ?, id_disco = ? WHERE id = ?")
						.setParameter(1, this.nombre)
						.setParameter(2, this.duracion)
						.setParameter(3, this.disco_contenedor.id)
						.setParameter(4, this.id)
						.executeUpdate();
				manager.getTransaction().commit();
			} else {
				// INSERT
				manager.getTransaction().begin();
				result=manager.createNativeQuery("INSERT INTO Cancion (nombre,duracion,id_disco) VALUES (?,?,?)")
						.setParameter(1, this.nombre)
						.setParameter(2, this.duracion)
						.setParameter(3, this.disco_contenedor.id)
						.executeUpdate();
				manager.getTransaction().commit();
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Metodo que Lista todos las Canciones de la base de datos
	 *
	 * @return Todas las Canciones
	 */
	public static List<Cancion> selectAll() {
		return selectAll("");
	}

	/**
	 * Funcion que selecciona por nombre todas las canciones de la base de datos que
	 * sea por el pattern
	 *
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una lista de Canciones
	 */

	public static List<Cancion> selectAll(String pattern) {
		List<Cancion> result = new ArrayList();

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM Cancion";

			if (pattern.length() > 0) {
				q += " WHERE nombre LIKE ?";
			}

			if (pattern.length() > 0) {
				result = manager.createQuery("FROM Cancion WHERE nombre LIKE '" + pattern + "%'").getResultList();
			} else {
				result = manager.createQuery("FROM Cancion").getResultList();
			}

			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}
	
	/**
	 * Funcion que selecciona por nombre todas las canciones de la lista en concreto pasando su id base de datos que
	 * sea por el pattern
	 *
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una lista de Canciones
	 */
	
	public static List<Cancion> selectAllCancionLista(String pattern,int id) {
		List<Cancion> result = new ArrayList();

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM Cancion";

			if (pattern.length() > 0) {
				q += " WHERE nombre LIKE ?";
			}

			if (pattern.length() > 0) {
				result = manager.createQuery("can.id,can.nombre from lista as list \" +\r\n"
						+ "\" left join lista_cancion as lc On lc.id_lista=list.id\" +\r\n"
						+ "\" left join cancion as can On lc.id_cancion=can.id WHERE (list.id="+id+") AND (nombre LIKE " + pattern + "%')").getResultList();
			} else {
				result = manager.createQuery("can.id,can.nombre from lista as list \" +\r\n"
						+ "\" left join lista_cancion as lc On lc.id_lista=list.id\" +\r\n"
						+ "\" left join cancion as can On lc.id_cancion=can.id WHERE list.id="+id).getResultList();
			}

			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Funcion que selecciona por id la cancion de la base de datos
	 *
	 * @param id id por lo que se filtra el select
	 * @return devuelve una Cancion
	 */
	public static Cancion selectAllForID(int id) {
		Cancion result = null;

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM Cancion WHERE id = ";

			List<Cancion> canciones = manager.createQuery(q + id).getResultList();

			if (canciones.size() != 0) {
				result = canciones.get(0);
			}

			manager.getTransaction().commit();

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}
	
	/**
	 * Funcion que selecciona por nombre todas las canciones de la lista en concreto pasando su id base de datos que
	 * sea por el pattern
	 *
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una lista de Canciones (version 2 )
	 */

	   public static List<Cancion> selectAllLista(int id_lista) {
	       List<Cancion> result = new ArrayList();

	       try {
	    	   
	    	   manager = Connection.connectToMysql();
	    	   manager.getTransaction().begin();
	    	   
	    	   
	    	   Lista  L = manager.find(Lista.class, id_lista);
	    	   
	    	   result = L.getCanciones();	
				
	    	   manager.getTransaction().commit();
	    	   
	       } catch (Exception ex) {
	           System.out.println(ex);
	       }
	       
	       return result;
	   }

	/**
	 * Funcion que selecciona por nombre la primera cancion de la base de datos que
	 * sea por el pattern
	 * 
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una Cancion
	 */
	public static Cancion selectAllForNombre(String nombre) {
		Cancion result = null;

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM Cancion WHERE nombre = ";

			List<Cancion> canciones = manager.createQuery("FROM Cancion WHERE nombre = '" + nombre + "'")
					.getResultList();

			if (canciones.size() != 0) {
				result = canciones.get(0);
			}

			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Borra de la base de datos La cancion
	 *
	 * @return -1 si no se ha borrado o el id de la cancion borrada
	 */
	public boolean remove() {
		boolean result = false;

		if (this.id > 0) {

			try {

				manager = Connection.connectToMysql();
				manager.getTransaction().begin();

				if (manager.createQuery("DELETE FROM Cancion WHERE id = " + this.id).executeUpdate() == 1) {
					result = true;
				}

				manager.getTransaction().commit();

			} catch (Exception ex) {
				System.out.println(ex);
			}
		}

		return result;
	}
	
	/**
	 * Metodo que guarda una cancion en la base de datos
	 *
	 * @return -1 en caso de que no haga nada o el id de la cancion que hayamos
	 *         agregado o editado
	 */
	public int saveH2() {
		int result = 0;

		try {
			manager = Connection.connectToH2();

			if (this.id > 0) {
				// UPDATE
				manager.getTransaction().begin();
				result=manager.createNativeQuery("UPDATE Cancion SET nombre = ?, duracion = ?, id_disco = ? WHERE id = ?")
						.setParameter(1, this.nombre)
						.setParameter(2, this.duracion)
						.setParameter(3, this.disco_contenedor.id)
						.setParameter(4, this.id)
						.executeUpdate();
				manager.getTransaction().commit();
			} else {
				// INSERT
				manager.getTransaction().begin();
				result=manager.createNativeQuery("INSERT INTO Cancion (nombre,duracion,id_disco) VALUES (?,?,?)")
						.setParameter(1, this.nombre)
						.setParameter(2, this.duracion)
						.setParameter(3, this.disco_contenedor.id)
						.executeUpdate();
				manager.getTransaction().commit();
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}
	
	/**
	 * Borra de la base de datos La cancion
	 *
	 * @return -1 si no se ha borrado o el id de la cancion borrada
	 */
	public boolean removeH2() {
		boolean result = false;

		if (this.id > 0) {

			try {

				manager = Connection.connectToH2();
				manager.getTransaction().begin();

				if (manager.createQuery("DELETE FROM Cancion WHERE id = " + this.id).executeUpdate() == 1) {
					result = true;
				}

				manager.getTransaction().commit();

			} catch (Exception ex) {
				System.out.println(ex);
			}
		}

		return result;
	}
	
	/**
	 * Funcion que selecciona por nombre todas las canciones de la base de datos que
	 * sea por el pattern
	 *
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una lista de Canciones
	 */
	public static List<Cancion> selectAllH2(String pattern) {
		List<Cancion> result = new ArrayList();

		try {
			manager = Connection.connectToH2();
			manager.getTransaction().begin();

			String q = "FROM Cancion";

			if (pattern.length() > 0) {
				q += " WHERE nombre LIKE ?";
			}

			if (pattern.length() > 0) {
				result = manager.createQuery("FROM Cancion WHERE nombre LIKE '" + pattern + "%'").getResultList();
			} else {
				result = manager.createQuery("FROM Cancion").getResultList();
			}

			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

}
