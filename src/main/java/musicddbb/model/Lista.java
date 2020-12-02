package musicddbb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="lista")
public class Lista {
	@Id
	@Column(name="id")
	protected int id;
	@Column(name="nombre")
    protected String nombre;
	@Column(name="descripcion")
    protected String descripcion;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_usuario")
    protected Usuario creador;
	@JoinTable(
	        name = "listas",
	        joinColumns = @JoinColumn(name = "id_lista", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="id_cancion", nullable = false)
	    )
	    @ManyToMany(cascade = CascadeType.ALL)
    protected List<Cancion> canciones;
	
	@ManyToMany(mappedBy="listas_suscrito", cascade= {CascadeType.ALL})
    protected List<Usuario> usuarios_suscritos = new ArrayList<Usuario>();
    
    public Lista(int id, String nombre, String descripcion) {
    	this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public Lista(int id, String nombre, String descripcion, Usuario creador) {
    	this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
    }

    public Lista(int id, String nombre, String descripcion, Usuario creador, List<Cancion> canciones, List<Usuario> usuarios_suscritos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
        //this.canciones = canciones;
        //this.usuarios_suscritos = usuarios_suscritos;
    }

    public Lista(String nombre, String descripcion, Usuario creador, List<Cancion> canciones, List<Usuario> usuarios_suscritos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
        //this.canciones = canciones;
        //this.usuarios_suscritos = usuarios_suscritos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
    	this.creador = creador;
    	List<Lista> listas = this.creador.getListas_creadas();
    	if(listas==null) {
    		listas = new ArrayList<Lista>();
    	}
    	if(!listas.contains(this)) {
    		listas.add(this);
    	}
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }
    
    public void setCanciones(Cancion c) {
    	if(this.canciones==null) {
    		this.canciones= new ArrayList<Cancion>();
    		this.canciones.add(c);
    	}
    	if(!this.canciones.contains(c)) {
    		this.canciones.add(c);
    		List<Lista> mylist = c.getListas();
    		if(mylist==null) {
    			mylist = new ArrayList<Lista>();
    			mylist.add(this);
    		}
    		if(mylist.contains(this)) {
    			mylist.add(this);
    		}
    	}
    }

    public List<Usuario> getUsuarios_suscritos() {
        return usuarios_suscritos;
    }
    
    public void setUsuarios_suscritos(List<Usuario> usuarios_suscritos) {
    	this.usuarios_suscritos = usuarios_suscritos;
		for(Usuario u: usuarios_suscritos) {
			List<Lista> listas = u.getListas_suscrito();
			if(listas==null) {
				listas = new ArrayList<Lista>();
			}
			if(!listas.contains(this)) {
				listas.add(this);
			}
		}
    }

    @Override
    public String toString() {
        return "\n------ ID: "+id+" ------\nNombre: "+nombre+"\nDescripcion: "+descripcion;
    }
    
    public String toStringWithUsuario(){
        String cadena = "";
        cadena+=toString();
        cadena+="\nCreador: ";
        cadena+="\n---------------------------------";
        cadena+=creador;
        cadena+="\n---------------------------------";
        return cadena;
    }
    
    public String toStringWithCanciones() {
        String cadena = "";
        cadena+=toString();
        if(!canciones.isEmpty()){
            cadena+="\nCanciones: ";
            cadena+="\n---------------------------------";
            for(Cancion c: canciones){
                cadena+=c;
            }
            cadena+="\n---------------------------------";
        }else{
            cadena+="\nCanciones: No tiene canciones";
        }
                
        return cadena;
    }
    
    public String toStringWithUsuarios_Suscritos() {
        String cadena = "";
        cadena+=toString();
        if(!usuarios_suscritos.isEmpty()){
            cadena+="\nUsuarios suscritos: ";
            cadena+="\n---------------------------------";
            for(Usuario u: usuarios_suscritos){
                cadena+=u;
            }
            cadena+="\n---------------------------------";
        }else{
            cadena+="\nUsuarios suscritos: No tiene usuarios";
        }
                
        return cadena;
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
		Lista other = (Lista) obj;
		if (id != other.id)
			return false;
		return true;
	}
    
}