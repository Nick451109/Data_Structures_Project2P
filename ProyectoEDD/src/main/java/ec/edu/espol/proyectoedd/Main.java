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

    public static void jugar() {
        ArrayList<String> preguntas = LecturaDatos.leerPreguntas();
        ArrayList<String> respuestas = LecturaDatos.leerRespuestas();
        BinaryTree<String> arbolJuego = CreadorArboles.creadorArboles(preguntas, respuestas);
        preguntar(arbolJuego, 4, arbolJuego.countLevelsRecursive());
        jugarAgain();

    }

    public static void preguntar(BinaryTree<String> arbolJuego, int n, int tree_levels) {
        String pregunta = arbolJuego.getRootContent();
        tree_levels--;
        
        
        if (arbolJuego.isLeaf()) {
            validarAnimal(pregunta);
            return;
        } else {
            System.out.print(pregunta + ":\n");
            n--;
            String respuesta = obtenerRespuesta();
            respuesta = validarRespuesta(respuesta);
            //---------------------------------------------------
            //cuando me quede sin preguntas y no pueda adivinar con certeza un animal
            //funciona con 2 preguntas
            if (respuesta.toUpperCase().equals("SI")&& n == 0 && tree_levels>1){
                BinaryTree<String> subarbol = arbolJuego.iterativeTreeSearch(pregunta);
                System.out.println("No se pudo llegar a una conclusion");
                System.out.println("Los posibles animales son: ");
                BinaryTree.printLeafNodes(subarbol.getLeft());
            }
            else if (respuesta.toUpperCase().equals("NO")&& n == 0 && tree_levels>1){
                BinaryTree<String> subarbol = arbolJuego.iterativeTreeSearch(pregunta);
                System.out.println("No se pudo llegar a una conclusion");
                System.out.println("Los posibles animales son: ");
                BinaryTree.printLeafNodes(subarbol.getRight());
            }
            //-----------------------------------------------------
            //mientras tenga preguntas y no haya adivinado sigo corriendo el juego
            if (respuesta.toUpperCase().equals("SI") && n > 0 ) {  
                preguntar(arbolJuego.getLeft(), n, tree_levels);
            } 
            else if (respuesta.toUpperCase().equals("NO") && n > 0) {    
                preguntar(arbolJuego.getRight(), n, tree_levels);
            }
            //-----------------------------------------------------
            //cuando no tenga preguntas y haya adivinado el animal
            //funciona con 3 preguntas
            else if (respuesta.toUpperCase().equals("NO") && n == 0  && tree_levels == 1 ) {
                preguntar(arbolJuego.getRight(), n, tree_levels);
            }
            else if (respuesta.toUpperCase().equals("SI") && n == 0 && tree_levels == 1 ) {
                preguntar(arbolJuego.getLeft(), n, tree_levels);
            }
                
        }
    }

    public static void validarAnimal(String animal) {
        if (animal.equals(" ")) {
            System.out.print("\nSe ha llegado al limite de preguntas.\nNo se ha encontrado un animal con esas caracteristicas\n");
        } else {
            System.out.print("El animal que estas pensando es: " + animal + "\n");
        }
    }

    public static void jugarAgain() {
        System.out.print("\n Desea jugar Nuevamente (SI/NO): \n");
        String respuesta = obtenerRespuesta();
        if (respuesta.toUpperCase().equals("SI")) {
            jugar();
        } else {
            System.out.print("\n******¡GRACIAS POR JUGAR!******\n");
        }
    }

    public static String obtenerRespuesta() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static String validarRespuesta(String respuesta) {
        if ((respuesta.toUpperCase().equals("SI") || respuesta.toUpperCase().equals("NO"))) {
            return respuesta;
        } else {
            System.out.print("Respuesta invalida\nIntente nuevamente: \n");
            return validarRespuesta(obtenerRespuesta());
        }
    }

}
