package inf101.chess.pieces;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import inf101.chess.model.ChessBoard;
import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

public class KingTest {
	@Test
	public void normalKingMoves() {
		IChessBoard board = new ChessBoard(3, 3);
		King movingKing = new King('W', new Location(1, 1));
		
		// The king should be able to move to all locations around it:
		List<Location> toConfirm = Arrays.asList(new Location(0, 0), new Location(0, 1),
											new Location(0, 2), new Location(1, 0), new Location(1, 2),
											new Location(2, 0), new Location(2, 1), new Location(2, 2));
		
		List<Location> possibleMoves = movingKing.getPossibleMoves(board);
		for (Location loc : toConfirm) {
			assertTrue(possibleMoves.contains(loc));
		}
	}
	
	@Test
	public void kingCaptures() {
		IChessBoard board = new ChessBoard(3, 3);
		// Initialize the king to move
		King movingKing = new King('B', new Location(1, 1));
		// Initialize some enemy kings around the king.
		King toCapture1 = new King('W', new Location(1, 0));
		King toCapture2 = new King('W', new Location(0, 1));
		King toCapture3 = new King('W', new Location(2, 2));
		
		
		// Initialize some friendly kings (the king should not be able to move here)
		King notCaptureable1 = new King('B', new Location(0, 0));
		King notCaptureable2 = new King('B', new Location(2, 1));
		King notCaptureable3 = new King('B', new Location(1, 2));
		
		board.setPiece(movingKing.getLocation(), movingKing);
		board.setPiece(toCapture1.getLocation(), toCapture1);
		board.setPiece(toCapture2.getLocation(), toCapture2);		
		board.setPiece(toCapture3.getLocation(), toCapture3);
		board.setPiece(notCaptureable1.getLocation(), notCaptureable1);
		board.setPiece(notCaptureable2.getLocation(), notCaptureable2);
		board.setPiece(notCaptureable3.getLocation(), notCaptureable3);
		
		List<King> enemyKings = Arrays.asList(toCapture1, toCapture2, toCapture3);
		List<King> friendlyKings = Arrays.asList(notCaptureable1, notCaptureable2, notCaptureable3);
		
		List<Location> possibleMoves = movingKing.getPossibleMoves(board);
		
		// Check that the enemy kings can be taken
		for (King k : enemyKings) {
			assertTrue(possibleMoves.contains(k.getLocation()));
		}
		
		// Check that friendly kings cannot be taken
		for (King k : friendlyKings) {
			assertFalse(possibleMoves.contains(k.getLocation()));
		}
	}

}
