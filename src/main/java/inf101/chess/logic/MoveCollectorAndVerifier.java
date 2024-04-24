package inf101.chess.logic;

import java.util.ArrayList;
import java.util.List;

import inf101.chess.model.IChessBoard;
import inf101.chess.pieces.Piece;
import inf101.grid.ChessMove;
import inf101.grid.Location;

public class MoveCollectorAndVerifier {
	private IChessBoard board;
	char currentPlayerChar;
	
	public MoveCollectorAndVerifier(IChessBoard board, char currentPlayerChar) {
		this.board = board;
		this.currentPlayerChar = currentPlayerChar;
	}

	/**
	 * Checks if the given move is valid. 
	 * 
	 * @param move the move to make
	 * @return true if valid move, false if not.
	 */
	
	public boolean validMove(ChessMove move) {
	    // Preliminary checks for null move or incorrect starting position
	    if (move == null || board.get(move.getFrom()) == null || board.get(move.getFrom()).getColour() != currentPlayerChar) {
	        return false;
	    }

	    // Check if the destination is occupied by an opponent's piece for capture
	    Piece destinationPiece = board.get(move.getTo());
	    if (destinationPiece != null && destinationPiece.getColour() == currentPlayerChar) {
	        return false; // Cannot capture own piece
	    }

	    // Simulate the move and check if king will be in danger
	    GameStateDeterminer determiner = new GameStateDeterminer(board, currentPlayerChar);

	    if (determiner.kingInDangerAfterMove(move)) {
	    	return false; // Move leaves king in check
	    }


	    return board.get(move.getFrom()).getPossibleMoves(board).contains(move.getTo());
	}
	
	/**
	 * Look through grid. When we find a piece, calculate it's 
	 * legal moves and verify with ValidMove. 
	 * @return a list of ChessMoves that are all legal moves
	 */
    public List<ChessMove> getMoves() {
		List<ChessMove> possibleMoves = new ArrayList<>();
	        for (Location from: board.locations()) {
	        	if (board.get(from) != null && board.get(from).getColour() == currentPlayerChar) {
	        		Piece pieceToMove = board.get(from);
	        		List<Location> moveTo = pieceToMove.getPossibleMoves(board);
	        		// If the move is not made by the king, just add all the moves as normal:
	        		if (pieceToMove.getSymbol() != 'K') {
		        		for (Location to : moveTo) {
		        			ChessMove move = new ChessMove(from, to, pieceToMove);
		        			if (validMove(move)) {
		        				possibleMoves.add(move);
		        			}
		        		}
	        		}

	        		// If the move is being made by the king, check if it is a castle and handle these differently
	        		// We can check this by seeing if the valid move moves the king more than one column
	        		else {
	        			for (Location to : moveTo) {
	        				ChessMove move;
	        				if (from.col - to.col < -1) {
	        					// Indicates queenside castling, which means the rook is at four to the left
	        					// from the current king position
	        					Location oldRookLocation = new Location(to.row, from.col - 4);
	        					Location newRookLocation = new Location(to.row, from.col - 1);
	        					move = new ChessMove(from, to, pieceToMove, 
	        							oldRookLocation, newRookLocation);
	        				}
	        				else if (from.col - to.col > 1) {
	        					// Indicates kingside castling, which means the rook is at three to the right
	        					// from the current king position
	        					Location oldRookLocation = new Location(to.row, from.col + 3);
	        					Location newRookLocation = new Location(to.row, from.col + 1);
	        					move = new ChessMove(from, to, pieceToMove, 
	        							oldRookLocation, newRookLocation);
	        				}
	        				else {
	        					// Indicates a normal king move
	        					move = new ChessMove(from, to, pieceToMove);
	        				}
	        				if (validMove(move)) {
		        				possibleMoves.add(move);
	        				}
	        					
	        				}
        					
        				}
        			}
        		}	        		
        return possibleMoves;
	}
}
