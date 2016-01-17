package ufc.chat;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

	private int porta;
	private List<Socket> clientes;
	ServerSocket servidor = null;

	public Servidor(int porta) {
		this.porta = porta;
		this.clientes = new ArrayList<>();
	}

	public void executa() throws IOException {
		try {
			System.out.println("Iniciando servidor..");
			servidor = new ServerSocket(this.porta);
			System.out.println("Servidor esperando conexões..");
			while (true) {
				Socket cliente = servidor.accept();
				TratadorDeMensagemDoCliente tc = new TratadorDeMensagemDoCliente(cliente, this);
				System.out.println("Nova conexao com o cliente " + cliente.getPort());
				new Thread(tc).start();
				this.clientes.add(cliente);
			}
		} catch (Exception e) {
			try {
				if (servidor != null) {
					servidor.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.err.println("A porta está ocupada ou o servidor foi encerrado.");
			e.printStackTrace();
		}
	}

	public void distribuiMensagem(Socket clienteQueEnviou, String msg) {
		for (Socket cliente : this.clientes) {
			if (!cliente.equals(clienteQueEnviou)) {
				try {
					PrintStream ps = new PrintStream(cliente.getOutputStream());
					ps.println(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}