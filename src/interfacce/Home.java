package interfacce;

import classi.*;
import com.sun.org.apache.bcel.internal.classfile.LineNumberTable;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.joda.time.*;
import utils.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by alessandro on 11/03/15.
 */
public class Home extends JPanel {
    private JPanel panel1;
    private JButton inviaLaFormazioneButton;
    private JList listaAvvisi;
    private JLabel nomeSquadra;
    private JLabel nomeUtente;
    private JTable tableClassifica;
    private JScrollPane scrollpaneClassifica;
    private JLabel nomeCampionato;
    private JTable ultimaGiornataTable;
    private JTable prossimaGiornataTable;
    private JLabel campionatoFinitolbl;
    private JLabel campionatoIniziolbl;
    private JScrollPane ultimaGiornataScrollPane;
    private JScrollPane prossimaGiornataScrollPane;
    private JTextArea testoAvvisi;
    private JPanel panelClassifica;
    private JLabel dataProssimalbl;
    private JLabel giornataProssimalbl;
    private JLabel giornataUltimalbl;
    private JLabel dataUltimalbl;
    private JLabel giornilbl;
    private JLabel orelbl;
    private JLabel minutilbl;
    private JLabel secondilbl;

    private Squadra squadra;

    private Applicazione applicazione;

    public void setSquadre(Squadra squadra){
        this.squadra = squadra;
        long prossima = squadra.getCampionato().prossimaGiornata().getGioReale().getDataOraInizio().getTime();
        long orarioConsegna = squadra.getCampionato().getOrarioConsegna() * 60 * 1000;
        this.prossimaGiornata = new DateTime(prossima-orarioConsegna);
    }

    private Utils utils = new Utils();

    private DateTime prossimaGiornata;

    private void setProssimaGiornata(DateTime prossimaGiornata){
        this.prossimaGiornata = prossimaGiornata;
    }

    private DateTime getProssimaGiornata(){
        return this.prossimaGiornata;
    }


    public void startCountDown(){
        final Timer t = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (prossimaGiornata.isAfter(new DateTime())) {
                    DateTime adesso = new DateTime();
                    //int d = Days.daysBetween(adesso,getProssimaGiornata()).getDays();
                    //int h = Hours.hoursBetween(adesso, getProssimaGiornata()).getHours();
                    //int m = Minutes.minutesBetween(adesso, getProssimaGiornata()).getMinutes();
                    int secondiTotali = Seconds.secondsBetween(adesso,getProssimaGiornata()).getSeconds();
                    int d = secondiTotali/60/60/24;
                    int h = secondiTotali/60/60%24;
                    int m = secondiTotali/60%60;
                    int s = secondiTotali%60;
                    aggiornaCountdown(d,h,m,s);
                }
            }
        });
        t.start();
    }

    public Home(Applicazione app){
        applicazione = app;

        inviaLaFormazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applicazione.getTabbedPane().setSelectedIndex(1);
            }
        });

        listaAvvisi.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    JList source = (JList) e.getSource();
                    int numeroAvviso = source.getSelectedIndex();
                    testoAvvisi.setText(squadra.getCampionato().getListaAvvisi().get(numeroAvviso)[1]);

                }

            }
        });



    }

    public void refresh(){
        nomeSquadra.setText(squadra.getNome());
        nomeUtente.setText(squadra.getProprietario().getNickname());
        nomeCampionato.setText(squadra.getCampionato().getNome());
        setTableClassifica();
        setListaAvvisi();
        if(squadra.getCampionato().getProssimaGiornata()==1){
            ultimaGiornataScrollPane.setVisible(false);
            campionatoIniziolbl.setVisible(true);
            giornataUltimalbl.setVisible(false);
            dataUltimalbl.setVisible(false);
        } else{
            setTableUltimaG();
            campionatoIniziolbl.setVisible(false);
            giornataUltimalbl.setText(String.valueOf(squadra.getCampionato().getProssimaGiornata()-1));
            dataUltimalbl.setText(String.valueOf(squadra.getCampionato().getCalendario().get(squadra.getCampionato().getProssimaGiornata() - 2).getGioReale().getDataOraInizio()));
        }

        if(squadra.getCampionato().getCalendario().get(squadra.getCampionato().getProssimaGiornata()-1).getGioReale().getNumeroGiornata()==squadra.getCampionato().getGiornataFine()){
            prossimaGiornataScrollPane.setVisible(false);
            campionatoFinitolbl.setVisible(true);
            giornataProssimalbl.setVisible(false);
            dataProssimalbl.setVisible(false);
        } else {
            setTableProssimaG();
            campionatoFinitolbl.setVisible(false);
            giornataProssimalbl.setText(String.valueOf(squadra.getCampionato().getProssimaGiornata()));
            dataProssimalbl.setText(String.valueOf(squadra.getCampionato().getCalendario().get(squadra.getCampionato().getProssimaGiornata()-1).getGioReale().getDataOraInizio()));
        }/*
        if(squadra.getCampionato().getProssimaGiornata()<squadra.getCampionato().getGiornataFine()){
            campionatoFinitolbl.setVisible(false);
            ultimaGiornataScrollPane.setVisible(false);
            setTableProssimaG();
        }
        if(squadra.getCampionato().getProssimaGiornata()>1){
            campionatoIniziolbl.setVisible(false);
            prossimaGiornataScrollPane.setVisible(false);
            setTableUltimaG();
        }*/
    }


    private void setTableProssimaG(){
        Object[] nomeColonne = {"Casa","Trasferta"};
        Object[][] righeProssimaGiornata = squadra.getCampionato().prossimaGiornata().prossimaGiornataToArray();

        TableNotEditableModel prossimaGiornataModel = new TableNotEditableModel(righeProssimaGiornata, nomeColonne);
        prossimaGiornataTable.setModel(prossimaGiornataModel);
        //setta il colore delle righe alternato
        prossimaGiornataTable.setDefaultRenderer(Object.class, new RenderTableAlternate());
    }

    private void setTableUltimaG(){
        Object[] nomeColonne = {"","","","","","",""};
        Object[][] righeUltimaGiornata = squadra.getCampionato().ultimaGiornata().partiteToArray();

        TableNotEditableModel prossimaGiornataModel = new TableNotEditableModel(righeUltimaGiornata, nomeColonne);
        ultimaGiornataTable.setModel(prossimaGiornataModel);
        //setta il colore delle righe alternato
        ultimaGiornataTable.setDefaultRenderer(Object.class, new RenderTableAlternate());
    }

    private void setTableClassifica(){
        Object[] nomeColonne = {"Squadra", "Punti"};
        Object[][] righeClassifica = utils.listaClassificaToArrayPiccola(squadra.getCampionato().getClassifica());

        TableNotEditableModel classificaModel = new TableNotEditableModel(righeClassifica, nomeColonne){
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };

        tableClassifica.setModel(classificaModel);

        //setta il colore delle righe alternato
        tableClassifica.setDefaultRenderer(Object.class, new RenderTableAlternate());

        tableClassifica.setAutoCreateRowSorter(true);
    }

    private void setListaAvvisi(){
        DefaultListModel listaAvvisiModel = new DefaultListModel();
        for(String[] avviso:squadra.getCampionato().getListaAvvisi()){
            listaAvvisiModel.addElement(avviso[0]);
        }
        listaAvvisi.setModel(listaAvvisiModel);
        testoAvvisi.setLineWrap(true);
    }

    public void aggiornaCountdown(long giorni, long ore, long minuti, long secondi){
        this.giornilbl.setText(String.valueOf(giorni));
        this.orelbl.setText(String.valueOf(ore));
        this.minutilbl.setText(String.valueOf(minuti));
        this.secondilbl.setText(String.valueOf(secondi));
    }



}
