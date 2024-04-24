package inf101.chess.logic;
import inf101.chess.model.IChessBoard;
import inf101.chess.pieces.Pawn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import inf101.chess.logic.DirectionalCalculator;
import inf101.chess.model.ChessBoard;
import inf101.grid.Location;

public class DirectionalCalculationTest {
	@Test
	public void cardinalTest() {
		IChessBoard board = new ChessBoard(4, 4);
		Location pieceLocation = new Location(2, 2);
		DirectionalCalculator calculator = new DirectionalCalculator();
			
		List<Location> expectedLocations = new ArrayList<>(Arrays.asList(
										new Location(1, 2), new Location(0, 2), 
										new Location(3, 2), new Location(2, 1), 
										new Location(2, 0), new Location(2, 3)));
			
		List<Location> trueLocations = calculator.calculateCardinal(pieceLocation, board, 'W');
		for (Location loc : expectedLocations) {
			assertTrue(trueLocations.contains(loc));
		}
	}
	
	
	@Test
	public void diagonalTest() {
		IChessBoard board = new ChessBoard(4, 4);
		Location pieceLocation = new Location(2, 2);
		DirectionalCalculator calculator = new DirectionalCalculator();
		
		List<Location> expectedLocations = new ArrayList<>(Arrays.asList(
										new Location(3, 3), new Location(1, 1), 
										new Location(0, 0), new Location(3, 1), 
										new Location(1, 3)));
		
		List<Location> trueLocations = calculator.calculateDiagonal(pieceLocation, board, 'W');
		for (Location loc : expectedLocations) {
			assertTrue(trueLocations.contains(loc));
		}
	}
	
	@Test
	public void cardinalCollisionTest() {
		IChessBoard board = new ChessBoard(4, 4);
		Location pieceLocation = new Location(2, 2);
		
		// Surround the piece to check that it now cannot move cardinally
		board.setPiece(new Location(2, 3), new Pawn('W', new Location(2, 3)));
		board.setPiece(new Location(2, 1), new Pawn('W', new Location(2, 1)));
		board.setPiece(new Location(3, 2), new Pawn('W', new Location(3, 2)));
		board.setPiece(new Location(1, 2), new Pawn('W', new Location(1, 2)));
		
		DirectionalCalculator calculator = new DirectionalCalculator();
		List<Location> legalMoves = calculator.calculateCardinal(pieceLocation,  board,  'W');
		assertEquals(legalMoves.size(), 0);
		
	}
	
	@Test
	public void diagonalCollisionTest() {
		IChessBoard board = new ChessBoard(4, 4);
		Location pieceLocation = new Location(2, 2);
		
		// Surround the piece to check that it now cannot move diagonally
		board.setPiece(new Location(3, 3), new Pawn('W', new Location(3, 3)));
		board.setPiece(new Location(1, 1), new Pawn('W', new Location(1, 1)));
		board.setPiece(new Location(1, 3), new Pawn('W', new Location(1, 3)));
		board.setPiece(new Location(3, 1), new Pawn('W', new Location(3, 1)));
		
		DirectionalCalculator calculator = new DirectionalCalculator();
		List<Location> legalMoves = calculator.calculateDiagonal(pieceLocation,  board,  'W');
		assertEquals(legalMoves.size(), 0);
		
	}
}
