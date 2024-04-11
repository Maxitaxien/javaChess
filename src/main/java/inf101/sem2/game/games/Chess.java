package inf101.sem2.game.games;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.sem2.game.ChessMoveGame;
import inf101.sem2.game.ChessBoard;
import inf101.sem2.game.ChessGraphics;
import inf101.sem2.game.Graphics;
import inf101.sem2.player.ChessPlayer;
import inf101.sem2.player.Player;
import inf101.chess.pieces.*;

public class Chess extends ChessMoveGame {
	
	/**
	 * Initializing chess much in the same way as Othello.
	 * Credit for this part of the code goes to the creator of Othello.java
	 */
	public Chess(ChessGraphics graphics, ChessPlayer p1, ChessPlayer p2) {
		super(new ChessBoard(8, 8), graphics);
		addPlayer(p1);
		addPlayer(p2);
		initializeBoard();
	}
	
	public Chess(ChessGraphics graphics, Iterable<ChessPlayer> players) {
		super(new ChessBoard(8, 8), graphics, players);
		initializeBoard();
	}
	
	private void initializeBoard() {
		// TODO: Initialize chess pieces
		// Currently dummy characters to represent colour
		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < board.numColumns(); col++) {
				Location loc = new Location(row, col);
				board.setPiece(loc, new Pawn('W', loc));
			}
		}
		
		players.nextPlayer();
		
		for (int row = board.numRows() - 1; row > board.numRows() - 3; row--) {
			for (int col = 0; col < board.numColumns(); col++) {
				Location loc = new Location(row, col);
				board.setPiece(loc, new Pawn('B', loc));
			}
		}
		
		players.nextPlayer();
	}
	
	// Partially inspired by the same method in BlobWars.
	public void makeMove(ChessMove move) {
		// TODO: 
		Location from = move.getFrom();
		Location to = move.getTo();
		char pieceSymbol = move.getPiece().getSymbol();
		
		if (!getPossibleLocations(from).contains(to))
            throw new IllegalArgumentException("Can't move piece that far.");
		
		// TODO: CHANGE THIS AND MAKE CAPTURE PART
		// Move piece to adjacent tile
        if (board.getNeighborhood(from).contains(to)) {
        	super.makeNormalMove(move);
            displayBoard();
        }
	}
	
	/**
	 * PUT THIS IN OVERRIDDEN
	 * Look through grid. When we find a piece, calculate it's 
	 * legal moves. 
	 * TODO: Make this behave differently when in check
	 */
	@Override
    public List<ChessMove> getPossibleMoves() {
        List<ChessMove> possibleMoves = new ArrayList<>();
        for (Location from: board.locations()) {
        	if (board.get(from) != null) {
        		List<Location> moveTo = board.get(from).getLegalMoves(board);
        		
        		for (Location to : moveTo) {
        			ChessMove move = new ChessMove(from, to, board.get(from));
        			possibleMoves.add(move);
        		}
        	}
        }
        return possibleMoves;
	}
	
	// TODO: Fix this method
	public List<Location> getPossibleLocations(Location loc) {
	    return board.getNeighborhood(loc, 1);
	}

	@Override
	public Chess copy() {
		Chess newGame = new Chess(graphics, players);
		copyTo(newGame);
		return newGame;
		}

	@Override
	public boolean isWinner(ChessPlayer player) {
		// TODO 
		return false;
	}

	@Override
	public boolean gameOver() {
		// TODO Implement logic for checkmate
		return false;
	}
	
	/**
	 * Credit for this method once again goes to Othello.
	 * @param loc
	 * @return boolean indicating if piece belongs to current player
	 */
	public boolean isOpponent(Location loc) {
		if (!board.isOnBoard(loc))
			return false;
		if (board.get(loc) == getCurrentPlayer())
			return false;
		if (board.get(loc) == null)
			return false;
		return true;
	}

	@Override
	public String getName() {
		return "Chess";
	}

}
