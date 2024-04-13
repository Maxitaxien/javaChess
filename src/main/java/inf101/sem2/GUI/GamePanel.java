package inf101.sem2.GUI;

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
 * A class for clickable panels
 * This is basically a Button with some special design that changes.
 * When this panel is clicked a call to the MouseListener will be made
 * which then changes the color.
 *
 * @author Anna Eilertsen - anna.eilertsen@uib.no
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public class GamePanel extends JPanel {

	private final Color SELECTED_PANEL_COLOR = Color.CYAN;
	private boolean isSelected;
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
	    // TODO: add little grey squares on each legal move for piece on square (maybe in clickablechessgrid)
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

	public boolean isSelected() {
		return isSelected;
	}
	
	public void setPiece(Piece piece) {
        this.pieceToDraw = piece;  // Update the piece
        repaint();  // Repaint to reflect the new piece
    }

}
