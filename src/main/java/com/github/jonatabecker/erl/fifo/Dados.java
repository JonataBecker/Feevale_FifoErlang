package com.github.jonatabecker.erl.fifo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JonataBecker
 */
public class Dados {

    private static Dados instance = null;
    private final List<Consumidor> consumidores;
    private final List<Produtor> produtores;
    private final List<Trabalho> trabalhos;
    
    private Dados() {
        this.consumidores = new ArrayList<>();
        this.produtores = new ArrayList<>();
        this.trabalhos = new ArrayList<>();
    }
    
    public static Dados get() {
        if (instance == null) {
            instance = new Dados();
        }
        return instance;
    }
    
    public void addConsumidor(Consumidor consumidor) {
        this.consumidores.add(consumidor);
    }
    
    public List<Consumidor> getConsumidores() {
        return consumidores;
    }
    
    public void addProdutor(Produtor produtor) {
        this.produtores.add(produtor);
    }

    public List<Produtor> getProdutores() {
        return produtores;
    }
    
    public void addTrabalho(Trabalho trabalho) {
        this.trabalhos.add(trabalho);
    }

    public List<Trabalho> getTrabalhos() {
        return trabalhos;
    }
        
}
