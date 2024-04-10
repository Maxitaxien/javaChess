package inf101.sem2.game;

import java.util.Objects;

import inf101.chess.pieces.Piece;
import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.player.Player;

/**
 * A class which is essentially a modification of
 * MultiMoveGame to work with chess. 
 * Most of the code is therefore similar.
 */
public abstract class ChessMoveGame extends Game<ChessMove>{

	public ChessMoveGame(GameBoard board, Graphics graphics) {
		super(board, graphics);
	}
	
	public ChessMoveGame(GameBoard board, Graphics graphics, Iterable<Player> players) {
		super(board, graphics, players);
	}
	
	/**
	 * Checks if the given move is valid. As this is calculated differently
	 * for each piece, this is calculated in the Piece classes.
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
		if (!Objects.equals(board.get(from), getCurrentPlayer()))
			return false;
		
		// Check that the move is legal for the piece (that it can move to this square)
		if (!toMove.getLegalMoves(board).contains(move.getTo())) {
			return false;
		}

		
		Location to = move.getTo();
		if (!board.canPlace(to))
			return false;
		
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
	public void makeNormalMove(ChessMove move) {
		if (!validMove(move))
			throw new IllegalArgumentException("Cannot make move:\n" + move);

		board.movePiece(move.getFrom(), move.getTo());
	}
	
	// TODO: Determine if this is even needed.
	public void makeCapture(ChessMove move) {
		if (!validMove(move))
			throw new IllegalArgumentException("Cannot make move:\n" + move);

		board.movePiece(move.getFrom(), move.getTo());
		// Adapt this to work with individual pieces
		board.swap(move.getTo(), getCurrentPlayer());
	}
	
	


}
