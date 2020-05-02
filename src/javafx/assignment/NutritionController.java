package javafx.assignment;

import com.jfoenix.controls.JFXButton;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javafx.assignment.Database;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cathyxu
 */
public class NutritionController implements Initializable {

    @FXML
    private Rectangle home;

    @FXML
    private PieChart PieChart;

    @FXML
    private TableView<Food> FoodList;

    @FXML
    private TableColumn<Food, Number> caloriesColumn;

    @FXML
    private TableColumn<Food, String> foodNameColumn;

    @FXML
    private TableColumn<Food, String> mealTypeColumn;

    @FXML
    private TableColumn<Food, String> dateColumn;

    @FXML
    private DatePicker userDate;
    @FXML
    private TextField foodName;

    @FXML
    private TextField foodCalories;

    @FXML
    private TextField mealType;

    @FXML
    private TextField fatText;

    @FXML
    private TextField carbohydrateText;

    @FXML
    private TextField proteinText;

    @FXML
    private Button enterfood;

    @FXML
    private JFXButton deleteFood;

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
    private Label foodOutput;

    @FXML
    private Label caloriesToday;

    @FXML
    private DatePicker macroDate;

    @FXML
    private Rectangle overall;

    @FXML
    private Label benchmark;

    @FXML
    private Label moreless;

    @FXML
    private Label newIdeal;

    @FXML
    private TextField userIdeal;

    @FXML
    private Button updateCalories;

    @FXML
    private JFXButton exportCSV;

    @FXML
    private Label exportSuccess;
    
    @FXML
    private ImageView exportSuccessImage;
    
    @FXML
    private void handleOverall(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Overall.fxml");

    }

    Database d = new Database();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    //initialise
    public void initialize(URL url, ResourceBundle rb) {
        exportSuccess.setVisible(false);
        foodOutput.setVisible(false);
        benchmark.setVisible(false);
        moreless.setVisible(false);
        caloriesToday.setVisible(false);

        caloriesColumn.setCellValueFactory(
                cellData -> cellData.getValue().getCaloriesProperty());

        foodNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().getFoodNameProperty());
        mealTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().getMealTypeProperty());
        dateColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDateProperty());

        FoodList.setItems(this.getFoodListData());

    }

    //update food to table
    @FXML
    private void addFood(ActionEvent event) throws IOException, SQLException {
        LocalDate reNewDate = userDate.getValue();
        String nutritionDate = reNewDate.toString();
        String newFoodName = foodName.getText().trim();
        int newFoodCalories = Integer.parseInt(foodCalories.getText().trim());
        String newMealType = mealType.getText().trim();
        int newFat = Integer.parseInt(fatText.getText().trim());
        int newCarbs = Integer.parseInt(carbohydrateText.getText().trim());
        int newProtein = Integer.parseInt(proteinText.getText().trim());

        openConnection();
        Statement st = conn.createStatement();

        try {

            st.executeUpdate("INSERT INTO Nutrition (Date, Foodname, Calories, MealType, Fats, Protein, Carbohydrates, UserID) "
                    + "VALUES ('" + nutritionDate + "', '" + newFoodName + "', "
                    + "" + newFoodCalories + ",'" + newMealType + "' , "
                    + "" + newFat + " ," + newProtein + ", "
                    + "" + newCarbs + ",1 );");
            foodOutput.setText("Update Successfull!");
            foodOutput.setVisible(true);
            System.out.println("nutrition table UPDATED");
            clearFields();

        } catch (Exception ex) {
            ex.printStackTrace();
            foodOutput.setText("Update unsuccessful. Please press re-enter your inputs.");
            foodOutput.setVisible(true);

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

    }

    @FXML //right now this deletes everything under the same date 
    private void deleteFoodAction(ActionEvent event) throws IOException, SQLException {
        int deleteCalories = FoodList.getSelectionModel().getSelectedItem().getCalories();
        String deleteFoodName = FoodList.getSelectionModel().getSelectedItem().getFoodName();
        String deletemealType = FoodList.getSelectionModel().getSelectedItem().getMealType();
        String deleteDate = FoodList.getSelectionModel().getSelectedItem().getDate();
        System.out.println(deleteCalories);
        System.out.println(deleteFoodName);
        System.out.println(deletemealType);
        System.out.println(deleteDate);

        FoodList.getItems().removeAll(FoodList.getSelectionModel().getSelectedItem());

        openConnection();
        Statement st = conn.createStatement();
        try {
            st.executeUpdate("DELETE FROM Nutrition WHERE Calories = " + deleteCalories
                    + " AND Foodname = '" + deleteFoodName + "' "
                    + " AND  MealType = '" + deletemealType + "' "
                    + "AND Date = '" + deleteDate + "'");

            System.out.println("delete food successful");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("delete food unsuccessful");
        }

    }

    //clearing text fields
    private void clearFields() {
        foodName.clear();
        foodCalories.clear();
        mealType.clear();
        fatText.clear();
        carbohydrateText.clear();
        proteinText.clear();
    }

    //Macros Pie Chart
    @FXML
    private void loadMacros(ActionEvent event) throws IOException {

        LocalDate reNewDate = macroDate.getValue();
        String piechartDate = reNewDate.toString();
        System.out.println(piechartDate);
        try {
            openConnection();
            Statement st = conn.createStatement();

            String selectQuery = "SELECT SUM(Fats), SUM(Carbohydrates), "
                    + "SUM(Protein) FROM Nutrition WHERE Date = '" + piechartDate + "' ";
            ResultSet rs = st.executeQuery(selectQuery);
//SOURCE: https://www.tutorialspoint.com/javafx/pie_chart.htm
            while (rs.next()) {

                int fat = rs.getInt("SUM(Fats)");
                int protein = rs.getInt("SUM(Protein)");
                int carbs = rs.getInt("SUM(Carbohydrates)");

                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Fats", fat),
                        new PieChart.Data("Protein", protein),
                        new PieChart.Data("Carbohydrates", carbs));
                PieChart.setData(pieChartData);
            }

            System.out.println("Pie Chart Created");

        } catch (Exception e) {
            System.out.println("Pie Chart Not Created");
            e.printStackTrace();

        }
        try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT SUM(Calories), IdealCaloriesConsumed "
                    + "FROM Nutrition NATURAL JOIN Ideal WHERE Date = '" + piechartDate + "'";

            ResultSet rs = st.executeQuery(selectQuery);
            int bench = 0;

            while (rs.next()) {
                System.out.println("this query exists");
                System.out.println();

                int WIdeal = rs.getInt("IdealCaloriesConsumed");
                int todayCal = rs.getInt("SUM(Calories)");

                if (WIdeal > todayCal) {
                    bench = WIdeal - todayCal;
                    benchmark.setText(Integer.toString(bench));
                    caloriesToday.setText(Integer.toString(rs.getInt("SUM(Calories)")));
                    moreless.setText("more");
                    moreless.setVisible(true);
                    caloriesToday.setVisible(true);
                    benchmark.setVisible(true);

                } else if (todayCal > WIdeal) {
                    bench = todayCal - WIdeal;
                    benchmark.setText(Integer.toString(bench));
                    caloriesToday.setText(Integer.toString(rs.getInt("SUM(Calories)")));
                    moreless.setText("too much");
                    moreless.setVisible(true);
                    caloriesToday.setVisible(true);
                    benchmark.setVisible(true);
                }

                System.out.println("this query exists");

            }

        } catch (SQLException ex) {
            System.out.println("update to hr max not successful");
            ex.printStackTrace();
        }
    }

    //update ideal
    @FXML
    public void updateIdeal(ActionEvent event) throws IOException, SQLException {
        LocalDate reNewDate = macroDate.getValue();
        String piechartDate = reNewDate.toString();
        
        int ideal = Integer.parseInt(userIdeal.getText().trim());
        newIdeal.setText(Integer.toString(ideal));
        userIdeal.clear();
        openConnection();
        Statement st = conn.createStatement();
        System.out.println(newIdeal);
        try {
            st.executeUpdate("UPDATE Ideal SET IdealCaloriesConsumed = " + ideal + " WHERE IdealID = 1");
            System.out.println("update successful");

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("update unsuccessful");
        }
        
        
        try {
            
            String selectQuery = "SELECT SUM(Calories), IdealCaloriesConsumed "
                    + "FROM Nutrition NATURAL JOIN Ideal WHERE Date = '" + piechartDate + "'";

            ResultSet rs = st.executeQuery(selectQuery);
            int bench = 0;

            while (rs.next()) {
                System.out.println("this query exists");
                System.out.println();

                int WIdeal = rs.getInt("IdealCaloriesConsumed");
                int todayCal = rs.getInt("SUM(Calories)");

                if (WIdeal > todayCal) {
                    bench = WIdeal - todayCal;
                    benchmark.setText(Integer.toString(bench));
                    caloriesToday.setText(Integer.toString(rs.getInt("SUM(Calories)")));
                    moreless.setText("more");
                    moreless.setVisible(true);
                    caloriesToday.setVisible(true);
                    benchmark.setVisible(true);

                } else if (todayCal > WIdeal) {
                    bench = todayCal - WIdeal;
                    benchmark.setText(Integer.toString(bench));
                    caloriesToday.setText(Integer.toString(rs.getInt("SUM(Calories)")));
                    moreless.setText("too much");
                    moreless.setVisible(true);
                    caloriesToday.setVisible(true);
                    benchmark.setVisible(true);
                }

                System.out.println("this query exists");

            }

        } catch (SQLException ex) {
            System.out.println("update to hr max not successful");
            ex.printStackTrace();
        }
        
        
        
        
        
        
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
    
    //handle back
    @FXML
    private void handleBack(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");
    }

    //FoodList table
    private ObservableList<Food> getFoodListData() {
        List<Food> foodListToReturn = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM Nutrition");
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

    //EXPORT
    //SOURCE: https://community.oracle.com/thread/2397100 
    //Additional Functionality - export to CSV File
    //PLEASE NOTE:
    //this only works on Macs. When exported, it saves to the 'shared' folder.
    //Search 'shared' in your mac to find it. Click the 'i' button next to 
    //the 'export to CSV' button when running the app for more info
    public void csvFile(ActionEvent event) throws Exception {
        Writer writer = null;
        try {
            File file = new File("/Users/Shared/FoodListExport.csv");

            writer = new BufferedWriter(new FileWriter(file));
            for (Food food : getFoodListData()) {

                String text = food.getDate() + ", " + food.getFoodName() + ", " + food.getCalories() + "\n";// ", "+  food.getFat() +", "+ food.getCarbohydrates() +", " +food.getProtein() + "\n"; // + ", "

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

    @FXML
    private void handleSteps(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Steps.fxml");

    }

    @FXML
    private void handleHome(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Screen.fxml");

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
    private void handleProfessional(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "HealthProfessionalView.fxml");

    }

}
