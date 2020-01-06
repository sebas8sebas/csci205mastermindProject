/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Names: Jonathan Basom and Sebastian Ascoli
 * Section: 9am / 11 am
 * Date: 10/6/2019
 * Time: 5:45 PM
 *
 * Project: csci205_hw
 * Package: hw01
 * Class: MasterMindBoard
 *
 * Description:
 *
 * ****************************************
 */
package hw01.game;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Master mind game class
 */
public class MasterMindBoard implements Serializable {

    /**
     * Current row the player is using
     */
    private int currentRow;

    /**
     * Boolean stating wether the player has won or not
     */
    private boolean win;

    /**
     * Max value of the slots (inclusive)
     */
    public static int MAX_SLOT_VALUE = 6;

    /**
     * Min value of the slot (inclusive)
     */
    public static int MIN_SLOT_VALUE = 1;

    /**
     * Max value of rows in the game
     */
    public static int DEFAULT_MAXIMUM_ATTEMPTS = 12;

    /**
     * Determines if the user should have unlimited guesses
     */
    boolean unlimitedAttempts;

    /**
     * Size of each row
     */
    public static int ROW_SIZE = 4;

    /**
     * Secret code the player is supposed to guess
     */
    private int[] secretCode = new int[ROW_SIZE];

    /**
     * Start time
     */
    private long startTime;

    /**
     * Finish time
     */
    private long finishTime;

    /**
     * Default Constructor
     * @author Jonathan
     * @author Sebastian
     */
    public MasterMindBoard() {
        this.currentRow = 1;
        this.win = false;
        int [] code = MasterMindUtility.generateRandomSecretCode();
        this.secretCode = code;
        this.unlimitedAttempts = false;

        this.startTime = System.nanoTime();
    }

    /**
     * Alternate constructor in which you specify the secret code
     * @param secretCode Array of 4 int that represent the secret code
     * @author Jonathan
     * @author Sebastian
     */
    public MasterMindBoard(int[] secretCode) throws MasterMindBoardException {
        this.currentRow = 1;
        this.win = false;

        //Make sure secret code is valid
        if (!MasterMindUtility.isSecretCodeValid(secretCode)){throw new MasterMindBoardException("Invalid Secret code");}

        this.secretCode = secretCode;
        this.unlimitedAttempts = false;

        this.startTime = System.nanoTime();
    }

    /**
     * Alternate constructor in which you specify if the user has unlimited guesses
     * @param unlimitedAttempts boolean to determine if the user has unlimited guesses
     *  @author Jonathan
     *  @author Sebastian
     */
    public MasterMindBoard(boolean unlimitedAttempts) {
        this.currentRow = 1;
        this.win = false;

        int [] code = MasterMindUtility.generateRandomSecretCode();
        this.secretCode = code;
        this.unlimitedAttempts = unlimitedAttempts;

        this.startTime = System.nanoTime();
    }

    /**
     * Method to guess the secret code, returns a row object containing the correct peg and position, correct peg but incorrect position,
     * and incorrect results from the guess
     *
     * Part of this code was based on information found at www.stackoverflow.com
     *
     * @see
     * <a href="https://stackoverflow.com/questions/1073919/how-to-convert-int-into-listinteger-in-java">
     *     https://stackoverflow.com/questions/1073919/how-to-convert-int-into-listinteger-in-java</a>
     *
     * @param guesses the array of ints the user is guessing
     * @return a row object which contains the number of correct, correct but wrong position and incorrect
     * guesses from the user
     * @throws Exception if the array does not have the correct size, or if the user tries to guess after
     * exceeding the maximum number of guesses
     * @author Jonathan
     * @author Sebastian
     */
    public Row guess(int[] guesses) throws MasterMindBoardException {
        if (this.unlimitedAttempts == false && this.currentRow > this.DEFAULT_MAXIMUM_ATTEMPTS) {
            throw new MasterMindBoardException("Exceeded maximum number of guesses");
        }
        else if (this.unlimitedAttempts == false && this.currentRow == this.DEFAULT_MAXIMUM_ATTEMPTS){
            //Sets finish time if this is the last attempt
            this.finishTime = System.nanoTime();
        }


        //Verify dimension of guesses array
        if (guesses.length != ROW_SIZE) {
            throw new MasterMindBoardException("Number of guesses must be " + ROW_SIZE);
        }

        Row guessResult = MasterMindUtility.makeGuess(guesses, this.secretCode);

        this.currentRow++;

        if (guessResult.getCorrectPegs() == ROW_SIZE) {
            this.win = true;

            //Sets finish time when player beats the game
            this.finishTime = System.nanoTime();
        }

        return guessResult;
    }

    /**
     * Checks if the user has won the game
     * @return boolean - true if the player has won the game
     * @author Jonathan
     * @author Sebastian
     */
    public boolean checkWin() {
        return this.win;
    }

    /**
     * Method to play the game in a command line interface
     * @author Jonathan
     * @author Sebastian
     */
    public void playCommandLine() throws Exception {

        //Sets start time again if player is playing in command line mode to make sure it records time
        //from when the player starts playing and not from when the object is initialized
        this.startTime = System.nanoTime();

        System.out.printf("Guess my code using numbers between %d and %d. You have %d guesses\n", MIN_SLOT_VALUE, MAX_SLOT_VALUE, DEFAULT_MAXIMUM_ATTEMPTS);
        Scanner in = new Scanner(System.in);


        for (int i = 1; i <= this.DEFAULT_MAXIMUM_ATTEMPTS; i++) {
            System.out.print("Guess " + i + ": ");
            String inputStr = in.nextLine();
            while (!MasterMindUtility.isValidInput(inputStr)) {
                System.out.println("Please provide valid input");
                System.out.print("Guess " + i + ": ");
                inputStr = in.nextLine();
            }

            int[] inputArray = MasterMindUtility.convertStrToArray(inputStr);
            Row result = this.guess(inputArray);
            System.out.print(inputStr + " --> " + result.toString());

            if(this.win){
                System.out.printf("%46s\n", "Congratulations you guessed correctly!");
                break;
            } else {
                if (this.currentRow > DEFAULT_MAXIMUM_ATTEMPTS){
                    System.out.printf("%42s\n", "You Lost! You ran out of attempts!");
                    System.out.println("The code was " + Arrays.toString(this.secretCode).replaceAll("[\\s\\[\\],]", ""));
                } else {
                    System.out.printf("%18s %d guesses left\n", "Try again.", DEFAULT_MAXIMUM_ATTEMPTS - this.currentRow + 1);
                }
            }
        }
    }

    /**
     * Get total elapsed time in seconds
     * @return time
     * @author Jonathan
     * @author Sebastian
     */
    public int getPlayTime(){
        return MasterMindUtility.findElapsedTime(this.startTime, this.finishTime);
    }

    /**
     * Returns the number of guesses the player has taken
     * @return number of guesses
     * @author Jonathan
     * @author Sebastian
     */
    public int getGuesses(){
        return this.currentRow - 1;
    }

    /**
     * Retrieves the secret code of the game
     * @return int[] representing the secret code
     */
    public int[] getSecretCode() {
        return secretCode;
    }

    /**
     * Sets initial time (only needed if for some reason game starts after initializing the object)
     */
    public void setInitialTime(){
        this.startTime = System.nanoTime();
    }
}

