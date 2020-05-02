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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cathyxu
 */
public class ResistanceExerciseController implements Initializable {

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    /**
     * Initializes the controller class.
     */
    Database d = new Database();
    //INSERTING DATA
    @FXML
    private TextField reDate;

    @FXML
    private TextField reActivityName;

    @FXML
    private TextField reWeight;

    @FXML
    private TextField reReps;

    @FXML
    private TextField reSets;

    @FXML
    private TextField reCaloriesBurnt;

    @FXML
    private Button enterNewResistanceExercise;
    
    @FXML
    private ImageView recommendedResistance;

    @FXML
    private Label workoutPlanLabel;
    
    @FXML
    private Rectangle rectangleWorkoutPlan;
    //delete
    @FXML
    private Button deleteRE;

    //table view
    @FXML
    private TableView<ResistanceExercisesTable> ResistanceExerciseList;

    @FXML
    private TableColumn<ResistanceExercisesTable, String> reDateCol;

    @FXML
    private TableColumn<ResistanceExercisesTable, String> reActivityCol;

    @FXML
    private TableColumn<ResistanceExercisesTable, Number> reSetsCol;

    @FXML
    private TableColumn<ResistanceExercisesTable, Number> reRepsCol;

    @FXML
    private TableColumn<ResistanceExercisesTable, Number> reWeightCol;

    @FXML
    private TableColumn<ResistanceExercisesTable, Number> reCaloriesCol;

    //output label
    @FXML
    private Label reOutputLabel;

    @FXML
    private DatePicker resistanceDate;
    
    @FXML
    private LineChart<String, Number> lineChartWeight;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        reDateCol.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());
        reActivityCol.setCellValueFactory(
                cellData -> cellData.getValue().getActivityProperty());
        reSetsCol.setCellValueFactory(
                cellData -> cellData.getValue().getSetsProperty());
        reRepsCol.setCellValueFactory(
                cellData -> cellData.getValue().getRepsProperty());
        reWeightCol.setCellValueFactory(
                cellData -> cellData.getValue().getWeightProperty());
        reCaloriesCol.setCellValueFactory(
                cellData -> cellData.getValue().getCaloriesBurntProperty());

        try {
            ResistanceExerciseList.setItems(this.getResistanceExerciseListData());

        } catch (SQLException ex) {
            Logger.getLogger(ResistanceExerciseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery1 = "SELECT * FROM ResistanceExercise;";
            ResultSet rs = st.executeQuery(selectQuery1);
            XYChart.Series<String, Number> weight = new XYChart.Series<>();
            weight.setName("Weight (kg)");
            while (rs.next()) {
                weight.getData().add(new XYChart.Data<>(rs.getString("re"), rs.getInt("weight")));
            }

            String selectQuery2 = "SELECT * FROM BodyMeasurements NATURAL JOIN Ideal;";
            rs = st.executeQuery(selectQuery2);
            XYChart.Series<String, Number> ideal = new XYChart.Series<>();
            ideal.setName("Ideal Weight");

            while (rs.next()) {
                ideal.getData().add(new XYChart.Data<>(rs.getString("Date"), rs.getInt("idealWeight")));
            }

            lineChartWeight.getData().addAll(weight);

            System.out.println("weight line created");
        } catch (SQLException ex) {
            //  Logger.getLogger(HeartRateController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("weight line not created");
        }

    }

    //inserting data to table
    private ObservableList<ResistanceExercisesTable> getResistanceExerciseListData() throws SQLException {
        List<ResistanceExercisesTable> ResistanceExerciseListToReturn = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM ResistanceExercises");
            while (rs.next()) {
                ResistanceExerciseListToReturn.add(
                        new ResistanceExercisesTable(rs.getString("reDate"), rs.getString("reActivity"), rs.getInt("sets"), rs.getInt("reps"), rs.getInt("weight"), rs.getInt("reCaloriesBurnt")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(ResistanceExerciseListToReturn);
        return FXCollections.observableArrayList(ResistanceExerciseListToReturn);
    }

    //delete button
    @FXML
    private void deleteRE(ActionEvent event) throws IOException, SQLException {
        String deleteReDate = ResistanceExerciseList.getSelectionModel().getSelectedItem().getDate();
        String deleteReActivity = ResistanceExerciseList.getSelectionModel().getSelectedItem().getActivity();
        int deleteReSets = ResistanceExerciseList.getSelectionModel().getSelectedItem().getSets();
        int deleteReReps = ResistanceExerciseList.getSelectionModel().getSelectedItem().getReps();
        int deleteReWeight = ResistanceExerciseList.getSelectionModel().getSelectedItem().getWeight();
        int deleteReCaloriesBurnt = ResistanceExerciseList.getSelectionModel().getSelectedItem().getCaloriesBurnt();
        System.out.println(deleteReDate);
        System.out.println(deleteReActivity);
        System.out.println(deleteReSets);
        System.out.println(deleteReReps);
        System.out.println(deleteReWeight);
        System.out.println(deleteReCaloriesBurnt);
        ResistanceExerciseList.getItems().removeAll(ResistanceExerciseList.getSelectionModel().getSelectedItem());

        openConnection();
        Statement st = conn.createStatement();
        try {
            //THE CODE IS SO FKN LONG BUT IDK HOW TO MOVE IT TO THE NEXT LINE SORRY 
            st.executeUpdate("DELETE FROM resistanceExercises WHERE reDate = '" + deleteReDate + "' AND reActivity = '" + deleteReActivity + "' AND sets = " + deleteReSets + " AND reps = " + deleteReReps + " AND weight = " + deleteReWeight + " AND reCaloriesBurnt = " + deleteReCaloriesBurnt + "");

            System.out.println("delete successful");
            reOutputLabel.setText("Successfully Deleted!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("delete unsuccessful");
            reOutputLabel.setText("Delete unsuccessful. Please ry again.");
        }

    }

    //ADD DATA
    @FXML
    private void addResistance(ActionEvent event) throws IOException, SQLException {
        LocalDate reNewDate = resistanceDate.getValue();
        String newREDate = reNewDate.toString();
        String newREName = reActivityName.getText().trim();
        int newRETime = Integer.parseInt(reWeight.getText().trim());
        int newREReps = Integer.parseInt(reReps.getText().trim());
        int newRESets = Integer.parseInt(reSets.getText().trim());
        int newRECalories = Integer.parseInt(reCaloriesBurnt.getText().trim());
        openConnection();
        Statement st = conn.createStatement();
        try {
            st.executeUpdate("INSERT INTO ResistanceExercises (reDate, reActivity, sets, reps, weight, reCaloriesBurnt) "
                    + "VALUES ('" + newREDate + "', '" + newREName + "', " + newRETime + "," + newREReps + "," + newRESets + " , " + newRECalories + ");");
            reOutputLabel.setText("Update Successfull!");
            reOutputLabel.setVisible(true);
            System.out.println("resistance table UPDATED");
            clearFields();
            //conn.close();
            //st.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            reOutputLabel.setText("Update unsuccessful. Please press re-enter your inputs.");
            reOutputLabel.setVisible(true);
        }

        reDateCol.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());
        reActivityCol.setCellValueFactory(
                cellData -> cellData.getValue().getActivityProperty());
        reSetsCol.setCellValueFactory(
                cellData -> cellData.getValue().getSetsProperty());
        reRepsCol.setCellValueFactory(
                cellData -> cellData.getValue().getRepsProperty());
        reWeightCol.setCellValueFactory(
                cellData -> cellData.getValue().getWeightProperty());
        reCaloriesCol.setCellValueFactory(
                cellData -> cellData.getValue().getCaloriesBurntProperty());
        try {

            ResistanceExerciseList.setItems(this.getResistanceExerciseListData());

        } catch (SQLException ex) {
            Logger.getLogger(ResistanceExerciseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void clearFields() {
        //reDate.clear();
        reActivityName.clear();
        reWeight.clear();
        reCaloriesBurnt.clear();
        reReps.clear();
        reSets.clear();
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
    private Text textProfessional;

    @FXML
    private Rectangle home;
    
    @FXML
    private Rectangle overall;
    
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
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");
    }

    @FXML
    private void handleProfessional(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HealthProfessionalView.fxml");

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
}
