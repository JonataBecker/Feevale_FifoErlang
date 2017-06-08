package com.github.jonatabecker.erl.gui;

import com.github.jonatabecker.erl.fifo.Dados;
import com.github.jonatabecker.erl.fifo.Produtor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author JonataBecker
 */
public class ProdutorComponent extends JPanel {

    public ProdutorComponent() {
        super();
        initGui();
    }

    private void initGui() {
        setPreferredSize(new Dimension(0, 150));
        setBackground(new Color(189, 189, 189));
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Produtor");
        label.setBorder(BorderFactory.createEmptyBorder(12, 5, 0, 0));
        add(label, BorderLayout.NORTH);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {}
                repaint();
            }
        }).start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Color color = new Color(15037299);
        Produtor produtor;
        for (int i = 0; i < Dados.get().getProdutores().size(); i++) {
            g.setColor(color);
            g.fillRect(5 + 105 * i, 40, 100, 100);
            produtor = Dados.get().getProdutores().get(i);
            g.setColor(Color.BLACK);
            g.drawString("PID: " + produtor.getPid(), 10 + 105 * i, 55);
            g.drawString("Status: " + produtor.getStatusString(), 10 + 105 * i, 75);
        }
    }
    
}
