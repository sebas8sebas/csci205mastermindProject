/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/26/2019
 * Time: 6:11 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI
 * Class: SceneTemplate
 *
 * Description:
 *
 * ****************************************
 */
package hw01.GUI.sceneTemplate;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Template for generating new scenes
 * @author Sebastian
 * @author Jonathan
 */
public abstract class SceneViewTemplate {

    /** BorderPane node for the root of all scene graphs */
    private BorderPane root;

    /** Title of the scene */
    private Label title;

    /** Menu at the top of the scene */
    private HBox menuBar;

    /** Button to go back to previous scene */
    private Button goBackBtn;

    /**
     * Constructor
     * @param w double width
     * @param h double height
     * @author Sebastian
     * @author Jonathan
     */
    public SceneViewTemplate(double w, double h) {
        initializeRoot(w, h);
        initializeBackBtn();
        initializeTitle();
        initializeMenuBar();
    }

    /**
     * Initialize the menu bar
     * @author Sebastian
     * @author Jonathan
     */
    private void initializeMenuBar() {
        //Put menu bar ar the top
        menuBar = new HBox();
        menuBar.setSpacing(30);
        menuBar.setStyle("-fx-background-color: #229fff");
        menuBar.setAlignment(Pos.TOP_LEFT);
        menuBar.getChildren().addAll(goBackBtn, title);

        root.setTop(menuBar);
    }

    /**
     * Initialize the title on the menu bar
     * @author Sebastian
     * @author Jonathan
     */
    private void initializeTitle() {
        //Title
        title = new Label();
        title.setStyle("-fx-font-size: 15");
    }

    /**
     * Initialize the back button on the menu bar
     * @author Sebastian
     * @author Jonathan
     */
    private void initializeBackBtn() {
        //Go Back Button
        goBackBtn = new Button();
        Image image = new Image("file:images/goBackArrow.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(10);
        imageView.setPreserveRatio(true);
        goBackBtn.setGraphic(imageView);
        goBackBtn.setStyle("-fx-background-color: #264cff");
    }

    /**
     * Initialize the BorderPane root
     * @param w double representing the width of the scene
     * @param h double representing the height of the scene
     * @author Sebastian
     * @author Jonathan
     */
    private void initializeRoot(double w, double h) {
        root = new BorderPane();

        //Set dimensions
        root.setPrefWidth(w);
        root.setPrefHeight(h);
    }


    public BorderPane getRoot() {
        return root;
    }

    public Label getTitle() {
        return title;
    }

    public HBox getMenuBar() {
        return menuBar;
    }

    public Button getGoBackBtn() {
        return goBackBtn;
    }
}
