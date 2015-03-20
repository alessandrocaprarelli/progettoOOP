package classi;

import java.util.ArrayList;

/**
 * Created by Christian on 03/03/2015.
 */
public class Squadra {
    private int ID;
    private String nome;
    private Persona proprietario;
    private ArrayList<Giocatore> giocatori;
    private Campionato campionato;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProprietario(Persona proprietario) {
        this.proprietario = proprietario;
    }

    public void setGiocatori(ArrayList<Giocatore> giocatori) {
        this.giocatori = giocatori;
    }

    public Campionato getCampionato() {
        return campionato;
    }

    public void setCampionato(Campionato campionato) {
        this.campionato = campionato;
    }

    public Squadra(Persona proprietario){
        this.proprietario = proprietario;
    }

    public Squadra(int ID, String nome){
        this.ID = ID;
        this.nome = nome;
    }

    public Squadra(int ID, String nome, Persona proprietario , Campionato campionato){
        this.ID = ID;
        this.nome = nome;
        this.proprietario = proprietario;
        this.campionato = campionato;
    }

    public Squadra(String nome, Persona prop, ArrayList<Giocatore> gioc) {
        this.nome = nome;
        this.proprietario = prop;
        for (Giocatore i : gioc) this.giocatori = gioc;
    }



    public String getNome() {
        return this.nome;
    }

    public Persona getProprietario() {
        return this.proprietario;
    }


    public ArrayList<Giocatore> getGiocatori(){
        return giocatori;
    }

}
