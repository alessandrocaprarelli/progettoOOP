package interfacce.gestore;

import db.Mysql;
import entità.Giocatore;
import entità.Squadra;
import utils.RenderTableAlternate;
import utils.TableNotEditableModel;
import utils.Utils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Classe per la gestione dei giocatori.
 * Estende un JPanel.
 * Viene instanziata solo se l'utente che si logga è il presidente di lega.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class GestioneGiocatori extends JPanel {
    private JTable tabellaGiocatori;
    private JButton removeButton;
    private JButton addButton;
    private JTable tabellaSquadra;
    private JComboBox comboBox;
    private JButton confermaRoseButton;
    private JSpinner spinner;
    private JPanel mainPanel;
    private JLabel soldiSpesilbl;
    private JPanel gestionePanel;
    private JPanel astaLivePanel;
    private JScrollPane tabellaListaGiocatori;

    private DefaultComboBoxModel comboBoxModel;
    private DefaultTableModel tabellaGiocatoriModel;
    private ArrayList<DefaultTableModel> tabellaSquadraModel;
    private ArrayList<Integer> soldiSpesi = new ArrayList<Integer>();

    //intestazioni delle due tabelle del panel, la prima per la tabella dei
    //giocatori mentre la seconda per la tabella delle squadre
    private String[] colonne2 = {"ID", "Cognome", "Ruolo", "Squadra Reale", "Prezzo iniziale", "Prezzo d'Acquisto"};
    private Object[] colonne1 = {"ID", "Cognome", "Ruolo", "Squadra Reale", "Prezzo Iniziale"};

    private Squadra squadra;

    private ArrayList<Giocatore> listaGiocatori;

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    private Applicazione applicazione;

    /**
     * Costruttore della pagina di gestione dei giocatori.
     * @param app
     */
    public GestioneGiocatori(final Applicazione app) {
        applicazione = app;


        //si aggiunge il listener per il cambio degli elementi
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int i;
                i = comboBox.getSelectedIndex();
                //mostro i soldi spesi per questa squadra
                soldiSpesilbl.setText(soldiSpesi.get(i).toString());
                //imposta il modella per la tabella della squadra secondo l'indice del combobox
                tabellaSquadra.setModel(tabellaSquadraModel.get(i));
                //controlla che la lista della squadra non sia completa
                if (tabellaSquadraModel.get(i).getRowCount() == 25) {
                    addButton.setEnabled(false);
                } else {
                    addButton.setEnabled(true);
                }
                //controlla che la lista della squadra non sia vuota
                if (tabellaSquadraModel.get(i).getRowCount() == 0) {
                    removeButton.setEnabled(false);
                } else {
                    removeButton.setEnabled(true);
                }
            }
        });

        //Pulsante aggiungi giocatore
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //si prende l'indice dell'elemento nel combobox che è lo stesso del modello associato alla tabella
                //della rispettiva squadra dato che le tabelle sono create in ordine
                int i = comboBox.getSelectedIndex();
                //controlla se è stata selezionata una riga nella tabella dei giocatori, altrimenti mostra un
                //messaggio d'errore per invitare a selezionare una riga prima di premere il pulsante
                if (tabellaGiocatori.getSelectedRow() != -1) {
                    int j = 0, k = 0;
                    //indice riga nella tabella dei giocatori
                    int r = tabellaGiocatori.getSelectedRow();
                    //memorizza i parametri in variabili per rendere il codice più chiaro
                    Integer ID = (Integer) tabellaGiocatori.getValueAt(r, 0);
                    String nomeGiocatore = (String) tabellaGiocatori.getValueAt(r, 1);
                    String ruolo = String.valueOf(tabellaGiocatori.getValueAt(r, 2));
                    String squadra = (String) tabellaGiocatori.getValueAt(r, 3);
                    Integer prezzoIniziale = (Integer) tabellaGiocatori.getValueAt(r, 4);
                    Integer prezzoPagato = (Integer) spinner.getValue();
                    /*fa scorrere un indice lungo le righe della tabella della squadra
                    //ad ogni iterazione se il valore del campo ruolo della riga della tabella squadra è uguale al ruolo
                    //del giocatore che si vuole inserire, incrementa un contatore per evitare che si inseriscano più
                    //giocatori di quelli consentiti per un determinato ruolo*/
                    while (k < tabellaSquadraModel.get(i).getRowCount()) {
                        if (String.valueOf(tabellaSquadraModel.get(i).getValueAt(k, 2)).equals(ruolo)) j++;
                        k++;
                    }
                    /*a seconda del ruolo controlla il contatore incrementato precedentemente e se c'è un
                    //errore nel numero di giocatori apre una finestra d'errore per segnalarlo. Se non c'è nessun errore,
                    //popola la nuova riga aggiungendola in fondo alla tabella*/
                    if (ruolo.equals("P") && j >= 3) {
                        JOptionPane.showMessageDialog(null, "Sono già stati aggiunti 3 portieri alla squadra.\nRimuovere un portiere per aggiungerne un altro.", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else if (ruolo.equals("D") && j >= 8) {
                        JOptionPane.showMessageDialog(null, "Sono già stati aggiunti 8 difensori alla squadra.\nRimuovere un difensore per aggiungerne un altro", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else if (ruolo.equals("C") && j >= 8) {
                        JOptionPane.showMessageDialog(null, "Sono già stati aggiunti 8 centrocampisti alla squadra.\nRimuovere un centrocampista per aggiungerne un altro", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else if (ruolo.equals("A") && j >= 6) {
                        JOptionPane.showMessageDialog(null, "Sono già stati aggiunti 6 attaccanti alla squadra.\nRimuovere un attaccante per aggiungerne un altro", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        //aggiunge la riga
                        ((DefaultTableModel) tabellaSquadraModel.get(i)).addRow(new Object[]{ID, nomeGiocatore, ruolo, squadra, prezzoIniziale, prezzoPagato});
                        //rimuove la riga
                        ((DefaultTableModel) tabellaGiocatori.getModel()).removeRow(r);

                        if (!getPanel().squadra.getCampionato().isGiocatoriDaInserire()) {
                            Mysql.aggiungiGiocatore(getPanel().squadra.getCampionato(),getPanel().squadra.getCampionato().getListaSquadrePartecipanti().get(i), ID, prezzoPagato);
                        }

                        soldiSpesi.set(i, soldiSpesi.get(i) + prezzoPagato);
                        aggiornaSoldi(soldiSpesi.get(i));

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nessuna riga della tabella dei giocatori è stata selezionata! \n Seleziona una riga prima di premere il pulsante", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                //controlla che la squadra non sia al completo. Nel caso lo sia, disabilita il pulsante per aggiungere nuovi elementi
                if (tabellaSquadraModel.get(i).getRowCount() == 25) {
                    addButton.setEnabled(false);
                }
                //dopo l'inserimento della prima riga della tabella abilita il pulsante di rimozione
                if (tabellaSquadraModel.get(i).getRowCount() == 1) {
                    removeButton.setEnabled(true);
                }
            }
        });

        //Pulsante rimuovi giocatore, commenti come sopra se non specificato
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = comboBox.getSelectedIndex();
                //aggiunge una nuova riga nella tabella dei giocatori e la elimina da quella delle squadre
                if (tabellaSquadra.getSelectedRow() != -1) {
                    int r = tabellaSquadra.getSelectedRow();
                    Integer ID = (Integer)tabellaSquadra.getValueAt(r, 0);
                    String cognome = (String) tabellaSquadra.getValueAt(r, 1);
                    String ruolo = String.valueOf(tabellaSquadra.getValueAt(r, 2));
                    String squadraReale = (String) tabellaSquadra.getValueAt(r, 3);
                    Integer prezzo = (Integer)tabellaSquadra.getValueAt(r, 4);
                    int prezzoVendita = (Integer) spinner.getValue();
                    //aggiunge la riga
                    ((DefaultTableModel) tabellaGiocatori.getModel()).addRow(new Object[]{ID, cognome, ruolo, squadraReale, prezzo});
                    //rimuove la riga
                    ((DefaultTableModel) tabellaSquadra.getModel()).removeRow(r);

                    if (!getPanel().squadra.getCampionato().isGiocatoriDaInserire()) {
                        Mysql.rimuoviGiocatore(getPanel().squadra.getCampionato(),getPanel().squadra.getCampionato().getListaSquadrePartecipanti().get(i), ID, prezzoVendita);
                    }

                    //aggiorno il contatore dei soldi spesi
                    soldiSpesi.set(i, soldiSpesi.get(i) - prezzoVendita);
                    aggiornaSoldi(soldiSpesi.get(i));

                } else {
                    JOptionPane.showMessageDialog(null, "Nessuna riga della tabella della squadra è stata selezionata! \n Seleziona una riga prima di premere il pulsante", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                if (tabellaSquadraModel.get(i).getRowCount() == 0) {
                    removeButton.setEnabled(false);
                }
                //nel caso in cui si sia rimosso il 25esimo elemento si riattiva il pulsante per aggiungere un giocatore alla squadra
                if (tabellaSquadraModel.get(i).getRowCount() == 24) {
                    addButton.setEnabled(true);
                }
            }
        });

        //Pulsante per confermare le squadre del campionato
        confermaRoseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //booleano per verificare che tutte le rose siano complete
                boolean completo = false;
                //numero degli elementi del combobox (numero di squadre del campionato)
                int n = comboBox.getItemCount();
                //se la tabella della relativa squadra ha 25 elementi setta completo come true.
                //Per inviare le modifiche tutte le rose devono essere complete
                for (int i = 0; i < n; i++) {
                    if (tabellaSquadraModel.get(i).getRowCount() == 25) completo = true;
                    else completo = false;
                }
                if (completo) {
                    //creo una lista di giocatori per ogni squadra
                    for (int i = 0; i < squadra.getCampionato().getListaSquadrePartecipanti().size(); i++) {
                        ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();
                        //inserisco i giocatori nella lista
                        for (int k = 0; k < 25; k++) {
                            int ID = (Integer) tabellaSquadraModel.get(i).getValueAt(k, 0);
                            String cognome = (String) tabellaSquadraModel.get(i).getValueAt(k, 1);
                            char ruolo = ((String) tabellaSquadraModel.get(i).getValueAt(k, 2)).charAt(0);
                            String squadra = (String) tabellaSquadraModel.get(i).getValueAt(k, 3);
                            int prezzoBase = (Integer) tabellaSquadraModel.get(i).getValueAt(k, 4);
                            int prezzoAcquisto = (Integer) tabellaSquadraModel.get(i).getValueAt(k, 5);

                            Giocatore giocatore = new Giocatore(ID, cognome, prezzoBase, prezzoAcquisto, squadra, ruolo);
                            listaGiocatori.add(giocatore);
                        }
                        squadra.getCampionato().getListaSquadrePartecipanti().get(i).setGiocatori(listaGiocatori);
                        int soldiDisponibili = squadra.getCampionato().getCreditiIniziali() - soldiSpesi.get(i) >= 0 ? squadra.getCampionato().getCreditiIniziali() - soldiSpesi.get(i) : 0;
                        squadra.getCampionato().getListaSquadrePartecipanti().get(i).setSoldiDisponibili(soldiDisponibili);
                    }
                    if (Mysql.inserisciGiocatori(squadra.getCampionato())) {
                        //se l'inserimento è andato bene mostra un dialog
                        JOptionPane.showMessageDialog(null, "Le rose sono state inserite con successo!", "Avviso", JOptionPane.INFORMATION_MESSAGE);
                        getPanel().refreshGiaInseriti();
                        applicazione.getFormazionePanel().refresh();
                        applicazione.getInfoPanel().refresh();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La rosa di qualche squadre non è completa.\nPrima di confermare è necessario completare tutte le rose.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    /**
     * Setta le tabelle delle squadre se ancora non sono mai state inserite le rose.
     * Vengono creti dei TableModel vuoti e inizializzato il contatore dei soldi spesi a 0.
     */
    private void setTabelleSquadre() {
        //crea una tabella vuota per ogni squadra del campionato
        tabellaSquadraModel = new ArrayList<DefaultTableModel>();
        int i = 0;
        for (Squadra s : squadra.getCampionato().getListaSquadrePartecipanti()) {
            tabellaSquadraModel.add(new DefaultTableModel() {
                //rende non modificabili le colonne dell'ID,cognome e ruolo
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            tabellaSquadraModel.get(i).setColumnIdentifiers(colonne2);

            //inizializzo a zero il contatore dei soldi spesi
            soldiSpesi.add(new Integer(0));
            i++;
        }
        //permette di selezionare una riga alla volta nella tabella giocatori
        DefaultListSelectionModel squadreListModel = new DefaultListSelectionModel() {
            @Override
            public void clearSelection() {
            }

            @Override
            public void removeSelectionInterval(int index0, int index1) {
            }
        };
        squadreListModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabellaSquadra.setSelectionModel(squadreListModel);

        tabellaSquadra.setModel(tabellaSquadraModel.get(0));
        tabellaSquadra.setDefaultRenderer(Object.class, new RenderTableAlternate());

        aggiornaSoldi(soldiSpesi.get(0));


    }

    /**
     * Setta le tabelle delle squadre se le rose sono già state inserite.
     * I model sono popolati con i giocatori delle rispettive squadre.
     */
    private void setTabelleSquadre2() {
        //crea una tabella vuota per ogni squadra del campionato
        tabellaSquadraModel = new ArrayList<DefaultTableModel>();
        int i = 0;
        for (Squadra s : squadra.getCampionato().getListaSquadrePartecipanti()) {

            tabellaSquadraModel.add(new TableNotEditableModel(s.listaGiocatoriRosaToArray(), colonne2));
            int totale = 0;
            for (Giocatore g : s.getGiocatori()) {
                totale += g.getPrezzoAcquisto();
            }
            soldiSpesi.add(new Integer(totale));
            i++;
        }
        //permette di selezionare una riga alla volta nella tabella giocatori
        DefaultListSelectionModel squadreListModel = new DefaultListSelectionModel() {
            @Override
            public void clearSelection() {
            }

            @Override
            public void removeSelectionInterval(int index0, int index1) {
            }
        };
        squadreListModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        squadreListModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Ignore extra messages.
                if (e.getValueIsAdjusting()) return;

                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (!lsm.isSelectionEmpty()) {
                    int riga = lsm.getMinSelectionIndex();
                    setSpinnerGiocatore((Integer) tabellaSquadra.getValueAt(riga, 4));

                }
            }
        });

        tabellaSquadra.setSelectionModel(squadreListModel);

        tabellaSquadra.setModel(tabellaSquadraModel.get(0));
        tabellaSquadra.setDefaultRenderer(Object.class, new RenderTableAlternate());

        aggiornaSoldi(soldiSpesi.get(0));


    }

    /**
     * Setta il combobox che serve per cambiare la squadra che si sta guardando.
     */
    public void setComboBox() {
        comboBoxModel = new DefaultComboBoxModel(squadra.getCampionato().squadreToArray());
        comboBox.setModel(comboBoxModel);
        comboBox.setToolTipText("ID-Nome Squadra-Nick Proprietario");
    }

    /**
     * Setta lo spinner che serve per il prezzo di acquisto/vendita.
     */
    public void setSpinner() {
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);
        spinner.setModel(spinnerModel);
    }

    /**
     * Setta lo spinner che serve per il prezzo di acquisto/vendita.
     */
    private void setSpinnerGiocatore(int prezzo) {
        /*model per l'oggetto spinner che si occupa dell'inserimento del prezzo d'acquisto
        dei giocatori al momento dell'inserimento nella tabella della squadra.
        Gli argomenti del costruttore sono; (partenza, minimo, massimo, incremento)
        */
        SpinnerModel spinnerModel = new SpinnerNumberModel(prezzo, prezzo, 1000, 1);
        spinner.setModel(spinnerModel);
    }

    /**
     * Setta la tabella dei giocatori disponibili.
     */
    public void setTabellaGiocatori() {
        Object[][] righeGiocatori = Utils.listaGiocatoriToArray(listaGiocatori);

        TableNotEditableModel giocatoriModel = new TableNotEditableModel(righeGiocatori, colonne1);

        //permette di selezionare una riga alla volta nella tabella giocatori
        DefaultListSelectionModel giocatoriListModel = new DefaultListSelectionModel() {
            @Override
            public void clearSelection() {
            }

            @Override
            public void removeSelectionInterval(int index0, int index1) {
            }
        };
        giocatoriListModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //quando viene selezionata una riga della tabella giornata aggiorna lo spinner
        giocatoriListModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Ignore extra messages.
                if (e.getValueIsAdjusting()) return;

                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (!lsm.isSelectionEmpty()) {
                    int riga = lsm.getMinSelectionIndex();
                    setSpinnerGiocatore((Integer) tabellaGiocatori.getValueAt(riga, 4));

                }
            }
        });
        tabellaGiocatori.setSelectionModel(giocatoriListModel);

        tabellaGiocatori.setModel(giocatoriModel);
        tabellaGiocatori.setDefaultRenderer(Object.class, new RenderTableAlternate());

    }

    /**
     * Aggiorni il label con il contatore dei soldi.
     * @param soldi
     */
    private void aggiornaSoldi(Integer soldi) {
        if (soldi <= squadra.getCampionato().getCreditiIniziali()) {
            soldiSpesilbl.setText(soldi.toString());
            soldiSpesilbl.setForeground(Color.black);
        } else {
            soldiSpesilbl.setText(soldi.toString());
            soldiSpesilbl.setForeground(Color.RED);
            soldiSpesilbl.setToolTipText("Questa squadra ha superato il budget consentito");
            JOptionPane.showMessageDialog(null, "Questa squadra ha superato il budget consentito.", "Errore", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Fa il refresh del panel, cioè richiamata i metodi per settare le tabelle, combobox e spinner.
     * Viene utilizzato quando non sono mai state inserite le rose, cioè le squadre sono ancora vuote.
     */
    public void refresh() {
        listaGiocatori = Mysql.selectGiocatoriAdmin();
        setComboBox();
        setSpinner();
        setTabellaGiocatori();
        setTabelleSquadre();
    }

    /**
     * Fa il refresh della pagina, cioè richiama i metodi per settare le tabelle, combobox e spinner.
     * Viene utilizzato quando sono già state inserite le rose, cioè le squadre hanno già dei giocatori.
     */
    public void refreshGiaInseriti() {
        listaGiocatori = Mysql.selectGiocatoriDisponibili(squadra.getCampionato());
        confermaRoseButton.setVisible(false);
        setComboBox();
        setSpinner();
        setTabellaGiocatori();
        setTabelleSquadre2();
    }

    public void refreshAstaLive(){
        gestionePanel.setVisible(false);
        astaLivePanel.setVisible(true);

    }

    private GestioneGiocatori getPanel(){
        return this;
    }

}

