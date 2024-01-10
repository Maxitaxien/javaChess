package inf101.sem2.game.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.game.Game;
import inf101.sem2.game.GameBoard;
import inf101.sem2.game.Graphics;
import inf101.sem2.game.MultiMoveGame;
import inf101.sem2.player.Player;

public class BlobWars extends MultiMoveGame {

    public BlobWars(Graphics graphics, Player p1, Player p2) {
        this(graphics);
        players.add(p1);
        players.add(p2);
        initializeBoard();
    }

    public BlobWars(Graphics graphics) {
        super(new GameBoard(8, 8), graphics);
    }

    public BlobWars(Graphics graphics, Iterable<Player> players) {
        super(new GameBoard(8, 8), graphics, players);
        initializeBoard();
    }

    /**
     * Place the inital 4 pieces in the corners of the board.
     */
    public void initializeBoard() {
        board.set(new Location(0, 0), players.getCurrentPlayer());
        board.set(new Location(0, board.numRows() - 1), players.getCurrentPlayer());
        players.nextPlayer();
        board.set(new Location(board.numColumns() - 1, 0), players.getCurrentPlayer());
        board.set(new Location(board.numColumns() - 1, board.numRows() - 1), players.getCurrentPlayer());
        players.nextPlayer();
    }

    @Override
    public void makeMove(Move move) {
        Location from = move.getFrom();
        Location to = move.getTo();
        if (!getPossibleLocations(from).contains(to))
            throw new IllegalArgumentException("Can't move piece that far.");

        // Move piece to adjacent tile
        if (board.getNeighborhood(from).contains(to)) {
            board.set(to, getCurrentPlayer());
            displayBoard();
        }
        // Move existing piece further
        else {
            super.makeMove(move);
            displayBoard();
        }
        swapAdjacentPieces(to);
        displayBoard();
    }

    public void swapAdjacentPieces(Location loc) {
        for (Location neighbour : board.getNeighborhood(loc)) {
            Player neighbourPiece = board.get(neighbour);
            if (neighbourPiece == null)
                continue;
            if (!neighbourPiece.equals(getCurrentPlayer())) {
                board.swap(neighbour, getCurrentPlayer());
            }
        }
    }

    @Override
    public boolean isWinner(Player player) {
        Player currentPlayer = getCurrentPlayer();
        Player otherPlayer = getCurrentPlayer();
        while (player.equals(otherPlayer)) {
            players.nextPlayer();
            otherPlayer = getCurrentPlayer();
        }
        players.setCurrentPlayer(currentPlayer);

        if (pieceCount(otherPlayer) == 0)
            return true;
        if (!board.isFull())
            return false;
        int boardSize = board.numColumns() * board.numRows();
        int pieceCount = pieceCount(player);
        return pieceCount > (boardSize / 2);
    }

    @Override
    public boolean gameOver() {
        if (board.isFull())
            return true;

        for (Player player : players) {
            if (pieceCount(player) == 0)
                return true;
        }

        for (Player player : players) {
            Game<?> gameCopy = this.copy();
            gameCopy.setCurrentPlayer(player);
            if (!gameCopy.getPossibleMoves().isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public List<Move> getPossibleMoves() {
        List<Location> oneMoveLocations = new ArrayList<>();
        List<Move> possibleMoves = new ArrayList<>();

        for (Location to : board.locations()) {
            if (!board.canPlace(to))
                continue;
            // find one neighbour you can copy from
            Location from = null;
            for (Location neighbour : board.getNeighborhood(to)) {
                if (Objects.equals(board.get(neighbour), getCurrentPlayer())) {
                    from = neighbour;
                    break;
                }
            }
            if (from != null) {
                oneMoveLocations.add(from);
                possibleMoves.add(new Move(from, to));
                continue;
            }
            // if no distance 1 neighbors,
            // find all distance 2 neighbors you can move from
            for (Location neighbour : this.getPossibleLocations(to)) {
                if (Objects.equals(board.get(neighbour), getCurrentPlayer())) {
                    possibleMoves.add(new Move(neighbour, to));
                }
            }
        }
        return possibleMoves;
    }

    public List<Location> getPossibleLocations(Location loc) {
        return board.getNeighborhood(loc, 2);
    }

    @Override
    public int score(Player player) {
        int otherPiecesSum = 0;
        for (Player p : players) {
            if (player.equals(p))
                continue;
            otherPiecesSum += pieceCount(p);
        }
        int nPlayerPieces = pieceCount(player);
        return nPlayerPieces - otherPiecesSum;
    }

    private int pieceCount(Player player) {
        int count = 0;
        for (Location loc : board.locations()) {
            Player piece = board.get(loc);
            if (piece == null)
                continue;
            if (piece.equals(player)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String getName() {
        return "Blob Wars";
    }

    @Override
    public Game<Move> copy() {
        BlobWars game = new BlobWars(graphics, players);
        copyTo(game);
        return game;
    }

}
