package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameClient {


	private static int SERVER_PORT = 8003;
	private Socket clientSocket;
	private YahtzeeFrame yahtzee;

	public GameClient() {
		yahtzee = new YahtzeeFrame(true);
		yahtzee.addClientListener(this);
	}

	public String[] GetGameList(String name) {
		String[] array = null;
		try {
			clientSocket = new Socket("localhost", SERVER_PORT);
			String str = "g" + name + "\n";
			clientSocket.getOutputStream().write(str.getBytes());
			BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			str = br.readLine();
			array = str.split(",");
			clientSocket.close();
			System.out.println("GetGameList out  str: " + str);
		} catch (UnknownHostException e) {	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return array;
	}

	public String[] GetGameData(String name, String date) {
		String[] array = null;
		try {
			clientSocket = new Socket("localhost", SERVER_PORT);
			String str = "l" + name + ":" + date + "\n";
			clientSocket.getOutputStream().write(str.getBytes());
			BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			str = br.readLine();
			array = str.split(",");
			clientSocket.close();
			System.out.println("GetGameData out  str: " + str);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return array;
	}

	public void SaveGameData(String name, String date, String data) {
		try {
			clientSocket = new Socket("localhost", SERVER_PORT);
			String str = "s" + name + ":" + date + ":" + data + "\n";
			clientSocket.getOutputStream().write(str.getBytes());
			clientSocket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GameClient gameClient = new GameClient();
	}
}
