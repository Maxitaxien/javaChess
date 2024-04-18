package inf101.chess.logic;

import java.util.List;

import inf101.chess.pieces.Piece;
import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;
import inf101.sem2.game.GameState;

public class GameStateDeterminer {
	private ChessBoard board;
	private char currentPlayerSymbol;

	public GameStateDeterminer(ChessBoard board, char currentPlayerSymbol) {
		this.board = board;
		this.currentPlayerSymbol = currentPlayerSymbol;
	}
	
	public GameState determineCheck() {
		Piece king = findKing(currentPlayerSymbol);
		Location kingLoc = king.getLocation();
		for (Location loc : board.locations()) {
			Piece piece = board.get(loc);
			if (piece != null) {
				if (piece.getColour() != currentPlayerSymbol) {
					List<Location> pieceMoves = piece.getPossibleMoves(board);
					if (pieceMoves.contains(kingLoc)) {
						return GameState.CHECK;
					}
				}
			}
		}
		return GameState.ACTIVE;
	}
	
	
	private Piece findKing(char playerSymbol) {
	    for (Location loc: board.locations()) {
	        Piece piece = board.get(loc);
	        if (piece != null && piece.getColour() == playerSymbol && piece.getSymbol() == 'K') {
	            return piece;
	        }
	    }
	    // Log an error or throw an exception when the king is not found
	    System.err.println("King not found for player " + currentPlayerSymbol);
	    System.err.println(board);
	    return null;
	}
}
