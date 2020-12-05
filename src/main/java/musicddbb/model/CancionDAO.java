package musicddbb.model;

import java.util.ArrayList;
import java.util.List;

public class CancionDAO extends Cancion{
	private boolean persist;

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
	//	this.disco_contenedor= c.disco_contenedor;
		this.listas= c.listas;
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
			};
	    }
	  
	  public void setListas(Lista lista) {
		  super.setListas(lista);
				if (persist) {
					save();
				};
			
	    }
	  
		public void save() {

		}


}
