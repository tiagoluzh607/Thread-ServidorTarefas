package br.com.alura.servidor;

import java.util.concurrent.ThreadFactory;

import br.com.alura.testevolatile.TratadorDeExcecao;

public class FabricaDeThreads implements ThreadFactory {

	private static int numero = 1;
	private ThreadFactory defaultThreadFactory;

	public FabricaDeThreads(ThreadFactory defaultThreadFactory) {
		this.defaultThreadFactory = defaultThreadFactory;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Thread newThread(Runnable r) {
		
		//Adicionando nomes as Thread
		//Thread thread = new Thread(r, "Thread Servidor Tarefas "+ numero );
		Thread thread = defaultThreadFactory.newThread(r);
		thread.setName("Thread Servidor Tarefas "+ numero);
		numero++;
		
		//Adicionando comportamento de exeção para todas as threads
		thread.setUncaughtExceptionHandler(new TratadorDeExcecao());
		
		return thread;
	}

}
