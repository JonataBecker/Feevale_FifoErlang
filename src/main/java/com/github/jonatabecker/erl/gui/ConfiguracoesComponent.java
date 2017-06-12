package com.github.jonatabecker.erl.gui;

import com.github.jonatabecker.erl.Client;
import java.awt.BorderLayout;
import java.awt.Color;
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
    private JTextField tempoProducao;
    private JTextField tempoTrabalho;
    private JTextField quantidadeConsumidores;
    private JButton button;
    
    public ConfiguracoesComponent() {
        super();
        initGui();
        initEvents();
    }

    private void initGui() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 0));
        add(buildForm(), BorderLayout.NORTH);        
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
    }
    
    private void initEvents() {
        button.addActionListener((e) -> {
            try {
                tamanhoFila.setEditable(false);
                quantidadeProdutores.setEditable(false);
                tempoProducao.setEditable(false);
                tempoTrabalho.setEditable(false);
                button.setEnabled(false);
                Client.get().init(tamanhoFila.getText(), 
                        quantidadeConsumidores.getText(), 
                        quantidadeProdutores.getText(), 
                        tempoProducao.getText(), 
                        tempoTrabalho.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    } 
    
    private JComponent buildForm() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(new JLabel("Tamanho Fila:"));
        panel.add(buildTamanhoFila());
        panel.add(new JLabel("Tempo máximo produção (segundos):"));
        panel.add(buildTempoProducao());
        panel.add(new JLabel("Tempo máximo trabalho (segundos):"));
        panel.add(buildTempoTrabalho());
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
        return tamanhoFila;
    }
   
    private JComponent buildTempoProducao() {
        tempoProducao = new JTextField();
        tempoProducao.setText("5");
        return tempoProducao;
    }
   
    private JComponent buildTempoTrabalho() {
        tempoTrabalho = new JTextField();
        tempoTrabalho.setText("5");
        return tempoTrabalho;
    }

    private JComponent buildQuantidadeConsumidores() {
        quantidadeConsumidores = new JTextField();
        quantidadeConsumidores.setText("3");
        return quantidadeConsumidores;
    }

    private JComponent buildQuantidadeProdutores() {
        quantidadeProdutores = new JTextField();
        quantidadeProdutores.setText("5");
        return quantidadeProdutores;
    }
    
    private JComponent buildButton() {
        button = new JButton("Iniciar");
        return button;
    }
    
}
