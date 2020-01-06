/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom and Sebastian Ascoli
 * Section: 9 am / 11 am
 * Date: 10/11/2019
 * Time: 11:42 AM
 *
 * Project: csci205_hw
 * Package: hw01
 * Class: UsefullFunctions
 *
 * Description:
 *
 * ****************************************
 */
package hw01.game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Utility class for useful functions
 */
public final class MasterMindUtility {

    /**
     * Repeatedly asks a user to enter an input until the input is valid
     * @param in scanner object
     * @param inputOptions string array wih valid input options
     * @return String verified input
     * @author Sebastian
     */
    public static String getValidInput(Scanner in, String[] inputOptions){
        while (true) {
            String inp = in.nextLine().trim();
            for (String s :
                    inputOptions) {
                if (s.equalsIgnoreCase(inp)) {
                    return s;
                }
            }
            System.out.print("Error: Please enter valid input: ");
        }
    }

    /**
     * Repeatedly asks the user to enter an input until it is numeric and integer
     * @param in scanner
     * @return numeric input
     * @author Sebastian
     */
    public static int getIntegerPositiveInput(Scanner in){
        while (true){
            String inp = in.nextLine();
            try{
                int intInput = Integer.parseInt(inp);

                if (intInput > 0){
                    return intInput;
                } else {
                    System.out.print("Error: Please enter positive number: ");
                }


            } catch (Exception e){
                System.out.print("Error: Please enter numeric input: ");
            }
        }
    }


    /**
     * Converts guess string (which should be already validated) into an array that the guess method
     * can take as a parameter
     * @param input string with user's guess
     * @return array of integers the guess method can take as parameter
     * @author Jonathan
     */
    static int[] convertStrToArray(String input) {
        int[] intArray = new int[MasterMindBoard.ROW_SIZE];
        for (int i = 0; i < input.length(); i++) {

            char c = input.charAt(i);
            intArray[i] = Integer.parseInt(String.valueOf(c));
        }

        return intArray;
    }

    /**
     * Checks if the guessed code string is in the right format
     * @param input input string
     * @return boolean
     * @author Jonathan
     */
    static boolean isValidInput(String input) {
        if (input.length() != MasterMindBoard.ROW_SIZE) {
            return false;
        }
        String pattern = String.format("[%d-%d]{%d}", MasterMindBoard.MIN_SLOT_VALUE, MasterMindBoard.MAX_SLOT_VALUE, MasterMindBoard.ROW_SIZE);
        return input.matches(pattern);
    }

    /**
     * Check if secret code provided in the constructor is valid
     * @param secretCode secret code array of integers
     * @return boolean stating weather code is valid or not
     * @author Jonathan
     */
    static boolean isSecretCodeValid(int[] secretCode){
        if (secretCode.length != MasterMindBoard.ROW_SIZE) {return false;}
        for (int digit:
                secretCode) {
            if (digit < MasterMindBoard.MIN_SLOT_VALUE || digit > MasterMindBoard.MAX_SLOT_VALUE) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generate random secret code
     * @author Jonathan
     */
    public static int[] generateRandomSecretCode() {

        int[] secretCode = new int[MasterMindBoard.ROW_SIZE];

        Random rand = new Random();
        for (int i = 0; i < secretCode.length; i++) {
            secretCode[i] = rand.nextInt(MasterMindBoard.MAX_SLOT_VALUE) + MasterMindBoard.MIN_SLOT_VALUE;
        }
        return secretCode;
    }



    /**
     * Returns a Row object for the results of a guess
     * @param userGuess guess made
     * @param code secret code
     * @return Row containing the results of the guess
     */
    public static Row makeGuess(int[] userGuess, int[]  code){
        int[] guesses = userGuess.clone();
        int[] secretCode = code.clone();

        if(guesses.length != secretCode.length){
            throw new IllegalArgumentException("Invalid Guess!");
        }

        List<Integer> guessList = Arrays.stream(secretCode).boxed().collect(Collectors.toList());

        int correctPegs = 0;
        for (int i = 0; i < guesses.length; i++) {
            if (guesses[i] == secretCode[i]) {
                correctPegs++;
                //Cast to object to make sure it removes the actual value instead of the index
                guessList.remove((Object) guesses[i]);
                guesses[i] = -1;
            }
        }

        int pegsIncorrectPosition = 0;
        for (int guess:
                guesses) {
            if (guessList.contains(guess)){
                pegsIncorrectPosition++;
                //Cast to object to make sure it removes the actual value instead of the index
                guessList.remove((Object)guess);
            }
        }

        return new Row(correctPegs, pegsIncorrectPosition, MasterMindBoard.ROW_SIZE - correctPegs - pegsIncorrectPosition);

    }

    /**
     * Determines how much time has elapsed in seconds
     * @param startTime long representing nanoseconds
     * @param finishTime long representing nanoseconds
     * @return elapsed time as an int representing seconds
     * @author Jonathan
     */
    public static int findElapsedTime(long startTime, long finishTime) {
        int time = (int)((finishTime - startTime) * Math.pow(10, -9));
        return time;
    }


}
