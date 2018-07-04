package br.com.alura.cliente;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Tarefas {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("localhost", 12345);
		System.out.println("conexao estabelecida");
		
		Thread threadEnviaComando = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					
					System.out.println("Podemos enviar Comandos!");
					
					PrintStream saida = new PrintStream(socket.getOutputStream());	
					Scanner teclado = new Scanner(System.in);
					while(teclado.hasNextLine()) {
						String linha = teclado.nextLine();
						
						if(linha.trim().equals("")){
							break;
						}
						saida.println(linha);
					}
					
					saida.close();
					teclado.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException();
				}
				
			}
		});
		
		Thread threadRecebeResposta = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					System.out.println("Recebendo dados do servidor");
					
					Scanner respostaServidor = new Scanner(socket.getInputStream());
					while(respostaServidor.hasNextLine()) {
						String linha = respostaServidor.nextLine();
						System.out.println(linha);
					}		
					
					respostaServidor.close();
					
				} catch (IOException e) {
					throw new RuntimeException();
				}
				
			}
		});
		
		threadRecebeResposta.start();
		threadEnviaComando.start();
		
		//Comando que diz para Thred main aguardar a conclução da threadenviacomando para prossegir
		try {threadEnviaComando.join();} catch(InterruptedException e) {throw new RuntimeException();}
		
		System.out.println("Fechando socket do cliente");
		socket.close();
	}

}
