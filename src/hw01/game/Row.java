/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom and Sebastian Ascoli
 * Section: 9am / 11 am
 * Date: 10/6/2019
 * Time: 6:01 PM
 *
 * Project: csci205_hw
 * Package: hw01
 * Class: Row
 *
 * Description:
 *
 * ****************************************
 */
package hw01.game;


import java.util.Objects;

/**
 * Row class
 * row objects store the number of correct pegs, pegs in incorrect position and incorrect pegs
 * from a user's guess in the MasterMind game
 */
public class Row {

    /**
     * Number of correct pegs
     */
    private int correctPegs;

    /**
     * Number of pegs in incorrect position
     */
    private int pegsIncorrectPosition;

    /**
     * Number of incorrect pegs
     */
    private int incorrectPegs;

    /**
     * Constructor
     * @param correctPegs number of correct pegs (red or *)
     * @param pegsInCorrectPosition number of pegs in incorrect position (white or +)
     * @param incorrectPegs number of incorrect pegs (-)
     * @author Jonathan
     * @author Sebastian
     */
    public Row(int correctPegs, int pegsInCorrectPosition, int incorrectPegs) {
        this.correctPegs = correctPegs;
        this.pegsIncorrectPosition = pegsInCorrectPosition;
        this.incorrectPegs = incorrectPegs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return correctPegs == row.correctPegs &&
                pegsIncorrectPosition == row.pegsIncorrectPosition &&
                incorrectPegs == row.incorrectPegs;
    }

    @Override
    public int hashCode() {
        return Objects.hash(correctPegs, pegsIncorrectPosition, incorrectPegs);
    }

    public int getCorrectPegs() {
        return correctPegs;
    }

    public int getPegsIncorrectPosition() {
        return pegsIncorrectPosition;
    }

    public int getIncorrectPegs() {
        return incorrectPegs;
    }


    /**
     * Gets string representation of object
     * for example: a row object with 2 correct pegs, 1 peg in incorrect position and
     * 1 incorrect peg would be represented as **+-
     * @return
     * @author Jonathan
     * @author Sebastian
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.correctPegs; i++) {
            s += "*";
        }
        for (int i = 0; i < this.pegsIncorrectPosition; i++) {
            s += "+";
        }
        for (int i = 0; i < this.incorrectPegs; i++) {
            s += "-";
        }
        return  s;
    }
}
