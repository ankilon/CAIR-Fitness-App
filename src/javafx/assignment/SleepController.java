/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTimePicker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import static javafx.assignment.Database.conn;
import static javafx.assignment.Database.openConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rachel.jiang
 */
public class SleepController implements Initializable {

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    @FXML
    private JFXButton deleteSleep;

    @FXML
    private Rectangle home;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Label average;
    Database d = new Database();

    @FXML
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
        exportSuccess.setVisible(false);
        sleepSuccessOutput.setVisible(false);
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT Date, hoursSlept FROM Sleep ORDER BY sleepID DESC LIMIT 7;";
//"SELECT * FROM (SELECT Date, SleepTime FROM Sleep ORDER BY sleepID ASC LIMIT 4) ORDER BY sleepID DESC);";
            ResultSet rs = st.executeQuery(selectQuery);
            // series1.setName("Weekly Average Heart Rate");

            //prepare xychart series objects by setting data                
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();

            while (rs.next()) {
                series1.setName("YEET");
                series1.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt("hoursSlept")));
            }
            barChart.getData().add(series1);

        } catch (SQLException ex) {
            //  Logger.getLogger(HeartRateController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("line not created");
        }

        //CREATE TABLE
        dateColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());

        wakeUpTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().getWakeUpTimeProperty());
        sleepTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().getSleepTimeProperty());

        SleepList.setItems(this.getSleepListData());

        //calculating avg 
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT SUM(hoursSlept), COUNT(hoursSlept) FROM Sleep;";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                int hrsAvg = rs.getInt("SUM(hoursSlept)") / rs.getInt("COUNT(hoursSlept)");
                average.setText(Integer.toString(hrsAvg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //sleeplist data
    private ObservableList<SleepTable> getSleepListData() {
        List<SleepTable> sleepListToReturn = new ArrayList<>();

        try {
            ResultSet rs = d.getResultSet("SELECT * FROM Sleep");
            while (rs.next()) {
                sleepListToReturn.add(
                        new SleepTable(rs.getString("Date"), rs.getString("wakeUpTime"), rs.getString("sleepTIme")));
            }
            System.out.println("sleep table data created");

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("sleep table data not created");
        }
        System.out.println(sleepListToReturn);

        return FXCollections.observableArrayList(sleepListToReturn);
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
    private DatePicker sleepDatePicker;

    @FXML
    private ImageView findSharedFolder;

    //TABLE
    @FXML
    private TableView<SleepTable> SleepList;
    //TABLE COLUMNS
    @FXML
    private TableColumn<SleepTable, String> dateColumn;

    @FXML
    private TableColumn<SleepTable, String> sleepTimeColumn;

    @FXML
    private TableColumn<SleepTable, String> wakeUpTimeColumn;

    //insert data
    @FXML
    private TextField sleepDate;

    @FXML
    private TextField timeFallenAsleep;

    @FXML
    private TextField timeWakenUp;

    @FXML
    private Label sleepSuccessOutput;

    @FXML
    private JFXButton enterSleep;

    //testing time picker
    @FXML
    private JFXTimePicker wakeUpTimePicker;

    @FXML
    private JFXTimePicker sleepTimePicker;

    @FXML
    private Rectangle overall;

    @FXML
    private Text textProfessional;

    @FXML
    private Button refreshSleep;

    @FXML
    private void handleOverall(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Overall.fxml");

    }

    @FXML
    private void handleProfessional(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HealthProfessionalView.fxml");

    }

    @FXML
    private void addSleep(ActionEvent event) throws IOException, SQLException {
        LocalTime noon = LocalTime.NOON;
        LocalTime timeAwakeTime = wakeUpTimePicker.getValue();
        LocalTime timeAsleepTime = sleepTimePicker.getValue();
        long elapsedMinsUpdated;
        if (timeAsleepTime.isAfter(noon)) {
            long elapsedMinutes = Duration.between(timeAsleepTime, timeAwakeTime).toHours();
            elapsedMinsUpdated = 24 + elapsedMinutes;
            System.out.println(elapsedMinsUpdated);
            System.out.println("after noon");

        } else {
            long elapsedMinutes = Duration.between(timeAsleepTime, timeAwakeTime).toHours();
            elapsedMinsUpdated = elapsedMinutes;
            System.out.println(elapsedMinsUpdated);
            System.out.println("before noon");
        }
//SOURCE: https://stackoverflow.com/questions/2592499/casting-and-getting-values-from-date-picker-and-time-picker-in-android/2592576 
//SOURCE: https://stackoverflow.com/questions/10346685/android-how-to-get-values-from-date-and-time-picker 

        String newTimeAwake = timeAwakeTime.toString();
        String newTimeAlseep = timeAsleepTime.toString();
        LocalDate sleepNewDate = sleepDatePicker.getValue();
        String newDate = sleepNewDate.toString();
        openConnection();
        Statement st = conn.createStatement();
        try {

            st.executeUpdate("INSERT INTO Sleep (Date, sleepTime, wakeUpTime, UserID, hoursSlept) "
                    + "VALUES ('" + newDate + "', '" + newTimeAlseep + "', '" + newTimeAwake + "', 1, '" + elapsedMinsUpdated + "');");
            sleepSuccessOutput.setText("Update Successfull!");
            sleepSuccessOutput.setVisible(true);
            System.out.println("sleep table UPDATED");
            clearFields();
            //conn.close();
            //st.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            sleepSuccessOutput.setText("Update unsuccessful. Please re-enter your inputs.");
            sleepSuccessOutput.setVisible(true);

        }

        try {

            String selectQuery = "SELECT SUM(hoursSlept), COUNT(hoursSlept) FROM Sleep;";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                int hrsAvg = rs.getInt("SUM(hoursSlept)") / rs.getInt("COUNT(hoursSlept)");
                average.setText(Integer.toString(hrsAvg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dateColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());
        sleepTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().getSleepTimeProperty());
        wakeUpTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().getWakeUpTimeProperty());
        SleepList.setItems(this.getSleepListData());

        barChart.getData().clear();

        try {

            String selectQuery = "SELECT Date, hoursSlept FROM Sleep ORDER BY sleepID DESC LIMIT 7;";
//"SELECT * FROM (SELECT Date, SleepTime FROM Sleep ORDER BY sleepID ASC LIMIT 4) ORDER BY sleepID DESC);";
            ResultSet rs = st.executeQuery(selectQuery);
            // series1.setName("Weekly Average Heart Rate");

            //prepare xychart series objects by setting data                
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();

            while (rs.next()) {
                series1.setName("Hours Slept");
                series1.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt("hoursSlept")));
            }
            barChart.getData().add(series1);

        } catch (SQLException ex) {
            //  Logger.getLogger(HeartRateController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("line not created");
        }

    }

    private void clearFields() {
        sleepDatePicker.getEditor().clear();
        wakeUpTimePicker.getEditor().clear();
        sleepTimePicker.getEditor().clear();
    }

    //DELETE SLEEP
    @FXML
    private void deleteSleep(ActionEvent event) throws IOException, SQLException {

        String deleteDate = SleepList.getSelectionModel().getSelectedItem().getDate();
        String deleteSleepTime = SleepList.getSelectionModel().getSelectedItem().getSleepTime();
        String deleteWakeUpTime = SleepList.getSelectionModel().getSelectedItem().getWakeUpTime();
        System.out.println(deleteDate);
        System.out.println(deleteSleepTime);
        System.out.println(deleteWakeUpTime);

        SleepList.getItems().removeAll(SleepList.getSelectionModel().getSelectedItem());

        openConnection();
        Statement st = conn.createStatement();
        try {
            st.executeUpdate("DELETE FROM Sleep WHERE date = '" + deleteDate + "' AND sleepTime = '" + deleteSleepTime + "' AND  wakeUpTime = '" + deleteWakeUpTime + "' AND UserID = 1");
            System.out.println("delete successful");
            sleepSuccessOutput.setText("Successfully Deleted!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("delete unsuccessful");
            sleepSuccessOutput.setText("Delete unsuccessful. Please try again.");
        }

    }

    @FXML
    private void handleRefresh(ActionEvent event) throws IOException, SQLException {

        try {
            barChart.getData().clear();
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT Date, hoursSlept FROM Sleep ORDER BY sleepID DESC LIMIT 7;";
//"SELECT * FROM (SELECT Date, SleepTime FROM Sleep ORDER BY sleepID ASC LIMIT 4) ORDER BY sleepID DESC);";
            ResultSet rs = st.executeQuery(selectQuery);
            // series1.setName("Weekly Average Heart Rate");

            //prepare xychart series objects by setting data                
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();

            while (rs.next()) {
                series1.setName("YEET");
                series1.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt("hoursSlept")));
            }
            barChart.getData().add(series1);

        } catch (SQLException ex) {
            //  Logger.getLogger(HeartRateController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("line not created");
        }
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT SUM(hoursSlept), COUNT(hoursSlept) FROM Sleep;";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                int hrsAvg = rs.getInt("SUM(hoursSlept)") / rs.getInt("COUNT(hoursSlept)");
                average.setText(Integer.toString(hrsAvg));
                System.out.println(Integer.toString(hrsAvg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT SUM(hoursSlept), COUNT(hoursSlept) FROM Sleep;";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                int hrsAvg = rs.getInt("SUM(hoursSlept)") / rs.getInt("COUNT(hoursSlept)");
                average.setText(Integer.toString(hrsAvg));
                System.out.println("SUM(hoursSlept)");
                System.out.println("COUNT(hoursSlept)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //load dis shit pls
    @FXML
    private Button load;

    @FXML
    private Label exportSuccess;

    @FXML
    private void loadSleep(MouseEvent event) throws IOException, SQLException {
        //https://www.tutorialspoint.com/javafx/bar_chart.htm
        barChart.getData().clear();

        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT Date, hoursSlept FROM Sleep ORDER BY sleepID DESC LIMIT 7;";
            ResultSet rs = st.executeQuery(selectQuery);
            // series1.setName("Weekly Average Heart Rate");

            //prepare xychart series objects by setting data                
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();

            while (rs.next()) {
                series1.setName("YEET");
                series1.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt("hoursSlept")));
            }
            barChart.getData().add(series1);

        } catch (SQLException ex) {
            //  Logger.getLogger(HeartRateController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("line not created");
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
    private void handleSharedFolder(MouseEvent event) throws IOException, SQLException {
        //try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SharedFolderFAQ.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Update Body Measurements");
        stage.setScene(new Scene(root1));
        stage.show();
    }
//EXPORT TO CSV
    @FXML
    private JFXButton csvSleep;

    //PLEASE NOTE:
    //this only works on Macs. When exported, it saves to the 'shared' folder.
    //Search 'shared' in your mac to find it. Click the 'i' button next to 
    //the 'export to CSV' button when running the app for more info
    @FXML
    public void csvFile(ActionEvent event) throws Exception {

        Writer writer = null;
        try {

            File file = new File("/Users/Shared/SleepTimesExport.csv");

            writer = new BufferedWriter(new FileWriter(file));
            for (SleepTable sleepTable : getSleepListData()) {

                String text = sleepTable.getDate() + ", " + sleepTable.getSleepTime() + ", " + sleepTable.getWakeUpTime() + "\n";// ", "+  food.getFat() +", "+ food.getCarbohydrates() +", " +food.getProtein() + "\n"; // + ", "

                writer.write(text);

                System.out.println("Export to CSV Successful");
                exportSuccess.setText("CSV file successfully exported to  your shared folders in your Mac");
                exportSuccess.setVisible(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            exportSuccess.setText("Export to CSV file unsuccessful");
            exportSuccess.setVisible(true);
        } finally {

            writer.flush();
            writer.close();

        }
    }

}
