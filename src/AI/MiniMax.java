package AI;

import Stratego.Board;

/**
 * Uses the MiniMax algorithm to play a move in a Stratego game.
 */
public class MiniMax
{
    private static double maxPly;

    /**
     * MiniMax cannot be instantiated.
     */
    private MiniMax() {}

    /**
     * Execute the MiniMax algorithm.
     * @param _player       the player that the Ai will identify as
     * @param _board        the Stratego board to play on
     * @param _maxPly       the maximum depth
     */
    static void run(Board.State _player, Board _board, double _maxPly)
    {
        if(_maxPly < 1) throw new IllegalArgumentException("Maximum depth must be greater than 0.");

        MiniMax.maxPly = _maxPly;
        miniMax(_player, _board, 0);
    }

    /**
     * The body of the MiniMax algorithm.
     * @param _player       the player that the AI will identify as
     * @param _board        the Stratego board to play on
     * @param _currentPly   the current depth
     * @return              the score of the board
     */
    private static int miniMax(Board.State _player, Board _board, int _currentPly)
    {
        //If the game on the board is over return score
        if(_currentPly++ == maxPly || _board.isGameOver())
            return score(_player, _board);

        //get highest or lowest score move depends on which player are identify as AI
        if(_board.getTurn() == _player) return getMax(_player, _board, _currentPly);
        else return getMin(_player, _board, _currentPly);
    }

    /**
     * Play the move with the highest score.
     * @param _player       the player that the AI will identify as
     * @param _board        the Stratego board to play on
     * @param _currentPly   the current depth
     * @return              the score of the board
     */
    private static int getMax(Board.State _player, Board _board, int _currentPly)
    {
        double bestScore = Double.NEGATIVE_INFINITY;
        int indexOfBestMove = -1;

        //select best from available moves
        for(Integer move: _board.getAvailableMoves())
        {
            Board modifiedBoard = _board.getDeepCopy();
            modifiedBoard.move(move);

            int score = miniMax(_player, modifiedBoard, _currentPly);

            if(score >= bestScore)
            {
                bestScore = score;
                indexOfBestMove = move;
            }
        }

        //play a highest score move and return score of this move
        _board.move(indexOfBestMove);
        return (int)bestScore;
    }

    /**
     * Play the move with the lowest score.
     * @param _player       the player that the AI will identify as
     * @param _board        the Stratego board to play on
     * @param _currentPly   the current depth
     * @return              the score of the board
     */
    private static int getMin(Board.State _player, Board _board, int _currentPly)
    {
        double bestScore = Double.POSITIVE_INFINITY;
        int indexOfBestMove = -1;

        //select worst from available moves
        for(Integer move : _board.getAvailableMoves())
        {
            Board modifiedBoard = _board.getDeepCopy();
            modifiedBoard.move(move);

            int score = miniMax(_player, modifiedBoard, _currentPly);

            if(score <= bestScore)
            {
                bestScore = score;
                indexOfBestMove = move;
            }
        }

        //play a lowest score move and return score of this move
        _board.move(indexOfBestMove);
        return (int)bestScore;
    }

    /**
     * Get the score of the board. 0 if the game is not over.
     * @param _player    the player that the AI will identify as
     * @param _board     the Stratego board to play on
     * @return          the score of the board
     */
    private static int score(Board.State _player, Board _board)
    {
        //if player is Blank throw exception
        if(_player == Board.State.Blank)
            throw new IllegalArgumentException("Player must be Blue or Red");

        //set opponent
//        Board.State opponent = (_player == Board.State.Blue) ? Board.State.Blue : Board.State.Red;

        int gameScore = _board.countPoints();

        //set the points
        if(_board.isGameOver() && _player == Board.State.Blue) return gameScore;
        else if(_board.isGameOver() && _player == Board.State.Red) return gameScore * -1;
        else return 0;
    }
}
