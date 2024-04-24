package inf101.chess.logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf101.chess.model.IChessBoard;
import inf101.chess.pieces.King;
import inf101.chess.pieces.Knight;
import inf101.chess.pieces.Rook;
import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.chess.model.ChessBoard;
import inf101.chess.model.GameState;

public class GameStateDeterminerTest {
	GameStateDeterminer determiner;
	IChessBoard board;
	King king;
	
	@BeforeEach
	public void setup() {
		IChessBoard board = new ChessBoard(8, 8);
		this.board = board;
		King king = new King('B', new Location(0, 4));
		this.king = king;
		board.setPiece(king.getLocation(), king);
		GameStateDeterminer determiner = new GameStateDeterminer(board, 'B');
		this.determiner = determiner;
	}
	
	
	@Test
	public void determineCheckTest() {
		// Black king is already initialized, should be found at 0, 4
		// Initialize a white rook in the same column:
		Rook attacker = new Rook('W', new Location(5, 4));
		board.setPiece(attacker.getLocation(), attacker);
		
		assertTrue(determiner.determineCheck() == GameState.CHECK);
		
		// Move the rook one column to the side, should no longer be check:
		board.movePiece(attacker.getLocation(), new Location(5, 5));
		
		assertFalse(determiner.determineCheck() == GameState.CHECK);
		
	}
	
	@Test
	public void kingInDangerTest() {
		// Initialize a Knight two to the left from the king
		Knight attacker = new Knight('W', new Location(0, 6));
		board.setPiece(attacker.getLocation(), attacker);
		
		// Create a move that moves the knight into a location to attack the king
		ChessMove move = new ChessMove(attacker.getLocation(), 
								new Location(2, 5), attacker);
		
		assertTrue(determiner.kingInDangerAfterMove(move));
	}
}
