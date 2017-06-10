package com.github.jonatabecker.erl.gui;

import com.github.jonatabecker.erl.FifoErlang;
import com.github.jonatabecker.erl.fifo.Dados;
import com.github.jonatabecker.erl.fifo.Trabalho;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author JonataBecker
 */
public class TrabalhoComponent extends JPanel {

    public TrabalhoComponent() {
        super();
        initGui();
    }

    private void initGui() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Fila");
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
        for (int i = 0; i < Dados.get().getTrabalhos().size(); i++) {
            Trabalho trabalho = Dados.get().getTrabalhos().get(i);
            g.setColor(FifoErlang.COLOR_DEFAULT);
            if (trabalho.isStatusConsumindo()) {
                g.setColor(ConsumidorComponent.COLOR);
            }
            if (trabalho.isStatusProduzindo()) {
                g.setColor(ProdutorComponent.COLOR);
            }
            g.fillRect(5 + 105 * i, 40, 100, 100);
            g.setColor(Color.BLACK);
            g.drawString(trabalho.getId(), 10 + 105 * i, 55);
            g.drawString(trabalho.getTempo() + " ms", 10 + 105 * i, 70);
        }
    }
    
}
