package inf101.chess.player.ai;

import java.util.ArrayList;
import java.util.List;

import inf101.chess.logic.MoveCollectorAndVerifier;
import inf101.chess.player.ChessPlayer;
import inf101.grid.Location;
import inf101.chess.model.IChessBoard;
import inf101.chess.pieces.Piece;

/**
 * A class that attempts to evaluate the resulting positions
 * of a move. To be used with AI chess players such as with minimax.
 * Operates on three simple principles:
 * - It is better to have pieces that have a higher total score value than your opponent.
 * - It is good to have many moves available (not be cramped)
 * - It is good to control the centre
 */
public class PositionScoring {
	/**
	 * Get the total score for the position.
	 * This method can be adjusted in future to 
	 * include more facets of the position
	 * @param board the current playing board
	 * @param currentPlayer the playing to evaluate for
	 * @return a double representing the total score for the position
	 */
	public static double score(IChessBoard board, ChessPlayer currentPlayer) {
        char currentPlayerChar = currentPlayer.getSymbol();

        int valueDifference = getValueDifference(board, currentPlayerChar);
        
        
        MoveCollectorAndVerifier moveGetter = new MoveCollectorAndVerifier(board, currentPlayer.getSymbol());
        int currentPlayerMoves = moveGetter.getMoves().size(); 
        
        int centerControl = countCenterPieces(board, currentPlayerChar);

        // Arbitrarily chosen weights, these can be adjusted.
        // The AI should greatly value piece values with less emphasis on the other factors
        double valueWeight = 5.2;
        double movesWeight = 1.1;
        double centerWeight = 2.1;

        return valueDifference * valueWeight + currentPlayerMoves * movesWeight + centerControl * centerWeight;
    }

	/**
	 * Calculates the difference in total value of pieces 
	 * between current player and opponent.
	 * @param board the board to check
	 * @param playerChar the current player character
	 * @return an integer representing the difference
	 */
	private static int getValueDifference(IChessBoard board, char playerChar) {
		int own = 0;
		int opponent = 0;
		for (Location loc : board.locations()) {
			if (board.get(loc) != null) {
				Piece piece = board.get(loc);
				int points = piece.getValue();
				if (piece.getColour() == playerChar) {
					own += points;
				}
				else {
					opponent += points;
				}
			}
		}
		
		return own - opponent;
	}
	

	
	/**
	 * Counts the number of pieces current player has in the four central squares.
	 * @param board the board to check
	 * @param playerChar the current player character
	 * @return an integer from 0 to 4
	 */
	private static int countCenterPieces(IChessBoard board, char playerChar) {
		int centerPieces = 0;
		// Center locations: 
		List<Location> centerLocations = new ArrayList<>();
		
		int centerRow1 = board.numRows() / 2;
		int centerRow2 = board.numRows() / 2 - 1;
		
		int centerCol1 = board.numColumns() / 2;
		int centerCol2 = board.numColumns() / 2 - 1;
		
		centerLocations.add(new Location(centerRow1, centerCol1));
		centerLocations.add(new Location(centerRow1, centerCol2));
		centerLocations.add(new Location(centerRow2, centerCol1));
		centerLocations.add(new Location(centerRow2, centerCol2));
		
		for (Location loc : centerLocations) {
			if (board.get(loc) != null) {
				if (board.get(loc).getColour() == playerChar) {
					centerPieces += 1;
				}
			}
		}
		
		return centerPieces;
	}
}
