package com.github.jonatabecker.erl.fifo;

/**
 *
 * @author JonataBecker
 */
public class Produtor {

    public static final int STATUS_DORMINDO = 1;
    public static final int STATUS_TRABALHANDO = 2;
   
    private final String pid;
    private int status;
    
    public Produtor(String pid) {
        this.pid = pid;
        this.status = STATUS_DORMINDO;
    }

    public String getPid() {
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
