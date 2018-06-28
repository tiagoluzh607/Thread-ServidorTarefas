package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		System.out.println("------- Iniciando Servidor ----");
		ServerSocket servidor = new ServerSocket(12345);
		
		//Aproveitando Threads em um pool de threads ou seja o sistema utiliza threads já abertas
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		
		while(true) {
			Socket socket =  servidor.accept();
			System.out.println("Aceitando novo cliente na porta "+ socket.getPort());
			
			DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
			threadPool.execute(distribuirTarefas);
			
			
		}
		
	}

}
