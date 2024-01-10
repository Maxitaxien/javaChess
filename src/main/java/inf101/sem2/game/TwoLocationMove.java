package inf101.sem2.game;

import inf101.grid.Location;

public class TwoLocationMove implements IMoveType<TwoLocationMove> {
   
    public final Location from;
    public final Location to;

    public TwoLocationMove(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public TwoLocationMove getMove() {
        return this;
    }
}
