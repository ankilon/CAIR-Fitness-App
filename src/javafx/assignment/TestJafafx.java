/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import java.sql.SQLException;
import javafx.assignment.Database;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author cathyxu
 */
public class TestJafafx extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        loadDatabase();
        Parent root = FXMLLoader.load(getClass().getResource("Screen.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void loadDatabase() throws SQLException {
        Database.createNutritionTable();
        Database.createStepsTable();
        Database.createHeartRateTable();
        Database.createSleepTable();
        Database.createBodyMeasurementsTable();
        Database.createActivitiesTable();
        Database.createAerobicExercisesTable();
        Database.createResistanceExercisesTable();
        Database.createGymAttendanceTable();
        Database.createIdealTable();
        Database.createMedicalCheckupsTable();
        Database.createMentalWellbeingTable();
        Database.createUserDatabase();
    }
}
