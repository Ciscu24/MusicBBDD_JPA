package musicddbb.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lista")
public class Disco {
	@Id
	@Column(name="id")
	protected int id;
	@Column(name = "nombre")
    protected String nombre;
	@Column(name = "foto")
    protected String foto;
	
	
    protected Artista creador;
	
    @Column(name = "fecha_produccion")
    protected Date fecha_produccion;
    
    @OneToMany(mappedBy = "disco_contenedor", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    protected List<Cancion> canciones;

    public Disco(int id) {
        this.id = id;
        this.nombre = "";
        this.foto = "";
        this.creador = null;
        this.fecha_produccion = null;
        this.canciones = null;
    }

    public Disco() {
        this(-1, "", "", null, null, null);
    }
    
    public Disco(String nombre, String foto, Artista creador, Date fecha_produccion, List<Cancion> canciones) {
        this.id = -1;
        this.nombre = nombre;
        this.foto = foto;
        this.creador = creador;
        this.fecha_produccion = fecha_produccion;
        this.canciones = canciones;
    }

    public Disco(int id, String nombre, String foto, Artista creador, Date fecha_produccion, List<Cancion> canciones) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.creador = creador;
        this.fecha_produccion = fecha_produccion;
        this.canciones = canciones;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getFecha_produccion() {
        return fecha_produccion;
    }

    public void setFecha_produccion(Date fecha_produccion) {
        this.fecha_produccion = fecha_produccion;
    }
    
    public Artista getCreador() {
        if(creador.nombre.equals("") || creador.foto.equals("") || creador.nacionalidad.equals("")){
           //creador = ArtistaDAO.selectAllForId(creador.id);
        }
        return creador;
    }

    public void setCreador(Artista creador) {
        this.creador = creador;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
        for(Cancion c:this.canciones) {
        	c.setDisco_contenedor(this);
        	
        }
    }
    
    @Override
    public String toString() {
        return "\n------ ID: "+id+" ------\nNombre: "+nombre+"\nFoto: "+foto+"\nFecha de Produccion: "+fecha_produccion;
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
    
    public String toStringWithCreador(){
        String cadena = "";
        cadena+=toString();
        cadena+="\nCreador: ";
        cadena+="\n---------------------------------";
        cadena+=creador;
        cadena+="\n---------------------------------";
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
		Disco other = (Disco) obj;
		if (id != other.id)
			return false;
		return true;
	}



    
    
}
