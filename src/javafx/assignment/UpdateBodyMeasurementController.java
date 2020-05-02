/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import static javafx.assignment.Database.conn;
import static javafx.assignment.Database.openConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author rachel.jiang
 */
public class UpdateBodyMeasurementController implements Initializable {

    /**
     * Initializes the controller class.
     */
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    Database d = new Database();

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton update;
    @FXML
    private Label success;

    @FXML
    private DatePicker date;

    @FXML
    private TextField weight;

    @FXML
    private TextField height;

    @FXML
    private TextField fatMass;

    @FXML
    private TextField leanMass;

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "BodyMeasurements.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        success.setVisible(false);
    }

    @FXML
    private void addBody(ActionEvent event) throws IOException, SQLException {
        LocalDate reNewDate = date.getValue();
        String newDate = reNewDate.toString();
        int newWeight = Integer.parseInt(weight.getText().trim());
        int newHeight = Integer.parseInt(height.getText().trim());
        int newFat = Integer.parseInt(fatMass.getText().trim());
        int newLean = Integer.parseInt(leanMass.getText().trim());

        openConnection();
        Statement st = conn.createStatement();

        try {

            st.executeUpdate("INSERT INTO BodyMeasurements (Date, WeightAmt, LeanMassPercentage, FatMassPercentage, Height) "
                    + "VALUES ('" + newDate + "', " + newWeight + ", " + newFat + ", " + newLean + ", " + newHeight + ");");

            success.setText("Update Successfull!");
            success.setVisible(true);
            System.out.println("body measurements table UPDATED");
            clearFields();

        } catch (Exception ex) {
            ex.printStackTrace();
            success.setText("Update unsuccessful. Please press re-enter your inputs.");
            success.setVisible(true);

        }

    }

    private void clearFields() {

        weight.clear();
        height.clear();
        leanMass.clear();
        fatMass.clear();

    }
}
