package backtracker;

import java.util.Collection;

/**
 * The representation of a single configuration for a puzzle.
 * The backtracker.Backtracker depends on these routines in order to
 * solve a puzzle.  Therefore, all puzzles must implement this
 * interface.
 *
 * @author GCCIS Faculty
 */
public interface Configuration {
    /**
     * Get the collection of successors from the current one.
     * 
     * @return All successors, valid and invalid
     */
    public Collection<Configuration> getSuccessors();
    
    /**
     * Is the current configuration valid or not?
     * 
     * @return true if valid; false otherwise
     */
    public boolean isValid();
    
    /**
     * Is the current configuration a goal?
     * @return true if goal; false otherwise
     */
    public boolean isGoal();
}
