package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {

	private static int SERVER_PORT = 8003;

	private ServerSocket serverSocket;
	private DatabaseManager databaseManager;

	private boolean connectDatabase = false;

	public GameServer() throws ClassNotFoundException {

		databaseManager = DatabaseManager.getInstance();
		if (databaseManager.connect() != null) {
			System.out.println("Database connect successfully");
			connectDatabase = true;
		} else {
			System.out.println("Warning: Database connect failed, Cannot use save/load game.");
		}

		try {
			serverSocket = new ServerSocket(SERVER_PORT);

			try {
				while (true) {
					System.out.println("serverSocket accept  in...");
					Socket socket = serverSocket.accept();
					System.out.println("serverSocket accept  out...");
					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String str = br.readLine();
					String msg = str.substring(1);
					System.out.println("str:" + str);
					if (str.charAt(0) == 'g') {// get game list
						ArrayList<Record> list = databaseManager.getGameInformation(msg);
						String result = "";
						for (int i = 0; i < list.size(); i++) {
							result += list.get(i).getDate();
							if (i + 1 < list.size())
								result += ",";
						}
						result += "\n";
						socket.getOutputStream().write(result.getBytes());
						System.out.println("date: " + result);
					} else if (str.charAt(0) == 'l') {// load game
						String[] arr = msg.split(":");
						ArrayList<Record> list = databaseManager.getGameInformation(arr[0]);
						String result = "\n";
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getDate().equals(arr[1])) {
								result = list.get(i).getData() + "\n";
								break;
							}
						}
						System.out.println("data: " + result);
						socket.getOutputStream().write(result.getBytes());
					} else if (str.charAt(0) == 's') {
						System.out.println("savegame: " + msg);
						String[] arr = msg.split(":");
						databaseManager.saveGame(arr[0], arr[1], arr[2]);
					}
					System.out.println("msg done");
				}
			} catch (IOException e) {

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {
		GameServer gameServer = new GameServer();
	}

}
