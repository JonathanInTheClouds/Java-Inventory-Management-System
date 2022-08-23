module com.jonathandlab.com.c482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jonathandlab.dev.c482 to javafx.fxml;
    exports com.jonathandlab.dev.c482;

    opens com.jonathandlab.dev.c482.controller to javafx.fxml;
    exports com.jonathandlab.dev.c482.controller;

    opens com.jonathandlab.dev.c482.model to javafx.fxml;
    exports com.jonathandlab.dev.c482.model;


}