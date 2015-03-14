package interfacce;

import classi.*;
import db.Mysql;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by alessandro on 12/03/15.
 */
public class Login extends JFrame {

    private JTextField usertxt;
    private JPasswordField passtxt;
    private JButton loginButton;
    private JButton registratiButton;
    private JComboBox comboBox1;
    private JButton gestisciButton;
    private JComboBox comboBox2;
    private JButton iscrivitiButton;
    private JButton creaButton;
    private JPanel panel1;
    private JPanel login1;
    private JPanel login2;
    private JLabel infolbl;
    private JTextField textField1;
    private JLabel nomeutentetxt;
    public Persona utente;
    public Utils utils = new Utils();

    public Login() {
        //titolo del frame
        super("Login - Gestore fantacalcio");

        final Mysql db = new Mysql();
        setContentPane(panel1);

        pack();

        infolbl.setVisible(false);

        //centra il frame
        setLocationRelativeTo(null);

        setVisible(true);

        setResizable(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utente = new Persona(usertxt.getText(), utils.passwordString(passtxt.getPassword()));
                if (db.login(utente)) {
                    CardLayout c1 = (CardLayout) (panel1.getLayout());
                    c1.show(panel1, "login2");
                } else infolbl.setVisible(true);
            }
        });


        //evento al clic del tasto enter
        passtxt.addKeyListener
                (new KeyAdapter() {
                     public void keyPressed(KeyEvent e) {
                         int key = e.getKeyCode();
                         if (key == KeyEvent.VK_ENTER) {
                             utente = new Persona(usertxt.getText(), utils.passwordString(passtxt.getPassword()));
                             if (db.login(utente)) {
                                 nomeutentetxt.setText(utente.getNickname());
                                 CardLayout c1 = (CardLayout) (panel1.getLayout());
                                 c1.show(panel1, "login2");
                             } else infolbl.setVisible(true);
                         }
                     }
                 }
                );
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registra registragui = new Registra();

            }
        });


        gestisciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Applicazione app = new Applicazione();
            }
        });
        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreaCampionato creaCampionato = new CreaCampionato(utente, getFrame());
                getFrame().setVisible(false);
            }
        });
    }

    public Login getFrame() {
        return this;
    }


}
