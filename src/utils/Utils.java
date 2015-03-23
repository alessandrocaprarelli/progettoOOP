package utils;

import classi.*;
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

            return db.inserisciGiocatoriAnno(listaGiocatori);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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

    public Object[][] listaClassificaToArrayPiccola(ArrayList<Classifica> listaClassifica){

        Object[][] listaObject = new Object[listaClassifica.size()][2];

        for(int i=0;i<listaClassifica.size();i++){
            listaObject[i][0] = listaClassifica.get(i).getSquadra().getNome();
            listaObject[i][1] = listaClassifica.get(i).getPunti();
        }

        return listaObject;
    }


    public Object[][] listaClassificaToArray(ArrayList<Classifica> listaClassifica){

        Object[][] listaObject = new Object[listaClassifica.size()][10];

        for(int i=0;i<listaClassifica.size();i++){
            listaObject[i][0] = listaClassifica.get(i).getSquadra().getNome();
            listaObject[i][1] = listaClassifica.get(i).getGiocate();
            listaObject[i][2] = listaClassifica.get(i).getVinte();
            listaObject[i][3] = listaClassifica.get(i).getPareggiate();
            listaObject[i][4] = listaClassifica.get(i).getPerse();
            listaObject[i][5] = listaClassifica.get(i).getDiffReti();
            listaObject[i][6] = listaClassifica.get(i).getGolFatti();
            listaObject[i][7] = listaClassifica.get(i).getGolSubiti();
            listaObject[i][8] = listaClassifica.get(i).getPunteggio();
            listaObject[i][9] = listaClassifica.get(i).getPunti();


        }

        return listaObject;
    }

    //crea il calendario
    public  void creaCalendario(int primaGiornata, int ultimaGiornata, Campionato campionato){
        ArrayList<Giornata> listaGiornate = new ArrayList<Giornata>();


        //numero partecipanti
        int n = campionato.getNumeroPartecipanti();

        //numero di giornate in un girone
        int giornate = n-1;

        //inizializzo il contatore delle giornate
        int k=1;

        //int[] casa = new int[n/2];
        //int[] trasferta = new int[n/2];

        ArrayList<Squadra> squadreCasa = new ArrayList<Squadra>();
        ArrayList<Squadra> squadreTrasferta = new ArrayList<Squadra>();


        for(int i =0;i<n/2;i++){

            squadreCasa.add(new Squadra(campionato.getListaSquadrePartecipanti().get(i).getID(),campionato.getListaSquadrePartecipanti().get(i).getNome()));
            squadreTrasferta.add(new Squadra(campionato.getListaSquadrePartecipanti().get(n-1-i).getID(),campionato.getListaSquadrePartecipanti().get(n-1-i).getNome()));

            //casa[i] =campionato.getListaSquadrePartecipanti().get(i).getID();
            //trasferta[i]= campionato.getListaSquadrePartecipanti().get(n-1-i).getID();
        }

        while (primaGiornata<=ultimaGiornata) {

            for (int i = 0; i < giornate && primaGiornata <= ultimaGiornata; i++) {

                ArrayList<Partita> listaPartite = new ArrayList<Partita>();

                if (i % 2 == 0) {
                    for (int j = 0; j < n / 2; j++) {
                        listaPartite.add(new Partita(squadreTrasferta.get(j), squadreCasa.get(j),j+1));
                    }
                } else {
                    for (int j = 0; j < n / 2; j++) {
                        listaPartite.add(new Partita(squadreCasa.get(j), squadreTrasferta.get(j),j+1));
                    }
                }
                listaGiornate.add(new Giornata(k, new GiornataReale(primaGiornata), listaPartite));

                Squadra pivot = squadreCasa.get(0);

                //int pivot = casa[0];

                Squadra riporto = spostaDestra(squadreTrasferta, squadreCasa.get(1),n);

                spostaSinistra(squadreCasa, riporto,n);

                squadreCasa.set(0,pivot);

                k++;
                primaGiornata++;


            }
            for (int i = 0; i < giornate && primaGiornata <= ultimaGiornata; i++) {


                ArrayList<Partita> listaPartite = new ArrayList<Partita>();

                for (int j = 0; j < n / 2; j++) {
                    listaPartite.add(new Partita(listaGiornate.get(i).getPartite().get(j).getOspite(), listaGiornate.get(i).getPartite().get(j).getCasa(),j+1));
                }
                listaGiornate.add(new Giornata(k, new GiornataReale(primaGiornata), listaPartite));

                k++;
                primaGiornata++;

            }
        }

        campionato.setCalendario(listaGiornate);

        for(Giornata giornata:listaGiornate){
            System.out.println("Giornata: " + giornata.getNumGiornata()+" Giornata reale: "+giornata.getNumGioReale().getNumeroGiornata());
            for(Partita partita:giornata.getPartite()){
                System.out.println(partita.getCasa().getID() + "-" + partita.getOspite().getID());
            }
            System.out.println("");

        }






    }

    private Squadra spostaDestra(ArrayList<Squadra> trasferta, Squadra casaUno, int n){
        Squadra riporto = trasferta.get(n/2-1);
        //int riporto =trasferta[n/2-1];
        for(int i=n/2-1;i>0;i--){
            trasferta.set(i,trasferta.get(i-1));
            //trasferta[i] = trasferta[i-1];
        }
        trasferta.set(0,casaUno);

        return riporto;
    }

    private void spostaSinistra(ArrayList<Squadra> casa, Squadra riporto, int n){
        for(int i=0;i<n/2-1;i++){
            casa.set(i,casa.get(i+1));
            //casa[i] = casa[i+1];
        }
        //casa[n/2-1] = riporto;
        casa.set(n/2-1,riporto);
    }
}
