package Stratego;

import java.util.HashSet;

/**
 * Represents the Stratego game board.
 */
public class Board
{
    //Width of board. BOARD_WIDTH * BOARD_WIDTH fields.
    static final int BOARD_WIDTH = 3;

    //Represents the State of the fields on the Board.
    public enum State
    {
        Blank, Blue, Red
    }

    private State[][] board;                    //Game board
    private State playersTurn;                  //which player should make a turn
    private State winner;                       //Winner of the game
    private HashSet<Integer> availableMoves;    //Available moves on this state of board
                                                //Integer is a id of the field (0 - BOARD_WIDTH*BOARD_WIDTH)

    private int moveCount;                      //number of moves
    private boolean gameOver;                   //if game is over

    //---------------
    // CONSTRUCTORS |--------------------------------------------------
    //---------------

    /**
     * Construct a Board for Stratego game.
     */
    Board()
    {
        board = new State[BOARD_WIDTH][BOARD_WIDTH];
        availableMoves = new HashSet<>();
        restart();
    }


    /**
     * Restart the game with a new blank board.
     */
    void restart()
    {
        moveCount = 0;
        gameOver = false;
        playersTurn = State.Blue;
        winner = State.Blank;
        //initialize a board fields and available moves
        initialize();
    }

    /**
     * Sets the all game fields as Blank and loads the available moves
     * (all moves are available at the start of the game).
     */
    private void initialize()
    {
        //Set all game fields on the board as Blank
        for(int row = 0; row < BOARD_WIDTH; row++)
            for(int col = 0; col < BOARD_WIDTH; col++)
                board[row][col] = State.Blank;

        //Clear available moves
        availableMoves.clear();

        //add all moves to available moves at the start of the game
        for(int i = 0; i < BOARD_WIDTH*BOARD_WIDTH; i++)
            availableMoves.add(i);
    }

    //----------
    // METHODS |--------------------------------------------------
    //----------

    /**
     * Colours a specified field with the color Blue or Red depends on whose turn it is
     * @param _x     the x coordinate of the field
     * @param _y     the y coordinate of the field
     * @return      true if the has not already been played
     */
    private boolean move(int _x, int _y)
    {
        //No move can be played if the game is over
        if(gameOver)
            throw new IllegalStateException("Statego game is over. No moves can be player.");

        //Set the color of playersTurn if the field is Blank
        if(board[_x][_y] == State.Blank) board[_x][_y] = playersTurn;
        else return false;


        moveCount++;
        //remove the position (row * BOARD_WIDTH + column)
        availableMoves.remove(_x * BOARD_WIDTH + _y);

        //if moveCount is equal to fields count the game is over
        //Default is an draw (winner is a Blank)
        if(moveCount == BOARD_WIDTH * BOARD_WIDTH)
        {
            winner = State.Blank;
            gameOver = true;

            //count points and select winner
//            int points = countPoints();
            countPoints();
        }

        //Check for a winner

        playersTurn = (playersTurn == State.Blue) ? State.Red : State.Blue;

        return true;
    }

    /**
     * Colours a specified field with the color Blue or Red depends on whose turn it is
     * @param _index     index of the field on the board (row: index*BOARD_WIDTH, col: index%BOARD_WIDTH)
     * @return          true if the has not already been played
     */
    public boolean move(int _index)
    {
        return move(_index / BOARD_WIDTH, _index % BOARD_WIDTH);
    }

    /**
     * Counts a point of finished game and select the winner
     * @return      winner points
     */
    private int countPoints()
    {
        int bluePoints = 0;
        int redPoints = 0;

        //check rows
        int[] rowPoints = countRows();
        bluePoints += rowPoints[0];
        redPoints += rowPoints[1];

        //check columns
        int[] colPoints = countColumns();
        bluePoints += colPoints[0];
        redPoints += colPoints[1];
        //check diagonalFromTopLeft
        int[] diag1Points = countDiagonalFromTopLeft();
        bluePoints += diag1Points[0];
        redPoints += diag1Points[1];
        //check diagonalFromTopRight
        int[] diag2Points = countDiagonalFromTopRight();
        bluePoints += diag2Points[0];
        redPoints += diag2Points[1];

        //set the winner
        winner = bluePoints > redPoints ? State.Blue : State.Red;
        //return winners points
        return bluePoints > redPoints ? bluePoints : redPoints;
    }

    /**
     * Counts a points of finished game in rows
     * @return      points of both players in rows as array [Blue, Red]
     */
    private int[] countRows()
    {
        int[] returnPointsArray = new int[2];
        int bluePoints = 0;
        int redPoints = 0;

        for(int row = 0; row < BOARD_WIDTH; row++)
        {
            State actualColor = State.Blank;
            int points = 0;
            for(int col = 0; col < BOARD_WIDTH; col++)
            {
                if(board[row][col] == actualColor) points++;
                else
                {
                    if(points > 1)
                    {
                        if(actualColor == State.Blue) bluePoints += points;
                        else redPoints += points;
                    }
                    points = 1;
                    actualColor = board[row][col];
                }
            }
            if(points > 1)
            {
                if(actualColor == State.Blue) bluePoints += points;
                else redPoints += points;
            }
        }

        returnPointsArray[0] = bluePoints;
        returnPointsArray[1] = redPoints;
        return returnPointsArray;
    }

    /**
     * Counts a points of finished game in columns
     * @return      points of both players in columns as array [Blue, Red]
     */
    private int[] countColumns()
    {
        int[] returnPointsArray = new int[2];
        int bluePoints = 0;
        int redPoints = 0;

        for(int col = 0; col < BOARD_WIDTH; col++)
        {
            State actualColor = State.Blank;
            int points = 0;
            for(int row = 0; row < BOARD_WIDTH; row++)
            {
                if(board[row][col] == actualColor) points++;
                else
                {
                    if(points > 1)
                    {
                        if(actualColor == State.Blue) bluePoints += points;
                        else redPoints += points;
                    }
                    points = 1;
                    actualColor = board[row][col];
                }
            }
            if(points > 1)
            {
                if(actualColor == State.Blue) bluePoints += points;
                else redPoints += points;
            }
        }

        returnPointsArray[0] = bluePoints;
        returnPointsArray[1] = redPoints;
        return returnPointsArray;
    }

    /**
     * Counts a points of finished game in diagonal from top to right
     * @return      Points of both players as array [Blue, Red]
     */
    private int[] countDiagonalFromTopRight()
    {
        int[] returnPointsArray = new int[2];
        int bluePoints = 0;
        int redPoints = 0;

        State actualColor = State.Blank;
        int points = 0;
        for(int pos = 0; pos < BOARD_WIDTH; pos++)
        {
            if(board[pos][pos] == actualColor) points++;
            else
            {
                if(points > 1)
                {
                    if(actualColor == State.Blue) bluePoints += points;
                    else redPoints += points;
                }
                points = 1;
                actualColor = board[pos][pos];
            }
        }
        if(points > 1)
        {
            if(actualColor == State.Blue) bluePoints += points;
            else redPoints += points;
        }

        returnPointsArray[0] = bluePoints;
        returnPointsArray[1] = redPoints;
        return returnPointsArray;
    }

    /**
     * Counts a points of finished game in diagonal from top to left
     * @return      Points of both players as array [Blue, Red]
     */
    private int[] countDiagonalFromTopLeft()
    {
        int[] returnPointsArray = new int[2];
        int bluePoints = 0;
        int redPoints = 0;

        State actualColor = State.Blank;
        int points = 0;
        int col = BOARD_WIDTH - 1;
        for(int row = 0; row < BOARD_WIDTH; row++)
        {
            if(board[row][col] == actualColor) points++;
            else
            {
                if(points > 1)
                {
                    if(actualColor == State.Blue) bluePoints += points;
                    else redPoints += points;
                }
                points = 1;
                actualColor = board[row][col];
            }
            col--;
        }
        if(points > 1)
        {
            if(actualColor == State.Blue) bluePoints += points;
            else redPoints += points;
        }

        returnPointsArray[0] = bluePoints;
        returnPointsArray[1] = redPoints;
        return returnPointsArray;
    }

    /**
     * Checks if the game is over (if there is a winner or there is a draw)
     * @return      true if game is over
     */
    public boolean isGameOver()
    {
        return gameOver;
    }

    /**
     * Get a copy of the array that represents the board.
     * @return          the board array
     */
    State[][] toArray ()
    {
        return board.clone();
    }

    /**
     * Check to see who's turn it is.
     * @return          the player who's turn it is
     */
    public State getTurn()
    {
        return playersTurn;
    }

    /**
     * Check to see who won.
     * @return          the player who won (or Blank if the game is a draw)
     */
    public State getWinner ()
    {
        if (!gameOver) throw new IllegalStateException("Stratego game is not over yet.");
        return winner;
    }

    /**
     * Get the indexes of all fields on the board that are empty.
     * @return      the indexes of empty fields (Blank)
     */
    public HashSet<Integer> getAvailableMoves()
    {
        return availableMoves;
    }

    /**
     * Get a deep copy of the Stratego board.
     * @return      an identical copy of the board
     */
    public Board getDeepCopy()
    {
        Board board             = new Board();

        for (int i = 0; i < board.board.length; i++)
            board.board[i] = this.board[i].clone();

        board.playersTurn       = this.playersTurn;
        board.winner            = this.winner;
        board.availableMoves   = new HashSet<>();
        board.availableMoves .addAll(this.availableMoves);
        board.moveCount         = this.moveCount;
        board.gameOver          = this.gameOver;
        return board;
    }

    //----------
    // PRINTERS |--------------------------------------------------
    //----------

    //Copied - toChange
    @Override
    public String toString ()
    {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < BOARD_WIDTH; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {

                if (board[y][x] == State.Blank) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != BOARD_WIDTH -1) {
                sb.append("\n");
            }
        }

        return new String(sb);
    }
}
