package musicddbb;

import musicddbb.model.Artista;
import musicddbb.model.Cancion;
import musicddbb.model.Disco;
import musicddbb.model.DiscoDAO;
import musicddbb.model.Usuario;
import musicddbb.model.UsuarioDAO;
import musicddbb.model.Lista;
import musicddbb.model.ListaDAO;
import musicddbb.model.SuscripcionDAO;
import musicddbb.utils.Connection;
import musicddbb.utils.Utils;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class AppControllerUsuario {
    public static Usuario usuario = null;

    public static void ejecutar() {
        principal();
    }

    public static void principal() {
        int numero;

        do {
            Scanner teclado = new Scanner(System.in);
            System.out.println("\n+-------------------+");
            System.out.println("|        Menu       |");
            System.out.println("+-------------------+");
            System.out.println("| 1) Iniciar sesion |");
            System.out.println("| 2) Registrarse    |");
            System.out.println("| 3) Empleados      |");
            System.out.println("| 0) Salir          |");
            System.out.println("+-------------------+");

            numero = Utils.devolverInt("Introduce una opción: ");
            opciones_principal(numero);
        } while (numero != 0);
    }

    static void opciones_principal(int numero) {
        switch (numero) {
            case 1:
                Iniciar_sesion(1);
                break;

            case 2:
                registrarse();
                break;

            case 3: 
                Iniciar_sesion(3);
                break;
                
            case 0:
                Connection.closeConnection();
                break;

        }
    }

    public static void Iniciar_sesion(int opcion) {

        boolean result = false;

        System.out.println("\n+-------------------+");
        System.out.println("|   Iniciar Sesion  |");
        System.out.println("+-------------------+");
        String nombre = Utils.devolverString("Introduce tu nombre: ");
        String correo = Utils.devolverString("Introduce tu correo: ");

        if (nombre.equals("") || correo.equals("")) {
            System.out.println("Usted no ha introducido nada");
            Utils.pulsarEnter();
        } else {
            List<Usuario> Buscar = UsuarioDAO.selectAll(nombre);

            boolean bandera = false;
            for (int i = 0; i < Buscar.size() && !bandera; i++) {
                if (Buscar.get(i).getNombre().equals(nombre) && Buscar.get(i).getCorreo().equals(correo)) {
                    usuario = Buscar.get(i);
                    bandera = true;
                }
            }
            if (nombre.equals("Admin") && bandera == true && opcion == 3) {
                AppControllerAdmin.lista_sesionAdmin();
            } else if (bandera == true && !nombre.equals("Admin") && opcion == 1) {
                lista_sesion(nombre);
            } else {
                System.out.println("Ususario o correo no validos");
                Utils.pulsarEnter();
            }

        }

    }

    public static void registrarse() {
        System.out.println("\n+-------------------+");
        System.out.println("|    Registrarse    |");
        System.out.println("+-------------------+");
        String nombre = Utils.devolverString("Introduce tu nombre: ");
        String correo = Utils.devolverString("Introduce un correo: ");
        String foto = Utils.devolverString("Introduce una foto: ");
        if (nombre.equals("") || correo.equals("") || foto.equals("")) {
            System.out.println("No se ha podido realizar el registro");
        } else {
        	Usuario usuarioCreacion = new Usuario(correo, nombre, foto);
            UsuarioDAO usuario = new UsuarioDAO(usuarioCreacion);
            usuario.save();
            System.out.println("Usuario creado");
        }
        Utils.pulsarEnter();
    }
    
    public static void lista_sesion(String usuario) {
        int opcion = 0;
        do {
            System.out.println("\n+----------------------------+");
            System.out.println("|       Menu Usuario         |");
            System.out.println("+----------------------------+");
            System.out.println("| 1) Listar Discos           |");
            System.out.println("| 2) Lista de Reproduccion   |");
            System.out.println("| 3) Suscripciones           |");
            System.out.println("| 0) Salir                   |");
            System.out.println("+----------------------------+");

            opcion = Utils.devolverInt("Introduce una opcion: ");

            switch (opcion) {
                case 1:
                    lista_discos();
                    break;

                case 2:
                    Menu_Lista_Reproduccion(AppControllerUsuario.usuario);
                    break;

                case 3:
                    menu_suscripciones(AppControllerUsuario.usuario);
                    break;
                    
                case 0:
                    AppControllerUsuario.usuario = null;
                    break;
            }
        } while (opcion != 0);
    }
    
    public static void lista_discos() {
        int opcion = 0;
        do {
            System.out.println("\n+--------------------------------------+");
            System.out.println("|             Menu Listar              |");
            System.out.println("+--------------------------------------+");
            System.out.println("| 1) Listar Discos por autor           |");
            System.out.println("| 2) Listar discos por nombre de disco |");
            System.out.println("| 3) Listar Todos                      |");
            System.out.println("| 0) Salir                             |");
            System.out.println("+--------------------------------------+");

            opcion = Utils.devolverInt("Introduce una opcion: ");

            switch (opcion) {
                case 1:
                	/*
                    String patternn = Utils.devolverString("Introduce el nombre del autor: ");

                    List<Artista> artista = ArtistaDAO.selectAll(patternn);
                    List<Disco> disco = new ArrayList<>();
                    
                    if(!artista.isEmpty()){
                        for (Artista a : artista) {
                            System.out.println("El artista que hemos encontrado es: ");
                            System.out.println(a.getNombre());
                            System.out.println("Su discos son: ");
                            for (int i = 0; i < a.getDisco().size(); i++) {
                                System.out.print("\n----------- Nº: "+ (i+1) + " ----------- " + a.getDisco().get(i));
                                disco.add(a.getDisco().get(i));
                            }
                        }

                        int opcion1 = Utils.devolverInt("\nIntroduce el numero de la lista para ver las canciones o 0 para salir : ");

                        if (opcion1!=0 && opcion1>0 && opcion1<=disco.size()) {
                            if(disco.get(opcion1-1).getCanciones().isEmpty()){
                                System.out.println("No hay canciones");
                                Utils.pulsarEnter();
                            }else{
                                System.out.println(disco.get(opcion1 - 1).getCanciones());
                                Utils.pulsarEnter();
                            }
                        } else if (opcion1<0 || opcion1>disco.size()){
                            System.out.println("Introduzca un numero correcto");
                            Utils.pulsarEnter();
                        }
                    }else{
                        System.out.println("No se ha encontrado ningun artista con ese nombre");
                        Utils.pulsarEnter();
                    }

                    */
                    break;

                case 2:
                    String pattern = Utils.devolverString("Introduce el nombre del disco: ");
                    List<Disco> disco_nombre = DiscoDAO.selectAll(pattern);

                    if (disco_nombre.isEmpty()) {
                        System.out.println("No se han encontrado discos");
                        Utils.pulsarEnter();
                    } else {
                        for (int i = 0; i < disco_nombre.size(); i++) {
                            System.out.print("\n----------- Nº: "+ (i+1) + " ----------- " + disco_nombre.get(i));
                        }

                        int opcion2 = Utils.devolverInt("\nIntroduce el numero de la lista para ver las canciones o 0 para salir : ");
                        
                        if (opcion2!=0 && opcion2>0 && opcion2<=disco_nombre.size()) {
                            if(disco_nombre.get(opcion2-1).getCanciones().isEmpty()){
                                System.out.println("No hay canciones");
                                Utils.pulsarEnter();
                            }else{
                                System.out.println(disco_nombre.get(opcion2 - 1).getCanciones());
                                Utils.pulsarEnter();
                            }
                        } else if (opcion2<0 || opcion2>disco_nombre.size()){
                            System.out.println("Introduzca un numero correcto");
                            Utils.pulsarEnter();
                        }
                    }
                    break;

                case 3:
                    List<Disco> discos = DiscoDAO.selectAll();

                    if (discos.isEmpty()) {
                        System.out.println("Error");
                        Utils.pulsarEnter();
                    } else {
                        for (int i = 0; i < discos.size(); i++) {
                            System.out.println("\n----------- Nº: "+ (i+1) + " ----------- " + discos.get(i));
                        }
                        int opcion3 = Utils.devolverInt("Introduce el numero de la lista para ver las canciones o 0 para salir : ");

                        if (opcion3!=0 && opcion3>0 && opcion3<=discos.size()) {
                            if(discos.get(opcion3-1).getCanciones().isEmpty()){
                                System.out.println("No hay canciones");
                                Utils.pulsarEnter();
                            }else{
                                System.out.println(discos.get(opcion3 - 1).getCanciones());
                                Utils.pulsarEnter();
                            }
                        } else if (opcion3<0 || opcion3>discos.size()){
                            System.out.println("Introduzca un numero correcto");
                            Utils.pulsarEnter();
                        }
                        break;
                    }
            }
        } while (opcion != 0);
    }

    public static void Menu_Lista_Reproduccion(Usuario usuario) {
        int opcion = 0;
        do {
            System.out.println("\n+------------------------------+");
            System.out.println("|    Menu Lista Reproduccion   |");
            System.out.println("+------------------------------+");
            System.out.println("| 1) Crear Lista               |");
            System.out.println("| 2) Editar Lista              |");
            System.out.println("| 3) Eliminar Lista            |");
            System.out.println("| 4) Editar Canciones          |");
            System.out.println("| 0) Salir                     |");
            System.out.println("+------------------------------+");

            opcion = Utils.devolverInt("Introduce una opcion: ");
            switch (opcion) {
                case 1:
                    String nombre = Utils.devolverString("Introduzca el nombre de la Lista: ");
                    String descripcion = Utils.devolverString("Introduzca una descripcion para la lista: ");
                    Lista l = new Lista(nombre, descripcion, usuario, null, null);
                    ListaDAO lDAO = new ListaDAO(l);
                    if (lDAO.save() != -1) {
                        System.out.println("La lista de reproduccion se ha creado con exito");
                    } else {
                        System.out.println("No se ha podido crear la lista de reproduccion");
                    }
                    break;

                case 2:
                    List<Lista> listas = ListaDAO.selectAll(usuario.getId());
                    for (Lista list : listas) {
                        System.out.println(list);
                    }

                    int idLista = Utils.devolverInt("Introduzca el id de la lista que desea cambiar: ");
                    Lista lst = ListaDAO.selectAllForID(idLista);

                    if (lst.getId() != -1) {
                        int opcion1 = 0;
                        do {
                            System.out.println("\n+--------------------------------+");
                            System.out.println("|   Lista: " + lst.getId() + "                   |");
                            System.out.println("+--------------------------------+");
                            System.out.println(" 1) Editar nombre: " + lst.getNombre());
                            System.out.println(" 2) Editar descripcion: " + lst.getDescripcion());
                            System.out.println(" 0) Guardar Lista");
                            opcion1 = Utils.devolverInt("Introduce una opcion: ");
                            switch (opcion1) {
                                case 1:
                                    String nombreCambio = Utils.devolverString("Introduce el nuevo nombre: ");
                                    lst.setNombre(nombreCambio);
                                    break;
                                case 2:
                                    String descripcionCambio = Utils.devolverString("Introduce la nueva descripcion: ");
                                    lst.setDescripcion(descripcionCambio);
                                    break;
                                case 0:
                                    ListaDAO listaDAO = new ListaDAO(lst);
                                    if (listaDAO.save() != -1) {
                                        System.out.println("La lista fue guardado con exito");
                                        Utils.pulsarEnter();
                                    } else {
                                        System.out.println("La lista no se ha guardado");
                                        Utils.pulsarEnter();
                                    }
                                    break;
                            }
                        } while (opcion1 != 0);
                    } else {
                        System.out.println("Usted ha introducido un numero incorrecto");
                        Utils.pulsarEnter();
                    }

                    break;

                case 3:
                    List<Lista> listass = ListaDAO.selectAll(usuario.getId());
                    for (Lista listaa : listass) {
                        System.out.println(listaa);
                    }

                    int idListaEliminado = Utils.devolverInt("Introduzca el id de la lista que desea eliminar: ");

                    ListaDAO listaElminado = new ListaDAO(ListaDAO.selectAllForID(idListaEliminado));

                    if (listaElminado.remove()) {
                        System.out.println("La lista se ha sido borrado con exito");
                        Utils.pulsarEnter();
                    } else {
                        System.out.println("La lista no se ha borrado");
                        Utils.pulsarEnter();
                    }

                    break;
                case 4:
                    menu_canciones(AppControllerUsuario.usuario);
                    break;
            }
        } while (opcion != 0);
    }
    
    public static void menu_canciones(Usuario usuario){
        /*
        List<Lista> listasdeusuario = ListaDAO.selectAll(usuario.getId());
        for (Lista list : listasdeusuario) {
            System.out.println(list);
        }
        
        int id_lista = Utils.devolverInt("Introduzca el id de la lista que quiere editar: ");
        
        Lista lista_seleccion = ListaDAO.selectAllForID(id_lista);
        
        if(lista_seleccion.getId() != -1){
            int opcion = 0;
            do {
                System.out.println("");
                System.out.println("          " + lista_seleccion.getNombre() + "        ");
                System.out.println("+----------------------------------+");
                System.out.println("| 1) Listar cancionnes de la lista |");
                System.out.println("| 2) Añadir cancion a la lista     |");
                System.out.println("| 3) Borrar cancion de la lista    |");
                System.out.println("| 0) Salir                         |");
                System.out.println("+----------------------------------+");

                opcion = Utils.devolverInt("Introduce una opcion: ");
                switch (opcion) {
                    case 1:
                        if(lista_seleccion.getCanciones().isEmpty()){
                            System.out.println("No hay canciones");
                        }else{
                            for(Cancion c : lista_seleccion.getCanciones()){
                                System.out.println(c);
                            }
                            Utils.pulsarEnter();
                        }
                        break;
                    case 2:
                        System.out.println("Que cancion quieres agreagar a la lista: ");
                        List<Cancion> all_canciones = CancionDAO.selectAll();
                        for(int i=0; i<all_canciones.size(); i++){
                            System.out.println("\n----------- Nº: "+ (i+1) + " ----------- " + all_canciones.get(i));
                        }

                        int n_cancion = Utils.devolverInt("\nIntroduzca el numero de la cancion para añadirla o 0 para salir: ");

                        if(n_cancion!=0 && n_cancion>0 && n_cancion<=all_canciones.size()){
                            Cancion cancionSelec = all_canciones.get(n_cancion-1);

                            if(!CancionEnLista(lista_seleccion, cancionSelec)){
                                Lista_CancionDAO.guardarCancionEnLista(lista_seleccion.getId(), cancionSelec.getId());
                                lista_seleccion.getCanciones().add(cancionSelec);
                                System.out.println("Usted ha añadido la cancion con exito");
                                Utils.pulsarEnter();
                            }else{
                                System.out.println("Usted ya ha habia añadido esta cancion a esta lista");
                                Utils.pulsarEnter();
                            }
                        }else if(n_cancion<0 || n_cancion>all_canciones.size()){
                            System.out.println("Introduzca un numero correcto");
                            Utils.pulsarEnter();
                        }
                        break;
                        
                    case 3:
                        System.out.println("Que cancion quieres borrar de la lista: ");
                        for(int i=0; i<lista_seleccion.getCanciones().size(); i++){
                            System.out.println("\n----------- Nº: "+ (i+1) + " ----------- " + lista_seleccion.getCanciones().get(i));
                        }
                        int n_cancionBorrar = Utils.devolverInt("\nIntroduzca el numero de la cancion para borrarla o 0 para salir: ");

                        if(n_cancionBorrar<0 || n_cancionBorrar>lista_seleccion.getCanciones().size()){
                            System.out.println("Introduzca un numero correcto");
                            Utils.pulsarEnter();
                        }else if(n_cancionBorrar!=0 && n_cancionBorrar>0 && n_cancionBorrar<=lista_seleccion.getCanciones().size()){
                            Cancion cancionBorrar = lista_seleccion.getCanciones().get(n_cancionBorrar-1);
                            Lista_CancionDAO.remove(lista_seleccion.getId(), cancionBorrar.getId());
                            lista_seleccion.getCanciones().remove(cancionBorrar);
                            System.out.println("La cancion ha sido borrada con exito");
                            Utils.pulsarEnter();
                        }
                        break;
                }
            } while (opcion != 0);
        }else{
            System.out.println("Usted ha introducido un numero incorrecto");
            Utils.pulsarEnter();
        }
        */
    }
    
    public static boolean CancionEnLista(Lista lista, Cancion cancion){
        boolean aux = false;
        for(int i=0; i<lista.getCanciones().size() && !aux; i++){
            if(lista.getCanciones().get(i).getId() == cancion.getId()){
                aux = true;
            }
        }
        return aux;
    }
    
    public static void menu_suscripciones(Usuario usuario){
        int opcion = 0;
        do{
            System.out.println("\n+---------------------------------+");
            System.out.println("|       Menu Suscripciones        |");
            System.out.println("+---------------------------------+");
            System.out.println("| 1) Listar suscripciones         |");
            System.out.println("| 2) Suscribirse a una lista      |");
            System.out.println("| 3) Desuscribirse de una lista   |");
            System.out.println("| 0) Salir                        |");
            System.out.println("+---------------------------------+");

            opcion = Utils.devolverInt("Introduce una opcion: ");

            switch (opcion) {
                case 1:
                    System.out.println("Tus suscripciones son: ");
                    for(Lista l: usuario.getListas_suscrito()){
                        System.out.println(l);
                    }
                    Utils.pulsarEnter();
                    break;

                case 2:
                    System.out.println("Estas son las listas de canciones que hay: ");
                    List<Lista> all_listas = ListaDAO.selectAll();
                    for(int i=0; i<all_listas.size(); i++){
                        System.out.println("\n----------- Nº: "+ (i+1) + " ----------- " + all_listas.get(i));
                    }
                    
                    int n_lista = Utils.devolverInt("\nIntroduzca el numero de la lista para suscribirse o 0 para salir: ");
                    
                    if(n_lista!=0 && n_lista>0 && n_lista<=all_listas.size()){
                        Lista listaSelec = all_listas.get(n_lista-1);
                        
                        if(!UsuarioEnLista(listaSelec.getUsuarios_suscritos(), usuario)){
                            SuscripcionDAO.guardarSuscripcion(usuario.getId(), listaSelec.getId());
                            //usuario.getListas_suscrito().add(listaSelec);
                            System.out.println("Usted se ha suscrito con exito");
                            Utils.pulsarEnter();
                        }else{
                            System.out.println("Usted ya esta suscrito a esa lista");
                            Utils.pulsarEnter();
                        }
                    }else if(n_lista<0 || n_lista>all_listas.size()){
                        System.out.println("Introduzca un numero correcto");
                        Utils.pulsarEnter();
                    }
                    
                    break;

                case 3:
                    List<Lista> all_listas_Suscrito = usuario.getListas_suscrito();
                    System.out.println("A que lista te quieres desuscribir: ");
                    for(int i=0; i<all_listas_Suscrito.size(); i++){
                        System.out.println("\n----------- Nº: "+ (i+1) + " ----------- " + all_listas_Suscrito.get(i));
                    }
                    int n_listaBorrar = Utils.devolverInt("\nIntroduzca el numero de la lista para desuscribirse o 0 para salir: ");
                    
                    if(n_listaBorrar<0 || n_listaBorrar>all_listas_Suscrito.size()){
                        System.out.println("Introduzca un numero correcto");
                        Utils.pulsarEnter();
                    }else if(n_listaBorrar!=0 && n_listaBorrar>0 && n_listaBorrar<=all_listas_Suscrito.size()){
                        Lista listaBorrar = all_listas_Suscrito.get(n_listaBorrar-1);
                        SuscripcionDAO.remove(usuario.getId(), listaBorrar.getId());
                        usuario.getListas_suscrito().remove(listaBorrar);
                        System.out.println("Usted se ha desuscrito con exito");
                        Utils.pulsarEnter();
                    }
                    
                    break;

            }
        } while (opcion != 0);
    }
    
    public static boolean UsuarioEnLista(List<Usuario> ListaEnUsuarios, Usuario usuario){
        boolean aux = false;
        for(int i=0; i<ListaEnUsuarios.size() && !aux; i++){
            if(ListaEnUsuarios.get(i).getId() == usuario.getId()){
                aux = true;
            }
        }
        return aux;
    }

}
