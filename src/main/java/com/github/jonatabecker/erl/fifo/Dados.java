package com.github.jonatabecker.erl.fifo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JonataBecker
 */
public class Dados {

    private final List<Consumidor> consumidores;
    private final List<Produtor> produtores;
    private final List<Trabalho> trabalhos;
    
    public Dados() {
        this.consumidores = new ArrayList<>();
        this.produtores = new ArrayList<>();
        this.trabalhos = new ArrayList<>();
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
