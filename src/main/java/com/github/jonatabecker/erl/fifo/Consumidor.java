package com.github.jonatabecker.erl.fifo;

/**
 *
 * @author JonataBecker
 */
public class Consumidor {

    public static final int STATUS_DORMINDO = 1;
    public static final int STATUS_TRABALHANDO = 2;

    private final long pid;
    private int status;

    public Consumidor(long pid) {
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

}
