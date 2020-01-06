/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Names: Jonathan Basom and Sebastian Ascoli
 * Section: 9am / 11 am
 * Date: 10/30/2019
 * Time: 5:45 PM
 *
 * Project: csci205_hw
 * Package: hw01
 * Class: PegColor
 *
 * Description:
 * Class to representing the colors of a peg
 * ****************************************
 */

package hw01.GUI.onePlayerGame;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to representing the colors of a peg
 * @author Sebastian
 * @author Jonathan
 */
public final class PegColor {

    /** List of Peg Colors */
    final static List<Paint> colors = new ArrayList<Paint>(Arrays.asList(Color.BLUE, Color.GREEN, Color.ORANGE, Color.PURPLE, Color.RED, Color.YELLOW));

    /**
     * Returns the numeric value of the color
     * @param color Color of the peg
     * @return the numeric value of the color or 0 if it is unselected
     * @author Sebastian
     */
    public static int getColorNumber(Paint color) {
        return colors.indexOf(color) + 1;
    }

    /**
     * Gets the next color
     * @param cur Paint object representing the current color of the Peg
     * @return Paint object for the new color of the peg
     * @author Sebastian
     */
    public static Paint getNextColor(Paint cur){
        return colors.get(getColorNumber(cur) % colors.size());
    }

}
