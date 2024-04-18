package inf101.chess.view;

import inf101.chess.model.ChessBoard;
import inf101.grid.Location;

public interface GridVisualiser {
	/**
	 * Shows little dots for possible moves on the
	 * current selected piece
	 * @param pieceLocation the location of the piece
	 */
    void showPossibleMoves(GamePanel panel, ChessBoard board, char playerColour);
    
    /**
     * Clears the current list of possible moves, 
     * so that a new list can be shown when a new
     * piece is selected.
     * 
     */
    void clearPossibleMoves();
    
    /**
     * 
     * Highlights the selected panel in a given
     * colour.
     * 
     * @param location the Location of the panel to highlight
     */
    void highlightSelectedPanel(GamePanel panel, char playerColour);
}