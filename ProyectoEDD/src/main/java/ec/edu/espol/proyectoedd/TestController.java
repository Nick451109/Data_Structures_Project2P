/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.proyectoedd;

import CargaDatos.CreadorArboles;
import CargaDatos.LecturaDatos;
import TDA.BinaryTree;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author nick1
 */
public class TestController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> preguntas2 = new ArrayList();
        ArrayList<String> respuestas2 = new ArrayList();
        String primero = "¿Es este animal un mamífero?";
        String segundo= "¿Es este animal un carnívoro?";
        String tercero = "¿Se para este animal sobre cuatro patas?";
        String uno = "Oso SI SI SI";
        String dos = "Lechuza NO SI NO";
        String tres = "Venado SI NO SI";
        String cuatro = "Paloma NO NO NO";
        preguntas2.add(primero);
        preguntas2.add(segundo);
        preguntas2.add(tercero);
        respuestas2.add(uno);
        respuestas2.add(dos);
        respuestas2.add(tres);
        respuestas2.add(cuatro);
        ArrayList<String> preguntas = LecturaDatos.leerPreguntas();
        ArrayList<String> respuestas = LecturaDatos.leerRespuestas();
        BinaryTree<String> salida = CreadorArboles.creadorArboles(preguntas, respuestas);
        LinkedList<String> visualizacion = salida.breadthTraversal();
        for(String s:visualizacion){
            System.out.println(s);
        }
    }    
    
}
