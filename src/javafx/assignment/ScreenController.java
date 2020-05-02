/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;
//YEEEEEEET

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author cathyxu
 */
public class ScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //comment from cathy x 2 
    @FXML
    private ImageView sleep;

    @FXML
    private ImageView nutrition;

    @FXML
    private ImageView physActivity;

    @FXML
    private ImageView measurements;

    @FXML
    private ImageView heartRate;

    @FXML
    private ImageView overall;

    @FXML
    private ImageView steps;

    @FXML
    private ImageView forMyHealthProfessional;

    @FXML
    private Pane paneSteps;

    @FXML
    private Pane panePhysical;

    @FXML
    private Pane paneNutrition;

    @FXML
    private Pane paneSleep;

    @FXML
    private Pane paneHeartRate;

    @FXML
    private Pane paneBody;

    @FXML
    private Pane paneMental;

    @FXML
    private Pane paneProfessional;
    
    @FXML
    private Pane paneOverall;

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    Database d = new Database();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleSleepButton(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Sleep.fxml");
    }
    
    @FXML
    private void handleOverall(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Overall.fxml");
    }

    @FXML
    private void handleNutritionButton(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Nutrition.fxml");
    }

    @FXML
    private void handlePhysicalActivityButton(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "PhysicalActivity.fxml");
    }

    @FXML
    private void handleBodyMeasurementsButton(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "BodyMeasurements.fxml");
    }

    @FXML
    private void handleHeartRateButton(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HeartRate.fxml");
    }

    

    @FXML
    private void handleStepsButton(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Steps.fxml");
    }

    @FXML
    private void handleMentalButton(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "MentalWellbeing.fxml");
    }

    @FXML
    private void handleProfessional(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HealthProfessionalView.fxml");

    }
    /*
    @FXML
    private void handleHealthProfessionalButton(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Measurements.fxml");
    }
     */
}
