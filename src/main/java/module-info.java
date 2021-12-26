
module org.but.feec.traintransportmanagement {
    requires javafx.controls;

    requires javafx.fxml;
    requires org.slf4j;

    requires fontawesomefx;
    requires org.controlsfx.controls;
    requires java.sql;

    requires com.zaxxer.hikari;
    requires bcrypt;


    opens org.but.feec.traintransportmanagement to javafx.fxml;
    exports org.but.feec.traintransportmanagement;
    exports org.but.feec.traintransportmanagement.controllers;
    opens org.but.feec.traintransportmanagement.controllers to javafx.fxml;
}