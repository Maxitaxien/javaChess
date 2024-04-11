package inf101.chess.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import inf101.grid.Location;

public class PawnTest {
	
	@Test 
	public void movePawn() {
		Pawn pawn = new Pawn('W', new Location(0, 0));
		pawn.movePiece(new Location(1, 0));
		assertEquals(pawn.getLocation(), new Location(1, 0));
	}
}
