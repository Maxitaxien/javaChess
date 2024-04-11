package inf101.sem2.player;

import javax.swing.JOptionPane;

import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.GUI.ChessGUI;
import inf101.sem2.GUI.GameGUI;
import inf101.sem2.game.ChessGame;
import inf101.sem2.game.ChessMoveGame;
import inf101.sem2.game.Game;
import inf101.sem2.game.MultiMoveGame;
import inf101.sem2.game.SingleMoveGame;

/**
 * This Player should be used if one wants input from GUI.
 * The game loop will stop when reaching an instance of GuiPlayer
 * and when a mouse click is detected the game loop will resume.
 *
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public class ChessGUIPlayer extends AbstractChessPlayer {

	public ChessGUIPlayer(char piece, String name) {
		super(piece, name);
	}

	public ChessGUIPlayer(char piece) {
		super(piece, readPlayerName(piece));
	}

	@Override
	public <T> T getMove(ChessGame<T> game) {
		ChessGUI gui;
		try {
			gui = (ChessGUI) game.getGraphics();
		} catch (Exception e1) {
			throw new IllegalArgumentException("GuiPlayer can not play without a GUI");
		}

		game.displayBoard();

		while (true) {
			Location loc = gui.getLocation();
			if (game.canPlace(loc)) {
				return (T) loc;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println("Sleep interrupted");
			}

			if (gui.wantRestart) {
				throw new RestartException();
			}

			if (gui.ended) {
				throw new GameEndedException();
			}
		}
	}

	private boolean hasValidMove(ChessMoveGame game, ChessMove move) {
		return game.validMove(move);
	}

	/**
	 * Asks player to type in name in a GUI pop up
	 *
	 * @param symbol - The symbol representing this player
	 * @return the name chosen by the player
	 */
	public static String readPlayerName(char symbol) {
		String name = null;
		while (!AbstractPlayer.isValidName(name)) {
			name = JOptionPane.showInputDialog("Player " + symbol + ". Type in your name.");
		}
		return name;
	}
}
