package inf101.sem2.game.games;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.sem2.game.Game;
import inf101.sem2.game.GameBoard;
import inf101.sem2.game.Graphics;
import inf101.sem2.game.SingleMoveGame;
import inf101.sem2.player.Player;

public class Othello extends SingleMoveGame {

	public Othello(Graphics graphics, Player p1, Player p2) {
		super(new GameBoard(8, 8), graphics);
		addPlayer(p1);
		addPlayer(p2);
		init();
	}

	public Othello(Graphics graphics, Iterable<Player> players) {
		super(new GameBoard(8, 8), graphics, players);
		init();
	}

	private void init() {
		board.set(new Location(3, 3), getCurrentPlayer());
		board.set(new Location(4, 4), getCurrentPlayer());
		players.nextPlayer();
		board.set(new Location(3, 4), getCurrentPlayer());
		board.set(new Location(4, 3), getCurrentPlayer());
		players.nextPlayer();
	}

	@Override
	public Game<Location> copy() {
		Game<Location> newGame = new Othello(this.graphics, players);
		copyTo(newGame);
		return newGame;
	}

	@Override
	public boolean isWinner(Player player) {
		if (!gameOver())
			return false;
		int count = getGameBoard().countPieces(player);
		for (Player p : players) {
			if (p.equals(player))
				continue;
			if (board.countPieces(p) >= count)
				return false;
		}
		return true;
	}

	@Override
	public boolean gameOver() {
		return getPossibleMoves().isEmpty();
	}

	@Override
	public String getName() {
		return "Othello";
	}

	@Override
	public boolean canPlace(Location loc) {
		if (!super.canPlace(loc))
			return false;
		if (getFlipped(loc).isEmpty())
			return false;
		else
			return true;
	}

	public boolean isOpponent(Location loc) {
		if (!board.isOnBoard(loc))
			return false;
		if (board.get(loc) == getCurrentPlayer())
			return false;
		if (board.get(loc) == null)
			return false;
		return true;
	}

	@Override
	public void makeMove(Location loc) {
		Player current = getCurrentPlayer();
		List<Location> toFlip = getFlipped(loc);

		super.makeMove(loc);
		displayBoard();

		for (Location l : toFlip) {
			board.swap(l, current);
		}
		displayBoard();
	}

	private List<Location> getFlipped(Location loc) {
		List<Location> flipped = new ArrayList<Location>();
		for (GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
			flipped.addAll(getFlipped(loc, dir));
		}
		return flipped;
	}

	private List<Location> getFlipped(Location loc, GridDirection dir) {
		List<Location> flipped = new ArrayList<Location>();
		Location target = loc.getNeighbor(dir);
		while (isOpponent(target)) {
			flipped.add(target);
			target = target.getNeighbor(dir);
		}

		if (board.isOnBoard(target) && getCurrentPlayer().equals(board.get(target)))
			return flipped;
		else
			return new ArrayList<Location>();
	}

	@Override
	public void restart() {
		super.restart();
		init();
	}

	@Override
    public int score(Player player) {
        int otherPiecesSum = 0;
        for (Player p : players) {
            if (player.equals(p))
                continue;
            otherPiecesSum += board.countPieces(p);
        }
        int nPlayerPieces = board.countPieces(player);
        return nPlayerPieces - otherPiecesSum;
    }
}
