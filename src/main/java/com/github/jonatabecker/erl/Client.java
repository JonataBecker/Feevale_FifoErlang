package com.github.jonatabecker.erl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JonataBecker
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Socket connection = new Socket("localhost", 3000);
        DataOutputStream output = new DataOutputStream(connection.getOutputStream());
  
        new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                
                
                while (true) {
                    String command = in.readLine();
                    System.out.println("T:" + command);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }).start();
        
        
        
        

        while(true) {
            String st = "Send";
            System.out.println(st);
            output.writeBytes(st + "\n");
            Thread.sleep(1000);
            
        }
    }
}
