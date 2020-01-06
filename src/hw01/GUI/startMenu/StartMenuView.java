/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli
 * Section: 11 am
 * Date: 10/26/2019
 * Time: 12:52 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI
 * Class: StartMenuView
 *
 * Description:
 * View of the Start Menu scene
 * ****************************************
 */
package hw01.GUI.startMenu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * View of the Start Menu scene
 */
public class StartMenuView {

    /** Width of the application */
    final public static int WIDTH = 400;

    /** Height of the application */
    final public static int HEIGHT = 650;

    /** Font size of the title of the application */
    final static int TITLESZE = 50;

    /** Root node for the Start Menu */
    private VBox root;

    /** Title of the application */
    public String TITLE = "Mastermind";

    /** Single Player Game Button */
    private final Button singleBtn;

    /** Two Player Game Button */
    private final Button twoPlayerBtn;

    /** Settings Button */
    private final Button configBtn;

    /**
     * Constructor
     */
    public StartMenuView() {

        root = new VBox();
        root.setPrefWidth(WIDTH);
        root.setPrefHeight(HEIGHT);

        //root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(15);

        Label title = setTitle();
        ImageView imageView = setStartMenuImage();

        singleBtn = new StartMenuButton("Single");
        twoPlayerBtn = new StartMenuButton("2 players");
        configBtn = new StartMenuButton("Settings");

        root.getChildren().addAll(title, imageView, singleBtn, twoPlayerBtn, configBtn);

        root.setStyle("-fx-background-color: #4b6a80");
    }

    /**
     * Set the title of the application
     * @return Label with the title of the application
     */
    private Label setTitle() {
        Label title = new Label(TITLE);
        title.setFont(Font.font(null, FontWeight.BOLD, null, TITLESZE));
        return title;
    }

    /**
     * Set the start menu image
     * @return ImageView containing the Start Menu image
     */
    private ImageView setStartMenuImage() {
        Image image = null;
        try {
            image = new Image(new FileInputStream("images/icon1.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(HEIGHT / 4);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public VBox getRoot() {
        return root;
    }

    public Button getSingleBtn() {
        return singleBtn;
    }

    public Button getConfigBtn() {
        return configBtn;
    }

    public Button getTwoPlayerBtn() {
        return twoPlayerBtn;
    }
}
