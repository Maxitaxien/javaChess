package inf101.chess.logic;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import inf101.chess.model.IChessBoard;
import inf101.chess.pieces.Bishop;
import inf101.chess.pieces.King;
import inf101.chess.pieces.Pawn;
import inf101.chess.pieces.Rook;
import inf101.grid.Location;
import inf101.chess.model.ChessBoard;

public class CastleRuleTest {
	@Test
	public void testKingSideCastle() {
		IChessBoard board = new ChessBoard(8, 8);
		King king = new King('W', new Location(7, 4));
		Rook rook = new Rook('W', new Location(7, 7));
		
		board.setPiece(king.getLocation(), king);
		board.setPiece(rook.getLocation(), rook);
		
		
		CastleRule rule = new CastleRule(board, king);
		List<Location> possibleCastles = rule.getLegalCastles();
		// Kingside castles should be legal
		assertTrue(possibleCastles.contains(new Location(7, 6)));
		
		// Queenside castles should not be legal
		assertFalse(possibleCastles.contains(new Location(7, 2)));
	}
	
	@Test
	public void testQueenSideCastle() {
		IChessBoard board = new ChessBoard(8, 8);
		King king = new King('B', new Location(0, 4));
		Rook rook = new Rook('B', new Location(0, 0));
		
		board.setPiece(king.getLocation(), king);
		board.setPiece(rook.getLocation(), rook);
		
		
		CastleRule rule = new CastleRule(board, king);
		List<Location> possibleCastles = rule.getLegalCastles();
		
		// Queenside castles should be legal
		assertTrue(possibleCastles.contains(new Location(0, 2)));
		
		// Kingside castles should not be legal
		assertFalse(possibleCastles.contains(new Location(0, 6)));
	}
	
	@Test
	public void testAttackedSquares() {
		IChessBoard board = new ChessBoard(8, 8);
		King king = new King('W', new Location(7, 4));
		Rook rookKingside = new Rook('W', new Location(7, 7));
		Rook rookQueenside = new Rook('W', new Location(7, 0));
		
		// First testing that both castles are legal:
		board.setPiece(king.getLocation(), king);
		board.setPiece(rookKingside.getLocation(), rookKingside);
		board.setPiece(rookQueenside.getLocation(), rookQueenside);
		
		CastleRule rule = new CastleRule(board, king);
		List<Location> possibleCastles = rule.getLegalCastles();
		
		assertTrue(possibleCastles.size() == 2);
		
		// Initialize a bishop in front of the king that attacks squares in between both castles
		Bishop attacker = new Bishop('B', new Location(6, 4));
		board.setPiece(attacker.getLocation(), attacker);
		
		rule = new CastleRule(board, king);
		possibleCastles = rule.getLegalCastles();
		
		assertTrue(possibleCastles.size() == 0);
	}
	
	@Test
	public void testSquaresEmpty() {
		IChessBoard board = new ChessBoard(8, 8);
		King king = new King('W', new Location(7, 4));
		Rook rookKingside = new Rook('W', new Location(7, 7));
		Rook rookQueenside = new Rook('W', new Location(7, 0));
		
		// First testing that both castles are legal:
		board.setPiece(king.getLocation(), king);
		board.setPiece(rookKingside.getLocation(), rookKingside);
		board.setPiece(rookQueenside.getLocation(), rookQueenside);
		
		CastleRule rule = new CastleRule(board, king);
		List<Location> possibleCastles = rule.getLegalCastles();
		
		assertTrue(possibleCastles.size() == 2);
		
		// Initialize a blockading piece at each side,
		// one of the same colour and one of the opposite 
		Pawn firstBlocker = new Pawn('W', new Location(7, 5));
		Pawn secondBlocker = new Pawn('B', new Location(7, 3));
		board.setPiece(firstBlocker.getLocation(), firstBlocker);
		board.setPiece(secondBlocker.getLocation(), secondBlocker);
		
		rule = new CastleRule(board, king);
		possibleCastles = rule.getLegalCastles();
		
		// Now no castles should be allowed
		assertTrue(possibleCastles.size() == 0);
	}
	
	@Test
	public void testKingMoved() {
		IChessBoard board = new ChessBoard(8, 8);
		King king = new King('B', new Location(0, 4));
		Rook rook = new Rook('B', new Location(0, 7));
		board.setPiece(king.getLocation(), king);
		board.setPiece(rook.getLocation(), rook);
		
		// "Move" the king
		king.moved();
		
		// Castles should now not be possible
		CastleRule rule = new CastleRule(board, king);
		List<Location> possibleCastles = rule.getLegalCastles();
		
		assertTrue(possibleCastles.size() == 0);
	}
	
	@Test
	public void testRookMoved() {
		IChessBoard board = new ChessBoard(8, 8);
		King king = new King('B', new Location(0, 4));
		Rook rook = new Rook('B', new Location(0, 0));
		board.setPiece(king.getLocation(), king);
		board.setPiece(rook.getLocation(), rook);
		
		// "Move" the rook
		rook.moved();
		
		// Castles should now not be possible
		CastleRule rule = new CastleRule(board, king);
		List<Location> possibleCastles = rule.getLegalCastles();
		
		assertTrue(possibleCastles.size() == 0);
	}
}
