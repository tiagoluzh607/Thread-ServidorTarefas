package br.com.alura.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidor;
	private ExecutorService threadPool;
	private BlockingQueue<String> filaComandos;

	public DistribuirTarefas(ExecutorService threadPool, BlockingQueue<String> filaComandos, Socket socket, ServidorTarefas servidor) {
		this.threadPool = threadPool;
		this.filaComandos = filaComandos;
		this.socket = socket;
		this.servidor = servidor;
		
	}

	@Override
	public void run() {
		
		try {
		
			System.out.println("Distribuindo tarefas para "+ socket.getPort());
			
			
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			
			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
			
			while(entradaCliente.hasNextLine()) {
				String comando = entradaCliente.nextLine();
				System.out.println("Comando recebido "+ comando);
				
				switch (comando) {
				
				case "c1":
					saidaCliente.println("Confirmação comando c1");
					ComandoC1 c1 = new ComandoC1(saidaCliente);
					this.threadPool.execute(c1);
					break;

				case "c2":
					saidaCliente.println("Confirmação comando c2");
					ComandoC2ChamaWS c2Ws = new ComandoC2ChamaWS(saidaCliente);
					ComandoC2AcessaBanco c2Banco = new ComandoC2AcessaBanco(saidaCliente);
					Future<String> futureWs = this.threadPool.submit(c2Ws); //com Callable nao é execute é submit
					Future<String> futureBanco = this.threadPool.submit(c2Banco); //com Callable nao é execute é submit
					
					this.threadPool.submit(new JuntaResultadosFutureWSFutureBanco(futureWs,futureBanco,saidaCliente));
					
					break;
					
				case "c3":
					this.filaComandos.put(comando); //bloqueia
					saidaCliente.println("Comando c3 adicionado na fila");
					break;
					
				case "fim":
					saidaCliente.println("Desligando o servidor");
					servidor.parar();
					break;
										
				default:
					saidaCliente.println("Comando nao encontrado");
					break;
				}
				
				
				//System.out.println(comando);
			}
			
			saidaCliente.close();
			entradaCliente.close();
		
			Thread.sleep(20000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}

}
