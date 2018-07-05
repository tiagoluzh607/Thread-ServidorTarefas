package br.com.alura.servidor;

import java.io.PrintStream;

public class ComandoC1 implements Runnable {

	private PrintStream saida;

	public ComandoC1(PrintStream saida) {
		this.saida = saida;
		
	}

	@Override
	public void run() {
		
		System.out.println("Executando comando c1");
		
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
		saida.println("Comando c1 executado com sucesso!");

	}

}
