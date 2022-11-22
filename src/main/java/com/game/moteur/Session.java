package com.game.moteur;

public class Session {

    public String nomJoueur;

    public int score;

    public Session(String nomJoueur) {
        this.nomJoueur = nomJoueur;
        this.score = 0;
    }

    public void addPoint(int i) {
        this.score += i;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public int getScore() {
        return score;
    }
}
