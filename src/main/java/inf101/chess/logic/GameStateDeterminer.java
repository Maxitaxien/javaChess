package inf101.chess.logic;

import java.util.List;

import inf101.chess.model.GameState;
import inf101.chess.model.IChessBoard;
import inf101.chess.pieces.Piece;
import inf101.grid.ChessMove;
import inf101.grid.Location;

public class GameStateDeterminer {
	private IChessBoard board;
	private char currentPlayerSymbol;

	public GameStateDeterminer(IChessBoard board, char currentPlayerSymbol) {
		this.board = board;
		this.currentPlayerSymbol = currentPlayerSymbol;
	}
	
	/**
	 * Determines if the current position is a check.
	 * @return a GameState CHECK if check and ACTIVE if not
	 */
	public GameState determineCheck() {
		Location kingLoc = findKing(board, currentPlayerSymbol);
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
	
	/**
	 * Checks if a player's move will leave the current king in danger
	 * @param move the move to test for
	 * @return a boolean indicating if the king can be taken
	 */
	public boolean kingInDangerAfterMove(ChessMove move) {
		IChessBoard testBoard = board.copy();
		testBoard.movePiece(move.getFrom(), move.getTo());

		
		Location kingLoc = findKing(testBoard, currentPlayerSymbol);

		for (Location loc: testBoard.locations()) {
			Piece piece = testBoard.get(loc);
			if (piece != null && piece.getLocation() != kingLoc) {
				if (piece.getColour() != currentPlayerSymbol) {
					List<Location> pieceMoves = piece.getPossibleMoves(testBoard);
					if (pieceMoves.contains(kingLoc)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Helper method to find the current player's king.
	 * @param boardToCheck the board to look for the king on
	 * @param playerSymbol the current player colour
	 * @return location of the found king
	 */
	private Location findKing(IChessBoard board, char currentPlayerSymbol) {
	    for (Location loc: board.locations()) {
	        Piece piece = board.get(loc);
	        if (piece != null && piece.getColour() == currentPlayerSymbol
	        		&& piece.getSymbol() == 'K') {
	            return loc;
	        }
	    }
	    System.err.println("King not found for player " + currentPlayerSymbol);
	    return null;
	}
}
