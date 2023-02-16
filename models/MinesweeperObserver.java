package models;

public interface MinesweeperObserver {
    public void cellUpdated(Location location);
    public void hint();
    public void unhint();
}
