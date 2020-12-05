package musicddbb.model;

import java.util.ArrayList;
import java.util.List;

public class ListaDAO extends Lista {

	private boolean persist;

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

	public void save() {

	}

}
