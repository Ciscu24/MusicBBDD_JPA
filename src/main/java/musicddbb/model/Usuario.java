package musicddbb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	protected int id;
	@Column(name="correo")
	protected String correo;
	@Column(name="nombre")
	protected String nombre;
	@Column(name="foto")
	protected String foto;
	
	@OneToMany(mappedBy="creador", cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	protected List<Lista> listas_creadas = new ArrayList<Lista>();
	
	@JoinTable(name="usuarios_suscritos", joinColumns= @JoinColumn(name="id_usuario"), inverseJoinColumns= @JoinColumn(name="id_lista"))
	@ManyToMany(cascade= {CascadeType.ALL})
	protected List<Lista> listas_suscrito = new ArrayList<Lista>();

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
		this.correo = correo;
		this.nombre = nombre;
		this.foto = foto;
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

	public List<Lista> getListas_creadas() {
		return listas_creadas;
	}

	public void setListas_creadas(List<Lista> listas_creadas) {
		this.listas_creadas = listas_creadas;
		for(Lista l: this.listas_creadas) {
			l.setCreador(this);
		}
	}

	public List<Lista> getListas_suscrito() {
		return listas_suscrito;
	}

	public void setListas_suscrito(List<Lista> listas_suscrito) {
		this.listas_suscrito = listas_suscrito;
		for(Lista l: listas_suscrito) {
			List<Usuario> usuarios = l.getUsuarios_suscritos();
			if(usuarios==null) {
				usuarios = new ArrayList<Usuario>();
			}
			if(!usuarios.contains(this)) {
				usuarios.add(this);
			}
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\n------ ID: " + id + " ------\nCorreo: " + correo + "\nNombre: " + nombre + "\nFoto: " + foto;
	}
	
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

	/*
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
