package inf101.chess.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import inf101.chess.pieces.Piece;

/**
 * Adapted from GamePanel by:
 * Anna Eilertsen - anna.eilertsen@uib.no
 * Martin Vatshelle - martin.vatshelle@uib.no
 * 
 * So that it can hold pieces.
 * Each GamePanel draws the relevant piece on the tile
 * if a piece is present there.
 * Also, it draws a little grey square if the move is one of the possible moves
 * of a selected piece.
 * 
 */
public class GamePanel extends JPanel {

	private final Color SELECTED_PANEL_COLOR = Color.CYAN;
	private boolean isSelected;
	private boolean possibleMove;
	private Color color;
	private Piece pieceToDraw;
	
	private static final long serialVersionUID = 1L;

	public GamePanel(MouseListener listener, Color color, Piece pieceToDraw) {
		this.color = color;
		this.pieceToDraw = pieceToDraw;
		setPreferredSize(new Dimension(3, 3));
		// use methods in JPanel to set initial style
		setEnabled(true);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		// setBorder(new RoundedBorder(20)); //10 is the radius
		// add mouse listener which calls click method
		addMouseListener(listener);
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Dimension dim = this.getSize();

	    // Overlay with selection color if selected
	    if (isSelected) {
	        g.setColor(SELECTED_PANEL_COLOR);
	        g.fillRect(0, 0, dim.width, dim.height);
	    }
	    else {
	    	// Set the background color
		    g.setColor(color);
		    g.fillRect(0, 0, dim.width, dim.height);
	    }
	   

	    // Draw piece if present
	    if (pieceToDraw != null) {
	        Image pieceImage = loadImage(pieceToDraw.getColour(), pieceToDraw.getSymbol());
	        if (pieceImage != null) {
	            int x = (dim.width - pieceImage.getWidth(this)) / 2;
	            int y = (dim.height - pieceImage.getHeight(this)) / 2;
	            g.drawImage(pieceImage, x, y, this);
	        }
	    }
	    
	    // If selected piece can move here, draw a little oval in the square
	    if (possibleMove) {
	        int circleSize = 10; 
	        int x = (dim.width - circleSize) / 2;
	        int y = (dim.height - circleSize) / 2;
	        g.setColor(new Color(128, 128, 128, 128)); // Grey color with transparency
	        g.fillOval(x, y, circleSize, circleSize);
	    }
	    
	}

	
	// Partial credit to ChatGPT
	// For loading methods and exception catching
	private Image loadImage(char colour, char symbol) {
		try {
			String loadingString = String.format("/chess/assets/%c%c.png", colour, symbol);
			ImageIcon icon = new ImageIcon(getClass().getResource(loadingString));
			Image pieceImage = icon.getImage();
			return pieceImage;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error loading image. Check file path.");
			return null;
		}
	}

	/**
	 * Sets the color of this panel.
	 * When the updateUI function is called the color
	 * will be changed to this color
	 *
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
	
	/**
	 * Sets the square as a possible move,
	 * which in turn
	 * @param possibleMove true or false depending on if we are turning on or off the square
	 */
	public void setPossibleMove(boolean possibleMove) {
		this.possibleMove = possibleMove;
	}

	/**
	 * Indicates if the square is selected by a ChessGUIPlayer
	 * @return boolean indicating if it is selected or not
	 */
	public boolean isSelected() {
		return isSelected;
	}
	
	/**
	 * Updates the piece currently contained in this gamepanel.
	 * @param piece the new piece to draw
	 */
	public void setPiece(Piece piece) {
        this.pieceToDraw = piece;  
        repaint(); 
    }
	
	/**
	 * Returns the piece currently contained in the GamePanel
	 * @return
	 */
	public Piece getPiece() {
		return this.pieceToDraw;
	}

}
