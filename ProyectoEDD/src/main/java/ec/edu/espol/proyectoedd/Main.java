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
        ArrayList<String> preguntas = LecturaDatos.leerPreguntas();
        ArrayList<String> respuestas = LecturaDatos.leerRespuestas();
        BinaryTree<String> arbolJuego = CreadorArboles.creadorArboles(preguntas, respuestas);
        preguntar(arbolJuego, 3);

        
    }
    public static void preguntar(BinaryTree<String> arbolJuego, int n){
        String pregunta= arbolJuego.getRootContent();
        if(arbolJuego.isLeaf()){
            System.out.print("El animal que estas pensando es: "+pregunta+"\n");
            return;
        }
        else{
            System.out.print(pregunta+": \n" );
            String respuesta = obtenerRespuesta();
            n--;
            if(respuesta.equals("SI")){
                preguntar(arbolJuego.getLeft(),n);
            }
            else if(respuesta.equals("NO")){
                preguntar(arbolJuego.getRight(),n);

            }
        }
    }
    public static String obtenerRespuesta(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    
}
