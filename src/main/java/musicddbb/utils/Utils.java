package musicddbb.utils;

import java.util.Scanner;

public class Utils {
    
    /**
     * Metodo que escribe y devuelve un string que introduzca un usuario
     *
     * @param texto Texto que quieres que se escribe, ejemplo (Introduce
     * contraseña)
     * @return string que introduce el usuario
     */
    public static String devolverString(String texto) {
        String resultado;
        Scanner teclado = new Scanner(System.in);

        System.out.print(texto);
        resultado = teclado.nextLine();

        return resultado;
    }

    /**
     * Metodo que escribe y devuelve un int que introduzca un usuario
     *
     * @param texto Texto que quieres que se escribe, ejemplo (Introduce numero)
     * @return int que introduce el usuario
     */
    public static int devolverInt(String texto) {
        int resultado = 0;
        boolean valid = false;
        Scanner teclado = new Scanner(System.in);
        do {
            try {
                System.out.print(texto);
                resultado = teclado.nextInt();
                valid = true;
            } catch (Exception e) {
                valid = false;
                System.out.println("\nIntroduce un numero correcto");
                teclado = new Scanner(System.in);
            }
        } while (!valid);
        return resultado;
    }

    /**
     * Metodo para que tengas que pulsar Enter para continuar
     */
    public static void pulsarEnter() {
        System.out.print("\nPulsa Enter para continuar...");
        Scanner teclado = new Scanner(System.in);
        teclado.nextLine();
    }
    
}
