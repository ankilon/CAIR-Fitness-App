/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author cathyxu
 */
public class GymAttendanceController implements Initializable {

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView arrowHome;

    @FXML
    private Text homeText;
    @FXML
    private Label attendance;

    @FXML
    private Label mins;

    @FXML
    private Button addUpdate;

    @FXML
    private JFXButton yes;

    @FXML
    private JFXButton no;

    @FXML
    private DatePicker today;

    @FXML
    private TextField userMinutes;

    @FXML
    private Label addLabel;

    @FXML
    private Label since1;

    @FXML
    private Label since2;

    @FXML
    private Label avg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        attendance.setVisible(false);
        mins.setVisible(false);
        userMinutes.setVisible(false);
        addLabel.setVisible(false);
        addUpdate.setVisible(false);

        try {
//int count = 0;
            openConnection();
            Statement st = conn.createStatement();

            String selectQuery = "SELECT COUNT(*) FROM gymAttendance "
                    + "WHERE attendance = 'Yes' ORDER BY gymAttId DESC LIMIT 7;";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                System.out.println(rs.getString(1));

                attendance.setText(rs.getString(1));
                attendance.setVisible(true);
                System.out.println("this query exists");

            }

        } catch (SQLException ex) {

            System.out.println("update to gym not successful");
            ex.printStackTrace();

        }
//total minutes spent at gym 
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT SUM(minsSpent) FROM gymAttendance;";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                mins.setText(rs.getString(1));
                mins.setVisible(true);
                System.out.println("print hours works !");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT date FROM gymAttendance ORDER BY gymAttId LIMIT 1;";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                since1.setText("Since " + rs.getString(1));
                since2.setText("Since " + rs.getString(1));
                mins.setVisible(true);
                System.out.println("print hours works !");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
//SOURCE: https://www.w3resource.com/sqlite/aggregate-functions-and-grouping-count.php
//avg minutes per visit
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT SUM(minsSpent), COUNT(*) FROM gymAttendance "
                    + "WHERE attendance = 'Yes';";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {

                avg.setText(Integer.toString(rs.getInt("SUM(minsSpent)") / rs.getInt(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");
    }

    @FXML
    private void handleNo(ActionEvent event) throws IOException, SQLException {
        LocalDate aeNewDate = today.getValue();
        String newToday = aeNewDate.toString();
        openConnection();
        Statement st = conn.createStatement();
        try {

            st.executeUpdate("INSERT INTO gymAttendance (UserId, date, attendance, minsSpent) "
                    + "VALUES (1, '" + newToday + "', 'No', 0);");
            addLabel.setText("Update Successfull!");
            addLabel.setVisible(true);
            System.out.println("no to gym table UPDATED");

        } catch (Exception ex) {
            ex.printStackTrace();
            addLabel.setText("Update unsuccessful. Please press re-enter your inputs.");
            addLabel.setVisible(true);

        }

    }

    @FXML
    private void handleYes(ActionEvent event) throws IOException, SQLException {
        addUpdate.setVisible(true);
        userMinutes.setVisible(true);
        no.setVisible(false);
        LocalDate aeNewDate = today.getValue();
        String newToday = aeNewDate.toString();
        int userMins = Integer.parseInt(userMinutes.getText().trim());
        System.out.println(newToday);
        openConnection();
        Statement st = conn.createStatement();
        //updating
        try {

            st.executeUpdate("INSERT INTO gymAttendance (UserId, date, attendance, minsSpent) "
                    + "VALUES (1, '" + newToday + "', 'Yes', " + userMins + ");");
            addLabel.setText("Update Successfull!");
            addLabel.setVisible(true);
            userMinutes.setVisible(true);
            System.out.println("yes to gym table UPDATED");

            clearFields();
            //conn.close();
            //st.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            addLabel.setText("Update unsuccessful. Please press re-enter your inputs.");
            addLabel.setVisible(true);

        }
        //count how many times yes
        try {

            String selectQuery = "SELECT COUNT(*) FROM gymAttendance "
                    + "WHERE attendance = 'Yes';";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                System.out.println(rs.getString(1));

                attendance.setText(rs.getString(1));
                attendance.setVisible(true);
                System.out.println("this query exists");

            }

        } catch (SQLException ex) {

            System.out.println("update to gym not successful");
            ex.printStackTrace();

        }

        //total gym attendance
        try {

            String selectQuery = "SELECT SUM(minsSpent) FROM gymAttendance; ";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                mins.setText(rs.getString(1));
                mins.setVisible(true);
                System.out.println("print hours works !");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //update average mins per visit
        try {
            
            String selectQuery = "SELECT SUM(minsSpent), COUNT(*) FROM gymAttendance "
                    + "WHERE attendance = 'Yes';";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {

                avg.setText(Integer.toString(rs.getInt("SUM(minsSpent)") / rs.getInt(2)));
            System.out.println(rs.getInt("SUM(minsSpent)")/rs.getInt(2));
                System.out.println(rs.getInt(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    private void clearFields() {
        userMinutes.clear();
    }

    @FXML
    private void handleUpdate(ActionEvent event) throws IOException {

    }

    //=================================
    @FXML
    private Rectangle steps;

    @FXML
    private Rectangle physicalActivity;

    @FXML
    private Rectangle nutrition;

    @FXML
    private Rectangle bodyMeasurments;

    @FXML
    private Rectangle sleep;

    @FXML
    private Rectangle heartRate;

    @FXML
    private Rectangle mentalWellbeing;

    @FXML
    private Rectangle professional;

    @FXML
    private Rectangle aerobic;

    @FXML
    private Rectangle resistance;

    @FXML
    private Rectangle gym;

    @FXML
    private Rectangle home;

    @FXML
    private Rectangle overall;

    @FXML
    private Text textProfessional;

    @FXML
    private void handleOverall(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Overall.fxml");

    }

    @FXML
    private void handleSteps(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Steps.fxml");

    }

    @FXML
    private void handlePhysicalActivity(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "PhysicalActivity.fxml");

    }

    @FXML
    private void handleNutrition(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Nutrition.fxml");

    }

    @FXML
    private void handleSleep(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Sleep.fxml");

    }

    @FXML
    private void handleHeartRate(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HeartRate.fxml");

    }

    @FXML
    private void handleBodyMeasurements(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "BodyMeasurements.fxml");

    }

    @FXML
    private void handleMentalWellbeing(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "MentalWellbeing.fxml");

    }

    @FXML
    private void handleAerobic(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "PhysicalActivity.fxml");

    }

    @FXML
    private void handleResistance(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "ResistanceExercise.fxml");

    }

    @FXML
    private void handleGym(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "GymAttendance.fxml");

    }

    @FXML
    private void handleProfessional(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HealthProfessionalView.fxml");

    }

}
