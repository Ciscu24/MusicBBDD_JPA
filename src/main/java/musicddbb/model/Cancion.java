package musicddbb.model;

import java.util.List;

public class Cancion {
	protected int id;
    protected String nombre;
    protected int duracion;
    protected Disco disco_contenedor;
    protected List<Lista> listas;
    
    public Cancion() {}

    public Cancion(int id, String nombre, int duracion, Disco disco_contenedor, List<Lista> listas) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.disco_contenedor = disco_contenedor;
        this.listas = listas;
    }
    
    public Cancion(String nombre, int duracion, Disco disco_contenedor) {
        this.id = -1;
        this.nombre = nombre;
        this.duracion = duracion;
        this.disco_contenedor = disco_contenedor;
        this.listas = null;
    }

    public Cancion(String nombre, int duracion, Disco disco_contenedor, List<Lista> listas) {
        this.id = -1;
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
        if(disco_contenedor.nombre.equals("") || disco_contenedor.foto.equals("")){
            //disco_contenedor = DiscoDAO.selectAllForId(disco_contenedor.id);
        }
        return disco_contenedor;
    }

    public void setDisco_contenedor(Disco disco_contenedor) {
        this.disco_contenedor = disco_contenedor;
    }

    public List<Lista> getListas() {
        if(listas == null){
            //listas = Lista_CancionDAO.selectAllListas(id);
        }
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
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
