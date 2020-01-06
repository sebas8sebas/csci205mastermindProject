/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/26/2019
 * Time: 8:55 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI.settings
 * Class: SettingsController
 *
 * Description:
 * Controller for the setting of the application
 * ****************************************
 */
package hw01.GUI.settings;

import hw01.GUI.sceneTemplate.SceneViewTemplateController;
import hw01.game.MasterMindBoard;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for the setting of the application
 * @author Sebastian
 * @author Jonathan
 */
public class SettingsController extends SceneViewTemplateController {

    /**
     * Constructor
     * @param primaryStage Stage for the application
     * @param prevScene Scene object representing the previous scene
     * @param view SettingsView
     * @author Sebastian
     */
    public SettingsController(Stage primaryStage, Scene prevScene, SettingsView view) {
        super(primaryStage, prevScene, view);

        view.getApplyBtn().setOnAction(event -> {
            int maxRows = Integer.parseInt(view.getComboBoxMaxRows().getValue());
            int rowSize = Integer.parseInt(view.getComboBoxRowSize().getValue());
            MasterMindBoard.DEFAULT_MAXIMUM_ATTEMPTS = maxRows;
            MasterMindBoard.ROW_SIZE = rowSize;

            primaryStage.setScene(prevScene);
        });

    }

}
