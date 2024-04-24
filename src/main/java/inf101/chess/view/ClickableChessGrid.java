package inf101.chess.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import inf101.chess.pieces.Piece;
import inf101.grid.ChessMove;
import inf101.grid.Grid;
import inf101.grid.Location;
import inf101.chess.logic.GameStateDeterminer;
import inf101.chess.model.ChessBoard;
import inf101.chess.model.IChessBoard;
import inf101.chess.player.ChessPlayerList;
/**
 * Inspired by ClickableGrid by:
 * Anna Eilertsen - anna.eilertsen@uib.no
 * Martin Vatshelle - martin.vatshelle@uib.no
 * 
 * The grid has been edited so that it also shows a little circle
 * on panels where the current player can move their piece to.
 * The panels in the ClickableChessGrid can each hold one piece.
 */
public class ClickableChessGrid extends JPanel {

	private static final long serialVersionUID = 8755882090377973497L;
	private MouseAdapter adapter;
	private Grid<GamePanel> clickablePanels; // clickable grid for user input
	private IChessBoard board;
	/**
	 * Location of panel most recently clicked
	 */
	private Location clickedPanel = null;
	/**
	 * Locations of panels which have been selected
	 */
	private List<Location> selectedPanels;
	
	/**
	 * Locations of panels that represent possible moves for
	 * the selected piece
	 */
	private List<Location> previousPossibleMoves;
	
	ChessPlayerList players;

	HashMap<Character, Color> colorMap;

	public ClickableChessGrid(IChessBoard gameBoard, ChessPlayerList players, List<Color> colours) {
		this.board = gameBoard;
		adapter = new ClickableGridListener();

		// assign colors to the players
		setColors(colours);
		
		this.players = players;

		// create clickable panels
		int rows = gameBoard.numRows();
		int cols = gameBoard.numColumns();
		clickablePanels = new Grid<>(rows, cols);
		selectedPanels = new ArrayList<>();
		previousPossibleMoves = new ArrayList<>();
		setLayout(new GridLayout(rows, cols));
		makeClickablePanels();

		// set gui options
		setPreferredSize(new Dimension(100 * gameBoard.numRows(), gameBoard.numColumns()));
		setMinimumSize(new Dimension(100 * gameBoard.numRows(), gameBoard.numColumns()));
		setRequestFocusEnabled(true);
		requestFocus();
		setVisible(true);
		validate();
	}

	/**
	 * Initializes a Map from Player to Color.
	 * This is later used color the grid of GamePanel to match
	 * the moves of the different players.
	 */
	private void setColors(List<Color> colours) {
		colorMap = new HashMap<>();
		colorMap.put('W', colours.get(0));
		colorMap.put('B', colours.get(1));
	}

	/**
	 * Should be called after a click to update the UI to reflect the current game
	 * state. 
	 * 
	 */
	public void updateGui() {
	    for (Location loc : board.locations()) {
	        GamePanel panel = clickablePanels.get(loc);
	        Piece piece = board.get(loc); 
	        panel.setPiece(piece);  
	    }
	    validate();
	    repaint();
	}

	/**
	 * Makes a grid of same size as the one in game and fills it with clickable
	 * panels
	 * For each panel, figure out which piece is there and draw the corresponding
	 * image.
	 * Draws every other square in alternating tile colours, until it hits a new row,
	 * when it starts on the opposite colour of the last row.
	 * 
	 */
	private void makeClickablePanels() {
	    int rowSize = board.numColumns();
	    
	    int rowCounter = 0;
	    int colCounter = 0; 

	    for (Location loc : board.locations()) {
	        Color tileColour = ((rowCounter + colCounter) % 2 == 0) ? Color.white : Color.darkGray;
	        GamePanel pan = new GamePanel(adapter, tileColour, board.get(loc));
	        clickablePanels.set(loc, pan);
	        add(pan);
	        colCounter += 1;

	        if (colCounter == rowSize) {
	            colCounter = 0;
	            rowCounter += 1; 
	        }
	    }
	    updateGui();
	}

	public Location getLastClick() {
		return clickedPanel;
	}

	public void clearLastClick() {
		clickedPanel = null;
	}

	public List<Location> getSelectedPanels() {
		return selectedPanels;
	}

	public void setSelected(GamePanel panel) {
		if (panel == null)
			throw new NullPointerException("GamePanel is null.");
		if (!clickablePanels.contains(panel))
			throw new IllegalArgumentException("GamePanel is not part of the grid.");

		panel.setSelected(true);
		Location panelLocation = clickablePanels.locationOf(panel);
		selectedPanels.add(panelLocation);
	}

	public void deselectPanels() {
		for (Location loc : selectedPanels) {
			clickablePanels.get(loc).setSelected(false);
		}
		selectedPanels.clear();
	}
	
	/**
	 * Reworked to fit better for chess
	 * and to display possible moves.
	 */
	class ClickableGridListener extends MouseAdapter {
	    @Override
	    public void mousePressed(MouseEvent me) {
	        if (clickablePanels.contains(me.getSource())) {
	            try {
	                GamePanel currentPanel = (GamePanel) me.getSource();
	                Location currentLocation = clickablePanels.locationOf(currentPanel);
	                Piece piece = board.get(currentLocation);

	                // Check if there's already a selection for a move
	                if (!selectedPanels.isEmpty()) {
	                    // Check if the second click is on a possible move location
	                    if (previousPossibleMoves.contains(currentLocation)) {
	                        selectedPanels.add(currentLocation); 
	                        if (selectedPanels.size() == 2) {
	                            clearPreviousPossibleMoves();
	                            updateGui();
	                            return; 
	                        }
	                    } else {
	                        // Not a valid move location, clear and restart the selection
	                        clearPreviousPossibleMoves();
	                        deselectPanels();
	                    }
	                }

	                // Selection process
	                if (piece != null && players.getCurrentPlayerChar() == piece.getColour()) {
	                    selectedPanels.clear();
	                    selectedPanels.add(currentLocation);
	                    currentPanel.setSelected(true);
	                    List<Location> possibleMoves = piece.getPossibleMoves(board);
	                    for (Location loc : possibleMoves) {
	                        GamePanel panel = clickablePanels.get(loc);
	                        GameStateDeterminer determiner = new GameStateDeterminer(board, players.getCurrentPlayerChar());
	                        if (!determiner.kingInDangerAfterMove(
	                        			new ChessMove(currentLocation, loc, piece))) {
	                            if (panel != null) {
	                                panel.setPossibleMove(true);
	                            }
		                        previousPossibleMoves.add(loc);
	                        }
	                    }
	                } else {
	                    // Clear selections if own piece is not clicked
	                    clearPreviousPossibleMoves();
	                    deselectPanels();
	                }
	                updateGui();
	            } catch (Exception e) {
	                System.err.println(e.getMessage());
	            }
	        }
	    }

	    private void clearPreviousPossibleMoves() {
	        for (Location loc : previousPossibleMoves) {
	            GamePanel panel = clickablePanels.get(loc);
	            if (panel != null) {
	                panel.setPossibleMove(false);
	            }
	        }
	        previousPossibleMoves.clear();
	    }
	}
}