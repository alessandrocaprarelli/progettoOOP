package utils;

import classi.Giocatore;
import classi.GiornataReale;
import com.sun.corba.se.spi.ior.ObjectKey;
import db.Mysql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alessandro on 04/03/15.
 */
public class Utils {

    //cast char --> string
    public String passwordString(char[] a) {
        return new String(a);
    }

    public boolean csvQuotazioni(String pathFile,  String csvSplitBy){
        BufferedReader br = null;
        boolean rsDb = false;
        final Mysql db = new Mysql();

        String line = "";

        ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();
        try {

            br = new BufferedReader(new FileReader(pathFile));

            while ((line = br.readLine()) != null) {

                String[] giocatore = line.split(csvSplitBy);

                listaGiocatori.add(new Giocatore(giocatore[2],Integer.parseInt(giocatore[0]),Integer.parseInt(giocatore[4]),giocatore[3],giocatore[1].charAt(0)));

            }

            if(listaGiocatori.isEmpty()) System.out.print("vuota");
            else System.out.print("piena");

            return rsDb = db.inserisciGiocatoriAnno(listaGiocatori);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return rsDb;
        } catch (IOException e) {
            e.printStackTrace();
            return rsDb;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public Object[][] listaGiocatoriToArray(ArrayList<Giocatore> listaGiocatori){

        Object[][] listaObject = new Object[listaGiocatori.size()][5];

        for(int i=0;i<listaGiocatori.size();i++){
            listaObject[i][0] = listaGiocatori.get(i).getID();
            listaObject[i][1] = listaGiocatori.get(i).getCognome();
            listaObject[i][2] = listaGiocatori.get(i).getRuolo();
            listaObject[i][3] = listaGiocatori.get(i).getSquadraReale();
            listaObject[i][4 ]=listaGiocatori.get(i).getPrezzoBase();

        }

        return listaObject;
    }

    public Object[][] listaGiornateToArray(ArrayList<GiornataReale> listaGiornate){
        Object[][] listaObject = new Object[listaGiornate.size()][5];

        for(int i=0; i<listaGiornate.size();i++){
            listaObject[i][0]= listaGiornate.get(i).getNumeroGiornata();
            listaObject[i][1] = listaGiornate.get(i).getDataInizio();
            listaObject[i][2] = listaGiornate.get(i).getOraInizio();
            listaObject[i][3] = listaGiornate.get(i).getDataFine();
            listaObject[i][4] = listaGiornate.get(i).getOraFine();

        }

        return listaObject;
    }
}
