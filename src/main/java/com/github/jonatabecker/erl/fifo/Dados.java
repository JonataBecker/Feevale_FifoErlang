package com.github.jonatabecker.erl.fifo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author JonataBecker
 */
public class Dados {

    private static Dados instance = null;
    private final Map<String, Consumidor> consumidores;
    private final Map<String, Produtor> produtores;
    private final Map<String, Trabalho> trabalhos;
    
    private Dados() {
        this.consumidores = new TreeMap<>();
        this.produtores = new TreeMap<>();
        this.trabalhos = new TreeMap<>();
    }
    
    public static Dados get() {
        if (instance == null) {
            instance = new Dados();
        }
        return instance;
    }
    
    public void addConsumidor(Consumidor consumidor) {
        this.consumidores.put(consumidor.getPid(), consumidor);
    }
    
    public Consumidor getConsumidor(String pid) {
        return this.consumidores.get(pid);
    }
    
    public List<Consumidor> getConsumidores() {
        return new ArrayList<>(consumidores.values());
    }
    
    public void addProdutor(Produtor produtor) {
        this.produtores.put(produtor.getPid(), produtor);
    }
    
    public Produtor getProdutor(String pid) {
        return this.produtores.get(pid);
    }
    
    public List<Produtor> getProdutores() {
        return new ArrayList<>(produtores.values());
    }
    
    public void addTrabalho(Trabalho trabalho) {
        this.trabalhos.put(trabalho.getId(), trabalho);
    }

    public Trabalho getTrabalho(String id) {
        return this.trabalhos.get(id);
    }
    
    public List<Trabalho> getTrabalhos() {
        return new ArrayList<>(trabalhos.values());
    }

    public void removeTrabalho(String id) {
        this.trabalhos.remove(id);
    }
        
}
