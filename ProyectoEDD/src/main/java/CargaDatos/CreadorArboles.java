/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CargaDatos;

import TDA.BinaryTree;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author CAELOS JR 2018
 */
public class CreadorArboles {
    private static Comparator<String> cmpContent = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        }; 

    public static BinaryTree<String> creadorArboles(ArrayList<String> preg, ArrayList<String> respuestas) {
        if (preg.isEmpty() || respuestas.isEmpty()) {
            return null;
        } else {
            Integer pos = 0;
            System.out.println("entro al creador");
            
            Stack<String> QRespuestasIndv1 = new Stack<>();
            Queue<String> QRespuestasIndv = new LinkedList<>();
            BinaryTree<String> original = new BinaryTree(preg.get(pos),cmpContent);
            pos++;
            while (pos < preg.size()) {
                original.setLeaves(preg.get(pos));
                pos++;
            }
            original.setLeaves(" ");
            for (String respuesta : respuestas) {
                QRespuestasIndv.clear();
                String[] respuestasIndv = respuesta.split(" ");
                for (String resptActual : respuestasIndv) {
                    QRespuestasIndv.offer(resptActual);
                }
                QRespuestasIndv.offer(QRespuestasIndv.poll());
                System.out.println(original.getRootContent());
                original.recursiveSet(QRespuestasIndv,"SI","NO");
            }
            return original;
        }
    }


}
