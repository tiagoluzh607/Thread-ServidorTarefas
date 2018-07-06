package br.com.alura.testevolatile;

import java.util.LinkedList;
import java.util.Queue;

public class TesteFila {

	public static void main(String[] args) {
		
		Queue<String> fila = new LinkedList<>();

		fila.offer("c1");
		fila.offer("c2");
		fila.offer("c3");
		
		System.out.println(fila.poll());
		System.out.println(fila.poll());
		System.out.println(fila.poll());
		
		System.out.println(fila.size());
	}

}
