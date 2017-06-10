package com.github.jonatabecker.erl.fifo;

/**
 *
 * @author JonataBecker
 */
public class Trabalho {

    public static final int STATUS_PRODUZINDO = 0;
    public static final int STATUS_PRODUZIDO = 1;
    public static final int STATUS_CONSUMINDO = 2;
    
    private final String id;
    private final int tempo;
    private int status;
    
    public Trabalho(String id, int tempo) {
        this.id = id;
        this.tempo = tempo;
        this.status = STATUS_PRODUZINDO;
    }

    public String getId() {
        return id;
    }

    public int getTempo() {
        return tempo;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatusConsumindo() {
        this.status = STATUS_CONSUMINDO;
    }

    public void setStatusProduzido() {
        this.status = STATUS_PRODUZIDO;
    }
    
    public boolean isStatusProduzindo() {
        return this.status == STATUS_PRODUZINDO;
    }
    
    public boolean isStatusConsumindo() {
        return this.status == STATUS_CONSUMINDO;
    }
    
}
