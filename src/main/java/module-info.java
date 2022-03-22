module jeudelavie.thegameoflife {
    requires javafx.controls;
    requires javafx.fxml;


    opens jeudelavie to javafx.fxml;
    exports jeudelavie;
    exports jeudelavie.controleur;
    opens jeudelavie.controleur to javafx.fxml;
}