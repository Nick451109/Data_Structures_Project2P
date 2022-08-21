module ec.edu.espol.proyectoedd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens ec.edu.espol.proyectoedd to javafx.fxml;
    exports ec.edu.espol.proyectoedd;
    
    opens ec.edu.espol.controllers to javafx.fxml;
    exports ec.edu.espol.controllers;
    
    opens Util to javafx.fxml;
    exports Util;
    
    opens TDA to javafx.fxml;
    exports TDA;
    
    opens CargaDatos to javafx.fxml;
    exports CargaDatos;
}
