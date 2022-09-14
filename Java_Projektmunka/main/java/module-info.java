module com.example.nagyhf_v1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.nagyhf_v1 to javafx.fxml;
    exports com.example.nagyhf_v1;
}