/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.assignment.Database.conn;
import static javafx.assignment.Database.openConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author rachel.jiang
 */
public class PhysicalActivityController implements Initializable {

    /**
     * Initializes the controller class.
     */
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    @FXML
    private Rectangle home;

    @FXML
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");
    }
    Database d = new Database();

    @FXML
    private TextField aeActivityName;

    @FXML
    private TextField aeTimeSpent;

    @FXML
    private TextField aeCaloriesBurnt;

    @FXML
    private Button enterNewAerobicExercise;

    @FXML
    private TextField aeDate;

    @FXML
    private TextField reDate;

    @FXML
    private Label totalBurnt;

    @FXML
    private Button deleteAE;

    @FXML
    private TableView<AerobicExercisesTable> AerobicExerciseList;

    @FXML
    private TableColumn<AerobicExercisesTable, String> aeDateCol;

    @FXML
    private TableColumn<AerobicExercisesTable, String> aeActivityCol;

    @FXML
    private TableColumn<AerobicExercisesTable, Number> aeMinsSpentCol;

    @FXML
    private TableColumn<AerobicExercisesTable, Number> aeCaloriesCol;

    @FXML
    private TableView<ResistanceExercisesTable> ResistanceExerciseList;

    @FXML
    private DatePicker aerobicsDate;

    @FXML
    private DatePicker caloriesDate;

    @FXML
    private Label aeOutputLabel;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private DatePicker firstD;

    @FXML
    private DatePicker secondD;

    @FXML
    private Label validation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        aeOutputLabel.setVisible(false);
        totalBurnt.setVisible(false);
        validation.setVisible(false);
        //barchart

        //Aerobic TABLE
        aeDateCol.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());
        aeActivityCol.setCellValueFactory(
                cellData -> cellData.getValue().getActivityProperty());
        aeMinsSpentCol.setCellValueFactory(
                cellData -> cellData.getValue().getMinsSpentProperty());
        aeCaloriesCol.setCellValueFactory(
                cellData -> cellData.getValue().getCaloriesBurntProperty());
        try {

            AerobicExerciseList.setItems(this.getAerobicExerciseListData());

        } catch (SQLException ex) {
            Logger.getLogger(PhysicalActivityController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //AEROBIC EXERCISE TABLE
    @FXML
    private void loadChart(ActionEvent event) throws IOException, SQLException {
        try {

            barChart.getData().clear();
            barChart.setTitle("Minutes Spent on Aerobics");
            LocalDate date1 = firstD.getValue();
            String d1 = date1.toString();

            LocalDate date2 = secondD.getValue();
            String d2 = date2.toString();
            if (date2.isAfter(date1)) {
                openConnection();
                Statement st = conn.createStatement();
                String selectQuery = "SELECT * FROM AerobicExercises WHERE aeDate "
                        + "BETWEEN '" + d1 + "' AND '" + d2 + "';;";
                ResultSet rs = st.executeQuery(selectQuery);
                XYChart.Series<String, Number> series1 = new XYChart.Series<>();
                series1.setName("Minutes Spent");
                while (rs.next()) {
                    System.out.println("result set exists");
                    series1.getData().add(new XYChart.Data<>(rs.getString("aeDate"), rs.getInt("minsSpent")));
                    System.out.println(rs.getString("aeDate"));
                    System.out.println(rs.getInt("minsSpent"));
                }
                barChart.getData().add(series1);
                System.out.println("phys act bar chart  created");
            } else if (date2.isBefore(date1)) {
                validation.setText("Invalid date range. Please try again");
                validation.setVisible(true);
            }
        } catch (SQLException ex) {
            System.out.println("phys act bar chart not created");

        }

    }
    
    //total calories burnt for the day
    @FXML
    private void loadCalories(ActionEvent event) throws IOException, SQLException {

        LocalDate newDate = caloriesDate.getValue();
        String newToday = newDate.toString();
        int total = 0;
        System.out.println(newToday);
        System.out.println("it works");

        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM AerobicExercises "
                    + "NATURAL JOIN ResistanceExercises "
                    + "WHERE aeDate = '" + newToday + "' AND reDate =  '" + newToday + "'";
            ResultSet rs = st.executeQuery(selectQuery);
            System.out.println("hope it works lmao");
            int caloriesBurntTotal = 0;
            while (rs.next()) {
                System.out.println(rs.getString("aeDate"));
                System.out.println(rs.getString("reDate"));
                System.out.println("result set exists");
                System.out.println(rs.getInt("aeCaloriesBurnt"));
                System.out.println(rs.getInt("reCaloriesBurnt"));
                //  System.out.println(rs.getInt("SUM(caloriesBurnt)"));
                caloriesBurntTotal = rs.getInt("aeCaloriesBurnt") + rs.getInt("reCaloriesBurnt"); //+ rs.getInt("SUM(reCaloriesBurnt");
                System.out.println(caloriesBurntTotal);
            }
            totalBurnt.setText(Integer.toString(caloriesBurntTotal));
            totalBurnt.setVisible(true);

        } catch (SQLException ex) {

        }

    }
    
    //aerobics exercise table
    private ObservableList<AerobicExercisesTable> getAerobicExerciseListData() throws SQLException {
        List<AerobicExercisesTable> AerobicExerciseListToReturn = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM AerobicExercises");
            while (rs.next()) {
                AerobicExerciseListToReturn.add(
                        new AerobicExercisesTable(rs.getString("aeDate"), rs.getString("aeActivity"), rs.getInt("minsSpent"), rs.getInt("aeCaloriesBurnt")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(AerobicExerciseListToReturn);
        return FXCollections.observableArrayList(AerobicExerciseListToReturn);
    }

    //deleting selected row from AEROBICS
    @FXML
    private void deleteAE(ActionEvent event) throws IOException, SQLException {

        String deleteAeActivity = AerobicExerciseList.getSelectionModel().getSelectedItem().getActivity();
        String deleteAeDate = AerobicExerciseList.getSelectionModel().getSelectedItem().getDate();
        int deleteAeMinsSpent = AerobicExerciseList.getSelectionModel().getSelectedItem().getMinsSpent();
        int deleteAeCaloriesBurnt = AerobicExerciseList.getSelectionModel().getSelectedItem().getCaloriesBurnt();
        System.out.println(deleteAeActivity);
        System.out.println(deleteAeDate);
        System.out.println(deleteAeMinsSpent);
        System.out.println(deleteAeCaloriesBurnt);

        AerobicExerciseList.getItems().removeAll(AerobicExerciseList.getSelectionModel().getSelectedItem());

        openConnection();
        Statement st = conn.createStatement();
        try {
            st.executeUpdate("DELETE FROM aerobicExercises WHERE aeDate = '" + deleteAeDate + "' AND aeActivity = '" + deleteAeActivity + "' AND  minsSpent = " + deleteAeMinsSpent + " AND aeCaloriesBurnt = " + deleteAeCaloriesBurnt + "");
            System.out.println("delete successful");
            aeOutputLabel.setText("Successfully Deleted!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("delete unsuccessful");
            aeOutputLabel.setText("Delete unsuccessful. Please ry again.");
        }

    }

    //user updates AEROBICS
    //https://stackoverflow.com/questions/18081126/convert-java-lang-string-to-java-lang-number 
    @FXML
    private void addAerobics(ActionEvent event) throws IOException, SQLException {

        LocalDate aeNewDate = aerobicsDate.getValue();
        String newAEDate = aeNewDate.toString();
        String newAEName = aeActivityName.getText().trim();
        int newAETime = Integer.parseInt(aeTimeSpent.getText().trim());
        int newCalories = Integer.parseInt(aeCaloriesBurnt.getText().trim());
        openConnection();
        Statement st = conn.createStatement();

        try {
            st.executeUpdate("INSERT INTO AerobicExercises "
                    + "(aeDate, aeActivity, minsSpent, aeCaloriesBurnt, UserID ) "
                    + "VALUES ('" + newAEDate + "', '" + newAEName + "', "
                            + "" + newAETime + "," + newCalories + ", 1);");
            aeOutputLabel.setText("Update Successfull!");
            aeOutputLabel.setVisible(true);
            System.out.println("aerobics table UPDATED");
            clearFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            aeOutputLabel.setText("Update unsuccessful. Please press re-enter your inputs.");
            aeOutputLabel.setVisible(true);

        }

        aeDateCol.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());
        aeActivityCol.setCellValueFactory(
                cellData -> cellData.getValue().getActivityProperty());
        aeMinsSpentCol.setCellValueFactory(
                cellData -> cellData.getValue().getMinsSpentProperty());
        aeCaloriesCol.setCellValueFactory(
                cellData -> cellData.getValue().getCaloriesBurntProperty());
        try {
            AerobicExerciseList.setItems(this.getAerobicExerciseListData());
        } catch (SQLException ex) {
            Logger.getLogger(PhysicalActivityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //clear TextFields
    private void clearFields() {

        aeActivityName.clear();
        aeTimeSpent.clear();
        aeCaloriesBurnt.clear();
        aerobicsDate.getEditor().clear();

    }
    //================
    //sidemenu
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
    private JFXTextField addactivity;

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
