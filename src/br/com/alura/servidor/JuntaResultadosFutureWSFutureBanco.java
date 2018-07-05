package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JuntaResultadosFutureWSFutureBanco implements Callable<Void> {

	private Future<String> futureWs;
	private Future<String> futureBanco;
	private PrintStream saidaCliente;

	public JuntaResultadosFutureWSFutureBanco(Future<String> futureWs, Future<String> futureBanco,PrintStream saidaCliente) {

				this.futureWs = futureWs;
				this.futureBanco = futureBanco;
				this.saidaCliente = saidaCliente;
	}

	@Override
	public Void call() throws Exception {
		
		System.out.println("Aguardando resultados do future WS e Banco");
	
		
		try {
			String numeroMagico = this.futureWs.get(20, TimeUnit.SECONDS); //o get vai pegar a resposta mas o maximo que ele vai esperar é 15 segundos
			String numeroMagico2 = this.futureBanco.get(20, TimeUnit.SECONDS); // se ele nao conseguir vai estourar um catch
			
			this.saidaCliente.println("Resultado comando c2 : "+ numeroMagico + ", "+numeroMagico2);
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			
			System.out.println("Timeout: Cancelando execucao do comando c2");
			this.saidaCliente.println("Timeout na Execucao do comando c2");
			this.futureWs.cancel(true);
			this.futureBanco.cancel(true);
		}
		
		
		return null;
	}

}
