package inf101.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Move implements Iterable<Location> {

    public final Location from;

    public final Location to;

    public Move(Location from, Location to) {
        if (from == null || to == null)
            throw new IllegalArgumentException("Locations cannot be null.");
        this.from = from;
        this.to = to;
    }

    public Move(List<Location> locations) {
        if (locations.size() != 2)
            throw new IllegalArgumentException("A move must have two locations.");
        if (!vaildateLocationList(locations))
            throw new IllegalArgumentException("Locations cannot be null.");

        this.from = locations.get(0);
        this.to = locations.get(1);
    }

    public boolean vaildateLocationList(List<Location> list) {
        for (Location loc : list) {
            if (loc == null)
                return false;
        }
        return true;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    @Override
    public Iterator<Location> iterator() {
        List<Location> locations = Arrays.asList(from, to);
        return locations.iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Iterator<Location> iterator = iterator();
        while (iterator.hasNext()) {
            Location loc = iterator.next();
            builder.append(loc);
            if (iterator.hasNext())
                builder.append(" --> ");
        }
        return builder.toString();
    }

}
