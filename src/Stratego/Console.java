package Stratego;

import AI.Algorithms;
import AI.AlphaBetaPruning;
import Stratego.Board.State;
import java.util.Scanner;


public class Console
{
    private Board board;
    private Scanner sc;
    private int playerBlueTime;
    private int playerRedTime;
    private long startMeasureTime;
    private long stopMeasureTime;

    /**
     * Console game constructor.
     * Creates a scanner to read player moves and board to play on.
     */
    private Console()
    {
        this.sc = new Scanner(System.in);
        this.board = new Board();
        playerBlueTime = 0;
        playerRedTime = 0;
        startMeasureTime = 0;
        stopMeasureTime = 0;
    }

    /**
     * Plays a Stratego game.
     */
    private void play()
    {
        System.out.println("Starting a new game.");
        do
        {
            do
            {
                this.printGameStatus();
                this.playMove();
            }
            while(!this.board.isGameOver());

            this.printWinner();
        }
        while(this.tryAgain());
    }

    /**
     * Plays a move in Stratego game.
     */
    private void playMove()
    {
        if (this.board.getTurn() == State.Blue)
        {
            startMeasureTime = System.nanoTime();

            this.getPlayerMove();
//            Algorithms.alphaBetaPruning(this.board, 5);
//            Algorithms.alphaBetaPruningStartedLines(this.board, 5);
//            Algorithms.alphaBetaPruningSortingMoves(this.board, 5);
//            Algorithms.alphaBetaPruningBestFirst(this.board, 5);]

            stopMeasureTime = System.nanoTime();
            playerBlueTime += (stopMeasureTime - startMeasureTime)/1000000;
        }
        else
        {
            startMeasureTime = System.nanoTime();

//            Algorithms.random(this.board);
            Algorithms.miniMax(this.board, 3);
//            Algorithms.alphaBetaPruning(this.board, 5);
//            Algorithms.alphaBetaPruningStartedLines(this.board, 5);
//            Algorithms.alphaBetaPruningSortingMoves(this.board, 5);
//            Algorithms.alphaBetaPruningBestFirst(this.board, 50);

            stopMeasureTime = System.nanoTime();
            playerRedTime += (stopMeasureTime - startMeasureTime)/1000000;
        }
    }

    /**
     * Prints a game status: board + actual points + is which player's turn
     */
    private void printGameStatus()
    {
        System.out.println("\n" + this.board + "\n");

        int[] gamePoints = this.board.countPointsArray();
        System.out.println(Board.ANSI_BLUE + gamePoints[0] + Board.ANSI_RESET + " - Blue's points");
        System.out.println(Board.ANSI_RED + gamePoints[1] + Board.ANSI_RESET + " - Red's points\n");

        System.out.println(this.board.getTurn().name() + "'s turn.");
    }


    /**
     * Gets a player move.
     */
    private void getPlayerMove()
    {
        System.out.print("Index of move: ");
        int move = this.sc.nextInt();
        if (move >= 0 && move < board.getBoardWidth() * board.getBoardWidth())
        {
            if (!this.board.move(move))
            {
                System.out.println("\nInvalid move.");
                System.out.println("\nThe selected index must be blank.");
            }
        }
        else
        {
            System.out.println("\nInvalid move.");
            System.out.println("\nThe index of the move must be between 0 and " +
                    ((board.getBoardWidth() * board.getBoardWidth()) - 1) + ", inclusive.");
        }

    }

    /**
     * Prints a winner of the Strateg game.
     */
    private void printWinner()
    {
        State winner = this.board.getWinner();
        System.out.println("\n" + this.board + "\n");

        int[] gamePoints = this.board.countPointsArray();
        System.out.println(Board.ANSI_BLUE + gamePoints[0] + Board.ANSI_RESET + " - Blue's points, turn's time: " + playerBlueTime);
        System.out.println(Board.ANSI_RED + gamePoints[1] + Board.ANSI_RESET + " - Red's points, turn's time: " + playerRedTime + "\n");

        if (winner == State.Blank) System.out.println("The Stratego is a Draw.");
        else System.out.println("Player " + winner.toString() + " wins!");
    }


    /**
     * Restarts a board if try again is ran.
     * @return True if the new game is started, false it it's not.
     */
    private boolean tryAgain()
    {
        if (this.promptTryAgain())
        {
            this.board.restart();
            System.out.println("Started new game.");
            System.out.println("Blue's turn.");
            return true;
        }
        else return false;
    }

    /**
     * Prompts a proposal of the new Stratego game on the console.
     * @return True if start new game, false if don't start.
     */
    private boolean promptTryAgain()
    {
        while(true)
        {
            System.out.print("Would you like to start a new game? (Y/N): ");
            String newGame = this.sc.next();
            if (newGame.equalsIgnoreCase("y")) {
                return true;
            }

            if (newGame.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Invalid input.");
        }
    }

    /**
     * Executable method to run the Console GUI with Stratego game.
     * @param args String array main args
     */
    public static void main(String[] args)
    {
        Console consoleGUI = new Console();
        consoleGUI.play();
    }
}
