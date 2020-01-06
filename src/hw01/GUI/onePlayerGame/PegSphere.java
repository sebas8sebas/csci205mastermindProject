/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/28/2019
 * Time: 11:28 AM
 *
 * Project: csci205_hw
 * Package: hw01.GUI.onePlayerGame
 * Class: Peg
 *
 * Description:
 * Class for the GUI representation of a peg
 * ****************************************
 */
package hw01.GUI.onePlayerGame;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 * Class for the GUI representation of a peg
 * @author Sebastian
 * @author Jonathan
 */
public class PegSphere extends Sphere {

    /** Color of the peg */
    private Paint color;

    /**
     * Creates a new instance of {@code Sphere} with the given radius.
     * The resolution defaults to 64 divisions along the sphere's axes.
     *
     * @param radius Radius
     * @author Sebastian
     */
    public PegSphere(double radius) {
        super(radius);
        color = Color.WHITE;
    }

    /**
     * Retrieves the color of the peg
     * @return Paint representing the color
     * @author Sebastian
     */
    public Paint getColor() {
        return color;
    }

    /**
     * Sets the color of the sphere
     * @param color Paint representing the new color
     * @author Sebastian
     */
    public void setColor(Paint color) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor((Color)color);
        material.setSpecularColor((Color)color);
        this.color = color;
        this.setMaterial(material);
    }

}
