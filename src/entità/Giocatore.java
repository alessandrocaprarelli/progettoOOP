package entità;

import java.io.Serializable;

/**
 * Classe per la gestione dei giocatori.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Giocatore implements Serializable{
    private String cognome;
    private int ID;
    private int prezzoBase;
    private int prezzoAcquisto;
    private String squadraReale;
    private char ruolo;
    private Voto voti;

    /**
     * Costruttore per i giocatori utilizzabili, non sono acquistati da alcuna squadra per cui non hanno un valore
     * prezzo di acquisto.
     * @param cognome cognome del giocatore
     * @param id id del giocatore
     * @param prezzoBase prezzo di base per l'acquisto del giocatore
     * @param squadra squadra reale di appartenenza
     * @param ruolo ruolo del giocatore
     */
    public Giocatore(String cognome, int id, int prezzoBase,  String squadra, char ruolo) {
        this.cognome = cognome;
        this.ID = id;
        this.prezzoBase = prezzoBase;
        this.squadraReale = squadra;
        this.ruolo = ruolo;
    }

    /**
     * Costruttore per i giocatori acquistati dalle squadre. Il prezzo di acquisto viene definito dal presidente di
     * lega nel caso di asta offline.
     * @param id id del giocatore
     * @param cognome cognome del giocatore
     * @param prezzoBase prezzo di partenza per l'acquisto del giocatore
     * @param prezzoAcquisto prezzo a cui è stato acquistato il giocatore
     * @param squadra squadre reale di appartenenza
     * @param ruolo ruolo del giocatore
     */
    public Giocatore(int id, String cognome,   int prezzoBase, int prezzoAcquisto, String squadra, char ruolo) {
        this.cognome = cognome;
        this.ID = id;
        this.prezzoBase = prezzoBase;
        this.prezzoAcquisto = prezzoAcquisto;
        this.squadraReale = squadra;
        this.ruolo = ruolo;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String nome) {
        this.cognome = nome;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPrezzoBase() {
        return prezzoBase;
    }

    public void setPrezzoBase(int prezzoBase) {
        this.prezzoBase = prezzoBase;
    }

    public String getSquadraReale() {
        return squadraReale;
    }

    public void setSquadraReale(String squadraReale) {
        this.squadraReale = squadraReale;
    }

    public char getRuolo() {
        return ruolo;
    }

    public void setRuolo(char ruolo) {
        this.ruolo = ruolo;
    }

    public Voto getVoti() {
        return voti;
    }

    public void setVoti(Voto voti) {
        this.voti = voti;
    }

    public int getPrezzoAcquisto() {
        return prezzoAcquisto;
    }

    public void setPrezzoAcquisto(int prezzoAcquisto) {
        this.prezzoAcquisto = prezzoAcquisto;
    }
}
