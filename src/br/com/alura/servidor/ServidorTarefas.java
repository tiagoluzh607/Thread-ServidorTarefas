package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
	
	
	
	
	private ExecutorService threadPool;
	private ServerSocket servidor;
	private AtomicBoolean estaRodando; //SEMPRE que uma variavel for acessada por diferentes threds que iram buscar o seu valor, para que ela possa ser efetivamente acessada deve se usar o volatile, porem junto com o volatile é necessario syncronizar o acesso para ter certeza de uma execução de cada vez, e para termor o volatile e o sync juntos utilizamo essa variavel atomica
	
	public ServidorTarefas() throws IOException {
		
		System.out.println("------- Iniciando Servidor ----");
		this.servidor = new ServerSocket(12345);
		
		//Aproveitando Threads em um pool de threads ou seja o sistema utiliza threads já abertas
		this.threadPool = Executors.newCachedThreadPool(); // sem limite e conexão ele cresce dinâmicamente e se um thred não é usado por 60 segundos ele fecha o thread 
		this.estaRodando.set(true);
		
	}
		
	public void rodar() throws IOException {
		
		while(this.estaRodando.get()) {
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
		estaRodando.set(false);
		servidor.close();
		threadPool.shutdown();
		
	}


	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
		servidor.parar();
		
	}

}
