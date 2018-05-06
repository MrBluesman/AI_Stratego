package AI;

import Stratego.Board;

import java.util.*;

public class AlphaBetaPruningSortingMoves
{
    private static double maxPly;
    /**
     * AlphaBetaPruning cannot be instantiated.
     */
    private AlphaBetaPruningSortingMoves() { }

    /**
     * Execute the algorithm.
     * @param _player       the player that the AI will identify as
     * @param _board        the Stratego board to play on
     * @param _maxPly       the maximum depth
     */
    static void run(Board.State _player, Board _board, double _maxPly)
    {
        if(_maxPly < 1) throw new IllegalArgumentException("Maximum depth must be greater than 0.");

        AlphaBetaPruningSortingMoves.maxPly = _maxPly;
        alphaBetaPruning(_player, _board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
    }

    /**
     * The body of the AlphaBeta Pruning algorithm.
     *
     * @param _player     the player that the AI will identify as
     * @param _board      the Stratego board to play on
     * @param _alpha      the alpha value
     * @param _beta       the beta value
     * @param _currentPly the current depth
     * @return            the score of the board
     */
    private static int alphaBetaPruning(Board.State _player, Board _board, double _alpha, double _beta, int _currentPly) {
        //If the game on the board is over return score
        if (_currentPly++ == maxPly || _board.isGameOver())
            return score(_player, _board);

        //get highest or lowest score move depends on which player are identify as AI
        if (_board.getTurn() == _player) return getMax(_player, _board, _alpha, _beta, _currentPly);
        else return getMin(_player, _board, _alpha, _beta, _currentPly);
    }

    /**
     * Play the move with the highest score.
     * @param _player     the player that the AI will identify as
     * @param _board      the Stratego board to play on
     * @param _alpha      the alpha value
     * @param _beta       the beta value
     * @param _currentPly the current depth
     * @return            the score of the board
     */
    private static int getMax(Board.State _player, Board _board, double _alpha, double _beta, int _currentPly)
    {
        int indexOfBestMove = -1;

        //select best from available moves
        List<Integer> availableMoves = sortAvailableMoves(_player, _board, _board.getAvailableMoves());
        for(Integer move : availableMoves)
        {
            Board modifiedBoard = _board.getDeepCopy();
            modifiedBoard.move(move);
            int score = alphaBetaPruning(_player, modifiedBoard, _alpha, _beta, _currentPly);

            if(score > _alpha)
            {
                _alpha = score;
                indexOfBestMove = move;
            }

            //prun
            if(_alpha >= _beta) break;
        }

        if(indexOfBestMove != -1) _board.move(indexOfBestMove);
        return (int)_alpha;
    }

    /**
     * Play the move with the lowest score.
     * @param _player     the player that the AI will identify as
     * @param _board      the Stratego board to play on
     * @param _alpha      the alpha value
     * @param _beta       the beta value
     * @param _currentPly the current depth
     * @return            the score of the board
     */
    private static int getMin(Board.State _player, Board _board, double _alpha, double _beta, int _currentPly)
    {
        int indexOfBestMove = -1;

        List<Integer> availableMoves = sortAvailableMoves(_player, _board, _board.getAvailableMoves());
        Collections.reverse(availableMoves);
        for(Integer move : availableMoves)
        {
            Board modifiedBoard = _board.getDeepCopy();
            modifiedBoard.move(move);
            int score = alphaBetaPruning(_player, modifiedBoard, _alpha, _beta, _currentPly);

            if(score < _beta)
            {
                _beta= score;
                indexOfBestMove = move;
            }

            //prun
            if(_alpha >= _beta) break;
        }

        if(indexOfBestMove != -1) _board.move(indexOfBestMove);
        return (int)_beta;
    }

    /**
     * Sort moves in game states tree using an evaluation function
     * @param _player     the player that the AI will identify as
     * @param _board      the Stratego board to play on
     * @param _availableMoves    available moves to sort
     * @return                   sorted available moves
     */
    private static List<Integer> sortAvailableMoves(Board.State _player, Board _board, HashSet<Integer> _availableMoves)
    {
        List<Integer> sortedMoves = new ArrayList<>();
        HashMap<Integer, Integer> movesWithCostToSort = new HashMap<>();
        //select best from available moves
        for(Integer move : _availableMoves)
        {
            Board modifiedBoard = _board.getDeepCopy();
            modifiedBoard.move(move);
            //add to HashMap to sort
            movesWithCostToSort.put(move, score(_player, modifiedBoard));
        }

        List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(movesWithCostToSort.entrySet());
        sortedList.sort(Map.Entry.comparingByValue());
        Collections.reverse(sortedList);

        for (Map.Entry<Integer, Integer> entry : sortedList)
            sortedMoves.add(entry.getKey());
        
        return sortedMoves;
    }

    /**
     * Evaluation function 2.
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

        int gameScore = _board.countPointsIncludesStartedLines();

        //set the points
        if(_player == Board.State.Blue) return gameScore;
        else if(_player == Board.State.Red) return gameScore * -1;
        else return 0;
    }
}
