package com.github.jonatabecker.erl;

import com.github.jonatabecker.erl.fifo.Consumidor;
import com.github.jonatabecker.erl.fifo.Dados;
import com.github.jonatabecker.erl.fifo.Produtor;
import com.github.jonatabecker.erl.fifo.Trabalho;
import com.github.jonatabecker.erl.gui.ConfiguracoesComponent;
import com.github.jonatabecker.erl.gui.FifoComponent;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author JonataBecker
 */
public class FifoErlang extends JFrame {
    
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        FifoErlang fifoErlang = new FifoErlang();
        fifoErlang.setVisible(true);
        
        Dados.get().addProdutor(new Produtor(1000));
        Dados.get().addConsumidor(new Consumidor(2000));
        Dados.get().addTrabalho(new Trabalho(500));
        
        
    }

    public FifoErlang() {
        super("Fifo Erlang");
        initGui();
    }

    private void initGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(new FifoComponent(), BorderLayout.CENTER);
        add(new ConfiguracoesComponent(), BorderLayout.EAST);
    }
    
}
