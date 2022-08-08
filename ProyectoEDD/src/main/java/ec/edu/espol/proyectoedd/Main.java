/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.proyectoedd;

import CargaDatos.CreadorArboles;
import CargaDatos.LecturaDatos;
import TDA.BinaryTree;
import Util.Util;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class Main {
    public static void main(String[] args) {
        Util.imprimirNameGame();
        jugar();
    }
    
    public static void jugar(){
        ArrayList<String> preguntas = LecturaDatos.leerPreguntas();
        ArrayList<String> respuestas = LecturaDatos.leerRespuestas();
        BinaryTree<String> arbolJuego = CreadorArboles.creadorArboles(preguntas, respuestas);
        preguntar(arbolJuego, 3);
        jugarAgain();
     
    }
    public static void preguntar(BinaryTree<String> arbolJuego, int n){
        String pregunta= arbolJuego.getRootContent();
        if(arbolJuego.isLeaf()){
            validarAnimal(pregunta);
            return;
        }
        else{
            System.out.print(pregunta+":\n" );
            n--;
            String respuesta = obtenerRespuesta();
            respuesta = validarRespuesta(respuesta);
            if(respuesta.toUpperCase().equals("SI")){
                preguntar(arbolJuego.getLeft(),n);
            }
            else if(respuesta.toUpperCase().equals("NO")){
                preguntar(arbolJuego.getRight(),n);
            }
        }
    }
    public static void validarAnimal(String animal){
        if(animal.equals(" ")){
            System.out.print("Se ha llegado al limite de preguntas.\nNo se ha encontrado un animal con esas caracteristicas\n");
        }else{
            System.out.print("El animal que estas pensando es: "+animal+"\n");
        }
    }
    
    public static void jugarAgain(){
        System.out.print("\n Desea jugar Nuevamente (SI/NO): \n");
        String respuesta = obtenerRespuesta();
        if(respuesta.toUpperCase().equals("SI")){
            jugar();
        }else{
            System.out.print("\n******¡GRACIAS POR JUGAR!******\n");
        }
    }
    
    public static String obtenerRespuesta(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    
    public static String validarRespuesta(String respuesta){
        if((respuesta.toUpperCase().equals("SI")||respuesta.toUpperCase().equals("NO"))){
            return respuesta;
        }else{
            System.out.print("Respuesta invalida\nIntente nuevamente: \n");
            return validarRespuesta(obtenerRespuesta());
        }
    }
   
}
