package com.github.jonatabecker.erl.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author JonataBecker
 */
public class ConfiguracoesComponent extends JComponent {
    
    private JTextField tamanhoFila;
    private JTextField quantidadeProdutores;
    private JTextField quantidadeConsumidores;
    private JButton button;
    
    public ConfiguracoesComponent() {
        super();
        initGui();
    }

    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 0));
        add(buildForm(), BorderLayout.NORTH);        
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
    private JComponent buildForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(new JLabel("Tamanho Fila:"));
        panel.add(buildTamanhoFila());
        panel.add(new JLabel("Quantidade de consumidores:"));
        panel.add(buildQuantidadeConsumidores());
        panel.add(new JLabel("Quantidade de produtores:"));
        panel.add(buildQuantidadeProdutores());
        panel.add(buildButton());
        return panel;
    }

    private JComponent buildTamanhoFila() {
        tamanhoFila = new JTextField();
        tamanhoFila.setText("10");
        tamanhoFila.setEditable(false);
        return tamanhoFila;
    }

    private JComponent buildQuantidadeConsumidores() {
        quantidadeConsumidores = new JTextField();
        quantidadeConsumidores.setText("3");
        quantidadeConsumidores.setEditable(false);
        return quantidadeConsumidores;
    }

    private JComponent buildQuantidadeProdutores() {
        quantidadeProdutores = new JTextField();
        quantidadeProdutores.setText("5");
        quantidadeProdutores.setEditable(false);
        return quantidadeProdutores;
    }
    
    private JComponent buildButton() {
        button = new JButton("Iniciar");
        return button;
    }
    
}
