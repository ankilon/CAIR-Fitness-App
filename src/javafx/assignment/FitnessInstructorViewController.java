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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cathyxu
 */
public class FitnessInstructorViewController implements Initializable {

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    Database d = new Database();
    /**
     * Initializes the controller class.
     */
    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label birth;

    @FXML
    private Label weight;

    @FXML
    private Label height;

    @FXML
    private Button report;

    @FXML
    private Text textProfessional;

    //TABLE
    @FXML
    private TableView<Food> FoodList;
    //TABLE COLUMNS
    @FXML
    private TableColumn<Food, Number> caloriesColumn;

    @FXML
    private TableColumn<Food, String> foodNameColumn;

    @FXML
    private TableColumn<Food, String> mealTypeColumn;

    @FXML
    private TableColumn<Food, String> dateColumn;
    
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
    
     //fxml for aerobic exercises table
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

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM User; ";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                firstName.setText(rs.getString("fname"));
                lastName.setText(rs.getString("lname"));
                birth.setText(rs.getString("DOB"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY bmID DESC LIMIT 1; ";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {
                weight.setText(Integer.toString(rs.getInt("WeightAmt")));
                height.setText(Integer.toString(rs.getInt("Height")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        caloriesColumn.setCellValueFactory(
                cellData -> cellData.getValue().getCaloriesProperty());

        foodNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().getFoodNameProperty());
        mealTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().getMealTypeProperty());
        dateColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());

        FoodList.setItems(this.getFoodListData());
        
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
  
        try {
            ResistanceExerciseList.setItems(this.getResistanceExerciseListData());
        } catch (SQLException ex) {
            Logger.getLogger(FitnessInstructorViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

         
        //Aerobic TABLE
        aeDateCol.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());
        aeActivityCol.setCellValueFactory(
                cellData -> cellData.getValue().getActivityProperty());
        try {
            AerobicExerciseList.setItems(this.getAerobicExerciseListData());
        } catch (SQLException ex) {
            Logger.getLogger(FitnessInstructorViewController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    
    //phys act table
    private ObservableList<AerobicExercisesTable> getAerobicExerciseListData() throws SQLException {
        List<AerobicExercisesTable> AerobicExerciseListToReturn = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM AerobicExercises "
                    + "ORDER BY aerobicExerciseID DESC LIMIT 7"); 
            while (rs.next()) {
                AerobicExerciseListToReturn.add(
                        new AerobicExercisesTable(rs.getString("aeDate"), rs.getString("aeActivity")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(AerobicExerciseListToReturn);
        return FXCollections.observableArrayList(AerobicExerciseListToReturn);
    }
    
    //resistance act table
    private ObservableList<ResistanceExercisesTable> getResistanceExerciseListData() throws SQLException {
        List<ResistanceExercisesTable> ResistanceExerciseListToReturn = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM ResistanceExercises "
                    + "ORDER BY resistanceExerciseID DESC LIMIT 7");
            while (rs.next()) {
                ResistanceExerciseListToReturn.add(
                    new ResistanceExercisesTable(rs.getString("reDate"), 
                    rs.getString("reActivity"), rs.getInt("sets"), 
                    rs.getInt("reps"), rs.getInt("weight")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(ResistanceExerciseListToReturn);
        return FXCollections.observableArrayList(ResistanceExerciseListToReturn);
    }

    //FoodList table
    private ObservableList<Food> getFoodListData() {
        List<Food> foodListToReturn = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM Nutrition ORDER BY nutID DESC LIMIT 6");
            while (rs.next()) {
                foodListToReturn.add(
                        new Food(rs.getString("Date"), rs.getString("Foodname"), 
                                rs.getInt("Calories"), rs.getString("MealType")));
            }
            System.out.println("nutrition table data created");

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("nutrition table data not created");
        }
        System.out.println(foodListToReturn);

        return FXCollections.observableArrayList(foodListToReturn);
    }

    @FXML
    private void handleReport(ActionEvent event) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReportTrainer.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Trainer's Report");
        stage.setScene(new Scene(root1));
        stage.show();
        
    }

    //============================================
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
    private Rectangle fitness;

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
    private void handleHealthProfessional(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HealthProfessionalView.fxml");

    }

    @FXML
    private void handleHFitnessView(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "FitnessInstructorView.fxml");

    }

    @FXML
    private void handleOverall(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Overall.fxml");

    }

    @FXML
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");

    }

}
