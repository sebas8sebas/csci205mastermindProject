/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/26/2019
 * Time: 7:31 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI.sceneTemplate
 * Class: SceneViewTemplateController
 *
 * Description:
 * Template for Controllers of all scenes
 * ****************************************
 */
package hw01.GUI.sceneTemplate;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Template for Controllers of all scenes
 * @author Sebastian
 * @author Jonathan
 */
public abstract class SceneViewTemplateController {

    /** View to control */
    private SceneViewTemplate theView;

    /** Previous scene of the application */
    private Scene prevScene;

    /**
     * Constructor
     * @param primaryStage Stage object for the application
     * @param prevScene Scene object representing the previous scene of the application
     * @param view SceneViewTemplate
     * @author Sebastian
     * @author Jonathan
     */
    public SceneViewTemplateController(Stage primaryStage, Scene prevScene, SceneViewTemplate view) {
        theView = view;

        prevScene = prevScene;

        Scene finalPrevScene = prevScene;
        view.getGoBackBtn().setOnAction(event -> {
            primaryStage.setScene(finalPrevScene);

        });
    }

    /**
     * Retrieve the controller's view
     * @return ScreenViewTemplate
     */
    public SceneViewTemplate getTheView() {
        return theView;
    }
}
