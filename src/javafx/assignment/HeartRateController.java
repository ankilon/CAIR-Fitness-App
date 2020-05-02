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
import java.util.ResourceBundle;
import static javafx.assignment.Database.conn;
import static javafx.assignment.Database.openConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author cathyxu
 */
public class HeartRateController implements Initializable {

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    Database Database = new Database();
    
    @FXML
    private Rectangle home;

    @FXML
    private Label todayMax;

    @FXML
    private Label todayMin;

    @FXML
    private Label average;

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
    private LineChart<String, Number> heartratelineChart;

    @FXML
    private DatePicker heartDate;

    @FXML
    private Rectangle overall;

    @FXML
    private Text textProfessional;

    @FXML
    private DatePicker firstD;

    @FXML
    private DatePicker secondD;

    @FXML
    private Label validation;
    
    @FXML
    private ProgressIndicator maximumHRIndicator;

    @FXML
    private ProgressIndicator minimumHRIndicator;

    @FXML
    private ProgressIndicator averageHRIndicator;

    @FXML
    private void handleOverall(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Overall.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        validation.setVisible(false);
    }

    @FXML
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");
    }



    @FXML
    private void handleLoad(ActionEvent event) throws IOException {
        LocalDate reNewDate = heartDate.getValue();
        String userDate = reNewDate.toString();
        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM HeartRate WHERE Date = '" + userDate + "'";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                todayMax.setText(Integer.toString(rs.getInt("Maximum")));

                System.out.println("this query exists");
                int maximum = rs.getInt("Maximum");
                if (maximum < 100) {
                    maximumHRIndicator.setProgress(0.10F);

                } else if (maximum >= 100 && maximum < 130) {
                    maximumHRIndicator.setProgress(0.30F);

                } else if (maximum >= 120 && maximum < 150) {
                    maximumHRIndicator.setProgress(0.49F);
                } else if (maximum >= 150 && maximum < 180) {
                    maximumHRIndicator.setProgress(0.68F);
                } else if (maximum >= 180 && maximum < 210) {
                    maximumHRIndicator.setProgress(0.80F);
                } else if (maximum >= 210 && maximum < 230) {
                    maximumHRIndicator.setProgress(0.95F);
                } else {
                    maximumHRIndicator.setProgress(0.99F);
                }

            }

        } catch (SQLException ex) {

            System.out.println("update to hr max not successful");
            ex.printStackTrace();

        }

        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM HeartRate WHERE Date = '" + userDate + "'";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                todayMin.setText(Integer.toString(rs.getInt("Minimum")));

                System.out.println("this query exists");
                int minimum = rs.getInt("Minimum");
                if (minimum < 60) {
                    minimumHRIndicator.setProgress(0.10F);
                } else if (minimum >= 60 && minimum < 65) {
                    minimumHRIndicator.setProgress(0.28F);

                } else if (minimum >= 65 && minimum < 70) {
                    minimumHRIndicator.setProgress(0.42);
                } else if (minimum >= 70 && minimum < 75) {
                    minimumHRIndicator.setProgress(0.59);
                } else if (minimum >= 75 && minimum < 80) {
                    minimumHRIndicator.setProgress(0.77F);
                } else if (minimum >= 80 && minimum <= 90) {
                    minimumHRIndicator.setProgress(0.88F);
                } else {
                    minimumHRIndicator.setProgress(0.99F);
                }

            }

        } catch (SQLException ex) {

            System.out.println("update to hr min not successful");
            ex.printStackTrace();

        }
        //average
        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM HeartRate WHERE Date = '" + userDate + "'";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                average.setText(Integer.toString(rs.getInt("Average")));

                System.out.println("this query exists");
                int average = rs.getInt("Average");
                if (average < 60) {
                    averageHRIndicator.setProgress(0.10F);
                } else if (average >= 60 && average < 65) {
                    averageHRIndicator.setProgress(0.28F);

                } else if (average >= 65 && average < 70) {
                    averageHRIndicator.setProgress(0.39);
                } else if (average >= 70 && average < 75) {
                    averageHRIndicator.setProgress(0.54);
                } else if (average >= 75 && average < 80) {
                    averageHRIndicator.setProgress(0.66F);
                } else if (average >= 80 && average < 85) {
                    averageHRIndicator.setProgress(0.79F);
                } else if (average >= 85 && average < 90) {
                    averageHRIndicator.setProgress(0.88F);
                } else {
                    averageHRIndicator.setProgress(0.97F);
                }

            }

        } catch (SQLException ex) {

            System.out.println("update to hr average not successful");
            ex.printStackTrace();

        }
    }

    @FXML
    private void loadChart(ActionEvent event) throws IOException {
        LocalDate date1 = firstD.getValue();
        String d1 = date1.toString();

        LocalDate date2 = secondD.getValue();
        String d2 = date2.toString();

        try {
            heartratelineChart.getData().clear();
            if (date2.isAfter(date1)) {
                openConnection();
                Statement st = conn.createStatement();
                String selectQuery = "SELECT Date, Minimum, Maximum FROM HeartRate "
                        + "WHERE Date BETWEEN '" + d1 + "' AND '" + d2 + "';";
                ResultSet rs = st.executeQuery(selectQuery);

                while (rs.next()) {
                    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
                    series1.setName("Minimum and Maximum Heartrate for " + rs.getString("Date"));
                    series1.getData().add(new XYChart.Data<>(rs.getString("Date"), rs.getInt("Minimum")));
                    series1.getData().add(new XYChart.Data<>(rs.getString("Date"), rs.getInt("Maximum")));
                    heartratelineChart.getData().add(series1);

                }

                System.out.println("heartrate line created");
            } else if (date2.isBefore(date1)) {
                validation.setText("Invalid date range. Please try again.");
                validation.setVisible(true);

            }
        } catch (SQLException ex) {
            System.out.println("heartrate line not created");
        }

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
    private void handleProfessional(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HealthProfessionalView.fxml");

    }

}
