package com.github.jonatabecker.erl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author JonataBecker
 */
public class Client {

    private Socket connection;
    private DataOutputStream output;
    private final ClientDados clientDados;
    private static Client client;

    private Client() {
        this.clientDados = new ClientDados();
    }

    public void connect() {
        try {
            connection = new Socket("localhost", 3000);
            output = new DataOutputStream(connection.getOutputStream());
            input();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void input() {
        new Thread(() -> {
            try {
                InputStreamReader r = new InputStreamReader(connection.getInputStream());
                BufferedReader in = new BufferedReader(r);
                while (true) {
                    String command = in.readLine();
                    if (command == null) {
                        return;
                    }
                    System.out.println("Commando:" + command);
                    clientDados.parser(command);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }).start();
    }

    public void init(String fila, String qCon, String qTra, String tPro, String tTra) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("init-");
        sb.append(fila).append("-");
        sb.append(qCon).append("-");
        sb.append(qTra).append("-");
        sb.append(tPro).append("-");
        sb.append(tTra).append("\n");
        output.writeBytes(sb.toString());
    }

    public void close() throws Exception {
        output.writeBytes("close\n");
    }

    public static Client get() {
        if (client == null) {
            client = new Client();
            client.connect();
        }
        return client;
    }
}
