package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWS implements Callable<String> { //callable funciona como umrunable porem permite retorno nesse caso de uma string

	private PrintStream saida;

	public ComandoC2ChamaWS(PrintStream saida) {
		this.saida = saida;
		
	}

	@Override
	public String call() throws Exception { // call executa um callable e retorna algum tipo
		
		System.out.println("Servidor Recebeu comando c2 - WS");
		
		saida.println("Processando comando c2 - WS...");
		
		Thread.sleep(15000);

		int numero = new Random().nextInt(100) + 1;
		
		
		saida.println("Servidor finalizou comando c2 - WS");
		
		return Integer.toString(numero);

	}

}
