package musicddbb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
	
	@Id
	@Column(name="id")
	protected int id;
	@Column(name="correo")
	protected String correo;
	@Column(name="nombre")
	protected String nombre;
	@Column(name="foto")
	protected String foto;
	
	//protected List<Lista> listas_creadas;
	//protected List<Lista> listas_suscrito;

	public Usuario() {
	}

	public Usuario(int id, String correo, String nombre, String foto, List<Lista> listas_creadas,
			List<Lista> listas_suscrito) {
		this.id = id;
		this.correo = correo;
		this.nombre = nombre;
		this.foto = foto;
		//this.listas_creadas = listas_creadas;
		//this.listas_suscrito = listas_suscrito;
	}

	public Usuario(String correo, String nombre, String foto) {
		this.id = -1;
		this.correo = correo;
		this.nombre = nombre;
		this.foto = foto;
		//this.listas_creadas = null;
		//this.listas_suscrito = null;
	}
	
	public Usuario(int id, String correo, String nombre, String foto) {
		this.id = id;
		this.correo = correo;
		this.nombre = nombre;
		this.foto = foto;
		//this.listas_creadas = null;
		//this.listas_suscrito = null;
	}

	/*public Usuario(String correo, String nombre, String foto, List<Lista> listas_creadas, List<Lista> listas_suscrito) {
		this.id = -1;
		this.correo = correo;
		this.nombre = nombre;
		this.foto = foto;
		//this.listas_creadas = listas_creadas;
		//this.listas_suscrito = listas_suscrito;
	}*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	/*public List<Lista> getListas_creadas() {
		if (listas_creadas == null) {
			// listas_creadas = ListaDAO.selectAll(id);
		}
		return listas_creadas;
	}

	public void setListas_creadas(List<Lista> listas_creadas) {
		this.listas_creadas = listas_creadas;
	}

	public List<Lista> getListas_suscrito() {
		if (listas_suscrito == null) {
			// listas_suscrito = SuscripcionDAO.selectAllListas(id);
		}
		return listas_suscrito;
	}

	public void setListas_suscrito(List<Lista> listas_suscrito) {
		this.listas_suscrito = listas_suscrito;
	}*/

	@Override
	public String toString() {
		return "\n------ ID: " + id + " ------\nCorreo: " + correo + "\nNombre: " + nombre + "\nFoto: " + foto;
	}
	/*
	public String toStringWithListas_Creadas() {
		String cadena = "";
		cadena += toString();
		if (!listas_creadas.isEmpty()) {
			cadena += "\nListas creadas: ";
			cadena += "\n---------------------------------";
			for (Lista l : listas_creadas) {
				cadena += l;
			}
			cadena += "\n---------------------------------";
		} else {
			cadena += "\nListas creadas: No tiene listas";
		}

		return cadena;
	}

	public String toStringWithListas_Suscrito() {
		String cadena = "";
		cadena += toString();
		if (!listas_suscrito.isEmpty()) {
			cadena += "\nListas suscrito: ";
			cadena += "\n---------------------------------";
			for (Lista l : listas_suscrito) {
				cadena += l;
			}
			cadena += "\n---------------------------------";
		} else {
			cadena += "\nListas suscrito: No tiene listas";
		}

		return cadena;
	}*/
}
