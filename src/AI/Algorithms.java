package AI;

import Stratego.Board;

/**
 * For use a various algorithm to play Stratego game (enemies).
 */
public class Algorithms {
    /**
     * Algorithms cannot be instantiated.
     */
    private Algorithms() {
    }

    /**
     * Play using a random moving;
     *
     * @param _board the Stratego board to play on
     */
    public static void random(Board _board) {
        Random.run(_board);
    }

    /**
     * Play using the MiniMax Algorithm.
     *
     * @param _board the Stratego board to play on
     */
    public static void miniMax(Board _board) {
        MiniMax.run(_board.getTurn(), _board);
    }

    /**
     * Play using the AlphaBeta Pruning algorithm.
     *
     * @param _board the Stratego board to play on
     */
    public static void alphaBetaPruning(Board _board) {
        AlphaBetaPruning.run(_board.getTurn(), _board, Double.POSITIVE_INFINITY);
    }

    /**
     * Play using the AlphaBeta Pruning algorithm.
     * Includes depth limit.
     *
     * @param _board the Stratego board to play on
     * @param _ply   the maximum depth
     */
    public static void alphaBetaPruning(Board _board, int _ply) {
        AlphaBetaPruning.run(_board.getTurn(), _board, _ply);
    }

    /**
     * Play using the AlphaBeta Pruning algorithm.
     * Includes depth limit and evaluation function based on started lines.
     *
     * @param _board the Stratego board to play on
     * @param _ply   the maximum depth
     */
    public static void alphaBetaPruningStartedLines(Board _board, int _ply)
    {
        AlphaBetaPruningStartedLines.run(_board.getTurn(), _board, _ply);
    }

    /**
     * Play using the AlphaBeta Pruning algorithm.
     * Includes depth limit and sorting moves based on evaluation function.
     *
     * @param _board the Stratego board to play on
     * @param _ply   the maximum depth
     */
    public static void alphaBetaPruningSortingMoves(Board _board, int _ply)
    {
        AlphaBetaPruningSortingMoves.run(_board.getTurn(), _board, _ply);
    }
}
