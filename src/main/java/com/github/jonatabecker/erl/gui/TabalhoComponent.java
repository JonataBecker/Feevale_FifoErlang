package com.github.jonatabecker.erl.gui;

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
public class TabalhoComponent extends JPanel {

    public TabalhoComponent() {
        super();
        initGui();
    }

    private void initGui() {
        setBackground(Color.red);
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Fila");
        label.setBorder(BorderFactory.createEmptyBorder(12, 5, 0, 0));
        add(label, BorderLayout.NORTH);
    }   
    
    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        for (int i = 0; i < 3; i++) {
            g.setColor(Color.PINK);
            g.fillRect(5 + 105 * i, 40, 100, 100);
        
        }
    }
    
}
