/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import com.jfoenix.controls.JFXSpinner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rachel.jiang
 */
public class OverallController implements Initializable {

    @FXML
    private ProgressIndicator indicatorTest;

    @FXML
    private JFXSpinner stepsSpinner;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sleepLabel.setVisible(false);
        physActOverall.setVisible(false);
        physOrangeCircle.setVisible(false);
        physGreenCircle.setVisible(false);
        physRedCircle.setVisible(false);
        sleepGreenCircle.setVisible(false);
        sleepRedCircle.setVisible(false);
        sleepOrangeCircle.setVisible(false);
        stepsLabel.setVisible(false);
        stepsGreenCircle.setVisible(false);
        stepsRedCircle.setVisible(false);
        stepsOrangeCircle.setVisible(false);
        stepsSpinner.setVisible(false);
        attendanceGreenCircle.setVisible(false);
        attendanceRedCircle.setVisible(false);
        gymLabel.setVisible(false);
        weightLabel.setVisible(false);
        mindfulMinsLabel.setVisible(false);
        mindfulGreenCircle.setVisible(false);
        mindfulRedCircle.setVisible(false);
        moodLabel.setVisible(false);
        mindfulOrangeCircle.setVisible(false);
        bmiLabel.setVisible(false);
        bmiRedCircle.setVisible(false);
        bmiOrangeCircle.setVisible(false);
        bmiGreenCircle.setVisible(false);
        bmiIndicatorLabel.setVisible(false);


    }
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

   
    //======================================
    @FXML
    private Rectangle home;

    @FXML
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");
    }

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
    private Rectangle overall;

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

    @FXML
    private void handleOverall(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Overall.fxml");

    }

    //DATE PICKER AND COLOUR SWITCHER
    @FXML
    private DatePicker overallDate;

    @FXML
    private Label physActOverall;

    @FXML
    private Circle physOrangeCircle;

    @FXML
    private Circle physGreenCircle;

    @FXML
    private Circle physRedCircle;

    @FXML
    private Circle sleepOrangeCircle;

    @FXML
    private Circle sleepRedCircle;

    @FXML
    private Circle sleepGreenCircle;

    @FXML
    private Circle stepsRedCircle;

    @FXML
    private Circle stepsOrangeCircle;

    @FXML
    private Circle stepsGreenCircle;

    @FXML
    private Label sleepLabel;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label gymLabel;

    @FXML
    private Circle attendanceRedCircle;

    @FXML
    private Circle attendanceGreenCircle;

    @FXML
    private Label weightLabel;

    @FXML
    private Label bmiLabel;

    @FXML
    private Label mindfulMinsLabel;

    @FXML
    private Circle mindfulGreenCircle;

    @FXML
    private Circle mindfulRedCircle;

    @FXML
    private Circle mindfulOrangeCircle;

    @FXML
    private Label moodLabel;
    
    @FXML
    private Button FAQ;

    @FXML
    private Circle bmiRedCircle;

    @FXML
    private Circle bmiOrangeCircle;

    @FXML
    private Circle bmiGreenCircle;
    
    @FXML
    private Label bmiIndicatorLabel;

    @FXML
    private void loadOverallDate(ActionEvent event) throws IOException, SQLException {
        LocalDate selectedDate = overallDate.getValue();
        String todayDate = selectedDate.toString();

        //PHYSICAL ACTIVITY calories
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM AerobicExercises NATURAL JOIN ResistanceExercises "
                    + "WHERE aeDate = '" + todayDate + "' AND  reDate =  '" + todayDate + "'";
            ResultSet rs = st.executeQuery(selectQuery);
            int caloriesBurntTotal = 0;
            while (rs.next()) {
                System.out.println(rs.getString("aeDate"));
                System.out.println(rs.getString("reDate"));
                System.out.println("result set exists");
                System.out.println(rs.getInt("aeCaloriesBurnt"));
                System.out.println(rs.getInt("reCaloriesBurnt"));
                //  System.out.println(rs.getInt("SUM(caloriesBurnt)"));
                caloriesBurntTotal = rs.getInt("aeCaloriesBurnt") + rs.getInt("reCaloriesBurnt"); 
                System.out.println(caloriesBurntTotal);
            }
            physActOverall.setText(Integer.toString(caloriesBurntTotal));
            physActOverall.setVisible(true);

            if (caloriesBurntTotal <= 400) {
                physOrangeCircle.setVisible(false);
                physGreenCircle.setVisible(false);
                physRedCircle.setVisible(true);
                System.out.println("phys red");
            } else if (caloriesBurntTotal > 400 && caloriesBurntTotal < 550) {

                physGreenCircle.setVisible(false);
                physRedCircle.setVisible(false);
                physOrangeCircle.setVisible(true);
                System.out.println("phys orange");
            } else {
                physOrangeCircle.setVisible(false);
                physRedCircle.setVisible(false);
                physGreenCircle.setVisible(true);
                System.out.println("phys green");
            }

        } catch (SQLException ex) {

        }

        //SLEEP
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM Sleep WHERE Date = '" + todayDate + "'";
            ResultSet rs = st.executeQuery(selectQuery);
            int hoursSlept = 0;
            while (rs.next()) {
                System.out.println(rs.getInt("HoursSlept"));
                hoursSlept = rs.getInt("HoursSlept");

            }
            if (hoursSlept < 5) {
                sleepOrangeCircle.setVisible(false);
                sleepGreenCircle.setVisible(false);
                sleepRedCircle.setVisible(true);
                System.out.println("steps red");
            } else if (hoursSlept >= 5 && hoursSlept < 8) {

                sleepGreenCircle.setVisible(false);
                sleepRedCircle.setVisible(false);
                sleepOrangeCircle.setVisible(true);
                System.out.println("steps orange");
            } else {
                sleepOrangeCircle.setVisible(false);
                sleepRedCircle.setVisible(false);
                sleepGreenCircle.setVisible(true);
                System.out.println("steps green");
            }
            sleepLabel.setText(Integer.toString(hoursSlept));
            sleepLabel.setVisible(true);

        } catch (SQLException ex) {

        }

        //STEPS
        try {
            stepsSpinner.setVisible(true);

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM Steps NATURAL JOIN Ideal WHERE dates = '" + todayDate + "'";
            ResultSet rs = st.executeQuery(selectQuery);
            int totalSteps = 0;
            int idealSteps = 0;
            while (rs.next()) {
                System.out.println(rs.getInt("totalSteps"));
                totalSteps = rs.getInt("totalSteps");
                idealSteps = rs.getInt("idealSteps");
                System.out.println(rs.getInt("idealSteps"));
            }
            if (totalSteps < 5400) {
                stepsOrangeCircle.setVisible(false);
                stepsGreenCircle.setVisible(false);
                stepsRedCircle.setVisible(true);
                System.out.println("sleep red");
            } else if (totalSteps >= 5400 && totalSteps < 8000) {

                stepsGreenCircle.setVisible(false);
                stepsRedCircle.setVisible(false);
                stepsOrangeCircle.setVisible(true);
                System.out.println("sleep orange");
            } else {
                stepsOrangeCircle.setVisible(false);
                stepsRedCircle.setVisible(false);
                stepsGreenCircle.setVisible(true);
                System.out.println("sleep green");
            }
            double percent = (double) totalSteps / idealSteps;
            double percentRound = Math.round(percent * 100.0) / 100.0;
            stepsLabel.setText(Integer.toString(totalSteps));
            stepsLabel.setVisible(true);
            System.out.println(totalSteps);
            System.out.println(idealSteps);
            System.out.println(percentRound);
            System.out.println(percent);
            stepsSpinner.setProgress(percentRound);

        } catch (SQLException ex) {

        }

        //gym attendance
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM GymAttendance WHERE date = '" + todayDate + "'";
            ResultSet rs = st.executeQuery(selectQuery);
            String attendance = "";
            while (rs.next()) {
                System.out.println("attendance");
                
                attendance = rs.getString("attendance");
                System.out.println(attendance);
            }
            gymLabel.setText(attendance);
            gymLabel.setVisible(true);

            if (attendance.equals("Yes") || attendance.equals("yes")) {
                attendanceGreenCircle.setVisible(true);
                attendanceRedCircle.setVisible(false);
                System.out.println("att red");
            } 
            else {
                attendanceRedCircle.setVisible(true);
                attendanceGreenCircle.setVisible(false);
                System.out.println("att green");
            }

        } catch (SQLException ex) {

        }
        //wellbeing
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM MentalWellbeing WHERE date = '" + todayDate + "'";
            ResultSet rs = st.executeQuery(selectQuery);
            int mindfulMins = 0;
            int hrsSad = 0;
            int hrsHappy = 0;
            int hrsOk = 0;
            while (rs.next()) {
                mindfulMins = rs.getInt("mindfulMins");
                hrsSad = rs.getInt("hrsSad");
                hrsHappy = rs.getInt("hrsHappy");
                hrsOk = rs.getInt("hrsOk");
                
                mindfulMins = rs.getInt("mindfulMins");
                System.out.println(mindfulMins);
                System.out.println(hrsSad);
                System.out.println(hrsHappy);
                System.out.println(hrsOk);
            }

            if (hrsSad > hrsHappy && hrsSad > hrsOk) {
                mindfulGreenCircle.setVisible(false);
                mindfulOrangeCircle.setVisible(false);
                mindfulRedCircle.setVisible(true);
                moodLabel.setText("Sad");
                moodLabel.setVisible(true);

            } else if (hrsHappy >= hrsSad && hrsHappy <= hrsOk) {
                System.out.println("happy");

                mindfulRedCircle.setVisible(false);
                mindfulOrangeCircle.setVisible(false);
                mindfulGreenCircle.setVisible(true);
                moodLabel.setText("Happy");
                moodLabel.setVisible(true);

            } else {
                mindfulRedCircle.setVisible(false);
                mindfulGreenCircle.setVisible(false);
                mindfulOrangeCircle.setVisible(true);
                moodLabel.setText("Ok");
                moodLabel.setVisible(true);
            }

            mindfulMinsLabel.setText(Integer.toString(mindfulMins));
            mindfulMinsLabel.setVisible(true);

        } catch (SQLException ex) {

        }

        //weight
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements WHERE date = '" + todayDate + "'";
            ResultSet rs = st.executeQuery(selectQuery);
            int WeightAmt = rs.getInt("WeightAmt");
            while (rs.next()) {

                
                WeightAmt = rs.getInt("WeightAmt");
                System.out.println(WeightAmt);
            }
            weightLabel.setText(Integer.toString(WeightAmt));
            weightLabel.setVisible(true);

        } catch (SQLException ex) {

        }

        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY bmID DESC LIMIT 1";
            ResultSet rs = st.executeQuery(selectQuery);
            double weight = rs.getInt("WeightAmt");
            double height = rs.getInt("Height");
            double BMIvalue = weight / (height / 100 * height / 100);
            double BMIrounded = Math.round(BMIvalue * 100.0) / 100.0;
            while (rs.next()) {
                bmiLabel.setText(Double.toString(BMIrounded));
                bmiLabel.setVisible(true);
                System.out.println(BMIrounded);
            }
            
            if(BMIrounded <15){
                bmiGreenCircle.setVisible(false);
                bmiOrangeCircle.setVisible(false);
                bmiRedCircle.setVisible(true);
                bmiIndicatorLabel.setText("Very Underweight");
                bmiIndicatorLabel.setVisible(true);
            }
            else if (BMIrounded >=15 && BMIrounded <18.5){
                bmiOrangeCircle.setVisible(true);
                bmiRedCircle.setVisible(false);
                bmiGreenCircle.setVisible(false);
                bmiIndicatorLabel.setText("Underweight");
                bmiIndicatorLabel.setVisible(true);
            }
            else if(BMIrounded >=18.5 && BMIrounded <25){
                bmiOrangeCircle.setVisible(false);
                bmiRedCircle.setVisible(false);
                bmiGreenCircle.setVisible(true);
                bmiIndicatorLabel.setText("Normal Weight");
                bmiIndicatorLabel.setVisible(true);
            }
            else if(BMIrounded >=25 && BMIrounded <30){
                bmiOrangeCircle.setVisible(true);
                bmiRedCircle.setVisible(false);
                bmiGreenCircle.setVisible(false);
                bmiIndicatorLabel.setText("Overweight");
                bmiIndicatorLabel.setVisible(true);
            }
            else{
                 bmiOrangeCircle.setVisible(false);
                bmiRedCircle.setVisible(true);
                bmiGreenCircle.setVisible(false);
                bmiIndicatorLabel.setText("Obese");
                bmiIndicatorLabel.setVisible(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    
    @FXML
    private void handleRecommendedWorkout(MouseEvent event) throws IOException {
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RecommendedResistanceRoutine.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Workout Recommendation");
        stage.setScene(new Scene(root1));
        stage.show();
    }
    @FXML
    private void handleFAQ(ActionEvent event) throws IOException {
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OverallFAQ.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Overall Health Ratings");
        stage.setScene(new Scene(root1));
        stage.show();
    }
}
