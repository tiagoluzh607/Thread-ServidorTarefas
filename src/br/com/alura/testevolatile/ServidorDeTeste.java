package br.com.alura.testevolatile;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorDeTeste {

	//private boolean estaRodando = false; variavel não atomica
	private volatile boolean estaRodando = false; //Uso do volatile para threads acessarem direto memória principal
	//private AtomicBoolean estaRodando(); //mesmo com o volatile teriamos que syncronizar o acesso a variavel, por isso o melhor é usar a classe atomica, que faz as duas coisas acessa direto a memoria e syncroniza o acesso a esta propriedade de forma implicita
	// para usar o Atomic para mudar a variavel é atraves do get e set exemplo: estaRodando.set(false); estaRodando.set(true);
    public static void main(String[] args) throws InterruptedException {
        ServidorDeTeste servidor = new ServidorDeTeste();
        servidor.rodar();
        servidor.alterandoAtributo();
    }

    private void rodar() {
        Thread thread = new Thread(new Runnable() {

            public void run() {
                System.out.println("Servidor começando, estaRodando = " + estaRodando );

                while(!estaRodando) {}

                if (estaRodando) {
                    throw new RuntimeException("Deu erro na thread...");
                }
                
                System.out.println("Servidor rodando, estaRodando = " + estaRodando );

                while(estaRodando) {}

                System.out.println("Servidor terminando, estaRodando = " + estaRodando );
            }
        });
        thread.setUncaughtExceptionHandler(new TratadorDeExcecao()); //código personalizado para tratamento de exeções
		thread.start();
    }

    private void alterandoAtributo() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Main alterando estaRodando = true");
        estaRodando = true;

        Thread.sleep(5000);
        System.out.println("Main alterando estaRodando = false");
        estaRodando = false;        
    }
}

