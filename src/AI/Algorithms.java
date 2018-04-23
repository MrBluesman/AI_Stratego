package AI;

import Stratego.Board;

/**
 * For use a various algorithm to play Stratego game (enemies).
 */
public class Algorithms
{
    /**
     * Algorithms cannot be instantiated.
     */
    private Algorithms() {}

    public static void random(Board _board)
    {
        Random.run(_board);
    }

}
