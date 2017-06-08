package com.github.jonatabecker.erl.fifo;

/**
 *
 * @author JonataBecker
 */
public class Produtor {

    public static final int STATUS_DORMINDO = 1;
    public static final int STATUS_TRABALHANDO = 2;
   
    private final long pid;
    private int status;
    
    public Produtor(long pid) {
        this.pid = pid;
        this.status = STATUS_DORMINDO;
    }

    public long getPid() {
        return pid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }  
    
    public String getStatusString() {
        return status == STATUS_DORMINDO ? "Dormindo" : "Trabalhando";
    }
    
}
