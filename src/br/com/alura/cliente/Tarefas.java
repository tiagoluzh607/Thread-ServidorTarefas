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
		
		PrintStream saida = new PrintStream(socket.getOutputStream());	
		Scanner teclado = new Scanner(System.in);
		while(teclado.hasNextLine()) {
			String linha = teclado.nextLine();
			
			if(linha.trim().equals("")){
				break;
			}
			saida.println(linha);
		}
		
		System.out.println("Recebendo dados do servidor");
		
		Scanner respostaServidor = new Scanner(socket.getInputStream());
		while(respostaServidor.hasNextLine()) {
			String linha = respostaServidor.nextLine();
			System.out.println(linha);
		}		
		
		respostaServidor.close();
		
		saida.close();
		teclado.close();
		socket.close();
	}

}
