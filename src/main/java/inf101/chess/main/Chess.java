package inf101.chess.main;

import java.util.List;

import inf101.grid.ChessMove;
import inf101.chess.pieces.Queen;
import inf101.chess.logic.MoveCollectorAndVerifier;
import inf101.chess.model.ChessBoard;
import inf101.chess.model.ChessGame;
import inf101.chess.model.GameState;
import inf101.chess.player.ChessPlayer;
import inf101.chess.view.ChessDummyGraphics;
import inf101.chess.view.ChessGraphics;

/**
 * This class extends on ChessGame to handle the main
 * game loop.
 */
public class Chess extends ChessGame {
	
	public Chess(ChessGraphics graphics, GameState state, ChessPlayer p1, ChessPlayer p2) {
		super(new ChessBoard(8, 8), graphics, state);
		addPlayer(p1);
		addPlayer(p2);
		initialize();
	}
	
	public Chess(ChessGraphics graphics, GameState state, Iterable<ChessPlayer> players) {
		super(new ChessBoard(8, 8), graphics, state, players);
		initialize();
	}
	
	/* ************** Methods *****************/

	/**
	 * This is the main game loop making sure each player gets to place one piece
	 * each turn.
	 */
	public void run() {
		// game loop
		int turnCounter = 0;
		while (true) {
			try {
				if (turnCounter == 0) {
					displayStartingPlayer();
				}
				if (getPossibleMoves().isEmpty()) {
					this.state = determineState(this.board);
					if (this.state == GameState.CHECK) {
						this.state = GameState.CHECKMATE;
						players.nextPlayer();
						break;
					}
					else {
						this.state = GameState.STALEMATE;
						break;
					}
				}
				
				// Update turn counter when white has made a move:
				boolean isWhite = getCurrentPlayerChar() == 'W';
				if (isWhite) {
					turnCounter += 1;
				}
				

				// Get move from player and execute
				ChessMove move = getCurrentPlayer().getMove(copy());
				
				// Update move representation if it is a capture
				if (board.isOpponent(getCurrentPlayerChar(),move.getTo())) {
					move.capture();
				}
				
				makeMove(move);
				
				// Handle possible pawn promotions:
				int promotionRow = (getCurrentPlayer().getSymbol() == 'W') ? 0 : 7;
				if (move.getPiece().getSymbol() == 'P' && move.getTo().row == promotionRow) {
					move.promotion();
					board.setPiece(move.getTo(), new Queen(move.getPiece().getColour(), move.getTo()));
				}
								
				

				displayLastMove(move, turnCounter, isWhite, false);
				
				players.nextPlayer();
				
				this.state = determineState(this.board);
					
				if (this.state == GameState.CHECK) {
						// To display the correct player, we switch before the display, then switch back
						players.nextPlayer();
						displayLastMove(move, turnCounter, isWhite, true);
						players.nextPlayer();
						;
				}
			} catch (IllegalArgumentException e) {
				graphics.displayMessage(e.getMessage());
			}
		}
		
		// print results when game is over
		String msg = (this.getState() == GameState.CHECKMATE) ? "Checkmate! The winner is: " + players.getCurrentPlayer() :
																 "Stalemate! It's a draw.";
		graphics.displayMessage(msg);
		graphics.display(board);
	}
	

	@Override
	protected void initialize() {
		board.initializeBoard();
	}
	
	@Override
	public void restart() {
		board.clearBoard();
		board.initializeBoard();
		players.restart();
		graphics.display(board);
		graphics.displayMessage("Welcome!");
	}
	
	/**
	 * Initializes a MoveCollectorAndVerifier that collects the possible moves in
	 * the position.
	 * @return a list of possible chess moves
	 */
	public List<ChessMove> getPossibleMoves() {
		MoveCollectorAndVerifier moveCollector = new MoveCollectorAndVerifier(getGameBoard(), players.getCurrentPlayerChar());
		return moveCollector.getMoves();
	}
	
	@Override
	public void makeMove(ChessMove move) {
		super.makeMove(move);
		displayBoard();
	}
	

	@Override
	public Chess copy() {
		Chess newGame = new Chess(graphics, state, players);
		copyTo(newGame);
		newGame.state = state;
		return newGame;
		}
	
	@Override
	public void displayBoard() {
		graphics.display(board);
	}

	@Override
	public void displayMessage(String message) {
		graphics.displayMessage(message);
	}

	@Override
	public void displayStartingPlayer() {
		String startingPlayer = getCurrentPlayer() + " starts the game";
		displayMessage(startingPlayer);
	}
	
	@Override
	public void displayLastMove(ChessMove move, int turn, boolean white, boolean check) {
		String numberDisplay = (white) ? ":" : ":...";
		String checkSymbol = (check) ? "+" : "";
		String displayString = getCurrentPlayer() + " played " + Integer.toString(turn) + numberDisplay + move + checkSymbol;
		displayMessage(displayString);
	}
	
	@Override
	public ChessGraphics getGraphics() {
		return graphics;
	}
	
	@Override
	public void sleep() {
		if (graphics instanceof ChessDummyGraphics)
			return;
		try {
			Thread.sleep(SINGLE_MOVE_TIME);
		} catch (InterruptedException e) {
			System.err.println("Sleep interrupted");
		}
	}
	

	@Override
	public String getName() {
		return "Chess";
	}


}
