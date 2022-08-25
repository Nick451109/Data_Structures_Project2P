/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javafx.scene.control.Alert;

/**
 *
 * @author USUARIO
 */
public class Util {
    public static void imprimirNameGame(){
        System.out.print("********************JUEGO*******************\n");
        System.out.print("*******¿En que animal estas pensando?*******\n");
    }
        public static void crearAlerta(String titulo, String mensaje){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.show();
    }
    public static void imprimirNoHaySolucion(){
        System.out.println("No se pudo llegar a una conclusion");
        System.out.println("Los posibles animales son: ");

    }
    
    
}
