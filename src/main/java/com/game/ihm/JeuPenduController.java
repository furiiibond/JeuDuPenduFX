package com.game.ihm;

import com.game.moteur.Partie;
import com.game.moteur.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class JeuPenduController {

    @FXML
    private TextField score;

    @FXML
    private TextField playerName;

    @FXML
    private TextField motATrouver;


    @FXML
    private GridPane keyboard;

    @FXML
    private Button astuce;

    @FXML
    private Circle tete;

    @FXML
    private Rectangle bras1;

    @FXML
    private Rectangle bras2;

    @FXML
    private Rectangle jambe1;

    @FXML
    private Rectangle jambe2;

    @FXML
    private Rectangle corps;

    private Partie partie;

    private Session session;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        // disable keyboard
        keyboard.setDisable(true);
        // for each button in the keyboard
        for (int i = 0; i < keyboard.getChildren().size(); i++) {
            Button button = (Button) keyboard.getChildren().get(i);
            button.setOnAction(event -> keyPressed(button));
        }
    }


    /**
     * le joueur a cliqué sur le bouton "Demarrer une partie"
     * on initialise une nouvelle partie et on active le clavier
     * si le joueur n'a pas rentré de nom, on lui demande de le faire
     * On initialise une nouvelle session
     */
    @FXML
    protected void newGame() {
        playerName.setDisable(false);
        // if the player name is not empty
        if (!playerName.getText().isEmpty()) {
            // enable the keyboard
            keyboard.setDisable(false);
            // disable edit player name
            playerName.setDisable(true);
            // enable astuce button
            astuce.setDisable(false);
            // create a new session
            if (session == null) { // start a new session if it's the first game
                session = new Session(playerName.getText());
            }
            // create a new game
            partie = new Partie(session);
            // update the word to find
            motATrouver.setText(partie.getMotCache());
            // update the score
            score.setText(session.getScore() + " | Bienvenue dans le jeu du pendu " + playerName.getText() + " !");
        } else {
            score.setText("Veuillez entrer un nom de joueur");
        }
    }

    /**
     * le joueur a cliqué sur une touche du clavier
     * on vérifie si la lettre est dans le mot à trouver
     * si oui, on met à jour le mot à trouver
     * si non, on dessine le pendu
     * @param button
     */
    private void keyPressed(Button button) {
        // get the text of the button
        String letter = button.getText();
        // disable the button
        button.setDisable(true);
        // check if the letter is in the word
        partie.letterProposed(letter);
        // update the word to find
        motATrouver.setText(partie.getMotCache());
        // update the score
        score.setText(String.valueOf(session.getScore()));
        // update the drawing
        dessiner(partie.getNbEssais());
        // check if the game is over
        if (partie.isGameOver()) {
            newPartie();
        }
    }

    /**
     * Nouvelle partie si le joueur a gagné ou perdu
     */
    private void newPartie() {
        // enable astuce button
        astuce.setDisable(false);
        // enable all buttons of the keyboard
        for (int i = 0; i < keyboard.getChildren().size(); i++) {
            Button button = (Button) keyboard.getChildren().get(i);
            button.setDisable(false);
        }
        // get old word
        String oldWord = partie.getMotATrouver();
        // get if the player has won
        boolean hasWon = partie.isWordFound();
        // nouvelle partie
        partie = new Partie(session);
        // update the word to find
        motATrouver.setText(partie.getMotCache());
        // if the player has won
        String message = "";
        if (hasWon) {
            message = session.getScore() + " | Bravo " + session.getNomJoueur() + " vous avez trouvé le mot '" + oldWord + "'";
        } else {
            message = session.getScore() + " | Dommage " + session.getNomJoueur() + " vous n'avez pas trouvé le mot '" + oldWord + "'";
        }
        // update the score and add a win message
        score.setText(message);
    }

    /**
     * dessine le pendu
     * on réinitialise le dessin à chaque fois
     * on aplique les modifications en fonction du nombre d'essais
     * @param nbEssais
     */
    public void dessiner(int nbErreurs) {
        resetDessin();
        if (nbErreurs >= 1) {
            tete.setVisible(true);
        }
        if (nbErreurs >= 2) {
            corps.setVisible(true);
        }
        if (nbErreurs >= 3) {
            bras1.setVisible(true);
        }
        if (nbErreurs >= 4) {
            bras2.setVisible(true);
        }
        if (nbErreurs >= 5) {
            jambe1.setVisible(true);
        }
        if (nbErreurs >= 6) {
            jambe2.setVisible(true);
        }
    }

    /**
     * réinitialise le dessin
     */
    public void resetDessin() {
        tete.setVisible(false);
        bras1.setVisible(false);
        bras2.setVisible(false);
        jambe1.setVisible(false);
        jambe2.setVisible(false);
        corps.setVisible(false);
    }

    public void displayALetter(ActionEvent actionEvent) {
        String letterDisplayed = partie.displayALetter();
        motATrouver.setText(partie.getMotCache());
        // disable the button
        ((Button) actionEvent.getSource()).setDisable(true);
        // disable the key with the same letter
        for (int i = 0; i < keyboard.getChildren().size(); i++) {
            Button button = (Button) keyboard.getChildren().get(i);
            if (button.getText().equals(letterDisplayed)) {
                button.setDisable(true);
            }
        }
    }

    public void displayInstructions(ActionEvent actionEvent) {
        // open a new javaFX windows with texte hello in it
        Popup.display("Informations", Partie.INSTRUCTIONS);
    }
}
