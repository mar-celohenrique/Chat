package ufc.chat;

import java.io.InputStream;
import java.util.Scanner;

class RecebedorDeMensagemDoServidor implements Runnable {

	private InputStream servidor;
	private Scanner s;

	public RecebedorDeMensagemDoServidor(InputStream servidor) {
		this.servidor = servidor;
	}

	public void run() {
		try{
			s = new Scanner(this.servidor);
			while (s.hasNextLine()) {
				System.out.println(s.nextLine());
			}
		}catch(Exception e){
			
		}
	}
}