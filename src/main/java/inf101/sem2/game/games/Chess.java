package inf101.sem2.game.games;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.sem2.game.ChessMoveGame;
import inf101.sem2.game.GameState;
import inf101.sem2.game.ChessBoard;
import inf101.sem2.game.ChessGraphics;
import inf101.sem2.player.ChessPlayer;
import inf101.chess.pieces.*;

public class Chess extends ChessMoveGame {
	
	/**
	 * Initializing chess much in the same way as Othello.
	 * Credit for this part of the code goes to the creator of Othello.java
	 */
	public Chess(ChessGraphics graphics, GameState state, ChessPlayer p1, ChessPlayer p2) {
		super(new ChessBoard(8, 8), graphics, state);
		addPlayer(p1);
		addPlayer(p2);
		initializeBoard();
	}
	
	public Chess(ChessGraphics graphics, GameState state, Iterable<ChessPlayer> players) {
		super(new ChessBoard(8, 8), graphics, state, players);
		initializeBoard();
	}
	
	private void initializeBoard() {
		int rows = board.numRows();
		int cols = board.numColumns();
		
		// White pawns
		for (int row = 1; row < 2; row++) {
			for (int col = 0; col < cols; col++) {
				Location loc = new Location(row, col);
				board.setPiece(loc, new Pawn('W', loc));
			}
		}
		
		// White rooks
		board.setPiece(new Location(0, 0), new Rook('W', new Location(0, 0)));
		board.setPiece(new Location(0, cols - 1), new Rook('W', new Location(0, cols - 1)));
		
		// White knights
		board.setPiece(new Location(0, 1), new Knight('W', new Location(0, 1)));
		board.setPiece(new Location(0, cols - 2), new Knight('W', new Location(0, cols - 2)));
		
		// White bishops
		board.setPiece(new Location(0, 2), new Bishop('W', new Location(0, 2)));
		board.setPiece(new Location(0, cols - 3), new Bishop('W', new Location(0, cols - 3)));
		
		// White queen and king
		board.setPiece(new Location(0, cols - 4), new Queen('W', new Location(0, cols - 4)));
		board.setPiece(new Location(0, 3), new King('W', new Location(0, 3)));
		
		
		players.nextPlayer();
		
		// Black pawns
		for (int row = rows - 2; row > rows - 3; row--) {
			for (int col = 0; col < cols; col++) {
				Location loc = new Location(row, col);
				board.setPiece(loc, new Pawn('B', loc));
			}
		}
		
		// Black rooks
		board.setPiece(new Location(rows - 1, 0), new Rook('B', new Location(rows - 1, 0)));
		board.setPiece(new Location(rows - 1, cols - 1), new Rook('B', new Location(rows - 1, cols - 1)));
				
		// Black knights
		board.setPiece(new Location(rows - 1, 1), new Knight('B', new Location(rows -1, 1)));
		board.setPiece(new Location(rows - 1, cols - 2), new Knight('B', new Location(rows - 1, cols - 2)));
				
		// Black bishops
		board.setPiece(new Location(rows -1, 2), new Bishop('B', new Location(rows - 1, 2)));
		board.setPiece(new Location(rows - 1, cols - 3), new Bishop('B', new Location(rows - 1, cols - 3)));
				
		// White queen and king
		board.setPiece(new Location(rows - 1, cols - 4), new Queen('B', new Location(rows - 1, cols - 4)));
		board.setPiece(new Location(rows - 1, 3), new King('B', new Location(rows - 1, 3)));
				
				
		
		players.nextPlayer();
	}

	// TODO: Make a move be a capture or a normal move based on an attribute in the ChessMove
	// Handle the different cases differently.
	public void makeMove(ChessMove move, GameState state) {		
		super.makeMove(move, state);		
		displayBoard();
	}
	
	/**
	 * PUT THIS IN OVERRIDDEN
	 * Look through grid. When we find a piece, calculate it's 
	 * legal moves. 
	 * TODO: Make this behave differently when in check
	 */
	@Override
    public List<ChessMove> getPossibleMoves() {
		List<ChessMove> possibleMoves = new ArrayList<>();
		if (state == GameState.ACTIVE) {
	        for (Location from: board.locations()) {
	        	if (board.get(from) != null) {
	        		Piece pieceToMove = board.get(from);
	        		// If the move is not made by the king, just add all the moves as normal:
	        		if (pieceToMove.getSymbol() != 'K') {
	        			List<Location> moveTo = pieceToMove.getPossibleMoves(board);
		        		
		        		for (Location to : moveTo) {
		        			ChessMove move = new ChessMove(from, to, pieceToMove);
		        			possibleMoves.add(move);
		        		}
	        		}
	        		// If the move is being made by the king, check if it is a castle and handle these differently
	        		// We can check this by seeing if the valid move moves the king more than one column
	        		else {
	        			List<Location> moveTo = pieceToMove.getPossibleMoves(board);
        			
	        			for (Location to : moveTo) {
	        				ChessMove move;
	        				if (from.col - to.col < -1) {
	        					// Indicates queenside castling, which means the rook is at four to the right
	        					// from the current king position
	        					Location oldRookLocation = new Location(to.row, from.col + 4);
	        					Location newRookLocation = new Location(to.row, from.col + 1);
	        					move = new ChessMove(from, to, pieceToMove, 
	        							oldRookLocation, newRookLocation);
	        					move.castled();
	        				}
	        				else if (from.col - to.col > 1) {
	        					// Indicates kingside castling, which means the rook is at three to the left
	        					// from the current king position
	        					Location oldRookLocation = new Location(to.row, from.col - 3);
	        					Location newRookLocation = new Location(to.row, from.col - 1);
	        					move = new ChessMove(from, to, pieceToMove, 
	        							oldRookLocation, newRookLocation);
	        					move.castled();
	        				}
	        				else {
	        					// Indicates a normal move
	        					move = new ChessMove(from, to, pieceToMove);
	        				}
	        				possibleMoves.add(move);
	        					
	        				}
        					
        				}
        			}
        		}	        		
	        	}
		// TODO: Copy board, for each move, make it.
		// Check if the king can be taken on the resulting board.
		// If so, the move is not legal. 
		// If no moves are found, set the state to CHECKMATE
		else if (state == GameState.CHECK) {
			;
		}
        return possibleMoves;
	}



	@Override
	public Chess copy() {
		Chess newGame = new Chess(graphics, state, players);
		copyTo(newGame);
		return newGame;
		}

	@Override
	public boolean isWinner(ChessPlayer player) {
		// TODO 
		return false;
	}

	@Override
	public boolean gameOver() {
		// TODO Implement logic for checkmate
		// If gamestate = check and no legal moves
		return false;
	}
	
	/**
	 * Credit for this method once again goes to Othello.
	 * @param loc
	 * @return boolean indicating if piece belongs to current player
	 */
	public boolean isOpponent(Location loc) {
		if (!board.isOnBoard(loc))
			return false;
		if (board.get(loc) == getCurrentPlayer())
			return false;
		if (board.get(loc) == null)
			return false;
		return true;
	}

	@Override
	public String getName() {
		return "Chess";
	}

}
