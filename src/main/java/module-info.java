module client.baitapui {
    requires javafx.controls;
    requires javafx.fxml;


    opens client.baitapui to javafx.fxml;
    exports client.baitapui;
}