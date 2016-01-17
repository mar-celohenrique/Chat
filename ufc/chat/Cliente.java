package ufc.chat;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	private String host;
	private int porta;
	private Socket cliente;
	private Scanner teclado;

	public Cliente(String host, int porta) {
		this.host = host;
		this.porta = porta;
	}

	public void executa() throws UnknownHostException, IOException {
		try {
			cliente = new Socket(this.host, this.porta);
			teclado = new Scanner(System.in);
			PrintStream saida = new PrintStream(cliente.getOutputStream());
			System.out.println("O cliente se conectou ao servidor!");

			RecebedorDeMensagemDoServidor r = new RecebedorDeMensagemDoServidor(cliente.getInputStream());
			new Thread(r).start();

			while (teclado.hasNextLine()) {
				saida.println(teclado.nextLine());
			}
		} catch (Exception e) {

		}

	}
}
