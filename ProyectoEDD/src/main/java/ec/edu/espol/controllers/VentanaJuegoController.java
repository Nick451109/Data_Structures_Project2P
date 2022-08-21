/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.controllers;

import CargaDatos.CreadorArboles;
import CargaDatos.LecturaDatos;
import TDA.BinaryTree;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author CAELOS JR 2018
 */
public class VentanaJuegoController implements Initializable {

    @FXML
    private Label pregActual;
    @FXML
    private TextField respuestaActual;
    @FXML
    private TextField numeroPreguntas;
    @FXML
    private Label RespuestasFinal;
    @FXML
    private Button Reintentar;
    private ArrayList<String> preguntas;
    private ArrayList<String> respuestas;
    private BinaryTree<String> arbolJuego;
    private String pregunta;
    private Integer n;
    private Integer tree_levels;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preguntas = LecturaDatos.leerPreguntas();
        respuestas = LecturaDatos.leerRespuestas();
        arbolJuego = CreadorArboles.creadorArboles(preguntas, respuestas);
        // TODO
    }

    @FXML
    private void comenzarJuego(MouseEvent event) {
        pregunta = arbolJuego.getRootContent();
        n = Integer.valueOf(numeroPreguntas.getText());
        tree_levels = arbolJuego.countLevelsRecursive();
        tree_levels--;
        pregActual.setText(pregunta);
    }

    public static void validarAnimal(String animal) {
        if (animal.equals(" ")) {
            System.out.print("\nSe ha llegado al limite de preguntas.\nNo se ha encontrado un animal con esas caracteristicas\n");
        } else {
            System.out.print("El animal que estas pensando es: " + animal + "\n");
        }
    }

    public static String validarRespuesta(String respuesta) {
        if ((respuesta.toUpperCase().equals("SI") || respuesta.toUpperCase().equals("NO"))) {
            return respuesta;
        } else {
            System.out.print("Respuesta invalida\nIntente nuevamente: \n");
            return null;
        }
    }

    @FXML
    private void obtenerRespuesta(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            n--;
            String respuesta = respuestaActual.getText();
            respuesta = validarRespuesta(respuesta);
            if (respuesta == null) {
                //mostrar un warning de que no se puede hacer eso 
                System.out.println("no es valido ");
            } else {
                //---------------------------------------------------
                //cuando me quede sin preguntas y no pueda adivinar con certeza un animal
                //funciona con 2 preguntas
                if (respuesta.toUpperCase().equals("SI") && n == 0 && tree_levels > 1) {
                    BinaryTree<String> subarbol = arbolJuego.iterativeTreeSearch(pregunta);
                    System.out.println("No se pudo llegar a una conclusion");
                    System.out.println("Los posibles animales son: ");
                    BinaryTree.printLeafNodes(subarbol.getLeft());
                    RespuestasFinal.setText("No se pudo llegar a una conclusion \n Los posibles animales son: \n" +subarbol.getLeft().getLeafs());
                    return;
                } else if (respuesta.toUpperCase().equals("NO") && n == 0 && tree_levels > 1) {
                    BinaryTree<String> subarbol = arbolJuego.iterativeTreeSearch(pregunta);
                    System.out.println("No se pudo llegar a una conclusion");
                    System.out.println("Los posibles animales son: ");
                    BinaryTree.printLeafNodes(subarbol.getRight());
                    RespuestasFinal.setText("No se pudo llegar a una conclusion \n Los posibles animales son: \n"+subarbol.getRight().getLeafs());
                    return;
                }
                //-----------------------------------------------------
                //mientras tenga preguntas y no haya adivinado sigo corriendo el juego
                if (respuesta.toUpperCase().equals("SI") && n > 0) {
                    arbolJuego = arbolJuego.getLeft();
                } else if (respuesta.toUpperCase().equals("NO") && n > 0) {
                    arbolJuego = arbolJuego.getRight();
                } //-----------------------------------------------------
                //cuando no tenga preguntas y haya adivinado el animal
                //funciona con 3 preguntas
                else if (respuesta.toUpperCase().equals("NO") && n == 0 && tree_levels == 1) {
                    arbolJuego = arbolJuego.getRight();
                } else if (respuesta.toUpperCase().equals("SI") && n == 0 && tree_levels == 1) {
                    arbolJuego = arbolJuego.getLeft();
                }
                pregunta = arbolJuego.getRootContent();
                respuestaActual.clear();
                if (!arbolJuego.isLeaf()) {
                    pregActual.setText(pregunta);
                } else {
                    validarAnimal(pregunta);
                    RespuestasFinal.setText(pregunta);
                    System.out.println("acabado");
                    return;
                }
            }
        }
    }
}
