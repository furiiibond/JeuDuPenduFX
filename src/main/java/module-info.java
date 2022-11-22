module com.game.jeuDuPendu {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.game.ihm to javafx.fxml;
    exports com.game.ihm;
    exports com.game.moteur;
    opens com.game.moteur to javafx.fxml;
}