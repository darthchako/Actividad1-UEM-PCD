
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
	private Socket socket;
	private ArrayList<ServerThread> ThreadList;
	private PrintWriter output;
	
	public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
		this.socket = socket;
		this.ThreadList = threads;		
	}
	
	@Override
	public void run() {
		try {
			//Leemos la escritura del cliente
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//Devolvemos el output al cliente y limpiamos el buffer
			output = new PrintWriter(socket.getOutputStream(),true);
			
			//Hacemos un bucle infinito para la lectura	
			while(true) {
				String outputString = input.readLine();
				//Salimos del chat si detectamos "exit"
				if(outputString.equals("exit")) {
					break;
				}
				NotificaClientes(outputString);
				System.out.println("Server recieved "+outputString);
			}
			
		}catch (Exception e) {
			System.out.println("Error ocurred"+e.getStackTrace());
		}
	}

	private void NotificaClientes(String outputString) {
		for(ServerThread sT: ThreadList) {
			sT.output.println(outputString);
		}
	}
}
