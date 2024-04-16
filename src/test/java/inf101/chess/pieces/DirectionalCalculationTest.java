package inf101.chess.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import inf101.chess.logic.DirectionalCalculator;
import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

public class DirectionalCalculationTest {
	@Test
	public void cardinalTest() {
		;
	}
	
	
	@Test
	public void diagonalTest() {
		ChessBoard board = new ChessBoard(4, 4);
		Location pieceLocation = new Location(2, 2);
		DirectionalCalculator calculator = new DirectionalCalculator();
		
		List<Location> expectedLocations = new ArrayList<>(Arrays.asList(
										new Location(1, 1), new Location(-1, -1), 
										new Location(-2, -2), new Location(1, -1), 
										new Location(-1, 1)));
		
		assertEquals(expectedLocations, calculator.calculateDiagonal(pieceLocation, board));
	}
	
	@Test
	public void diagonalUnevenBoardtest() {
		ChessBoard board = new ChessBoard(5, 6);
		Location pieceLocation = new Location(4, 4);
		DirectionalCalculator calculator = new DirectionalCalculator();
		
		List<Location> expectedLocations = new ArrayList<>(Arrays.asList(
										new Location(-1, -1), new Location(-2, -2), 
										new Location(-3, -3), new Location(-4, -4), 
										new Location(-1, 1)));
		
		assertEquals(expectedLocations, calculator.calculateDiagonal(pieceLocation, board));
	}
	
}
