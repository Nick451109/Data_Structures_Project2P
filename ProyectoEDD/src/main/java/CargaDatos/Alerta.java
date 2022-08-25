/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CargaDatos;

import javafx.scene.control.Alert;

/**
 *
 * @author USUARIO
 */
public class Alerta {
    public static void crearAlerta(String titulo, String mensaje){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.show();
    }
    
}
