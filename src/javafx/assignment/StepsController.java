package javafx.assignment;

import com.gluonhq.charm.glisten.control.ProgressIndicator;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.assignment.Database;
import static javafx.assignment.Database.conn;
import static javafx.assignment.Database.openConnection;
import javafx.event.ActionEvent;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author rachel.jiang
 */
public class StepsController implements Initializable {
    //intialise page switcher class and database
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    Database d = new Database();

    //labels
    @FXML
    private Label currentSteps;
    
    @FXML
    private Label currentCaloriesBurnt;
    
    @FXML
    private Label distanceToday;
    
    @FXML
    private Label stairs;
    
    @FXML
    StackedBarChart<String, Number> stackedBarChart;
    
    @FXML
    private DatePicker stepDate;
    
    @FXML
    private Label idealS;

    @FXML
    private TextField userIdeal;


    @FXML
    private DatePicker firstD;

    @FXML
    private DatePicker secondD;

    @FXML
    private Label validation;

    @FXML
    private void handleOverall(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Overall.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        currentSteps.setVisible(false);
        currentCaloriesBurnt.setVisible(false);
        distanceToday.setVisible(false);
        stairs.setVisible(false);
        validation.setVisible(false);

        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT IdealSteps FROM Ideal";

            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("beef");
                idealS.setText(Integer.toString(rs.getInt("IdealSteps")));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
//https://www.tutorialspoint.com/javafx/stacked_bar_chart.htm 
    
    @FXML
    public void updateLine(ActionEvent event) throws IOException {
        stackedBarChart.getData().clear();

        try {

            LocalDate date1 = firstD.getValue();
            String d1 = date1.toString();

            LocalDate date2 = secondD.getValue();
            String d2 = date2.toString();

            if (date2.isAfter(date1)) {
                openConnection();
                Statement st = conn.createStatement();

                String selectQuery1 = "SELECT * FROM Steps NATURAL JOIN Ideal WHERE dates BETWEEN '" + d1 + "' AND '" + d2 + "'";
                ResultSet rs = st.executeQuery(selectQuery1);
                XYChart.Series<String, Number> steps = new XYChart.Series<>();
                XYChart.Series<String, Number> steps2 = new XYChart.Series<>();

                steps.setName("Steps Per Day");
                steps2.setName("Steps Until goal");

                while (rs.next()) {

                    int stepsTillGoal = rs.getInt("idealSteps") - rs.getInt("totalSteps");
                    System.out.println("result set exists");
                    if (stepsTillGoal < 0) {
                        steps.getData().add(new XYChart.Data<>(rs.getString("dates"), rs.getInt("totalSteps")));

                    } else {
                        steps.getData().add(new XYChart.Data<>(rs.getString("dates"), rs.getInt("totalSteps")));
                        steps2.getData().add(new XYChart.Data<>(rs.getString("dates"), stepsTillGoal));
                    }
                }
                stackedBarChart.getData().addAll(steps, steps2);
                System.out.println("steps stacked chart created");
            } else if (date2.isBefore(date1)) {
                validation.setText("Invalid date range. Please try again.");
                validation.setVisible(true);
            }
        } catch (SQLException ex) {

            System.out.println("steps stacked bar chart not created");
        }
    }

    @FXML
    public void updateIdeal(ActionEvent event) throws IOException, SQLException {
        int newIdeal = Integer.parseInt(userIdeal.getText().trim());
        idealS.setText(Integer.toString(newIdeal));
        openConnection();
        Statement st = conn.createStatement();
        System.out.println(newIdeal);
        try {
            st.executeUpdate("UPDATE Ideal SET IdealSteps = " + newIdeal + " WHERE IdealID = 1");
            System.out.println("update successful");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("update unsuccessful");
        }
        userIdeal.clear();

        stackedBarChart.getData().clear();

        try {
            LocalDate date1 = firstD.getValue();
            String d1 = date1.toString();

            LocalDate date2 = secondD.getValue();
            String d2 = date2.toString();

            String selectQuery1 = "SELECT * FROM Steps NATURAL JOIN Ideal WHERE dates BETWEEN '" + d1 + "' AND '" + d2 + "'";
            ResultSet rs = st.executeQuery(selectQuery1);
            XYChart.Series<String, Number> steps = new XYChart.Series<>();
            XYChart.Series<String, Number> steps2 = new XYChart.Series<>();

            steps.setName("Steps Per Day");
            steps2.setName("Steps Until goal");

            while (rs.next()) {

                int stepsTillGoal = rs.getInt("idealSteps") - rs.getInt("totalSteps");
                System.out.println("result set exists");
                if (stepsTillGoal < 0) {
                    steps.getData().add(new XYChart.Data<>(rs.getString("dates"), rs.getInt("totalSteps")));

                } else {
                    steps.getData().add(new XYChart.Data<>(rs.getString("dates"), rs.getInt("totalSteps")));
                    steps2.getData().add(new XYChart.Data<>(rs.getString("dates"), stepsTillGoal));
                }
            }
            stackedBarChart.getData().addAll(steps, steps2);
            System.out.println("steps stacked chart created");

        } catch (SQLException ex) {

            System.out.println("steps stacked bar chart not created");
        }
    }

    //progress bars
    @FXML
    private ProgressIndicator totalStepsIndicator;
    @FXML
    private ProgressIndicator caloriesBurntIndicator;
    @FXML
    private ProgressIndicator totalDistanceIndicator;
    @FXML
    private ProgressIndicator flightStairsIndicator;

    @FXML
    private void handleLoadSteps(ActionEvent event) {

        LocalDate reNewDate = stepDate.getValue();
        String userDate = reNewDate.toString();

        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM Steps WHERE dates = '" + userDate + "' ";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                currentSteps.setText(Integer.toString(rs.getInt("totalSteps")));

                System.out.println("this query exists");
                int totalSteps = rs.getInt("totalSteps");

                if (totalSteps < 4000) {
                    totalStepsIndicator.setProgress(0.14F);
                } else if (totalSteps >= 4000 && totalSteps < 5000) {
                    totalStepsIndicator.setProgress(0.39F);
                } else if (totalSteps >= 5000 && totalSteps < 6000) {
                    totalStepsIndicator.setProgress(0.49F);
                } else if (totalSteps >= 6000 && totalSteps < 6200) {
                    totalStepsIndicator.setProgress(0.56F);
                } else if (totalSteps >= 6000 && totalSteps < 6500) {
                    totalStepsIndicator.setProgress(0.66F);
                } else if (totalSteps >= 6500 && totalSteps < 7000) {
                    totalStepsIndicator.setProgress(0.790F);
                } else if (totalSteps >= 7000 && totalSteps < 8000) {
                    totalStepsIndicator.setProgress(0.85F);
                } else if (totalSteps >= 8000 && totalSteps < 9000) {
                    totalStepsIndicator.setProgress(0.93F);
                } else {
                    totalStepsIndicator.setProgress(0.99F);
                }

            }

            System.out.println("update steps created");
            currentSteps.setVisible(true);

        } catch (SQLException ex) {

            System.out.println("update to steps table not successful");
            ex.printStackTrace();

        }

        //current calories burnt
        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM Steps WHERE dates = '" + userDate + "' ";
            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                currentCaloriesBurnt.setText(Integer.toString(rs.getInt("caloriesBurnt")));

                System.out.println("this query exists");
                int caloriesBurnt = rs.getInt("caloriesBurnt");
                if (caloriesBurnt < 240) {
                    caloriesBurntIndicator.setProgress(0.20F);
                } else if (caloriesBurnt >= 250 && caloriesBurnt < 350) {
                    caloriesBurntIndicator.setProgress(0.47F);

                } else if (caloriesBurnt >= 350 && caloriesBurnt < 450) {
                    caloriesBurntIndicator.setProgress(0.68F);
                } else if (caloriesBurnt >= 450 && caloriesBurnt < 550) {
                    caloriesBurntIndicator.setProgress(0.83F);
                } else {
                    caloriesBurntIndicator.setProgress(0.95F);
                }

            }

            System.out.println("update calories created");
            currentCaloriesBurnt.setVisible(true);

        } catch (SQLException ex) {

            System.out.println("update to steps table not successful");
            ex.printStackTrace();

        }

        //total distance today
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM Steps WHERE dates = '" + userDate + "' ";
            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                distanceToday.setText(Integer.toString(rs.getInt("totalDistance")));

                System.out.println("this query exists");
                int totalDistance = rs.getInt("totalDistance");
                if (totalDistance < 4) {
                    totalDistanceIndicator.setProgress(0.10F);
                } else if (totalDistance >= 4 && totalDistance < 5) {
                    totalDistanceIndicator.setProgress(0.22F);

                } else if (totalDistance >= 5 && totalDistance < 6) {
                    totalDistanceIndicator.setProgress(0.45F);
                } else if (totalDistance >= 6 && totalDistance < 7) {
                    totalDistanceIndicator.setProgress(0.68F);
                } else if (totalDistance >= 7 && totalDistance < 8) {
                    totalDistanceIndicator.setProgress(0.80F);
                } else {
                    totalDistanceIndicator.setProgress(0.95F);
                }
            }

            System.out.println("update total distance created");
            distanceToday.setVisible(true);

        } catch (SQLException ex) {
            System.out.println("update to steps table not successful");
            ex.printStackTrace();

        }

        //stairs climbed 
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM Steps WHERE dates = '" + userDate + "' ";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                stairs.setText(rs.getString("flightsStairsWalked"));

                System.out.println("this query exists");

                int flightsStairs = rs.getInt("flightsStairsWalked");
                if (flightsStairs < 2) {
                    flightStairsIndicator.setProgress(0.10F);
                } else if (flightsStairs >= 2 && flightsStairs < 3) {
                    flightStairsIndicator.setProgress(0.22F);

                } else if (flightsStairs >= 3 && flightsStairs < 4) {
                    flightStairsIndicator.setProgress(0.45F);
                } else if (flightsStairs >= 4 && flightsStairs < 5) {
                    flightStairsIndicator.setProgress(0.68F);
                } else if (flightsStairs >= 5 && flightsStairs < 6) {
                    flightStairsIndicator.setProgress(0.80F);
                } else {
                    flightStairsIndicator.setProgress(0.95F);
                }

            }

            System.out.println("update flights stairs created");
            stairs.setVisible(true);

        } catch (SQLException ex) {
            System.out.println("update to steps table not successful");
            ex.printStackTrace();

        }
    }

    //===============================
    //side menu
    @FXML
    private Rectangle home;

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
    private Rectangle steps;

    @FXML
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");

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
    private void handleSteps(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Steps.fxml");

    }

    @FXML
    private void handleProfessional(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HealthProfessionalView.fxml");

    }

}
