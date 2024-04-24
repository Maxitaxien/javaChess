package inf101.chess.main;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf101.chess.model.GameState;
import inf101.chess.player.AbstractChessPlayer;
import inf101.chess.player.ChessPlayerList;
import inf101.chess.player.ai.DumbChessPlayer;
import inf101.chess.view.ChessDummyGraphics;
import inf101.chess.view.ChessGraphics;
import inf101.grid.ChessMove;
import inf101.grid.Location;

/**
 * Test methods specific to the Chess class
 * Most of these are tested through practical testing,
 * so there is only one test here.
 * I found that it was hard to test a game loop
 * through unit tests.
 */
public class ChessTest {
	Chess chess;
		
	@BeforeEach
	public void setup() {
		AbstractChessPlayer p1 = new DumbChessPlayer('W');
		AbstractChessPlayer p2 = new DumbChessPlayer('B');
		ChessPlayerList playerList = new ChessPlayerList(Arrays.asList(p1, p2));
		ChessGraphics dummyGraphics = new ChessDummyGraphics();
			
		this.chess = new Chess(dummyGraphics, GameState.ACTIVE, playerList);
		this.chess.getGameBoard().initializeBoard();
	}
	

	/*
	 * Test that the game is restarted correctly and re-initialized
	 */
	@Test
	public void testRestart() {
		// make a random move
		Location moveFrom = new Location(0, 1);
		Location moveTo = new Location(2, 2);
		chess.makeMove(new ChessMove(moveFrom, moveTo, 
									chess.getGameBoard().get(moveFrom)));
		
		// The piece should now be at the position:
		assertTrue(chess.getGameBoard().get(moveTo) != null);
		
		// change player to 'B'
		chess.nextPlayer();
		
		chess.restart();
		
		
		// now player should be 'W' and there should be nothing at moveTo
		assertTrue(chess.getGameBoard().get(moveTo) == null);
		assertTrue(chess.getCurrentPlayerChar() == 'W');
	}

}
