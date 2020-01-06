/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom
 * Section: 9am
 * Date: 10/17/2019
 * Time: 1:16 PM
 *
 * Project: csci205_hw
 * Package: hw01
 * Class: MasterMindBoardException
 *
 * Description:
 *
 * ****************************************
 */
package hw01.game;

/**
 * Exception for MasterMindBoard object
 * @author Sebastian
 */
public class MasterMindBoardException extends Exception{

    /**
     * Constructor
     * @param m String message to be displayed
     */
    MasterMindBoardException(String m){
        super(m);
    }

}
