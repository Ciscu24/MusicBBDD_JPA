package musicddbb.model;

import java.util.List;



public class Artista {

	protected int id;

    protected String nombre;

    protected String nacionalidad;

    protected String foto;
    
	
    protected List<Disco> disco;

    public Artista() {
        this(-1, "", "", "", null);
    }

    public Artista(String nombre, String nacionalidad, String foto, List<Disco> disco) {
        this.id = -1;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.foto = foto;
        this.disco = disco;
    }

    public Artista(int id, String nombre, String nacionalidad, String foto, List<Disco> disco) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.foto = foto;
        this.disco = disco;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Disco> getDisco() {

        if (disco == null) {
            //disco = DiscoDAO.selectAll(id);
        }

        return disco;
    }

    public void setDisco(List<Disco> disco) {
        this.disco = disco;
    }

    @Override
    public String toString() {
        return "\n------ ID: "+id+" ------\nNombre: "+nombre+"\nNacionalidad: "+nacionalidad+"\nFoto: "+foto;
    }

    public String toStringWithDisco() {
        String cadena = "";
        cadena+=toString();
        if(!disco.isEmpty()){
            cadena+="\nDiscos: ";
            cadena+="\n---------------------------------";
            for(Disco d: disco){
                cadena+=d;
            }
            cadena+="\n---------------------------------";
        }else{
            cadena+="\nDiscos: No tiene discos";
        }
                
        return cadena;
    }
}
