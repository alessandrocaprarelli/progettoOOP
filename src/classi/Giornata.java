package classi;

import java.util.ArrayList;

/**
 * Created by Giacomo on 18/03/15.
 */
public class Giornata {
    private int numGiornata;
    private GiornataReale gioReale;
    ArrayList<Partita> partite;

    public Giornata(int numGiornata, GiornataReale gioReale) {
        this.numGiornata = numGiornata;
        this.gioReale = gioReale;
    }

    public void calcolaGiornata() {
        for (Partita i : partite ) i.calcolaPartita();
    }

    public int getNumGiornata() {
        return numGiornata;
    }

    public void setNumGiornata(int numGiornata) {
        this.numGiornata = numGiornata;
    }

    public GiornataReale getNumGioReale() {
        return gioReale;
    }

    public void setNumGioReale(GiornataReale numGioReale) {
        this.gioReale = numGioReale;
    }
}
