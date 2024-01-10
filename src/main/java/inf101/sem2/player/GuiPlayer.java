package inf101.sem2.player;

import javax.swing.JOptionPane;

import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.GUI.GameGUI;
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
public class GuiPlayer extends AbstractPlayer {

	public GuiPlayer(char piece, String name) {
		super(piece, name);
	}

	public GuiPlayer(char piece) {
		super(piece, readPlayerName(piece));
	}

	@Override
	public <T> T getMove(Game<T> game) {
		GameGUI gui;
		try {
			gui = (GameGUI) game.getGraphics();
		} catch (Exception e1) {
			throw new IllegalArgumentException("GuiPlayer can not play without a GUI");
		}

		game.displayBoard();

		while (true) {
			if (game instanceof SingleMoveGame) {
				Location loc = gui.getLocation();
				if (game.canPlace(loc)) {
					return (T) loc;
				}
			} else if (game instanceof MultiMoveGame) {
				MultiMoveGame mmgGame = (MultiMoveGame) game;
				Move move = gui.getMove();
				if (mmgGame.validMove(move)) {
					return (T) move;
				}
			} else {
				throw new UnsupportedOperationException("GuiPlayer does not support this game");
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

	private boolean hasValidMove(MultiMoveGame game, Move move) {
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
