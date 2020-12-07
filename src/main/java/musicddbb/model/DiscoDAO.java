package musicddbb.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;

import musicddbb.utils.Connection;

public class DiscoDAO extends Disco {
	private boolean persist;
	private static EntityManager manager;
	
	private DiscoDAO(int id) {
		super(id);
		persist=false;
	}
	
	private DiscoDAO() {
		super();
		persist=false;		
	}
	
	private DiscoDAO(String nombre,String foto,Artista creador, Date fecha_produccion,List<Cancion> canciones ) {
		super(nombre,foto,creador,fecha_produccion,canciones);
		persist=false;		
	}
	
	private DiscoDAO(int id,String nombre,String foto,Artista creador, Date fecha_produccion,List<Cancion> canciones ) {
		super(id,nombre,foto,creador,fecha_produccion,canciones);
		persist=false;
	
	}
	
	public void persist() {
		persist=true;
	}
	
	public DiscoDAO(Disco d) {
		this.id=d.id;
		this.nombre=d.nombre;
		this.creador=d.creador;
		this.fecha_produccion=d.fecha_produccion;
		this.canciones=d.canciones;		
	}
	
	public void detatch() {
		persist=false;
	}
	
	public boolean ispersist() {
		return persist;
	}
	
	public void setPersist(boolean persist) {
		this.persist=persist;
	}
	
	public void setId(int id) {
		super.setId(id);
		if(persist) {
			save();
		}
	}
	
	public void setNombre(String nombre) {
		super.setNombre(nombre);
		if(persist) {
			save();
		}
	}
	
	public void setFoto(String foto) {
		super.setFoto(foto);
		if(persist) {
			save();
		}
	}
	
	//Relación de miguel 
	public void setCreador(Artista creador) {
		
	}
	
	public void setFecha_produccion(Date fecha_produccion) {
		super.setFecha_produccion(fecha_produccion);
		if(persist) {
			save();
		}
	}
	
	public void setCanciones(List<Cancion> canciones) {
		super.setCanciones(canciones);
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
	
	
	
}
	















