package com.github.jonatabecker.erl.gui;

import com.github.jonatabecker.erl.FifoErlang;
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

    public static final Color COLOR = Color.decode("#aed581");
    
    public ConsumidorComponent() {
        super();
        initGui();
    }

    private void initGui() {
        setPreferredSize(new Dimension(0, 150));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
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
        for (int i = 0; i < Dados.get().getConsumidores().size(); i++) {
            Consumidor consumidor = Dados.get().getConsumidores().get(i);
            g.setColor(FifoErlang.COLOR_DEFAULT);
            if (consumidor.isTrabalhando()) {
                g.setColor(COLOR);
            }
            g.fillRect(5 + 105 * i, 40, 100, 100);
            g.setColor(Color.BLACK);
            g.drawString("PID: " + consumidor.getPid(), 10 + 105 * i, 55);
        }
    }   


}
