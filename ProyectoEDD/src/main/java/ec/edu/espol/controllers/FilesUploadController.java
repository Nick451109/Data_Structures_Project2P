/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.proyectoedd.App;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author nick1
 */
public class FilesUploadController implements Initializable {

    @FXML
    private Button btPreguntas;
    @FXML
    private Button btRespuestas;
    @FXML
    private Text txtArchPreguntas;
    @FXML
    private Text txtArchRepuestas;
    @FXML
    private Button btGuardar;
   
    private Path rutaArchivoPreg;
    private Path rutaArchivoResp;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void selectPreguntas(ActionEvent event) {
        File file = getFile();
        rutaArchivoPreg = Paths.get(file.toURI());
        txtArchPreguntas.setText(rutaArchivoPreg.toString());
    }

    @FXML
    private void selectRespuestas(ActionEvent event) {
        File file = getFile();
        rutaArchivoResp = Paths.get(file.toURI());
        txtArchRepuestas.setText(rutaArchivoResp.toString());
    }

    @FXML
    private void guardarArchivos(ActionEvent event) throws IOException {      
        CopyOption[] options = new CopyOption[]{
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES
        };

        try {

            Files.copy(this.rutaArchivoPreg, Paths.get("Preguntas.txt"), options);
            Files.copy(this.rutaArchivoResp, Paths.get("Respuestas.txt"), options);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        App.setRoot("VentanaJuego");

    }

    private File getFile() {
        FileChooser fil_chooser = new FileChooser();
        fil_chooser.setInitialDirectory(new File(System.getProperty("user.home"))); //ruta predeterminada
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");

        fil_chooser.getExtensionFilters().add(extFilter);
        File file = fil_chooser.showOpenDialog(null);
        
         if (file!=null){
            return file; 
         }
        return null;
    }
}
