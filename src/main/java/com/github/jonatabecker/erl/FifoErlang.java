package com.github.jonatabecker.erl;

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
    }

    public FifoErlang() {
        super("Fifo Erlang");
        initGui();
    }

    private void initGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        add(new FifoComponent(), BorderLayout.CENTER);
        add(new ConfiguracoesComponent(), BorderLayout.EAST);
    }
    
}
