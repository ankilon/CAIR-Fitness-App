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
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.assignment.Database.conn;
import static javafx.assignment.Database.openConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cathyxu
 */
public class MentalWellbeingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    Database d = new Database();

    @FXML
    private Rectangle home;
    @FXML
    private ImageView sad;
    @FXML
    private ImageView happy;
    @FXML
    private ImageView angry;
    @FXML
    private ImageView stressed;
    @FXML
    private Label mentalAdviceOutput;
    @FXML
    private Button close;
    @FXML
    private Rectangle outputPopUpBox;
    @FXML
    private BarChart<String, Number> barChartMindfulMins;
    @FXML
    private Label mindfulMins;
    @FXML
    private Rectangle mindfulBox;
    @FXML
    private JFXButton mindfulFAQ;
    @FXML
    private StackedAreaChart<String, Number> mood;
    @FXML
    StackedBarChart<String, Number> stackedBarChart;

    @FXML
    private DatePicker firstD;

    @FXML
    private DatePicker secondD;

    @FXML
    private DatePicker first;

    @FXML
    private DatePicker second;

    @FXML
    private Label moodValidation;

    @FXML
    private Label mindfulValidation;

    @FXML
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        close.setVisible(false);
        mentalAdviceOutput.setVisible(false);
        outputPopUpBox.setVisible(false);
        moodValidation.setVisible(false);
        mindfulValidation.setVisible(false);

        //MINDFUL MINUNTES BAR CHART
    }

    @FXML
    private void handleMindfulFAQ(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mindfulFAQ.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Mindful Minutes");
        stage.setScene(new Scene(root1));
        stage.show();

    }

    @FXML
    private void loadMood(ActionEvent event) throws IOException {

        try {

            stackedBarChart.getData().clear();
            LocalDate date1 = first.getValue();
            String d1 = date1.toString();

            LocalDate date2 = second.getValue();
            String d2 = date2.toString();

            if (date2.isAfter(date1)) {
                openConnection();
                Statement st = conn.createStatement();
                String selectQuery1 = "SELECT * FROM MentalWellbeing WHERE date "
                        + "BETWEEN '" + d1 + "' AND '" + d2 + "';";

                ResultSet rs = st.executeQuery(selectQuery1);
                XYChart.Series<String, Number> sadChart = new XYChart.Series<>();
                XYChart.Series<String, Number> okChart = new XYChart.Series<>();
                XYChart.Series<String, Number> happyChart = new XYChart.Series<>();

                happyChart.setName("Happy Hours");
                okChart.setName("Ok Hours");
                sadChart.setName("Sad Hours");
                stackedBarChart.setTitle("Mood Hours");

                while (rs.next()) {

                    System.out.println("result set exists");

                    sadChart.getData().add(new XYChart.Data<>(rs.getString("date"), rs.getInt("hrsSad")));
                    okChart.getData().add(new XYChart.Data<>(rs.getString("date"), rs.getInt("hrsOk")));
                    happyChart.getData().add(new XYChart.Data<>(rs.getString("date"), rs.getInt("hrsHappy")));

                }
                stackedBarChart.getData().addAll(sadChart, okChart, happyChart);
                System.out.println("steps stacked chart created");
            } else if (date2.isBefore(date1)) {
                moodValidation.setText("Invalid date range. Please try again.");
                moodValidation.setVisible(true);
            }
        } catch (SQLException ex) {

            System.out.println("steps stacked bar chart not created");
        }
    }

    @FXML
    private void loadMindful(ActionEvent event) {
        try {
            barChartMindfulMins.getData().clear();
            LocalDate date1 = firstD.getValue();
            String d1 = date1.toString();

            LocalDate date2 = secondD.getValue();
            String d2 = date2.toString();

            openConnection();
            Statement st = conn.createStatement();
            if (date2.isAfter(date1)) {
                String selectQuery1 = "SELECT * FROM MentalWellbeing WHERE date BETWEEN '" + d1 + "' AND '" + d2 + "';";
                ResultSet rs = st.executeQuery(selectQuery1);

                XYChart.Series<String, Number> mindfulMins = new XYChart.Series<>();
                mindfulMins.setName("Mindful Minutes per Day");

                while (rs.next()) {

                    mindfulMins.getData().add(new XYChart.Data<>(rs.getString("date"), rs.getInt("mindfulMins")));
                    System.out.println("result set exists");
                }

                barChartMindfulMins.getData().add(mindfulMins);
                System.out.println("mindful mins chart created");

            } else if (date2.isBefore(date1)) {
                mindfulValidation.setText("Invalid date range. Please try again.");
                mindfulValidation.setVisible(true);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MentalWellbeingController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("mindful mins chart not created");
        }
    }

    @FXML
    private void handleSad(MouseEvent event) throws IOException {
        outputPopUpBox.setVisible(true);
        mentalAdviceOutput.setVisible(true);
        close.setVisible(true);

        mentalAdviceOutput.setText("It's ok to feel sad! If you're feeling down, try to discover what you can do to feel better again. For example, find a creative way to express your feelings, talk to someone, or even see a professional! ");
    }

    @FXML
    private void handleHappy(MouseEvent event) throws IOException {
        outputPopUpBox.setVisible(true);
        mentalAdviceOutput.setVisible(true);
        close.setVisible(true);

        mentalAdviceOutput.setText("Happiness is the key to a fulfilling life. Why not do something spontaneous such as going on a roadtrip! Keep smiling!");

    }

    @FXML
    private void handleAngry(MouseEvent event) throws IOException {
        outputPopUpBox.setVisible(true);
        mentalAdviceOutput.setVisible(true);
        close.setVisible(true);

        mentalAdviceOutput.setText("We can feel angry due to many reasons, however there are many ways that can help you feel better. There is nothing wrong with being angry, but it is important that you know how to cope with it. Some good ways to cope with anger include finding out why you're angry, writing it down, exercising or doing something relaxing!");
    }

    @FXML
    private void handleOk(MouseEvent event) throws IOException {
        outputPopUpBox.setVisible(true);
        mentalAdviceOutput.setVisible(true);
        close.setVisible(true);

        mentalAdviceOutput.setText("There are many ways to boost your mood! Why not learn a new sport, start writing a new journal or even start your own mini garden! Occupying yourself with activities you enjoy will boost your mood when you are not feeling the best!");
    }

    @FXML
    private void handleMindful(MouseEvent event) throws IOException {
        mindfulBox.setVisible(true);
        mindfulMins.setVisible(true);
        close.setVisible(true);
    }

    @FXML
    private void handleClose(ActionEvent event) throws IOException {
        outputPopUpBox.setVisible(false);
        mentalAdviceOutput.setVisible(false);
        close.setVisible(false);

    }

    @FXML
    private void handleRecommendedWorkout(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recommendedResistanceRoutine.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Resistance Exercise Workout Plan Example");
        stage.setScene(new Scene(root1));
        stage.show();
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

}
