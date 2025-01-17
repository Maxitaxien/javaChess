package inf101.chess.player;

import javax.swing.JOptionPane;

import inf101.chess.logic.MoveCollectorAndVerifier;
import inf101.chess.model.ChessGame;
import inf101.chess.view.ChessGUI;
import inf101.grid.ChessMove;
/**
 * This Player should be used if one wants input from GUI.
 * The game loop will stop when reaching an instance of GuiPlayer
 * and when a mouse click is detected the game loop will resume.
 *
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public class ChessGUIPlayer extends AbstractChessPlayer {

	public ChessGUIPlayer(char symbol, String name) {
		super(symbol, name);
	}

	public ChessGUIPlayer(char symbol) {
		super(symbol, readPlayerName(symbol));
	}

	@Override
	public ChessMove getMove(ChessGame game) {
	    ChessGUI gui;
	    try {
	        gui = (ChessGUI) game.getGraphics();
	    } catch (Exception e1) {
	        throw new IllegalArgumentException("GuiPlayer can not play without a GUI");
	    }

	    game.displayBoard();

	    while (true) {
	        ChessMove move = gui.getMove(game.getGameBoard());
	        MoveCollectorAndVerifier moveVerifier = new MoveCollectorAndVerifier(game.getGameBoard(), symbol);
	        
	        if (moveVerifier.validMove(move)) {
	        	
	            return move;
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
	

	/**
	 * Asks player to type in name in a GUI pop up
	 *
	 * @param symbol - The symbol representing this player
	 * @return the name chosen by the player
	 */
	public static String readPlayerName(char symbol) {
		String name = null;
		while (!AbstractChessPlayer.isValidName(name)) {
			name = JOptionPane.showInputDialog("Player " + symbol + ". Type in your name.");
		}
		return name;
	}
	
}
