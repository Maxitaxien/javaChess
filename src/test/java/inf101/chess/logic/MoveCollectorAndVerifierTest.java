package inf101.chess.logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf101.chess.model.ChessBoard;
import inf101.chess.model.IChessBoard;
import inf101.chess.pieces.Bishop;
import inf101.chess.pieces.King;
import inf101.chess.pieces.Knight;
import inf101.chess.pieces.Pawn;
import inf101.chess.pieces.Piece;
import inf101.grid.ChessMove;
import inf101.grid.Location;

public class MoveCollectorAndVerifierTest {
	IChessBoard board;
	MoveCollectorAndVerifier verifier;
	@BeforeEach
	public void setup() {
		this.board = new ChessBoard(8, 8);
		// Setup the kings so that move collection doesn't throw errors:
		this.board.setPiece(new Location(0, 4), new King('B', new Location(0, 4)));
		this.board.setPiece(new Location(7, 4), new King('W', new Location(7, 4)));
		this.verifier = new MoveCollectorAndVerifier(board, 'B');
		
		
	}
	
	@Test
	public void collectPawnKnightKingMoves() {
		Pawn pawn = new Pawn('B', new Location(1, 1));
		Knight knight = new Knight('B', new Location(0, 1));
		board.setPiece(pawn.getLocation(), pawn);
		board.setPiece(knight.getLocation(), knight);
		
		List<ChessMove> possibleMoves = verifier.getMoves();
		// Because of comparison issues, let's convert every move
		// to their string representation
		List<String> possibleMovesS = new ArrayList<>();
		for (ChessMove move : possibleMoves) {
			possibleMovesS.add(move.toString());
		}
		
		// The pawn should have two moves:
		ChessMove firstPawnMove = new ChessMove(pawn.getLocation(), new Location(2, 1), pawn);
		ChessMove secondPawnMove = new ChessMove(pawn.getLocation(), new Location(3, 1), pawn);
		// The knight should have three moves:
		ChessMove firstKnightMove = new ChessMove(knight.getLocation(), new Location(2, 0), knight);
		ChessMove secondKnightMove = new ChessMove(knight.getLocation(), new Location(2, 2), knight);
		ChessMove thirdKnightMove = new ChessMove(knight.getLocation(), new Location(1, 3), knight);
		// We also need to add the king moves:
		Piece king = board.get(new Location(0, 4));
		ChessMove firstKingMove = new ChessMove(king.getLocation(), new Location(1, 4), king);
		ChessMove secondKingMove = new ChessMove(king.getLocation(), new Location(0, 5), king);
		ChessMove thirdKingMove = new ChessMove(king.getLocation(), new Location(0, 3), king);
		ChessMove fourthKingMove = new ChessMove(king.getLocation(), new Location(1, 5), king);
		ChessMove fifthKingMove = new ChessMove(king.getLocation(), new Location(1, 3), king);
		
		List<ChessMove> movesToTest = Arrays.asList(firstPawnMove, secondPawnMove, firstKnightMove,
									secondKnightMove, thirdKnightMove, firstKingMove, secondKingMove,
									thirdKingMove, fourthKingMove, fifthKingMove);
		
		List<String> movesToTestS = new ArrayList<>();
		for (ChessMove move : movesToTest) {
			movesToTestS.add(move.toString());
		}
		
		// First test that all the moves are in the legal moves collected:
		for (String move : movesToTestS) {
			assertTrue(possibleMovesS.contains(move));
		}
			
		// Then test that no other legal moves than these were collected:
		for (String move : possibleMovesS) {
			assertTrue(movesToTestS.contains(move));
		}
	}
	
	@Test
	public void collectsOwnMoves() {
		// Test that opposing pieces' moves are not collected
		// Check if our king's moves are collected but not the opponents
		Piece king = board.get(new Location(0, 4));
		ChessMove firstKingMove = new ChessMove(king.getLocation(), new Location(1, 4), king);
		ChessMove secondKingMove = new ChessMove(king.getLocation(), new Location(0, 5), king);
		ChessMove thirdKingMove = new ChessMove(king.getLocation(), new Location(0, 3), king);
		ChessMove fourthKingMove = new ChessMove(king.getLocation(), new Location(1, 5), king);
		ChessMove fifthKingMove = new ChessMove(king.getLocation(), new Location(1, 3), king);
		
		// Although there is also an opposing king on (7, 4), our king's moves
		// should now be the only ones that exist
		
		List<ChessMove> movesToTest = Arrays.asList(firstKingMove, secondKingMove, thirdKingMove,
													fourthKingMove, fifthKingMove);
		
		List<String> movesToTestS = new ArrayList<>();
		for (ChessMove move : movesToTest) {
			movesToTestS.add(move.toString());
		}
		
		List<ChessMove> possibleMoves = verifier.getMoves();
		// Because of comparison issues, let's convert every move
		// to their string representation
		List<String> possibleMovesS = new ArrayList<>();
		for (ChessMove move : possibleMoves) {
			possibleMovesS.add(move.toString());
		}
		
		// First test that all the moves are in the legal moves collected:
		for (String move : movesToTestS) {
			assertTrue(possibleMovesS.contains(move));
		}
					
		// Then test that no other legal moves than these were collected:
		for (String move : possibleMovesS) {
			assertTrue(movesToTestS.contains(move));
		}
	}
	
	@Test
	public void nullMoveCheck() {
		ChessMove move = null;
		assertFalse(verifier.validMove(move));
	}
	
	@Test
	public void ownPieceCheck() {
		// Test that a move onto another friendly piece is not valid
		// Note that we already have a king on (0, 4)
		Bishop friendlyBishop = new Bishop('B', new Location(0,5));
		board.setPiece(friendlyBishop.getLocation(), friendlyBishop);
		
		Piece ourKing = board.get(new Location(0, 4));
		ChessMove move = new ChessMove(ourKing.getLocation(), new Location(0, 5), ourKing);
		assertFalse(verifier.validMove(move));
	}
}
