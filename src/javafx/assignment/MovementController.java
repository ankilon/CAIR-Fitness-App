 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author cathyxu
 */
public class MovementController implements Initializable {

    /**
     * Initializes the controller class.
     */
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    @FXML
    private Rectangle home;

    @FXML
    private Rectangle steps;

    @FXML
    private Rectangle activities;

    Database d = new Database();

    @FXML
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");
    }

    @FXML
    private void handleSteps(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Steps.fxml");
    }

    @FXML
    private void handleActivities(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Activities.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
