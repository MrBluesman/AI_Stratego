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

    /**
     * Play a random move;
     * @param _board
     */
    public static void random(Board _board)
    {
        Random.run(_board);
    }

    /**
     * Play a move using the MiniMax Algorithm.
     * @param _board
     */
    public static void miniMax(Board _board)
    {
        MiniMax.run(_board.getTurn(), _board, Double.POSITIVE_INFINITY);
    }

}
