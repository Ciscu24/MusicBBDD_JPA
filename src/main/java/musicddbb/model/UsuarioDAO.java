package musicddbb.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import musicddbb.utils.Connection;

public class UsuarioDAO extends Usuario {
	
	private boolean persist;
	private static EntityManager manager;

	public UsuarioDAO() {
		super();
		persist = false;
	}

	public UsuarioDAO(Usuario u) {
		this.id = u.id;
		this.correo = u.correo;
		this.nombre = u.nombre;
		this.foto = u.foto;
		this.listas_creadas = u.listas_creadas;
		this.listas_suscrito = u.listas_suscrito;
		persist = false;
	}

	@Override
	public void setId(int id) {
		super.setId(id);
		if(persist){
			save();
		}
	}

	@Override
	public void setNombre(String nombre) {
		super.setNombre(nombre);
		if (persist) {
			save();
		}
	}

	@Override
	public void setCorreo(String correo) {
		super.setCorreo(correo);
		if (persist) {
			save();
		}
	}

	@Override
	public void setFoto(String foto) {
		super.setFoto(foto);
		if (persist) {
			save();
		}
	}

	@Override
	public void setListas_creadas(List<Lista> listas_creadas) {
		super.setListas_creadas(listas_creadas);
		if (persist) {
			save();
		}
	}

	@Override
	public void setListas_suscrito(List<Lista> listas_suscrito) {
		super.setListas_suscrito(listas_suscrito);
		if (persist) {
			save();
		}
	}
	
	public void persist() {
		persist = true;
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

	/**
	 * Metodo que guarda un usuario en la base de datos
	 *
	 * @return 0 en caso de que no haga nada o 1 si se ha agregado o editado
	 */
	public int save() {
		int result = 0;

		try {
			manager = Connection.connectToMysql();

			if (this.id > 0) {
				//UPDATE
				manager.getTransaction().begin();
				result=manager.createNativeQuery("UPDATE Usuario SET correo = ?, nombre = ?, foto = ? WHERE id = ?")
		        .setParameter(1, this.correo)
		        .setParameter(2, this.nombre)
		        .setParameter(3, this.foto)
		        .setParameter(4, this.id)
		        .executeUpdate();
				manager.getTransaction().commit();
			} else {
				// INSERT
				manager.getTransaction().begin();
				result=manager.createNativeQuery("INSERT INTO Usuario (correo,nombre,foto) VALUES (?,?,?)")
		        .setParameter(1, this.correo)
		        .setParameter(2, this.nombre)
		        .setParameter(3, this.foto)
		        .executeUpdate();
				manager.getTransaction().commit();
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Metodo que Lista todos los usuarios de la base de datos
	 *
	 * @return Todas los usuarios
	 */
	public static List<Usuario> selectAll() {
		return selectAll("");
	}

	/**
	 * Funcion que selecciona por nombre todos los usuarios de la base de datos que
	 * sea por el pattern
	 *
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una lista de usuarios
	 */
	public static List<Usuario> selectAll(String pattern) {
		List<Usuario> result = new ArrayList();

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();
			
			String q = "FROM Usuario";

			if (pattern.length() > 0) {
				q += " WHERE nombre LIKE ?";
			}
			

			if (pattern.length() > 0) {
				result = manager.createQuery("FROM Usuario WHERE nombre LIKE '"+ pattern +"%'")
						.getResultList();
			}else {
				result = manager.createQuery("FROM Usuario").getResultList();
			}

			
			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Funcion que devuelve un usuario pasando el id en cuestion
	 *
	 * @param id por lo que se filtra el select
	 * @return devuelve un usuario
	 */
	public static Usuario selectAllForID(int id) {
		Usuario result = null;

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM Usuario WHERE id = ";
			
			List<Usuario> usuarios = manager.createQuery(q + id).getResultList();
			
			if(usuarios.size()!=0) {
				result = usuarios.get(0);
			}
			
			manager.getTransaction().commit();

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Funcion que devuelve un usuario pasando el nombre de el
	 * 
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve un usuario
	 */
	public static Usuario selectAllForNombre(String nombre) {
		Usuario result = null;

		try {
			manager = Connection.connectToMysql();
			manager.getTransaction().begin();

			String q = "FROM Usuario WHERE nombre = ";
			
			List<Usuario> usuarios = manager.createQuery("FROM Usuario WHERE nombre = '"+ nombre +"'").getResultList();
			
			if(usuarios.size()!=0) {
				result = usuarios.get(0);
			}
			
			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * Borra de la base de datos el usuario
	 *
	 * @return false si no se ha borrado o true si se ha borrado correctamente
	 */
	public boolean remove() {
		boolean result = false;

		if (this.id > 0) {

			try {
				
				manager = Connection.connectToMysql();
				manager.getTransaction().begin();
				
				if(manager.createQuery("DELETE FROM Usuario WHERE id = " + this.id).executeUpdate() == 1) {
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
	 * Metodo que guarda un usuario en la base de datos de H2
	 *
	 * @return 0 en caso de que no haga nada o 1 si se ha agregado o editado
	 */
	public int saveH2() {
		int result = 0;

		try {
			manager = Connection.connectToH2();

			if (this.id > 0) {
				//UPDATE
				manager.getTransaction().begin();
				result=manager.createNativeQuery("UPDATE Usuario SET correo = ?, nombre = ?, foto = ? WHERE id = ?")
		        .setParameter(1, this.correo)
		        .setParameter(2, this.nombre)
		        .setParameter(3, this.foto)
		        .setParameter(4, this.id)
		        .executeUpdate();
				manager.getTransaction().commit();
			} else {
				// INSERT
				manager.getTransaction().begin();
				result=manager.createNativeQuery("INSERT INTO Usuario (correo,nombre,foto) VALUES (?,?,?)")
		        .setParameter(1, this.correo)
		        .setParameter(2, this.nombre)
		        .setParameter(3, this.foto)
		        .executeUpdate();
				manager.getTransaction().commit();
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}
	
	/**
	 * Borra de la base de datos H2 el usuario 
	 *
	 * @return false si no se ha borrado o true si se ha borrado correctamente
	 */
	public boolean removeH2() {
		boolean result = false;

		if (this.id > 0) {

			try {
				
				manager = Connection.connectToH2();
				manager.getTransaction().begin();
				
				if(manager.createQuery("DELETE FROM Usuario WHERE id = " + this.id).executeUpdate() == 1) {
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
	 * Funcion que selecciona por nombre todos los usuarios de la base de datos H2 que
	 * sea por el pattern
	 *
	 * @param pattern Palabra por lo que se filtra el select
	 * @return devuelve una lista de usuarios
	 */
	public static List<Usuario> selectAllH2(String pattern) {
		List<Usuario> result = new ArrayList();

		try {
			manager = Connection.connectToH2();
			manager.getTransaction().begin();
			
			String q = "FROM Usuario";

			if (pattern.length() > 0) {
				q += " WHERE nombre LIKE ?";
			}
			

			if (pattern.length() > 0) {
				result = manager.createQuery("FROM Usuario WHERE nombre LIKE '"+ pattern +"%'")
						.getResultList();
			}else {
				result = manager.createQuery("FROM Usuario").getResultList();
			}

			
			manager.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return result;
	}
}
