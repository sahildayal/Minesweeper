/**
 * Contains code for Location that stores and provides information on a given location
 * 
 * @author Nicholas Boulos, Sahil Dayal, Nathan Z
 */

package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Location {
    //Fields
    private final int row;
    private final int col;
    private LocationState state = LocationState.HIDDEN;
    private boolean hasMine = false;
    private final HashSet<Location> neighbors = new HashSet<>();

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Location copy() {
        Location l = new Location(row, col);
        l.state = state;
        l.hasMine = hasMine;
        return l;
    }

    public void addNeighbor(Location location) {
        neighbors.add(location);
    }

    public int checkSurroundings() {
        int amount = 0;
        for (Location neighbor : neighbors) {
            if (neighbor.hasMine) {
                amount++;
            }
        }
        return amount;
    }

    public LocationState getState(){return state;}

    public Set<Location> reveal() {
        if (state != LocationState.HIDDEN) {
            return new HashSet<>();
        }

        HashSet<Location> revealed = new HashSet<>();
        revealed.add(this);

        if (hasMine) {
            state = LocationState.MINE;
        } else if (checkSurroundings() > 0) {
            state = LocationState.NUMBERED;
        } else {
            state = LocationState.EMPTY;
            for (Location neighbor : neighbors) {
                revealed.add(neighbor);
                revealed.addAll(neighbor.reveal());
            }
        }

        return revealed;
    }

    public boolean getMine() {return hasMine;}

    public void setMine(){hasMine = true;}

    public int getRow() {return row;}

    public int getCol() {return col;}

    public void reset() {
        hasMine = false;
        state = LocationState.HIDDEN;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return getRow() == location.getRow() && getCol() == location.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getCol());
    }
}