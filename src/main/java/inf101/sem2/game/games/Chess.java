package inf101.sem2.game.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import inf101.grid.ChessMove;
import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.game.ChessMoveGame;
import inf101.sem2.game.Game;
import inf101.sem2.game.GameBoard;
import inf101.sem2.game.Graphics;
import inf101.sem2.game.MultiMoveGame;
import inf101.sem2.player.Player;

public class Chess extends ChessMoveGame {
	
	/**
	 * Initializing chess much in the same way as Othello.
	 * Credit for this part of the code goes to the creator of Othello.java
	 */
	public Chess(Graphics graphics, Player p1, Player p2) {
		super(new GameBoard(8, 8), graphics);
		addPlayer(p1);
		addPlayer(p2);
		initializeBoard();
	}
	
	public Chess(Graphics graphics, Iterable<Player> players) {
		super(new GameBoard(8, 8), graphics, players);
		initializeBoard();
	}
	
	private void initializeBoard() {
		// TODO: Initialize chess pieces
		// Currently dummy characters to represent colour
		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < board.numColumns(); col++) {
				board.set(new Location(row, col), getCurrentPlayer());
			}
		}
		
		players.nextPlayer();
		
		for (int row = board.numRows() - 1; row > board.numRows() - 3; row--) {
			for (int col = 0; col < board.numColumns(); col++) {
				board.set(new Location(row, col), getCurrentPlayer());
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
	 * Inspired by the equivalent method in BlobWars
	 * TODO: Call pieces individual methods
	 */
	@Override
    public List<ChessMove> getPossibleMoves() {
        List<ChessMove> possibleMoves = new ArrayList<>();
        for (Piece piece: board.)

        for (Location to : board.locations()) {
            if (!board.canPlace(to))
                continue;
   
            Location from = null;
            for (Location neighbour : board.getNeighborhood(to)) {
                if (Objects.equals(board.get(neighbour), getCurrentPlayer())) {
                    from = neighbour;
                    break;
                }
            }
            if (from != null) {
                oneMoveLocations.add(from);
                possibleMoves.add(new ChessMove(from, to));
                continue;
            }
        }
        return possibleMoves;
	}
	
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
	public boolean isWinner(Player player) {
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
