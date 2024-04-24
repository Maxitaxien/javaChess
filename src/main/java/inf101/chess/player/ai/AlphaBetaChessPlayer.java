package inf101.chess.player.ai;

import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.chess.logic.MoveCollectorAndVerifier;
import inf101.chess.model.ChessGame;
import inf101.chess.model.GameState;
import inf101.chess.player.AbstractChessPlayer;

/**
 * Adapted from the AlphaBetaPlayer algorithm to work with chess, by:
 * @author Sondre Bolland - sondre.bolland@uib.no
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 * 
 * Description for the AlphaBetaPlayer the class is based on: 
 * 
 * This AI is based on an algorithm that is not curriculum for INF101, but maybe
 * for INF102.
 * Since many of the students had tried implementing such an AI I have included
 * one in this solution.
 * But such a level of AI is not expected at the level of INF101.
 * <p>
 * This player will work both for TicTacToe, Connect4, Othello
 * and also for other 2 player games.
 * <p>
 * This player uses a minimax strategy to search for the best move, with AlphaBeta pruning.
 * That means that it tries all possible moves and gives a score to each move.
 * The score is given by calling the method recursively for the other player to
 * make the move that is best for him.
 * We assume that what is best for you is worst for your opponent,
 * otherwise this strategy will not work.
 * One can define their own score method in the respective games to better evaluate
 * a game state whether a player wants to select those moves or not.
 * <p>

 */
public class AlphaBetaChessPlayer extends AbstractChessPlayer {
	/**
	 * Defines how many steps ahead the search should continue
	 */
	int depth;
    /**
     * Player variable to keep track of who wants to 
     * maximize and who wants to minimize the score at a given move.
     * Needed to update <code>alpha</code> and <code>beta<\code>
     */
    char originalPlayerChar;

    
    double bestScore = Double.MIN_VALUE;;

	public AlphaBetaChessPlayer(char piece, int level) {
		super(piece, "AlphaBeta");
		depth = level;
	}

	@Override
	public ChessMove getMove(ChessGame game) {
		game.displayMessage(name + " is thinking...");
		// Getting the colour of the the alphabetaplayer
		char currentPlayerChar = game.getCurrentPlayerChar();
		this.originalPlayerChar = game.getCurrentPlayerChar();
		ChessStrategy best = bestMove(game, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 
										currentPlayerChar, false);

		return best.move;
	}

	/**
	 * Chooses the move that maximizes the players score
	 *
	 * @param game the current game to look through at higher depth passed as copy of game
	 * @param depth the depth to search
	 * @param alpha alpha for the alpha cutoff, initializes at negative infinity
	 * @param beta beta for the beta cutoff, initializes at positive infinity
	 * @param currentPlayerChar the colour of the current player
	 * @param maximizingPlayer whether the current player is maximizing or minimizing
	 * @return the best move
	 */
	private ChessStrategy bestMove(ChessGame game, int depth, double alpha, double beta, char currentPlayerChar, boolean maximizingPlayer) {
	    if (game.getState() == GameState.CHECKMATE ||
	    	game.getState() == GameState.STALEMATE || depth == 1) {
	        double score = PositionScoring.score(game.getGameBoard(), game.getCurrentPlayer());
	        if (maximizingPlayer) {
	            return new ChessStrategy(null, score); 
	        } else {
	            return new ChessStrategy(null, -score); 
	        }
	    }

	    ChessStrategy bestStrategy = null;
	    MoveCollectorAndVerifier moveGetter = new MoveCollectorAndVerifier(game.getGameBoard(), currentPlayerChar);
	    double value = maximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

	    for (ChessMove move : moveGetter.getMoves()) {
	        ChessGame newGame = game.copyGameWithoutGraphics();
	        if (!moveGetter.validMove(move)) {
	            continue; 
	        }

	        // If the move is a castle, reflect this
		    Location from = move.getFrom();
		    Location to = move.getTo();
		    if (move.getPiece().getSymbol() == 'K' && Math.abs(from.col - to.col) > 1) {
		    	boolean kingside = to.col > from.col;
		        Location rookFrom = new Location(from.row, kingside ? game.getGameBoard().numColumns() - 1 : 0);
		        Location rookTo = new Location(to.row, kingside ? 5 : 3);
		        move = new ChessMove(from, to, move.getPiece(), rookFrom, rookTo);
		    }
	        newGame.makeMove(move);
	        // Recursively call bestMove with flipped maximizingPlayer
	        ChessStrategy currentStrategy = bestMove(newGame, depth - 1, alpha, beta, currentPlayerChar == 'W' ? 'B' : 'W', !maximizingPlayer);
	        double currentScore = currentStrategy.score;

	        if (maximizingPlayer) {
	            if (currentScore > value) {
	                value = currentScore; 
	                bestStrategy = new ChessStrategy(move, value);
	            }
	            alpha = Math.max(alpha, value);
	            if (value >= beta) {
	                break; // Beta cutoff
	            }
	        } else {
	            if (currentScore < value) {
	                value = currentScore; // Minimize score
	                bestStrategy = new ChessStrategy(move, value);
	            }
	            beta = Math.min(beta, value);
	            if (value <= alpha) {
	                break; // Alpha cutoff
	            }
	        }
	    }

	    if (bestStrategy == null) {
	        System.err.println("No strategy was found!");
	        return new ChessStrategy(null, maximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
	    }
	  
	    return bestStrategy;
	}
	
}

/**
 * A inner class only to be used by MiniMax player
 * This class keeps track of a move and a score associated with that move
 *
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
class ChessStrategy {
	ChessMove move;
	double score;

	public ChessStrategy(ChessMove move, double score) {
		this.move = move;
		this.score = score;
	}
}
