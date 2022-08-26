/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CargaDatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author CAELOS JR 2018
 */
public class LecturaDatos {

    public static ArrayList<String> leerPreguntas() {
    //    System.out.println("Se esta cargando la lista Fotos\n");
        ArrayList<String> preguntasTxt = new ArrayList<>();
        try (BufferedReader bw = new BufferedReader(new FileReader("Preguntas.txt"))) {
            String linea;
            while ((linea = bw.readLine()) != null) {
                linea = linea.substring(1);
                preguntasTxt.add(linea);
            }
        } catch (IOException ex) {
            System.out.println("Problema al leer preguntas del txt");
            ex.printStackTrace();
        }
        return preguntasTxt;
    }
    
    public static ArrayList<String> leerRespuestas() {
    //    System.out.println("Se esta cargando la lista Fotos\n");
        ArrayList<String> respuestasTxt = new ArrayList<>();
        try (BufferedReader bw = new BufferedReader(new FileReader("Respuestas.txt"))) {
            String linea;
            while ((linea = bw.readLine()) != null) {
                respuestasTxt.add(linea.trim());
            }
        } catch (IOException ex) {
            System.out.println("Problema al leer preguntas del txt");
            ex.printStackTrace();
        }
        return respuestasTxt;
    }
        public static void saveRespuesta(String nomfile, String respuesta) {
        System.out.print("Ingreso al saveRespuesta");
        StringBuilder sb = new StringBuilder();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomfile, true))) {
            //id, descripcion, lugar, personas,fecha,album, comentarios,reacciones
            sb.append(respuesta).append(System.getProperty("line.separator"));
            bw.write(sb.toString());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
