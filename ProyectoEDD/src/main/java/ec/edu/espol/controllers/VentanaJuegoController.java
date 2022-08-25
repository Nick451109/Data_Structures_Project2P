/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.controllers;

import CargaDatos.CreadorArboles;
import CargaDatos.LecturaDatos;
import TDA.BinaryTree;
import Util.Util;
import ec.edu.espol.proyectoedd.App;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private TextField numeroPreguntas;
    @FXML
    private Label RespuestasFinal;
    @FXML
    private Button Reintentar;
    private ArrayList<String> preguntas;
    private ArrayList<String> respuestas;
    private BinaryTree<String> arbolJuego;
    private String pregunta;
    private Integer npregDisponibles;
    private Integer tree_levels;
    @FXML
    private Button btYES;
    @FXML
    private Button btNo;

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
        if(numeroPreguntas.getText().isBlank()){
            Util.crearAlerta("ERROR", "No se ha ingresado el numero de preguntas");
        }else{
            pregunta = arbolJuego.getRootContent();
            npregDisponibles = Integer.valueOf(numeroPreguntas.getText());
            tree_levels = arbolJuego.countLevelsRecursive();
            validarCantidadPreguntas();
            pregActual.setText(pregunta);
            habilitarbotonesJuego(true);
        }       
    }

    @FXML
    private void respuestaSI(MouseEvent event) {
        actualizarVariables();
        //cuando me quede sin preguntas y no pueda adivinar con certeza un animal
        if (npregDisponibles == 0 && tree_levels > 1) {
            BinaryTree<String> subarbol = arbolJuego.iterativeTreeSearch(pregunta);
            System.out.println(arbolJuego);
            Util.imprimirNoHaySolucion();
            BinaryTree.printLeafNodes(subarbol.getLeft());
            RespuestasFinal.setText("No se pudo llegar a una conclusion \n Los posibles animales son: \n" + subarbol.getLeft().getLeafs());
            System.out.println("\n"+subarbol.getLeft().getLeafs());
            mostrarRespuesta(true);
            return;
        
        }
        //mientras tenga preguntas y no haya adivinado sigo corriendo el juego
        else if( npregDisponibles > 0) {
            arbolJuego = arbolJuego.getLeft();
        }
        //cuando no tenga preguntas y haya adivinado el animal
        else if(npregDisponibles == 0 && tree_levels == 1) {
            arbolJuego = arbolJuego.getLeft();
        }
        pregunta = arbolJuego.getRootContent();
        if (!arbolJuego.isLeaf()) {
            pregActual.setText(pregunta);
        } else {
            validarAnimal(pregunta);
            mostrarRespuesta(true);
            System.out.println("acabado");
            return;
                }
    }

    @FXML
    private void respuestaNO(MouseEvent event) {
        actualizarVariables();
        //cuando me quede sin preguntas y no pueda adivinar con certeza un animal
        if (npregDisponibles == 0 && tree_levels > 1) {
            BinaryTree<String> subarbol = arbolJuego.iterativeTreeSearch(pregunta);
            Util.imprimirNoHaySolucion();
            BinaryTree.printLeafNodes(subarbol.getRight());
            LinkedList<String> leafNodes= subarbol.getRight().getLeafs();
            validarListaAnimales(leafNodes);
            mostrarRespuesta(true);
            return;
        }      
        //mientras tenga preguntas y no haya adivinado sigo corriendo el juego
        else if ( npregDisponibles > 0) {
            arbolJuego = arbolJuego.getRight();
        }
        //cuando no tenga preguntas y haya adivinado el animal
        else if (npregDisponibles == 0 && tree_levels == 1) {
            arbolJuego = arbolJuego.getRight();
        }
        pregunta = arbolJuego.getRootContent();
        if (!arbolJuego.isLeaf()) {
            pregActual.setText(pregunta);
        }else {
            validarAnimal(pregunta);
            mostrarRespuesta(true);
            System.out.println("acabado");
            return;
        }
    }
    @FXML
    private void regresar(MouseEvent event) throws IOException {
        App.setRoot("VentanaPrincipal");

    }
    @FXML
    private void JugarDeNuevo(MouseEvent event) throws IOException {
         App.setRoot("VentanaJuego");
    }
    
    public void actualizarVariables(){
        npregDisponibles--;        
        tree_levels = arbolJuego.countLevelsRecursive();
        tree_levels--;      
    }
    
    
    public void validarCantidadPreguntas(){
        if(tree_levels-1<npregDisponibles){
            Util.crearAlerta("Comando Invalido", "Cantidad máxima de preguntas: " + (tree_levels -1) );
        }
    }
    public  void validarAnimal(String animal) {
        if (animal.equals(" ")) {
            System.out.print("\nSe ha llegado al limite de preguntas.\nNo se ha encontrado un animal con esas caracteristicas\n");
            RespuestasFinal.setText("Se ha llegado al limite de preguntas.\nNo se ha encontrado un animal con esas caracteristicas\n");
        } else {
            System.out.print("El animal que estas pensando es: " + animal + "\n");
            RespuestasFinal.setText(pregunta);

        }
    }
    public void validarListaAnimales(LinkedList<String> animales){
        if(animales.size()==0){
            RespuestasFinal.setText("Se ha llegado al limite de preguntas.\nNo se ha encontrado un animal con esas caracteristicas\n");
        }else if(animales.size()==1){
            RespuestasFinal.setText(animales.toString());
        }else{
            RespuestasFinal.setText("No se pudo llegar a una conclusion \n Los posibles animales son: \n" +animales.toString());
        }
    }
    public void habilitarbotonesJuego(boolean b){
        btYES.setVisible(b);
        btNo.setVisible(b);
        pregActual.setVisible(b);
    }
    public void mostrarRespuesta(Boolean b){
        RespuestasFinal.setVisible(b);
        habilitarbotonesJuego(!b);
        Reintentar.setVisible(true);
    }




}
