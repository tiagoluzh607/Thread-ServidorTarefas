package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {
	
	
	
	
	private ExecutorService threadPool;
	private ServerSocket servidor;
	private boolean estaRodando;
	
	public ServidorTarefas() throws IOException {
		
		System.out.println("------- Iniciando Servidor ----");
		this.servidor = new ServerSocket(12345);
		
		//Aproveitando Threads em um pool de threads ou seja o sistema utiliza threads já abertas
		this.threadPool = Executors.newCachedThreadPool(); // sem limite e conexão ele cresce dinâmicamente e se um thred não é usado por 60 segundos ele fecha o thread 
		this.estaRodando = true;
		
	}
		
	public void rodar() throws IOException {
		
		while(this.estaRodando) {
			try {
				Socket socket =  servidor.accept();
				System.out.println("Aceitando novo cliente na porta "+ socket.getPort());
				
				DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket,this);
				threadPool.execute(distribuirTarefas);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				System.out.println("SocketException, Está rodando? "+ this.estaRodando);
			}
			
		}
		
	}

	public void parar() throws IOException {
		estaRodando = false;
		servidor.close();
		threadPool.shutdown();
		
	}


	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
		servidor.parar();
		
	}

}
