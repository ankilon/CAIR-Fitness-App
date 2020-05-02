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
public class HealthProfessionalViewController implements Initializable {

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

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
    private Text textProfessional;

    @FXML
    private Button report;

    @FXML
    private Button openUpdate;

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

        dateColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());

        checkupTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().getCheckupTypeProperty());
        doctorNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDoctorNameProperty());
        descriptionColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDescriptionProperty());
        MedRecordsList.setItems(this.getMedRecordsListData());
    }
    //table
    @FXML
    private TableView<MedicalRecordsTable> MedRecordsList;
    //TABLE COLUMNS
    @FXML
    private TableColumn<MedicalRecordsTable, String> dateColumn;

    @FXML
    private TableColumn<MedicalRecordsTable, String> checkupTypeColumn;

    @FXML
    private TableColumn<MedicalRecordsTable, String> doctorNameColumn;

    @FXML
    private TableColumn<MedicalRecordsTable, String> descriptionColumn;
    Database d = new Database();

    //CREATE TABLE
    private ObservableList<MedicalRecordsTable> getMedRecordsListData() {
        List<MedicalRecordsTable> medRecordsListToReturn = new ArrayList<>();

        try {
            ResultSet rs = d.getResultSet("SELECT * FROM MedicalCheckups");
            while (rs.next()) {
                medRecordsListToReturn.add(
                        new MedicalRecordsTable(rs.getString("date"), rs.getString("checkupType"), rs.getString("doctorName"), rs.getString("description")));
            }
            System.out.println("med records table data created");

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("medrecords table data not created");
        }
        System.out.println(medRecordsListToReturn);

        return FXCollections.observableArrayList(medRecordsListToReturn);
    }

    @FXML
    private void handleReport(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReportDoctor.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Medical Report");
        stage.setScene(new Scene(root1));
        stage.show();

    }

    @FXML
    private void handleUpdate(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateMedical.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Update Medical Checkups");
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
