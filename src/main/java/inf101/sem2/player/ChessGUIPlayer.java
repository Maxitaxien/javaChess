package inf101.sem2.player;

import javax.swing.JOptionPane;

import inf101.chess.pieces.Piece;
import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.sem2.GUI.ChessGUI;
import inf101.sem2.game.ChessGame;
import inf101.sem2.game.ChessMoveGame;
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
	public ChessMove getMove(ChessGame game) {
	    ChessGUI gui;
	    try {
	        gui = (ChessGUI) game.getGraphics();
	    } catch (Exception e1) {
	        throw new IllegalArgumentException("GuiPlayer can not play without a GUI");
	    }

	    game.displayBoard();

	    while (true) {
	    	System.out.println("Getting move.");
	        Location from = gui.getFrom();
	        Location to = gui.getTo();
	        
	        if (from == null || to == null) {
	        	continue;
	        }
	        
	        Piece piece = game.getGameBoard().get(from);
	        
	        ChessMove move = new ChessMove(from, to, piece);
	        
	        if (game.validMove(move)) {
	        	System.out.println("Valid move found.");
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
