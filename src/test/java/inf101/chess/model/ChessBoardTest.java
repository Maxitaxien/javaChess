package inf101.chess.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf101.chess.model.ChessBoard;
import inf101.chess.pieces.Pawn;
import inf101.chess.player.ChessPlayer;
import inf101.chess.player.ai.DumbChessPlayer;
import inf101.grid.Location;

/*
 * Inspired by the tests used in GameBoardTest.
 */
class ChessBoardTest {
	
	ChessBoard board;
	ChessPlayer p1;
	ChessPlayer p2;

	@BeforeEach
	public void setUp() {
		board = new ChessBoard(8, 8);
		p1 = new DumbChessPlayer('W');
		p2 = new DumbChessPlayer('B');
	}
	
	@Test
	public void testSetPiece() {
		Location loc = new Location(2, 3);
		board.setPiece(loc, new Pawn(p1.getSymbol(), loc));
		assertEquals(p1.getSymbol(), board.get(loc).getColour());
		board.setPiece(loc, new Pawn(p2.getSymbol(), loc));
		assertEquals(p2.getSymbol(), board.get(loc).getColour());
	}
	
	@Test
	public void testCanPlace() {
		Location loc = new Location(2, 3);
		assertTrue(board.squareEmpty(loc));
		board.setPiece(loc, new Pawn('W', loc));
		assertFalse(board.squareEmpty(loc));
	}


	
	@Test
	public void testMovePiece() {
		Location initialLoc = new Location(6, 6);
		Location moveToLoc = new Location(5, 6);
		
		Pawn pieceToMove = new Pawn('B', initialLoc);
		board.setPiece(initialLoc,  pieceToMove);
		board.movePiece(initialLoc, moveToLoc);
		assertEquals(board.get(initialLoc), null);
		assertEquals(board.get(moveToLoc), pieceToMove);
	}
	
	@Test
	public void testInitialize() {
		board.initializeBoard();
		// Check that pieces were put at the correct locations		
		List<Integer> rowsToFill = Arrays.asList(0, 1, 6, 7);
		for (int row : rowsToFill) {
			for (int col = 0; col < board.numColumns(); col++) {
				assertTrue(board.get(new Location(row, col)) != null);
			}
		}
	}
	
	@Test
	public void testClear() {
		board.initializeBoard();
		board.clearBoard();
		
		for (Location loc : board.locations()) {
			assertTrue(board.get(loc) == null);
		}
	}

	
	@Test
	public void testCopy() {
		IChessBoard newBoard = board.copy();
		for(Location loc : board.locations()) {
			assertEquals(board.get(loc), newBoard.get(loc));
		}
	}


}
