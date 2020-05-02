/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rachel.jiang
 */
public class MindfulFAQController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private DatePicker lol;
    
    @FXML
    private DatePicker xd;
    
    @FXML
    private Label yeet;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
  
    //String user2 = user1.toString(); 
   //DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
   //String format_2 = (user1).format(formatter_2);
   //yeet.setText(user1);
    }    
    public void ShowDate(ActionEvent event){
    LocalDate user1 = lol.getValue();
    LocalDate user2 = xd.getValue();
    
    if (user1.isAfter(user2)){
        yeet.setText("F");
    }
    else if (user1.isBefore(user2)){
        yeet.setText("L");
        }
    
        System.out.println(user1);
    
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
