package interfacce;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Christian on 12/03/2015.
 */
public class CardPanel extends JPanel{

    private Image img;
    private Dimension size;

    public CardPanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public CardPanel(Image img) {
        this.img = img;
        size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}