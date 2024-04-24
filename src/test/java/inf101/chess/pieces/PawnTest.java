package inf101.chess.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import inf101.grid.Location;
import inf101.chess.model.IChessBoard;
import inf101.chess.model.ChessBoard;

public class PawnTest {
	
	@Test 
	public void updatePosition() {
		Pawn pawn = new Pawn('W', new Location(0, 0));
		pawn.movePiece(new Location(1, 0));
		assertEquals(pawn.getLocation(), new Location(1, 0));
	}
	
	@Test
	public void blockedByOwnPiece() {
		// Initialize the pawn to move
		Pawn movingPawn = new Pawn('W', new Location(2, 0));
		Pawn blockingPawn = new Pawn('W', new Location(1, 0));
		IChessBoard board = new ChessBoard(3, 3);
		board.setPiece(movingPawn.getLocation(), movingPawn);
		board.setPiece(blockingPawn.getLocation(), blockingPawn);
		
		// Pawn should have no legal moves when our pawn is directly
		// in front:
		assertEquals(movingPawn.getPossibleMoves(board).size(), 0);
	}
	
	@Test
	public void blockedByOpposingPiece() {
		// Initialize the pawn to move
		Pawn movingPawn = new Pawn('W', new Location(2, 0));
		Pawn blockingPawn = new Pawn('B', new Location(1, 0));
		IChessBoard board = new ChessBoard(3, 3);
		board.setPiece(movingPawn.getLocation(), movingPawn);
		board.setPiece(blockingPawn.getLocation(), blockingPawn);
		
		// Pawn should have no legal moves when opposing pawn is directly
		// in front:
		assertEquals(movingPawn.getPossibleMoves(board).size(), 0);
		
	}
	
	@Test
	public void canCapture() {
		// Initialize the pawn to move
		Pawn movingPawn = new Pawn('W', new Location(3, 1));
		Pawn blockingPawn = new Pawn('B', new Location(2, 1));
		// Initalize two capturable pawns
		Pawn firstCapture = new Pawn('B', new Location(2, 0));
		Pawn secondCapture = new Pawn('B', new Location(2, 2));
		
		IChessBoard board = new ChessBoard(4, 4);
		board.setPiece(movingPawn.getLocation(), movingPawn);
		board.setPiece(blockingPawn.getLocation(), blockingPawn);
		board.setPiece(firstCapture.getLocation(), firstCapture);
		board.setPiece(secondCapture.getLocation(), secondCapture);
		
		
		List<Location> expectedLocations = new ArrayList<>(Arrays.asList(
				new Location(2, 2), new Location(2, 0)));

		// Pawn should only have the legal moves of the two captures
		assertEquals(movingPawn.getPossibleMoves(board), expectedLocations);
	}
	
	@Test
	public void testStartingMove() {
		// Initialize the pawn to move
		Pawn movingPawn = new Pawn('W', new Location(2, 0));
		IChessBoard board = new ChessBoard(3, 3);
		board.setPiece(movingPawn.getLocation(), movingPawn);
		
		// The pawn has not moved, so moving 2 up should be in it's moves.
		assertTrue(movingPawn.getPossibleMoves(board).contains(new Location(0, 0)));
				
		// Now "move" the pawn, and the starting move shouldn't be possible.
		movingPawn.moved();
		
		assertFalse(movingPawn.getPossibleMoves(board).contains(new Location(0, 0)));
	}
	
}
