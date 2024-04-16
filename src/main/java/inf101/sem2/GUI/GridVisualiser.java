package inf101.sem2.GUI;
import java.awt.Color;

import inf101.grid.Location;

public interface GridVisualiser {
	/**
	 * Shows little dots for possible moves on the
	 * current selected piece
	 * @param pieceLocation the location of the piece
	 */
    void showPossibleMoves(Location pieceLocation);
    
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
    void highlightSelectedPanel(Location location, Color color);
}