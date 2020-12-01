package musicddbb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    /*protected List<Cancion> canciones;
    protected List<Usuario> usuarios_suscritos;*/

    public Lista() {
        this.id = -1;
    }
    
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

    /*public List<Cancion> getCanciones() {
        if(canciones == null){
            //canciones = Lista_CancionDAO.selectAllCanciones(id);
        }
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public List<Usuario> getUsuarios_suscritos() {
        if(usuarios_suscritos == null){
            //usuarios_suscritos = SuscripcionDAO.selectAllUsuario(id);
        }
        return usuarios_suscritos;
    }

    public void setUsuarios_suscritos(List<Usuario> usuarios_suscritos) {
        this.usuarios_suscritos = usuarios_suscritos;
    }*/
    
    

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
    /*
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
    }*/

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
