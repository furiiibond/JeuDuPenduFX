package com.game.moteur;

import java.io.IOException;
import java.util.List;

public class Partie {

    // nom du fichier dictionaire
    private static final String NOM_FICHIER_DICTIONNAIRE = "pli07.txt";
    public static final int NOMBRE_ESSAIS_POSSIBLE = 6;

    public static final String INSTRUCTIONS = "Bienvenue dans le jeu du pendu !\n" +
            "Vous devez deviner un mot en entrant des lettres.\n" +
            "Vous avez droit à 6 erreurs avant de perdre.\n" +
            "Bonne chance !";

    private final String motATrouver;

    private final Session session;

    private String motCache;

    // le joueur n'a qu'un seul jocker par partie
    private boolean jocker; // jocker = true si le joueur a un jocker

    private int nbEssais;


    public Partie(Session session) {
        this.motATrouver = getARandomWordInList();
        this.motCache = generateHiddenWord();
        this.nbEssais = 0;
        this.session = session;
        this.jocker = true;
    }

    /**
     * @return un mot au hasard dans la liste des mots du dictionnaire
     */
    public String getARandomWordInList() {
        FileReaderDict fileReaderDict = new FileReaderDict(NOM_FICHIER_DICTIONNAIRE);
        try {
            List<String> words = fileReaderDict.read();
            int randomIndex = (int) (Math.random() * words.size());
            // downcase the word
            return words.get(randomIndex).toLowerCase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return un mot avec des * à la place des lettres
     */
    private String generateHiddenWord() {
        String hiddenWord = "";
        for (int i = 0; i < motATrouver.length(); i++) {
            hiddenWord += "*";
        }
        return hiddenWord;
    }

    /**
     * Le joueur propose une lettre
     *
     * @param letter
     */
    public void letterProposed(String letter) {
        // check if the letter is in the word
        if (motATrouver.contains(letter)) {
            // if yes, replace the * with the letter
            String motATrouverTemp = "";
            for (int j = 0; j < motATrouver.length(); j++) {
                if (motATrouver.charAt(j) == letter.charAt(0)) {
                    motATrouverTemp += letter;
                } else {
                    motATrouverTemp += motCache.charAt(j);
                }
            }
            motCache = motATrouverTemp;
            session.addPoint(1); // un point pour chaque lettre trouvée
        } else {
            nbEssais++;
        }
    }

    /**
     * @return true si le mot est trouvé
     */
    public boolean isWordFound() {
        return !motCache.contains("*");
    }

    /**
     * @return true si le nombre d'essais est atteint ou si le mot est trouvé
     */
    public boolean isGameOver() {
        if (isWordFound()) {
            session.addPoint(5); // 5 points bonus si le mot est trouvé
            return true;
        } else if (nbEssais == NOMBRE_ESSAIS_POSSIBLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Le joueur utilise son jocker pour trouver une lettre
     *
     */
    public String displayALetter() {
        if (jocker) {
            // on affiche une lettre au hasard
            int randomIndex = (int) (Math.random() * motATrouver.length());
            String letter = String.valueOf(motATrouver.charAt(randomIndex));
            letterProposed(letter);
            session.addPoint(-1); // on ne donne pas de point pour le jocker
            jocker = false;
            return letter;
        }
        // le boutton étant désactivé, on ne devrait jamais arriver ici
        throw new RuntimeException("Vous avez déjà utilisé votre jocker");
    }

    public String getMotCache() {
        return motCache;
    }

    public int getNbEssais() {
        return nbEssais;
    }

    public String getMotATrouver() {
        return motATrouver;
    }
}
