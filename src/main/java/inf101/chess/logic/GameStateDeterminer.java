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
	
	public boolean kingInDangerAfterMove(ChessMove move) {
		IChessBoard testBoard = board.copy(true);
		testBoard.movePiece(move.getFrom(), move.getTo());

		
		Location kingLoc = findKing(testBoard, currentPlayerSymbol);

		for (Location loc: testBoard.locations()) {
			Piece piece = testBoard.get(loc);
			if (piece != null) {
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
	
	
	private Location findKing(IChessBoard boardToCheck, char playerSymbol) {
	    for (Location loc: boardToCheck.locations()) {
	        Piece piece = boardToCheck.get(loc);
	        if (piece != null && piece.getColour() == playerSymbol && piece.getSymbol() == 'K') {
	            return loc;
	        }
	    }
	    System.err.println("King not found for player " + currentPlayerSymbol);
	    return null;
	}
}
