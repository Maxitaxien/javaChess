package inf101.chess.main;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf101.chess.player.ChessPlayerList;
import inf101.chess.model.GameState;
import inf101.chess.pieces.Pawn;
import inf101.chess.pieces.Piece;
import inf101.chess.player.AbstractChessPlayer;
import inf101.chess.player.ai.DumbChessPlayer;
import inf101.chess.view.ChessDummyGraphics;
import inf101.chess.view.ChessGraphics;
import inf101.grid.ChessMove;
import inf101.grid.Location;

/**
 * Tests for moves specific to ChessGame,
 * although Chess extends ChessGame.
 */
public class ChessGameTest {
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
	
	@Test
	public void makeNormalMoveTest() {
		// Move a black pawn forwards, then check that the board position is updated
		Piece pieceToMove = chess.getGameBoard().get(new Location(1, 0));
		// Location to move to
		Location loc = new Location(2, 0);

		chess.makeMove(new ChessMove(pieceToMove.getLocation(), loc, pieceToMove));
		
		// Check that the board is updated
		assertTrue(chess.getGameBoard().get(loc).getSymbol() == pieceToMove.getSymbol());
	}
	
	@Test
	public void makeCaptureTest() {
		Piece pieceToMove = chess.getGameBoard().get(new Location(6, 0));
		// Location to move to
		Location loc = new Location(5, 1);
		// Place a black piece at location
		chess.getGameBoard().setPiece(loc, new Pawn('B', loc));

		// Make the capture
		chess.makeMove(new ChessMove(pieceToMove.getLocation(), loc, pieceToMove));
		
		// Check that the board is updated
		assertTrue(chess.getGameBoard().get(loc).getSymbol() == pieceToMove.getSymbol());
	}
	
	@Test
	public void isOpponentTest() {
		System.out.println(chess.getCurrentPlayerChar());
		// Note that the current player is 'W'
		assertTrue(chess.isOpponent(new Location(0, 0)));
		assertFalse(chess.isOpponent(new Location(7, 7)));
		
		// Swap to black, now the opposite should be true
		chess.nextPlayer();
		
		assertTrue(chess.isOpponent(new Location(7, 7)));
		assertFalse(chess.isOpponent(new Location(0, 0)));
		
		
		
	}
}
