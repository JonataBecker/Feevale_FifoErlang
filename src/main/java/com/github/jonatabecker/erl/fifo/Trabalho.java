package com.github.jonatabecker.erl.fifo;

/**
 *
 * @author JonataBecker
 */
public class Trabalho {

    private final String id;
    private final int tempo;

    public Trabalho(String id, int tempo) {
        this.id = id;
        this.tempo = tempo;
    }

    public String getId() {
        return id;
    }

    public int getTempo() {
        return tempo;
    }
    
}
