module ec.edu.espol.proyectoedd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens ec.edu.espol.proyectoedd to javafx.fxml;
    exports ec.edu.espol.proyectoedd;
}
