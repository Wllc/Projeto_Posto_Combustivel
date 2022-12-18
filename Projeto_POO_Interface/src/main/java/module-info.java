module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens visao.demo to javafx.fxml;
    opens dominio to javafx.fxml;
    exports visao.demo;
    exports dominio;
}