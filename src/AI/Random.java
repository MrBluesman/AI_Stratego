package AI;

import Stratego.Board;

/**
 * Plays a random move in Stratego game.
 *
 */
public class Random
{
    /**
     * Random cannot be instantiated.
     * Only static methods.
     */
    private Random () {}

    /**
     * Execute the Random algorithm.
     * @param _board     the Stratego game board to play on.
     */
    static void run (Board _board)
    {
        int[] moves = new int[_board.getAvailableMoves().size()];
        int index = 0;

        for(Integer item : _board.getAvailableMoves())
        {
            moves[index] = item;
            index++;
        }

        int randomMove = moves[new java.util.Random().nextInt(moves.length)];
        _board.move(randomMove);
    }
}
