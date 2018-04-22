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
    private HashSet<Integer> movesAvailable;    //Available moves on this state of board
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
        movesAvailable = new HashSet<>();
        restart();
    }

    //----------
    // METHODS |--------------------------------------------------
    //----------

    /**
     * Restart the game with a new blank board.
     */
    private void restart()
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
        movesAvailable.clear();

        //add all moves to available moves at the start of the game
        for(int i = 0; i < BOARD_WIDTH*BOARD_WIDTH; i++)
            movesAvailable.add(i);
    }
}
