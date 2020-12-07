package musicddbb.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import musicddbb.utils.Connection;

public class DiscoDAO extends Disco {
	private boolean persist;
	private static EntityManager manager;

	private DiscoDAO(int id) {
		super(id);
		persist = false;
	}

	private DiscoDAO() {
		super();
		persist = false;
	}

	private DiscoDAO(String nombre, String foto, Artista creador, Date fecha_produccion, List<Cancion> canciones) {
		super(nombre, foto, creador, fecha_produccion, canciones);
		persist = false;
	}

	private DiscoDAO(int id, String nombre, String foto, Artista creador, Date fecha_produccion,
			List<Cancion> canciones) {
		super(id, nombre, foto, creador, fecha_produccion, canciones);
		persist = false;

	}

	public void persist() {
		persist = true;
	}

	public DiscoDAO(Disco d) {
		this.id = d.id;
		this.nombre = d.nombre;
		this.creador = d.creador;
		this.fecha_produccion = d.fecha_produccion;
		this.canciones = d.canciones;
	}

	public void detatch() {
		persist = false;
	}

	public boolean ispersist() {
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

	public void setFoto(String foto) {
		super.setFoto(foto);
		if (persist) {
			save();
		}
	}

	// Relación de miguel
	public void setCreador(Artista creador) {

	}

	public void setFecha_produccion(Date fecha_produccion) {
		super.setFecha_produccion(fecha_produccion);
		if (persist) {
			save();
		}
	}

	public void setCanciones(List<Cancion> canciones) {
		super.setCanciones(canciones);
		if (persist) {
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
		int result = -1;

		try {
			manager = Connection.connectToMysql();

			if (this.id > 0) {
				// UPDATE
				manager.getTransaction().begin();
				manager.createNativeQuery("UPDATE Cancion SET nombre = ?, duracion = ?, WHERE id = ?")
						.setParameter(1, this.nombre).setParameter(2, this.duracion).setParameter(3, this.id)
						.executeUpdate();
				manager.getTransaction().commit();
			} else {
				// INSERT
				manager.getTransaction().begin();
				manager.createNativeQuery("INSERT INTO Cancion (id,nombre,duracion) VALUES (?,?,?)")
						.setParameter(1, this.id).setParameter(2, this.nombre).setParameter(3, this.duracion)
						.executeUpdate();
				manager.getTransaction().commit();
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Metodo que lista todas los discos de la base de datos
	 * 
	 * @return Devuelve una lista de discos
	 */
	public static List<Disco> selectAll() {
		return selectAll("");
	}

	/**
	 * Funcion que selecciona por nombre el disco de la base de datos
	 * 
	 * @param pattern
	 * @return Devuelve una lista de Discos
	 */
	public static List<Disco> selectAll(String pattern) {
		List<Disco> result = new ArrayList<Disco>();

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM disco";

			if (pattern.length() > 0) {
				q += "WHERE nombre LIKE ?";
			}

			if (pattern.length() > 0) {
				result = manager.createQuery("FROM disco WHERE nombre LIKE '" + pattern + "%'").getResultList();

			} else {
				result = manager.createQuery("FROM disco").getResultList();
			}

			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * ´ Función que selecciona por id el disco de la base de datos
	 * 
	 * @param id , id por lo que se filtra el select
	 * @return devuelve un disco
	 */
	public static Disco selectAllForID(int id) {
		Disco result = null;
		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM disco WHERE idb=?";

			List<Disco> Discos = manager.createQuery(q + id).getResultList();

			if (Discos.size() != 0) {
				result = Discos.get(0);
			}
			manager.getTransaction().commit();

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Funcion que selecciona por nombre el primer disco de la base de datos que sea
	 * por el nombre
	 * 
	 * @param nombre Palabra por lo que filtra el select
	 * @return devuelve un disco
	 */
	public static Disco selectAllforNombre(String nombre) {
		Disco result = null;

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM disco WHERE nombre =";

			List<Disco> discos = manager.createQuery("FROM disco WHERE nombre = '" + nombre + "'").getResultList();

			if (discos.size() != 0) {
				result = discos.get(0);
			}
			
			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);

		}
		return result;
	}

	/**
	 * Borra de la base de datos un Disco
	 *
	 * @return -1 si no se ha borrado el disco o el id del disco eliminado
	 */
	public boolean remove() {
		boolean result = false;

		if (this.id > 0) {

			try {
				manager = Connection.connectToMysql();
				manager.getTransaction().begin();

				if (manager.createQuery("DELETE FROM disco WHERE ID =" + this.id).executeUpdate() == 1) {
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
