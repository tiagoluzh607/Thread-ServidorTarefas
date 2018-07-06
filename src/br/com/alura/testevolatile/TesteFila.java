package br.com.alura.testevolatile;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TesteFila {

	public static void main(String[] args) throws InterruptedException {
		
		BlockingQueue<String> fila = new ArrayBlockingQueue<>(3);

		fila.put("c1"); // o put adiciona um elemento na lista porem agora usando BlockingQueue se a fila estiver cheia ele trava o thread esperando liberar um espaço para colocar o elemento
		fila.put("c2");
		fila.put("c3");
		
		System.out.println(fila.take()); //o Take tira um elemento da lista porem agora usando BlockingQueue se a fila nao tiver mais elementos ele trava o thread aguardando que um elemento entre para tirar
		System.out.println(fila.take());
		System.out.println(fila.take());
		
		System.out.println(fila.size());
	}

}
