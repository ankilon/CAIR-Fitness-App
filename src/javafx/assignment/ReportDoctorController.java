/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author rachel.jiang
 */
public class ReportDoctorController implements Initializable {

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
    private Label dr;
    
    @FXML
    private Label drType;
    
    @FXML
    private Button report;
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

    //STUFF
     //CREATE TABLE
    private ObservableList<MedicalRecordsTable> getMedRecordsListData() {
        List<MedicalRecordsTable> medRecordsListToReturn = new ArrayList<>();

        try {
            ResultSet rs = d.getResultSet("SELECT * FROM MedicalCheckups");
            while (rs.next()) {
                medRecordsListToReturn.add(
                        new MedicalRecordsTable(rs.getString("date"), rs.getString("description")));
            }
            System.out.println("med records table data created");

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("medrecords table data not created");
        }
        System.out.println(medRecordsListToReturn);

        return FXCollections.observableArrayList(medRecordsListToReturn);
    }

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
         dateColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());

      //  checkupTypeColumn.setCellValueFactory(
        //        cellData -> cellData.getValue().getCheckupTypeProperty());
        //doctorNameColumn.setCellValueFactory(
          //      cellData -> cellData.getValue().getDoctorNameProperty());
        descriptionColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDescriptionProperty());
        MedRecordsList.setItems(this.getMedRecordsListData());
        
    }
    
    @FXML
    
    //PRINT TO PDF ATTEMPT
    public void printPDF (ActionEvent event) throws IOException{
   PrinterJob job = PrinterJob.createPrinterJob();
 if(job != null){
   //job.printPage(p);
   job.endJob();
 }

    }
}

 
        
        //sorry i think the file location might be for Macs only and IDK HOW TO GET IT TO WORK ON WINDOWS :(
        
        
        //IT only downloads to your shared folders on mac HAHA SORRY @ MARKERS --> will need to write a note about it