package com.github.jonatabecker.erl.gui;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author JonataBecker
 */
public class FifoComponent extends JComponent {

    public FifoComponent() {
        super();
        initGui();
    }

    private void initGui() {
        setLayout(new BorderLayout());
        add(buildProdutorConsumidor(), BorderLayout.NORTH);
        add(buildTrabalho(), BorderLayout.CENTER);
    }
    
    private JComponent buildProdutorConsumidor() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new ConsumidorComponent(), BorderLayout.NORTH);
        panel.add(new ProdutorComponent(), BorderLayout.CENTER); 
        return panel;
    }
    
    private JComponent buildTrabalho() {
        return new TrabalhoComponent();
    }
    
}
