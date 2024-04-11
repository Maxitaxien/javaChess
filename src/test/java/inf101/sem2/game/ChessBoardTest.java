package inf101.sem2.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf101.chess.pieces.Pawn;
import inf101.grid.Location;
import inf101.sem2.player.ChessPlayer;
import inf101.sem2.player.ai.DumbChessPlayer;

/*
 * Inspired by the tests used in GameBoardTest.
 */
class ChessBoardTest {
	
	ChessBoard board;
	ChessPlayer p1;
	ChessPlayer p2;

	@BeforeEach
	void setUp() {
		board = new ChessBoard(4, 7);
		p1 = new DumbChessPlayer('W');
		p2 = new DumbChessPlayer('B');
	}
	
	@Test
	void testGameBoard() {
		assertEquals(4, board.numRows());
		assertEquals(7, board.numColumns());
	}
	
	@Test
	void testSetPiece() {
		Location loc = new Location(2, 3);
		board.setPiece(loc, new Pawn(p1.getSymbol(), loc));
		assertEquals(p1.getSymbol(), board.get(loc).getColour());
		board.setPiece(loc, new Pawn(p2.getSymbol(), loc));
		assertEquals(p1.getSymbol(), board.get(loc).getColour());
	}

	@Test
	void testCanPlace() {
		Location loc = new Location(2, 3);
		assertTrue(board.isEmpty(loc));
		board.setPiece(loc, new Pawn('W', loc));
		assertFalse(board.isEmpty(loc));
	}

	@Test
	void testCountNumInRow() {
		assertEquals(0, board.countNumInRow(p1.getSymbol()));
		board.setPiece(new Location(1, 1), new Pawn('W', new Location(1, 1)));
		assertEquals(1, board.countNumInRow(p1.getSymbol()));
		board.setPiece(new Location(2, 2), new Pawn('W', new Location(2, 2)));
		assertEquals(2, board.countNumInRow(p1.getSymbol()));
		board.setPiece(new Location(3, 1), new Pawn('W', new Location(3, 1)));
		assertEquals(2, board.countNumInRow(p1.getSymbol()));
		board.setPiece(new Location(2, 1), new Pawn('B', new Location(2, 1)));
		assertEquals(2, board.countNumInRow(p1.getSymbol()));
		assertEquals(1, board.countNumInRow(p2.getSymbol()));
	}

	@Test
	void testIsFull() {
		assertFalse(board.isFull());
		board.setPiece(new Location(2, 2), new Pawn('W', new Location(2, 2)));
		assertFalse(board.isFull());
		Pawn pawn = new Pawn('W', new Location(0, 0));
		
		board.fill(pawn);
		assertTrue(board.isFull());
	}

	@Test
	void testCopy() {
		GameBoard newBoard = board.copy();
		for(Location loc : board.locations()) {
			assertEquals(board.get(loc), newBoard.get(loc));
		}
		assertEquals(board.numRows(), newBoard.numRows());
		assertEquals(board.numColumns(), newBoard.numColumns());
	}

	@Test
	void testToString() {
		int numP1 = 0;
		for(Location loc : board.locations()) {
			int r = (int) (3 * Math.random());

			switch (r) {
				case 1 -> {
					board.setPiece(loc, p1);
					numP1++;
				}
				case 2 -> board.setPiece(loc, p2);
			}
		}
		String s = board.toString();
		int count = 0;
		for(char c : s.toCharArray()) {
			if(c == p1.getSymbol()) {
				count++;
			}
		}
		assertEquals(numP1, count);
	}


}
