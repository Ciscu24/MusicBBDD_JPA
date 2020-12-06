package musicddbb.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import musicddbb.utils.Connection;

public class ListaDAO extends Lista {

	private boolean persist;
	private static EntityManager manager;

	public ListaDAO(String nombre, String descripcion, Usuario creador, List<Cancion> canciones,
			List<Usuario> usuarios_suscritos) {
		super(nombre, descripcion, creador, canciones, usuarios_suscritos);
		// TODO Auto-generated constructor stub
		persist = false;
	}

	public ListaDAO(int id, String nombre, String descripcion, Usuario creador, List<Cancion> canciones,
			List<Usuario> usuarios_suscritos) {
		super(-1, nombre, descripcion, creador, canciones, usuarios_suscritos);
		// TODO Auto-generated constructor stub
		persist = false;
	}

	private ListaDAO(int id, String nombre, String descripcion, Usuario creador) {
		super(id, nombre, descripcion, creador);
		// TODO Auto-generated constructor stub
		persist = false;
	}

	private ListaDAO(String nombre, String descripcion, Usuario creador) {
		super(-1, nombre, descripcion, creador);
		// TODO Auto-generated constructor stub
		persist = false;
	}

	private ListaDAO(int id, String nombre, String descripcion) {
		super(id, nombre, descripcion);
		persist = false;
		// TODO Auto-generated constructor stub
	}

	private ListaDAO(String nombre, String descripcion) {
		super(-1, nombre, descripcion);
		persist = false;
		// TODO Auto-generated constructor stub
	}

	public void persist() {
		persist = true;
	}

	public ListaDAO(Lista L) {
		this.id = L.id;
		this.nombre = L.nombre;
		this.descripcion = L.descripcion;
		this.creador = L.creador;
		this.canciones = L.canciones;
		this.usuarios_suscritos = L.usuarios_suscritos;
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

	public void setDescripcion(String descripcion) {
		super.setDescripcion(descripcion);
		if (persist) {
			save();
		}
	}

	public void setCreador(Usuario creador) {
		super.setCreador(creador);
		if (persist) {
			save();
		}
	}

	public void setCanciones(Cancion c) {
		super.setCanciones(c);
		if (persist) {
			save();
		}
	}

	public void setUsuarios_suscritos(List<Usuario> usuarios_suscritos) {
		super.setUsuarios_suscritos(usuarios_suscritos);
		if (persist) {
			save();
		}
	}

	/**
	 * Metodo que guarda una lista en la base de datos
	 *
	 * @return -1 en caso de que no haga nada o el id de la lista que hayamos
	 *         agregado o editado
	 */
	public int save() {
		int result = -1;

		try {
			manager = Connection.connectToMysql();

			if (this.id > 0) {
				// UPDATE
				manager.getTransaction().begin();
				manager.createNativeQuery("UPDATE Lista SET nombre = ?, descripcion = ?, WHERE id = ?")
						.setParameter(1, this.nombre).setParameter(2, this.descripcion).setParameter(3, this.id)
						.executeUpdate();
				manager.getTransaction().commit();
			} else {
				// INSERT
				manager.getTransaction().begin();
				manager.createNativeQuery("INSERT INTO Lista (id,nombre,descripcion) VALUES (?,?,?)")
						.setParameter(1, this.id).setParameter(2, this.nombre).setParameter(3, this.descripcion)
						.executeUpdate();
				manager.getTransaction().commit();
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Metodo que Lista todas las listas de la base de datos
	 *
	 * @return Todas los listas
	 */

	public static List<Lista> selectAll() {
		return selectAll("");
	}

	/**
	 * Funcion que selecciona por nombre todas las listas de la base de datos que
	 * sea por el pattern
	 *
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una lista de listas(playlist)
	 */
	public static List<Lista> selectAll(String pattern) {
		List<Lista> result = new ArrayList();

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM Lista";

			if (pattern.length() > 0) {
				q += " WHERE nombre LIKE ?";
			}

			if (pattern.length() > 0) {
				result = manager.createQuery("FROM Lista WHERE nombre LIKE '" + pattern + "%'").getResultList();
			} else {
				result = manager.createQuery("FROM Lista").getResultList();
			}

			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Funcion que selecciona por id todas las listas de la base de datos
	 *
	 * @param id id por lo que se filtra el select
	 * @return devuelve una lista de lists(Playlist)
	 */
	public static Lista selectAllForID(int id) {
		Lista result = null;

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM Lista WHERE id = ";

			List<Lista> listas = manager.createQuery(q + id).getResultList();

			if (listas.size() != 0) {
				result = listas.get(0);
			}

			manager.getTransaction().commit();

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Funcion que selecciona por nombre todas las listas de la base de datos que
	 * sea por el pattern
	 * 
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una lista de Listas(playlist)
	 */
	public static Lista selectAllForNombre(String nombre) {
		Lista result = null;

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM Lista WHERE nombre = ";

			List<Lista> listas = manager.createQuery("FROM Lista WHERE nombre = '" + nombre + "'").getResultList();

			if (listas.size() != 0) {
				result = listas.get(0);
			}

			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Borra de la base de datos la lista
	 *
	 * @return -1 si no se ha borrado o el id de la lista borrada
	 */
	public boolean remove() {
		boolean result = false;

		if (this.id > 0) {

			try {

				manager = Connection.connectToMysql();
				manager.getTransaction().begin();

				if (manager.createQuery("DELETE FROM lista WHERE id = " + this.id).executeUpdate() == 1) {
					result = true;
				}

				manager.getTransaction().commit();

			} catch (Exception ex) {
				System.out.println(ex);
			}
		}

		return result;
	}

}
