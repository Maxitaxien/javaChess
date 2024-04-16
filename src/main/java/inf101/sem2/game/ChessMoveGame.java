package inf101.sem2.game;

import inf101.chess.pieces.King;
import inf101.chess.pieces.Piece;
import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.sem2.player.ChessPlayer;

/**
 * A class which is essentially a modification of
 * MultiMoveGame to work with chess. 
 * Most of the code is therefore similar.
 */
public abstract class ChessMoveGame extends ChessGame{

	public ChessMoveGame(ChessBoard board, ChessGraphics graphics, GameState state) {
		super(board, graphics, state);
	}
	
	public ChessMoveGame(ChessBoard board, ChessGraphics graphics, GameState state, Iterable<ChessPlayer> players) {
		super(board, graphics, state, players);
	}
	
	/**
	 * Checks if the given move is valid. As this is calculated differently
	 * for each piece, this is calculated in the Piece classes.
	 * TODO: Determine if move is a capture
	 * 
	 * @param move the move to make
	 * @return true if valid move. False if not.
	 */
	@Override
	public boolean validMove(ChessMove move) {
		if (move == null)
			return false;
		
		Piece toMove = move.getPiece();
		
		// Check that piece in first location is the players piece
		Location from = move.getFrom();
		if (!(board.getPlayerChar(from) == getCurrentPlayer().getSymbol())) {
			return false;
		}
		
		// Check that the move is legal for the piece (that it can move to this square)
		if (!toMove.getPossibleMoves(board).contains(move.getTo())) {
			return false;
		}
		
		// Handle castling. If a king has a legal move that moves more than one square to the left or right,
		// that move is a legal castle and should be handled accordingly.
		if (toMove instanceof King) {
			if (Math.abs(move.getFrom().col - move.getTo().col) > 1) {
				return true;
			}
		}

		return true;
	}
	
	/**
	 * This method performs a move for the current player and advances to next
	 * player. This method is called for moves that are not captures.
	 * 
	 * Credit to the makeMove function in BlobWars.
	 *
	 * @param loc
	 */
	public void makeMove(ChessMove move, GameState state) {
		if (!validMove(move))
			throw new IllegalArgumentException("Cannot make move:\n" + move);

		board.movePiece(move.getFrom(), move.getTo());
		if (move.isCastle()) {
			board.movePiece(move.getRookFrom(), move.getRookTo());
		}
	}
	


}
