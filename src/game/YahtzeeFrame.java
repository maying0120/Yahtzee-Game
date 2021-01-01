package game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class YahtzeeFrame extends JFrame {


	public static Font font = new Font("", Font.PLAIN, 10);
	public static int NOT_START = 0;
	public static int GAME_START = 1;
	public static int GAME_OVER = 2;
	public static boolean CAN_ROLL = false;
	public static boolean CAN_SELECT = false;
	private int rollTime;
	private GameClient gameClient;
	private JMenuBar menuBar;
	private TopPanel tp;
	private CenterPanel cp;
	private RightPanel rp;
	private GameData gameData;
	private int status;
	private boolean connect;
	private int turnCount;

	public YahtzeeFrame() {
		this(false);
	}
	public YahtzeeFrame(boolean connect) {
		this(connect, 1);
	}



	public YahtzeeFrame(boolean connect, int num) {
		this.connect = connect;
		status = NOT_START;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Game");
		this.setSize(500, 1000);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		init();
		setLayout(new BorderLayout());
		this.add(tp, BorderLayout.NORTH);
		this.add(cp, BorderLayout.CENTER);
		this.add(rp, BorderLayout.EAST);
		this.setJMenuBar(menuBar);
		this.validate();
		this.setVisible(true);

		String name = JOptionPane.showInputDialog("Please input your" + " name");
		gameData = new GameData();
		gameData.setName(name);
		start();
	}

	private void init() {
	
		tp = new TopPanel(this);
		cp = new CenterPanel(this);
		rp = new RightPanel(this);
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("Game");
		menu.setFont(font);
		JMenuItem item1 = new JMenuItem("Load Game", KeyEvent.VK_L);
		item1.setFont(font);
		item1.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				if (connect == false) {
					JOptionPane.showMessageDialog(null, "Connect server failed, can not use this function");
				} else {
					sendLoadGame();
					if (status != GAME_START) {
						sendLoadGame();
					} else {
						tp.setHint("loaded game");
					}
				}
			}
		});
		JMenuItem item2 = new JMenuItem("Save Game", KeyEvent.VK_S);
		item2.setFont(font);
		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (connect == false) {
					JOptionPane.showMessageDialog(null, "Connect server failed, can not use this function");
				} else {
					if (status == GAME_START) {
						saveGame();
					} else {
						tp.setHint("saved game");
					}
				}
			}
		});
		JMenuItem item3 = new JMenuItem("Exit", KeyEvent.VK_E);
		item3.setFont(font);
		item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exit");
				System.exit(1);
			}
		});
		menu.add(item1);
		menu.add(item2);
		menu.add(item3);
		menuBar.add(menu);
	}

	private void sendLoadGame() {
		// gameClient.sendMessage("l" + gameData.getName());
		String[] gamelist = gameClient.GetGameList(gameData.getName());
		System.out.println(gamelist.length);
		if (gamelist == null || gamelist.length == 0) {
			JOptionPane.showMessageDialog(null, "error", "not found game", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Object selectedValue = JOptionPane.showInputDialog(null, "Choose one", "Game", JOptionPane.INFORMATION_MESSAGE,
				null, gamelist, gamelist[0]);

		String[] data = gameClient.GetGameData(gameData.getName(), (String) selectedValue);
		if (data.length == 13) {
			int[] sc = new int[13];
			int[] tmpsc = new int[13];
			for (int i = 0; i < 13; i++)
				sc[i] = Integer.parseInt(data[i]);
			turnCount = 0;
			cp.setScores(sc);
			for (int i = 0; i < 13; i++) {
				if (sc[i] >= 0) {
					cp.clearAndRefresh(i);
					tmpsc[i] = sc[i];
				} else
					tmpsc[i] = 0;
			}
			cp.refreshScoreAfterRoll(tmpsc);
			rollTime = 0;
			CAN_SELECT = true;
		}
	}

	public void loadGame(String str) {
		GameData data = null;
		try {
			data = (GameData) GameLogic.deserializeToObject(str);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Load game failure");
		}
		if (data != null) {
			gameData = data;
		}
	}

	private void saveGame() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		gameData.setDate(sdf.format(new Date()));
		try {
			String str = "";
			int[] ss = cp.getScores();
			for (int i = 0; i < ss.length; i++) {
				str += ss[i];
				if (i < ss.length - 1)
					str += ",";
			}
			gameClient.SaveGameData(gameData.getName(), gameData.getDate(), str);
			// gameClient.sendMessage("s" + GameLogic.serializeToString(gameData));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Save game failure");
		}
	}

	public void start() {
		if (status == NOT_START) {
			status = GAME_START;
			turnCount = 0;
			rp.clearAll();
			cp.clearAll();
			startOneTurn();
		}
	}

	public void roll() {
		int[] dices = rp.getNumbers();
		int[] scores = GameLogic.getScores(dices);
		cp.refreshScoreAfterRoll(scores);
		rollTime++;
		CAN_SELECT = true;
		if (rollTime == 3) {
			CAN_ROLL = false;
			tp.setHint("please select a score");
		} else {
			tp.setHint("please roll again or select a score");
		}
	}

	public void afterSelect(String buttonsName) {
		tp.setHint("select " + buttonsName);
		CAN_SELECT = false;
	
		turnCount++;
		if (isGameOver()) {
			gamOver();
		} else {
			startOneTurn();
		
		}
	}

	public void gamOver() {
		status = GAME_OVER;
		CAN_SELECT = false;
		CAN_ROLL = false;
		tp.setHint("Game over");
		int[] scores = cp.getScores();
		gameData.setScores(scores);
		int score = GameLogic.getTotalScore(scores);
		int n = JOptionPane.showConfirmDialog(null, "your score is " + score + ". Do you want a new game?", "Game over",
				JOptionPane.YES_NO_OPTION);
		if (n == 0) {
			status = NOT_START;
			start();
		} else {
			System.exit(1);
		}
	}

	public void startOneTurn() {
		rollTime = 0;
		playerPlay();
	}

	private void playerPlay() {
		CAN_ROLL = true;
		rollTime = 0;
		tp.setHint("please roll dices");
		tp.setPlayerName(gameData.getName());
		rp.clearAll();
	}

	public boolean isGameOver() {
		return turnCount == 13;
	}

	public int getStatus() {
		return status;
	}

	public void addClientListener(GameClient gameClient) {
		this.gameClient = gameClient;
	}
}
