/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author rachel.jiang
 */
public class ReportTrainerController implements Initializable {
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
        
        try{
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY bmID DESC LIMIT 1; ";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {
                //System.out.println(Integer.toString(rs.getInt("WeightAmt")));
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
        reCaloriesCol.setCellValueFactory(
                cellData -> cellData.getValue().getCaloriesBurntProperty());
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
        aeMinsSpentCol.setCellValueFactory(
                cellData -> cellData.getValue().getMinsSpentProperty());
        aeCaloriesCol.setCellValueFactory(
                cellData -> cellData.getValue().getCaloriesBurntProperty());
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
            ResultSet rs = d.getResultSet("SELECT * FROM AerobicExercises ORDER BY aerobicExerciseID DESC LIMIT 3"); //ORDER BY aerobicExerciseID DESC LIMIT 7");// ORDER BY nutID DESC LIMIT 20");
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
    
    //resistance act table
    private ObservableList<ResistanceExercisesTable> getResistanceExerciseListData() throws SQLException {
        List<ResistanceExercisesTable> ResistanceExerciseListToReturn = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM ResistanceExercises ORDER BY resistanceExerciseID DESC LIMIT 3 ");
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

    //FoodList table
    private ObservableList<Food> getFoodListData() {
        List<Food> foodListToReturn = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM Nutrition ORDER BY nutID DESC LIMIT 4");
            while (rs.next()) {
                foodListToReturn.add(
                        new Food(rs.getString("Date"), rs.getString("Foodname"), rs.getInt("Calories"), rs.getString("MealType")));
            }
            System.out.println("nutrition table data created");

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("nutrition table data not created");
        }
        System.out.println(foodListToReturn);

        return FXCollections.observableArrayList(foodListToReturn);
    }



    }

