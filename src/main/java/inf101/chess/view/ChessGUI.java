package inf101.chess.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import inf101.chess.model.ChessBoard;
import inf101.chess.model.IChessBoard;
import inf101.chess.pieces.King;
import inf101.chess.pieces.Piece;
import inf101.chess.player.ChessGUIPlayer;
import inf101.chess.player.ChessPlayer;
import inf101.chess.player.ChessPlayerList;
import inf101.grid.ChessMove;
import inf101.grid.Location;

/**
 * Adapted from GameGUI by Martin Vatshelle.
 * The main addition is the getMove method. 
 */
public class ChessGUI implements ActionListener, ChessGraphics {

	private JButton backButton; // Button to go back to end the game and go back to main menu
	private JButton restartButton; // Button to restart the game
	private JLabel statusMessage; // field for displaying message to user
	private ClickableChessGrid board; // clickable grid for user input
	private ChessPlayerList players;
	private JFrame frame;

	public boolean wantRestart = false;
	public boolean ended = false;

	public ChessGUI(ChessPlayerList players) {
		this.players = players;
		JPanel buttons = createButtonPanel();
		statusMessage = new JLabel();
		statusMessage.setPreferredSize(new Dimension(300, 50));
		statusMessage.setText("Welcome!");

		// make new main window for the game
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.setVisible(false);
				ended = true;
			}
		});
		frame.add(statusMessage, BorderLayout.NORTH);
		frame.add(buttons, BorderLayout.SOUTH);
		drawGameBoard();
	}

	public void setName(String name) {
		frame.setTitle(name);
	}

	/**
	 * Draws the gameBoard on the JFrame
	 * and refreshes the rest of the graphics
	 */
	private void drawGameBoard() {
		// add the clickable grid panel
		frame.doLayout();
		frame.pack();
		frame.setVisible(true);
		frame.validate();
	}

	/**
	 * Helper method that creates the button panel
	 */
	private JPanel createButtonPanel() {
		backButton = new JButton();
		backButton.addActionListener(this);
		backButton.setText("Back");

		restartButton = new JButton();
		restartButton.addActionListener(this);
		restartButton.setText("Restart");

		JPanel buttons = new JPanel();
		// buttons.setLayout(new BorderLayout());
		// buttons.add(backButton, BorderLayout.WEST);
		// buttons.add(restartButton, BorderLayout.EAST);
		buttons.add(backButton);
		buttons.add(restartButton);
		return buttons;
	}

	// Called whenever a button is pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		// removeClickablePanels();
		if (e.getSource() == backButton) {
			ended = true;
			System.err.println("GameGui has ended game");
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			return;
		}
		if (e.getSource() == restartButton) {
			wantRestart = true;
			return;
		}
		drawGameBoard();
	}

	public void removeClickablePanels() {
		if (board != null && board.getParent() != null) {
			board.getParent().remove(board);
		}
	}

	@Override
	public void displayMessage(String message) {
		statusMessage.setText(message);
	}
	
	/**
	 * This method reports the move selected by the user.
	 * 
	 * @return
	 */
	public ChessMove getMove(IChessBoard iChessBoard) {
	    List<Location> selectedPanels = board.getSelectedPanels();
	    if (selectedPanels.size() != 2)
	        return null;
	    Location from = selectedPanels.get(0);
	    Location to = selectedPanels.get(1);
	    Piece piece = iChessBoard.get(from);
	    
	    // If playing with another human, update the current player for the 
	    // clickable panels as well, for move-showing purposes.
	    if (players.getNextPlayer() instanceof ChessGUIPlayer) {
	        board.players.nextPlayer();
	    }

	    // Create a different kind of move if castling is attempted
	    if (piece instanceof King && Math.abs(from.col - to.col) > 1) {
	        boolean kingside = to.col > from.col;
	        Location rookFrom = new Location(from.row, kingside ? iChessBoard.numColumns() - 1 : 0);
	        Location rookTo = new Location(to.row, kingside ? 5 : 3);
	        return new ChessMove(from, to, piece, rookFrom, rookTo);
	    } else {
	        // Regular move
	        return new ChessMove(from, to, piece);
	    }
	}	
	

	/**
	 * Maps from Player values to colors
	 *
	 * @param pieceAt The piece to be drawn
	 * @return The color that this GUI implementation associates with the provided
	 *         piece
	 */
	protected List<Color> getColors() {
		Color[] colors = { Color.WHITE, Color.BLACK };
		return Arrays.asList(colors);
	}

	public void display(IChessBoard gameBoard) {
		removeClickablePanels();
		this.board = new ClickableChessGrid(gameBoard, players, getColors());
		frame.add("Center", this.board);
		frame.setMinimumSize(new Dimension(100 * gameBoard.numColumns(), 100 * gameBoard.numRows() + 100));
		drawGameBoard();
		
	}
}
