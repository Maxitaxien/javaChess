package inf101.chess.main;

import java.util.List;

import inf101.grid.ChessMove;
import inf101.chess.pieces.Queen;
import inf101.chess.logic.MoveCollectorAndVerifier;
import inf101.chess.model.ChessBoard;
import inf101.chess.model.ChessGame;
import inf101.chess.model.GameState;
import inf101.chess.player.ChessPlayer;
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
		while (true) {
			try {
				displayPlayerTurn();
				if (getPossibleMoves().isEmpty()) {
					players.nextPlayer();
					this.state = determineState(this.board);
					if (this.state == GameState.CHECK) {
						this.state = GameState.CHECKMATE;
						break;
					}
					else {
						this.state = GameState.STALEMATE;
						break;
					}
				}
				

				// Get move from player and execute
				ChessMove move = getCurrentPlayer().getMove(copy());
				makeMove(move);
				
				// Handle possible pawn promotions:
				int promotionRow = (getCurrentPlayer().getSymbol() == 'W') ? 0 : 7;
				if (move.getPiece().getSymbol() == 'P' && move.getTo().row == promotionRow) {
					board.setPiece(move.getTo(), new Queen(move.getPiece().getColour(), move.getTo()));
					graphics.display(board);
				}
				players.nextPlayer();
					
				this.state = determineState(this.board);
					
				if (this.state == GameState.CHECK) {
						graphics.displayMessage("Check!");
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
	
	/**
	 * Sets up piecces on the board through a call to
	 * the board.
	 */
	private void initialize() {
		board.initializeBoard();
	}
	
	@Override
	public void makeMove(ChessMove move) {
		super.makeMove(move);
		displayBoard();
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
	public Chess copy() {
		Chess newGame = new Chess(graphics, state, players);
		copyTo(newGame);
		newGame.state = state;
		return newGame;
		}
	

	@Override
	public String getName() {
		return "Chess";
	}


}
