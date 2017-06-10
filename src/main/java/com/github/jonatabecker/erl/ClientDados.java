package com.github.jonatabecker.erl;

import com.github.jonatabecker.erl.fifo.Consumidor;
import com.github.jonatabecker.erl.fifo.Dados;
import com.github.jonatabecker.erl.fifo.Produtor;
import com.github.jonatabecker.erl.fifo.Trabalho;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JonataBecker
 */
public class ClientDados {

    private final Map<String, Command>  commands;

    public ClientDados() {
        this.commands = new HashMap<>();
        this.commands.put("createProdutor", new AddProdutor());
        this.commands.put("createConsumidor", new AddConsumidor());
        this.commands.put("iniciaProducao", new IniciaProducao());
        this.commands.put("finalizaProducao", new FinalizaProducao());
        this.commands.put("iniciaConsumo", new IniciaConsumo());
        this.commands.put("finalizaConsumo", new FinalizaConsumo());        
    }
   
    public void parser(String data) {
        int index = data.indexOf('-');
        Command command = commands.get(data.substring(0, index));
        if (command != null) {
            command.exec(data.substring(index + 1));
        }
    }
    
    
    private interface Command {
    
        public void exec(String data);
        
    }
    
    private class AddProdutor implements Command {

        @Override
        public void exec(String data) {
            Dados.get().addProdutor(new Produtor(data));
        }
        
    }
    
    private class AddConsumidor implements Command {

        @Override
        public void exec(String data) {
            Dados.get().addConsumidor(new Consumidor(data));
        }
        
    }
   
    private class IniciaProducao implements Command {

        @Override
        public void exec(String data) {
            String[] d = data.split("-");
            Dados.get().getProdutor(d[0]).setStatus(Produtor.STATUS_TRABALHANDO);
            Dados.get().addTrabalho(new Trabalho(d[1], 0));
        }
        
    }
    
    private class FinalizaProducao implements Command {

        @Override
        public void exec(String data) {
            String[] d = data.split("-");
            Dados.get().getProdutor(d[0]).setStatus(Produtor.STATUS_DORMINDO);
            Dados.get().getTrabalho(d[1]).setStatusProduzido();
        }
        
    }
  
    private class IniciaConsumo implements Command {

        @Override
        public void exec(String data) {
            String[] d = data.split("-");
            Dados.get().getConsumidor(d[0]).setStatus(Consumidor.STATUS_TRABALHANDO);
            Dados.get().getTrabalho(d[1]).setStatusConsumindo();
        }
        
    }
    
    private class FinalizaConsumo implements Command {

        @Override
        public void exec(String data) {
            String[] d = data.split("-");
            Dados.get().getConsumidor(d[0]).setStatus(Consumidor.STATUS_DORMINDO);
            Dados.get().removeTrabalho(d[1]);
        }
        
    }
    
    
    
}
