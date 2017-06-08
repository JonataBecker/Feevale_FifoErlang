package com.github.jonatabecker.erl.gui;

import com.github.jonatabecker.erl.fifo.Consumidor;
import com.github.jonatabecker.erl.fifo.Dados;
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
public class ConsumidorComponent extends JPanel {

    public ConsumidorComponent() {
        super();
        initGui();
    }

    private void initGui() {
        setPreferredSize(new Dimension(0, 150));
        setBackground(new Color(224, 224, 224));
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Consumidor");
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
        Color color = new Color(13951319);
        Consumidor consumidor;
        for (int i = 0; i < Dados.get().getConsumidores().size(); i++) {
            g.setColor(color);
            g.fillRect(5 + 105 * i, 40, 100, 100);
            consumidor = Dados.get().getConsumidores().get(i);
            g.setColor(Color.BLACK);
            g.drawString("PID: " + consumidor.getPid(), 10 + 105 * i, 55);
            g.drawString("Status: " + consumidor.getStatusString(), 10 + 105 * i, 75);        
        }
    }   


}
