package javafx.assignment;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import static javafx.assignment.Database.conn;
import static javafx.assignment.Database.openConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;

/**
 * FXML Controller class
 *
 * @author rachel.jiang
 */
public class BodyMeasurementsController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    private LineChart<String, Number> lineChartWeight;

    @FXML
    private ImageView arrowHome;

    @FXML
    private Text homeText;

    @FXML
    private Rectangle back;

    @FXML
    private Button edit;

    @FXML
    private Label currentWeight;
    @FXML
    private Label currentHeight;
    @FXML
    private Label idealWeight;

    @FXML
    private Label BMI;

    @FXML
    private TextField date;

    @FXML
    private TextField weight;

    @FXML
    private TextField height;

    @FXML
    private TextField idealW;

    @FXML
    private Label asof;

    @FXML
    private Label success;

    @FXML
    private Rectangle enter;

    @FXML
    private Button testIdeal;

    @FXML
    private Label lossGainRequired;
    @FXML
    private PieChart PieChart;

    @FXML
    private DatePicker pieDate;

    @FXML
    private ImageView bmiFAQ;

    @FXML
    private Button loadLineChart;

    @FXML
    private Button updateBody;

    @FXML
    private DatePicker firstD;

    @FXML
    private DatePicker secondD;

    @FXML
    private Button refresh;

    @FXML
    private Label validation;

    //initialise database and pageswitcher
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    Database d = new Database();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validation.setVisible(false);

        //piechart
        success.setVisible(false);
        lineChartWeight.setTitle("Weight Over Time");

        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY bmID"
                    + " DESC LIMIT 1";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                currentWeight.setText(Integer.toString(rs.getInt("WeightAmt")));

            }

            System.out.println("display weight successful");

        } catch (SQLException ex) {
            System.out.println("display to weight table not successful");
            ex.printStackTrace();
        }
        //calculating loss/gain

        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements NATURAL "
                    + "JOIN Ideal ORDER BY bmID DESC LIMIT 1";

            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("result set exists");

                int weight = rs.getInt("WeightAmt");
                System.out.println(weight);
                int idealWeight = rs.getInt("idealWeight");
                if (idealWeight > weight) {
                    int weightGain = idealWeight - weight;
                    lossGainRequired.setText(weightGain + "kg gain");

                } else {
                    int weightLoss = weight - idealWeight;
                    lossGainRequired.setText(weightLoss + "kg loss");
                }

                System.out.println(idealWeight);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //calculating bmi
        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY"
                    + " bmID DESC LIMIT 1";
            ResultSet rs = st.executeQuery(selectQuery);
            double weight = rs.getInt("WeightAmt");
            double height = rs.getInt("Height");
            double BMIvalue = weight / (height / 100 * height / 100);
            double BMIrounded = Math.round(BMIvalue * 100.0) / 100.0;
            while (rs.next()) {
                BMI.setText(Double.toString(BMIrounded));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //current height label
        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY "
                    + "bmID DESC LIMIT 1";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                currentHeight.setText(Integer.toString(rs.getInt("Height")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //ideal weight
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT IdealWeight FROM Ideal";

            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("result for ideal weight set exists");
                idealWeight.setText(Integer.toString(rs.getInt("IdealWeight")));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //latest date
        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY"
                    + " bmID DESC LIMIT 1";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                asof.setText("As of: " + rs.getString("Date"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
    }
    //UPDATE IDEAL WEIGHT
    @FXML
    private void loadLine(ActionEvent event) throws IOException {
        lineChartWeight.getData().clear();

        try {
            LocalDate date1 = firstD.getValue();
            String d1 = date1.toString();

            LocalDate date2 = secondD.getValue();
            String d2 = date2.toString();

            openConnection();
            if (date2.isAfter(date1)) {
                Statement st = conn.createStatement();
                String selectQuery1 = "SELECT * FROM BodyMeasurements WHERE Date "
                        + "BETWEEN '" + d1 + "' AND '" + d2 + "'";
                ResultSet rs = st.executeQuery(selectQuery1);
                lineChartWeight.setTitle("Weight Over Time");
                XYChart.Series<String, Number> weight = new XYChart.Series<>();
                weight.setName("Current Weight");
                while (rs.next()) {
                    System.out.println(d1);
                    weight.getData().add(new XYChart.Data<>(rs.getString("Date"), rs.getInt("WeightAmt")));
                }

                String selectQuery2 = "SELECT * FROM BodyMeasurements NATURAL "
                        + "JOIN Ideal WHERE Date BETWEEN '" + d1 + "' AND '" + d2 + "'";
                rs = st.executeQuery(selectQuery2);
                XYChart.Series<String, Number> ideal = new XYChart.Series<>();
                ideal.setName("Ideal Weight");

                while (rs.next()) {
                    ideal.getData().add(new XYChart.Data<>(rs.getString("Date"), rs.getInt("idealWeight")));
                }

                lineChartWeight.getData().addAll(weight, ideal);

                System.out.println("weight line created");
            } else if (date2.isBefore(date1)) {
                validation.setText("Invalid date range. Please try again.");
                validation.setVisible(true);

            }
        } catch (SQLException ex) {
            System.out.println("weight line not created");
        }
    }

    @FXML
    private void loadBmiFAQ(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bmiFAQ.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("BMI");
        stage.setScene(new Scene(root1));
        stage.show();

    }

    @FXML
    public void updateIdeal(ActionEvent event) throws IOException, SQLException {
        int newIdeal = Integer.parseInt(idealW.getText().trim());
        idealWeight.setText(Integer.toString(newIdeal));
        idealW.clear();
        openConnection();
        Statement st = conn.createStatement();
        System.out.println(newIdeal);
        try {
            st.executeUpdate("UPDATE Ideal SET IdealWeight = " + newIdeal + " "
                    + "WHERE IdealID = 1");
            System.out.println("update successful");

            String selectQuery = "SELECT * FROM BodyMeasurements NATURAL JOIN "
                    + "Ideal ORDER BY bmID DESC LIMIT 1";

            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("result set exists");
                int weight = rs.getInt("WeightAmt");
                System.out.println(weight);
                int idealWeight = rs.getInt("idealWeight");
                if (idealWeight > weight) {
                    int weightGain = idealWeight - weight;
                    lossGainRequired.setText(weightGain + "kg gain");

                } else {
                    int weightLoss = weight - idealWeight;
                    lossGainRequired.setText(weightLoss + "kg loss");
                }
            }

            System.out.println(idealWeight);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("update unsuccessful");
        }

        lineChartWeight.getData().clear();

        try {
            LocalDate date1 = firstD.getValue();
            String d1 = date1.toString();

            LocalDate date2 = secondD.getValue();
            String d2 = date2.toString();

            String selectQuery1 = "SELECT * FROM BodyMeasurements WHERE Date "
                    + "BETWEEN '" + d1 + "' AND '" + d2 + "'";
            ResultSet rs = st.executeQuery(selectQuery1);
            XYChart.Series<String, Number> weight = new XYChart.Series<>();
            weight.setName("Current Weight");
            while (rs.next()) {
                weight.getData().add(new XYChart.Data<>(rs.getString("Date"), rs.getInt("WeightAmt")));
            }

            String selectQuery2 = "SELECT * FROM BodyMeasurements NATURAL JOIN"
                    + " Ideal;";
            rs = st.executeQuery(selectQuery2);
            XYChart.Series<String, Number> ideal = new XYChart.Series<>();
            ideal.setName("Ideal Weight");

            while (rs.next()) {
                ideal.getData().add(new XYChart.Data<>(rs.getString("Date"), rs.getInt("idealWeight")));
            }

            lineChartWeight.getData().addAll(weight, ideal);

            System.out.println("weight line created");
        } catch (SQLException ex) {
            System.out.println("weight line not created");
        }
    }

//calculating BMI
    @FXML
    private void addBody(MouseEvent event) throws IOException, SQLException {

        String newDate = date.getText().trim();
        int newWeight = Integer.parseInt(weight.getText().trim());
        int newHeight = Integer.parseInt(height.getText().trim());
        openConnection();
        Statement st = conn.createStatement();

        try {

            st.executeUpdate("INSERT INTO BodyMeasurements (Date, WeightAmt, Height) "
                    + "VALUES ('" + newDate + "', " + newWeight + ", " + newHeight + ");");
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
        date.clear();
        weight.clear();
        height.clear();
        idealW.clear();

    }

    @FXML
    private void handleRefresh(ActionEvent event) throws IOException, SQLException {
        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY bmID "
                    + "DESC LIMIT 1";

            ResultSet rs = st.executeQuery(selectQuery);

            while (rs.next()) {

                currentWeight.setText(Integer.toString(rs.getInt("WeightAmt")));

            }

            System.out.println("display weight successful");

        } catch (SQLException ex) {
            System.out.println("display to weight table not successful");
            ex.printStackTrace();
        }
        //calculating loss/gain

        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements NATURAL JOIN "
                    + "Ideal ORDER BY bmID DESC LIMIT 1";

            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("result set exists");

                int weight = rs.getInt("WeightAmt");
                System.out.println(weight);
                int idealWeight = rs.getInt("idealWeight");
                if (idealWeight > weight) {
                    int weightGain = idealWeight - weight;
                    lossGainRequired.setText(weightGain + "kg gain");

                } else {
                    int weightLoss = weight - idealWeight;
                    lossGainRequired.setText(weightLoss + "kg loss");
                }

                System.out.println(idealWeight);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //calculating bmi
        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY "
                    + "bmID DESC LIMIT 1";
            ResultSet rs = st.executeQuery(selectQuery);
            double weight = rs.getInt("WeightAmt");
            double height = rs.getInt("Height");
            double BMIvalue = weight / (height / 100 * height / 100);
            double BMIrounded = Math.round(BMIvalue * 100.0) / 100.0;
            while (rs.next()) {
                BMI.setText(Double.toString(BMIrounded));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //current height label
        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY "
                    + "bmID DESC LIMIT 1";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                currentHeight.setText(Integer.toString(rs.getInt("Height")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //ideal weight
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT IdealWeight FROM Ideal";

            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("result for ideal weight set exists");
                idealWeight.setText(Integer.toString(rs.getInt("IdealWeight")));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {

            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements ORDER BY "
                    + "bmID DESC LIMIT 1";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                asof.setText("As of: " + rs.getString("Date"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) throws IOException, SQLException {
        //try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateBodyMeasurement.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Update Body Measurements");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void loadPieChart(ActionEvent event) throws IOException {
        LocalDate reNewDate = pieDate.getValue();
        String piechartDate = reNewDate.toString();
        System.out.println(piechartDate);
        try {
            openConnection();
            Statement st = conn.createStatement();

            String selectQuery = "SELECT * FROM BodyMeasurements WHERE Date"
                    + " = '" + piechartDate + "' ";
            ResultSet rs = st.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println(rs.getInt("FatMassPercentage"));
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Fat Mass", rs.getInt("FatMassPercentage")),
                        new PieChart.Data("Lean Mass", rs.getInt("LeanMassPercentage")));

                PieChart.setData(pieChartData);

            }

            System.out.println("Pie Chart Created");

        } catch (Exception e) {
            System.out.println("Pie Chart Not Created");

        }
    }

    @FXML
    private void handleBack(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");
    }

    @FXML
    private void handleEdit(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "UpdateBodyMeasurement.fxml");
    }

    @FXML
    private void handleCurrentWeight(MouseEvent event) throws IOException, SQLException {
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
    private Text textProfessional;

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
    private Rectangle home;

    @FXML
    private JFXButton JFXenter;

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
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");

    }

    @FXML
    private void handleProfessional(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HealthProfessionalView.fxml");

    }

    @FXML
    private void handleOverall(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Overall.fxml");

    }
}
