/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import static javafx.assignment.Database.conn;
import static javafx.assignment.Database.openConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
/**
 * FXML Controller class
 *
 * @author rachel.jiang
 */
public class UpdateMedicalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private DatePicker date;
    
    @FXML
    private TextField checkupType;
    
    @FXML
    private TextField drName;
    
    @FXML
    private TextField description;
    
    @FXML
    private Button update;
    
    @FXML
    private Label success;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        success.setVisible(false);
    }    
    
    
    @FXML
    private void addMedical(ActionEvent event) throws IOException, SQLException {
            LocalDate reNewDate = date.getValue();
            String newDate = reNewDate.toString();
            String checkup = checkupType.getText();
            String doctor = drName.getText();
            String desc = description.getText();

        openConnection();
        Statement st = conn.createStatement();
      
        try {

            st.executeUpdate("INSERT INTO MedicalCheckups (UserID, date, checkupType, doctorName, description) "
                    + "VALUES (1, '" + newDate + "', '" + checkup + "', '" + doctor + "', '" + desc + "' );");
            
            success.setText("Update Successfull!");
            success.setVisible(true);
            System.out.println("body measurements table UPDATED");
            clearFields();

        } catch (Exception ex) {
            ex.printStackTrace();
            success.setText("Update unsuccessful. Please press re-enter your inputs.");
            success.setVisible(true);

        }
        
        
        

    }
        private void clearFields() {
        
        checkupType.clear();
        drName.clear();
        description.clear();
        
        
        
    

    }
}
