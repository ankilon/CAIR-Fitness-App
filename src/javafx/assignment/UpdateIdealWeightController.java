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
import java.util.ResourceBundle;
import static javafx.assignment.Database.conn;
import static javafx.assignment.Database.openConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cathyxu
 */
public class UpdateIdealWeightController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Database d = new Database();
    @FXML
    private Rectangle updateIdealButton;

    @FXML
    private TextField newIdeal;

    @FXML
    private Label successUpdateOutput;

    @FXML
    private Text updateIdealText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        successUpdateOutput.setVisible(false);
    }

    @FXML
    public void updateIdeal(MouseEvent event) throws IOException, SQLException {

        int newIdealWeight = Integer.parseInt(newIdeal.getText().trim());

        newIdeal.clear();

        Stage stage = (Stage) updateIdealButton.getScene().getWindow();
        stage.close();

        openConnection();
        Statement st = conn.createStatement();

        ResultSet rs = d.getResultSet("SELECT * FROM Ideal WHERE IdealID = 1");
        try {

            System.out.println(newIdealWeight);

            while (rs.next()) {

                st.executeQuery("UPDATE Ideal SET IdealWeight = " + newIdealWeight + "");
                System.out.println("update successful");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("update unsuccessful");
        }

    }

}
