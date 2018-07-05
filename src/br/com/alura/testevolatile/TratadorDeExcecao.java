package br.com.alura.testevolatile;

import java.lang.Thread.UncaughtExceptionHandler;

public class TratadorDeExcecao implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		
		System.out.println("Thread que aconteceu o erro: "+ t.getName() + " Mensagem de Erro: "+ e.getMessage());

	}

}
