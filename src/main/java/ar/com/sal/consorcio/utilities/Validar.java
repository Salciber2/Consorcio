package ar.com.sal.consorcio.utilities;

public class Validar {
    
    public static boolean esNumero(String cadena){
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }
}
