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
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
    private BinaryTree<String> arbolInicialJuego;
    private String pregunta;
    private Integer npregDisponibles;
    private Boolean ultRespuesta;
    private Integer tree_levels;
    @FXML
    private Button btYES;
    @FXML
    private Button btNo;
    @FXML
    private Button CambiarRespuesta;
    @FXML
    private Button empezar;
    @FXML
    private RadioButton agregarAnimalRB;
    @FXML
    private TextField agregarAnimalTF;
    @FXML
    private Button btGuardarNuevoA;
    private Queue<String> qrespuestas;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preguntas = LecturaDatos.leerPreguntas();
        respuestas = LecturaDatos.leerRespuestas();
        System.out.println(preguntas);
        System.out.println(respuestas);
        arbolJuego = CreadorArboles.creadorArboles(preguntas, respuestas);
        arbolInicialJuego = CreadorArboles.creadorArboles(preguntas, respuestas);
        this.ultRespuesta = null;
        qrespuestas = new LinkedList<>();
        // TODO
    }

    @FXML
    private void comenzarJuego(MouseEvent event) {
  //      empezar.setVisible(false);
        if(numeroPreguntas.getText().isBlank()){
            Util.crearAlerta("ERROR", "No se ha ingresado el numero de preguntas");
            return;
        }else if(!numeroPreguntas.getText().matches(".*[0-9].*")){
            Util.crearAlerta("ERROR", "Se debe de ingresar un numero");
            return;
        }
        pregunta = arbolJuego.getRootContent();
        npregDisponibles = Integer.valueOf(numeroPreguntas.getText());
        tree_levels = arbolJuego.countLevelsRecursive();
        if(excedeNumeroPreguntas()){
            return;
        }
        pregActual.setText(pregunta);
        habilitarbotonesJuego(true);
              
    }

    @FXML
    private void respuestaSI(MouseEvent event) {
        qrespuestas.offer("SI");
        this.ultRespuesta = true;
        actualizarVariables();
        //cuando me quede sin preguntas y no pueda adivinar con certeza un animal
        if (npregDisponibles == 0 && tree_levels > 1) {
            BinaryTree<String> subarbol = arbolJuego.iterativeTreeSearch(pregunta);
            System.out.println(arbolJuego);
            Util.imprimirNoHaySolucion();
            BinaryTree.printLeafNodes(subarbol.getLeft());
            RespuestasFinal.setText("No se pudo llegar a una conclusion \n Los posibles animales son: \n" + subarbol.getLeft().getLeafs(" "));
            System.out.println("\n"+subarbol.getLeft().getLeafs(" "));
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
        qrespuestas.offer("NO");
        this.ultRespuesta = false;
        actualizarVariables();
        //cuando me quede sin preguntas y no pueda adivinar con certeza un animal
        if (npregDisponibles == 0 && tree_levels > 1) {
            BinaryTree<String> subarbol = arbolJuego.iterativeTreeSearch(pregunta);
            Util.imprimirNoHaySolucion();
            BinaryTree.printLeafNodes(subarbol.getRight());
            LinkedList<String> leafNodes= subarbol.getRight().getLeafs(" ");
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
    
    
    public boolean excedeNumeroPreguntas(){
        if(tree_levels-1<npregDisponibles||npregDisponibles<=0){
            Util.crearAlerta("Comando Invalido", "Cantidad máxima de preguntas: " + (tree_levels -1) );
            return true;
        }return false;
    }
    
    public  void validarAnimal(String animal) {
        if (animal.equals(" ")) {
            System.out.print("\nSe ha llegado al limite de preguntas.\nNo se ha encontrado un animal con esas caracteristicas\n");
            RespuestasFinal.setText("Se ha llegado al limite de preguntas.\nNo se ha encontrado un animal con esas caracteristicas\n");
            habilitarAgregarAnimal(true);//FUNCIONALIDAD EXTRA
        } else {
            System.out.print("El animal que estas pensando es: " + animal + "\n");
            RespuestasFinal.setText("El animal que estas pensando es: \n"+pregunta);
            CambiarRespuesta.setVisible(true);
            System.out.println("si se hizo");
        }
    }
    public void validarListaAnimales(LinkedList<String> animales){
        if(animales.size()==0){
            RespuestasFinal.setText("Se ha llegado al limite de preguntas.\nNo se ha encontrado un animal con esas caracteristicas\n");
            habilitarAgregarAnimal(true);//FUNCIONALIDAD EXTRA
        }else if(animales.size()==1){
            RespuestasFinal.setText("El animal que estas pensando es: \n"+animales.toString());
        }else{
            RespuestasFinal.setText("No se pudo llegar a una conclusion \n Los posibles animales son: \n" +animales.toString());
        }
    }
    public void habilitarbotonesJuego(boolean b){
        btYES.setVisible(b);
        btNo.setVisible(b);
        pregActual.setVisible(b);
    }
    //FUNCIONALIDAD EXTRA
    public void habilitarAgregarAnimal(boolean b){           
            agregarAnimalRB.setVisible(b);
            agregarAnimalTF.setVisible(b);
            btGuardarNuevoA.setVisible(b);
    }
    public void mostrarRespuesta(Boolean b){
        RespuestasFinal.setVisible(b);
        habilitarbotonesJuego(!b);
        Reintentar.setVisible(true);
    }

    @FXML
    private void CambiarRespuesta(MouseEvent event) {
        String respuesta = null;
        BinaryTree<String> arbolPadre = arbolInicialJuego.iterativeParentTreeSearch(arbolJuego.getRootContent());
        if(ultRespuesta){
            if (arbolPadre.getRight().getRootContent().equals(" ") || arbolPadre.getRight().getRootContent() == null){
                respuesta = "ninguna";
            }else{
                respuesta = arbolPadre.getRight().getRootContent();
            }
            RespuestasFinal.setText("Si hubieras respondido diferente la ultima pregunta\n la respuesta hubiera sido: \n" +respuesta);
        }else if(!ultRespuesta){
            if (arbolPadre.getLeft().getRootContent().equals(" ") || arbolPadre.getLeft().getRootContent() == null){
                respuesta = "ninguna";
            }else{
                respuesta = arbolPadre.getLeft().getRootContent();
            }
            RespuestasFinal.setText("Si hubieras respondido diferente la ultima pregunta\n la respuesta hubiera sido: \n" +respuesta);
        }
        CambiarRespuesta.setVisible(false);
    }

    @FXML
    private void agregarAnimal(MouseEvent event) {
        agregarAnimalTF.setDisable(false);

    }

    @FXML
    private void guardarNuevoAnimal(MouseEvent event) {
                if(!agregarAnimalTF.getText().isBlank()){
            Util.crearAlerta("CONFIRMACION", "Se ha guardado el animal: "+agregarAnimalTF.getText() );
            String nuevaRespuesta = agregarAnimalTF.getText();
            for(String resp: qrespuestas){
                nuevaRespuesta = nuevaRespuesta+ " " + resp;
            }
            System.out.print(nuevaRespuesta);
            LecturaDatos.saveRespuesta("Respuestas.txt", nuevaRespuesta);
        }else{
            Util.crearAlerta("ERROR", "No se ha guardado el animal" );

        }
    }


}
