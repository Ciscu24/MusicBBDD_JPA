package musicddbb.model;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="cancion")
public class Cancion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	protected int id;
	@Column(name="nombre")
    protected String nombre;
	@Column(name="duracion")
    protected int duracion;
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_disco")
    protected Disco disco_contenedor;
	@ManyToMany(mappedBy = "canciones", cascade= {CascadeType.ALL})
    protected List<Lista> listas;
    
    public Cancion() {}

    public Cancion(int id, String nombre, int duracion, Disco disco_contenedor, List<Lista> listas) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.disco_contenedor = disco_contenedor;
        this.listas = listas;
    }
    public Cancion(int id, String nombre, int duracion) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
     
     
    }
    
    public Cancion(String nombre, int duracion, Disco disco_contenedor) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.disco_contenedor = disco_contenedor;
        this.listas = null;
    }

    public Cancion(String nombre, int duracion, Disco disco_contenedor, List<Lista> listas) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.disco_contenedor = disco_contenedor;
        this.listas = listas;
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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Disco getDisco_contenedor() {

        return disco_contenedor;
    }

    public void setDisco_contenedor(Disco disco_contenedor) {
        this.disco_contenedor = disco_contenedor;
        List<Cancion> canciones= this.disco_contenedor.getCanciones();
        if(canciones==null) {
        	canciones=new ArrayList<Cancion>();
        }
        if(!canciones.contains(this)) {
        	canciones.add(this);
        }
       
    }

    public List<Lista> getListas() {
       
        return listas;
    }

    public void setListas(List<Lista> listas) {
    	this.listas = listas;
		for(Lista l: listas) {
			List<Cancion> canciones = l.getCanciones();
			if(canciones==null) {
				canciones = new ArrayList<Cancion>();
			}
			if(!canciones.contains(this)) {
				canciones.add(this);
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
		Cancion other = (Cancion) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "\n------ ID: "+id+" ------\nNombre: "+nombre+"\nDuracion: "+duracion;
    }
    
    public String toStringWithDisco(){
        String cadena = "";
        cadena+=toString();
        cadena+="\nDisco contenedor: ";
        cadena+="\n---------------------------------";
        cadena+=disco_contenedor;
        cadena+="\n---------------------------------";
        return cadena;
    }
    
    public String toStringWithListas() {
        String cadena = "";
        cadena+=toString();
        if(!listas.isEmpty()){
            cadena+="\nListas: ";
            cadena+="\n---------------------------------";
            for(Lista l: listas){
                cadena+=l;
            }
            cadena+="\n---------------------------------";
        }else{
            cadena+="\nListas: No tiene listas";
        }
                
        return cadena;
    }
}
