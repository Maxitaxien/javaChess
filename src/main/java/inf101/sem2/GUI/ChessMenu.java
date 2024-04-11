package inf101.sem2.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import inf101.sem2.game.ChessGame;
import inf101.sem2.game.Game;
import inf101.sem2.game.games.BlobWars;
import inf101.sem2.game.games.ConnectFour;
import inf101.sem2.game.games.Othello;
import inf101.sem2.game.games.TicTacToe;
import inf101.sem2.game.games.Chess;
import inf101.sem2.player.ChessPlayer;
import inf101.sem2.player.GameEndedException;
import inf101.sem2.player.ChessGUIPlayer;
import inf101.sem2.player.Player;
import inf101.sem2.player.RestartException;
import inf101.sem2.player.ai.AlphaBetaChessPlayer;
import inf101.sem2.player.ai.AlphaBetaPlayer;
import inf101.sem2.player.ai.DumbPlayer;

/**
 * This class is a Game menu which lets you choose which game to play.
 *
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public class ChessMenu implements ActionListener {
	private final JButton startButton; // BUtton to start new Chess game
	private final JFrame frame;
	public Chess game;
	public ChessGUI gui;
	boolean start;

	public ChessMenu() {
		// make new main window for the game
		frame = new JFrame();
		frame.setTitle("Game menu");

		// make panel for game buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.setBorder(new EmptyBorder(10, 10, 30, 10));

		startButton = addButton(buttons, "Chess");

		// add buttons to the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(buttons);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Adds buttons with a fixed style and add it to the panel
	 * 
	 * @param buttons - The JPanel containing all the buttons.
	 * @param name    - The name to be displayed on the button.
	 * @return
	 */
	JButton addButton(JPanel buttons, String name) {
		JButton button = new JButton();
		button.setText(name);
		button.setFont(new Font("Arial", Font.PLAIN, 40));
		button.addActionListener(this);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setBorder(new RoundedBorder(20)); // 10 is the radius
		buttons.add(Box.createRigidArea(new Dimension(20, 20)));
		buttons.add(button);
		return button;
	}

	// this method is inherited from ActionListener and is the method
	// that gets called when buttons are clicked.
	// TODO: Add settings button below chess button
	@Override
	public void actionPerformed(ActionEvent e) {
		if (game != null) {
			System.err.println("Game is in progress, only one game at the time is possible.");
			return;
		}
		
		char s1 = 'W';
		char s2 = 'B';
				
		Iterable<ChessPlayer> players = getPlayers(s1, s2);
		ChessGUI graphics = new ChessGUI(players);
		
		if (e.getSource() == startButton) {
			game = new Chess(graphics, players);
		}
		if (game == null) {
			System.err.println("Button not recognized, no game created.");
		} else {
			gui = graphics;
			gui.ended = false;
			gui.wantRestart = false;
			start = true;
			graphics.display(game.getGameBoard());
		}
	}

	/**
	 * Generates a list of players based on user input
	 *
	 * @return an Iterable of 2 Players
	 */
	public static Iterable<ChessPlayer> getPlayers(char s1, char s2) {
		List<ChessPlayer> players = new ArrayList<>();
		// add player1
		players.add(new ChessGUIPlayer(s1));

		// add player2
		if (promptMultiplayer()) {
			players.add(new ChessGUIPlayer(s2));
		} else {
			// make AI
			int intelligence = promptIntelligence();
			players.add(new AlphaBetaChessPlayer(s2, intelligence));
		}
		return players;
	}
	
	/**
	 * Prompts player for level of intelligence in AI player
	 * Credit: ChatGPT
	 * @return the selected intelligence level (an integer between 1 and 8)
	 */
	private static int promptIntelligence() {
	    String userInput;
	    int intelligenceLevel = 0;

	    do {
	        userInput = JOptionPane.showInputDialog(
	                null,
	                "Select the intelligence level of the AI (1-8):",
	                "AI Intelligence Level",
	                JOptionPane.PLAIN_MESSAGE);

	        try {
	            intelligenceLevel = Integer.parseInt(userInput);
	            if (intelligenceLevel < 1 || intelligenceLevel > 8) {
	                JOptionPane.showMessageDialog(
	                        null,
	                        "Please enter a number between 1 and 8.",
	                        "Invalid Input",
	                        JOptionPane.ERROR_MESSAGE);
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(
	                    null,
	                    "Please enter a valid integer between 1 and 8.",
	                    "Invalid Input",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    } while (intelligenceLevel < 1 || intelligenceLevel > 8);

	    return intelligenceLevel;
	}

	/**
	 * Helper method that prompts for multiplayer or not
	 *
	 * @return true if multiplayer is selected, false otherwise
	 */
	private static boolean promptMultiplayer() {
		String[] possibilities = { "Multiplayer", "Single Player (against AI)" };
		String s = (String) JOptionPane.showInputDialog(
				null,
				"Welcome:\n" + "Select one or two players",
				"MKGame StartUp",
				JOptionPane.PLAIN_MESSAGE,
				null,
				possibilities,
				null);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			System.out.println("Received " + s);
		}
		return s.equals(possibilities[0]);
	}

	/**
	 * This method runs an infinite loop checking if a button
	 * has been clicked.
	 */
	public void run() {
		while (true) {
			if (gui != null && gui.ended) {
				game = null;
				start = false;
			}

			if (gui != null && gui.wantRestart) {
				game.restart();
				gui.wantRestart = false;
				start = true;
			}

			if (start) {
				try {
					System.err.println("Starting the game");
					start = false;
					game.run();
				} catch (RestartException e) {
					System.err.println("Restarting the game");
				} catch (GameEndedException e) {
					System.err.println("Game ended");
				}
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
}