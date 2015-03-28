package interfacce;

import classi.*;
import org.joda.time.*;
import utils.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pagina principale dell'applicazione. Mostra le informazioni
 * sul campionato più importanti.
 * Estende un JPanel.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Mattioli
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

    private Utils utils = new Utils();

    private DateTime prossimaGiornata;

    private Squadra squadra;

    private Applicazione applicazione;

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

    /**
     * Aggiorna la pagina con i dati della squadra e del campionato.
     * Viene chiamata da Applicazione dopo che è stato settato il riferimento
     * interno a squadra.
     */
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
        }
    }

    /**
     * Setta il riferimento alla squadra loggato.
     * Viene utilizzato da Applicazione.
     * @param squadra
     */
    public void setSquadra(Squadra squadra){
        this.squadra = squadra;
        long prossima = squadra.getCampionato().prossimaGiornata().getGioReale().getDataOraInizio().getTime();
        long orarioConsegna = squadra.getCampionato().getOrarioConsegna() * 60 * 1000;
        this.prossimaGiornata = new DateTime(prossima-orarioConsegna);
    }

    /**
     * Fa partire il countdown per l'inserimento della prossima formazione.
     * Viene utilizzato da applicazione.
     */
    public void startCountDown(){
        final Timer t = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (prossimaGiornata.isAfter(new DateTime())) {
                    //ora e tempo in questo momento
                    DateTime adesso = new DateTime();
                    //numero di secondi fino alla limite per l'inserimento della formazione
                    int secondiTotali = Seconds.secondsBetween(adesso,prossimaGiornata).getSeconds();
                    //calcolo dei giorni, ore, minuti e secondi
                    int d = secondiTotali/60/60/24;
                    int h = secondiTotali/60/60%24;
                    int m = secondiTotali/60%60;
                    int s = secondiTotali%60;
                    //aggiorno i label del countdown
                    aggiornaCountdown(d,h,m,s);
                }
            }
        });
        t.start();
    }

    /**
     * Restituisce il l'oggetto prossima giornata.
     * Serve in Applicazione per controllare se è ancora possibile inserire la formazione.
     * @return
     */
    public DateTime getProssimaGiornata(){
        return this.prossimaGiornata;
    }

    /**
     * Setta la tabella della prossima giornata.
     * Viene utilizzato un modello modificato per rendere le celle non modificabili.
     * Viene utilizzato un render modificato per far mostrare il colore delle righe alternato.
     */
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
