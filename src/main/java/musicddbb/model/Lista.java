package musicddbb.model;

import java.util.List;

public class Lista {
	protected int id;
    protected String nombre;
    protected String descripcion;
    protected Usuario creador;
    protected List<Cancion> canciones;
    protected List<Usuario> usuarios_suscritos;

    public Lista() {
        this.id = -1;
    }

    public Lista(int id, String nombre, String descripcion, Usuario creador, List<Cancion> canciones, List<Usuario> usuarios_suscritos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
        this.canciones = canciones;
        this.usuarios_suscritos = usuarios_suscritos;
    }

    public Lista(String nombre, String descripcion, Usuario creador, List<Cancion> canciones, List<Usuario> usuarios_suscritos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
        this.canciones = canciones;
        this.usuarios_suscritos = usuarios_suscritos;
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
        if(this.creador.correo.equals("") || this.creador.nombre.equals("") || this.creador.foto.equals("")){
            //creador = new UsuarioDAO().selectAllForID(creador.id);
        }
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public List<Cancion> getCanciones() {
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
}
