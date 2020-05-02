/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jacob
 */
public class Food {

    private StringProperty foodName;
    private StringProperty mealType;
    private StringProperty Date;
    private IntegerProperty calories;
    private IntegerProperty fat;
    private IntegerProperty protein;
    private IntegerProperty carbohydrate;

    /*
    public Food() {
        this("","","", "", "", "", "");
    }
     */
    public Food(String foodName, String mealType, String Date, int calories, int fat, int protein, int carbohydrate) {
        this.foodName = new SimpleStringProperty(foodName);
        this.mealType = new SimpleStringProperty(mealType);
        this.Date = new SimpleStringProperty(Date);
        this.calories = new SimpleIntegerProperty(calories);
        this.fat = new SimpleIntegerProperty(fat);
        this.carbohydrate = new SimpleIntegerProperty(carbohydrate);
    }
    
    //constructor for inserting to db

    public Food(String Date, String foodName, int calories, String mealType) {
        this.foodName = new SimpleStringProperty(foodName);
        this.mealType = new SimpleStringProperty(mealType);
        this.Date = new SimpleStringProperty(Date);
        this.calories = new SimpleIntegerProperty(calories);
    }

    Food(String string, String string0, String string1, int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Food(String string, int aInt, String string0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Food(String string, String string0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//getters

    public String getFoodName() {
        return foodName.get();
    }

    public String getMealType() {
        return mealType.get();
    }

    public String getDate() {
        return Date.get();
    }

    public int getCalories() {
        return calories.get();
    }

    public int getProtein() {
        return protein.get();
    }

    public int getCarbohydrates() {
        return carbohydrate.get();
    }

    public int getFat() {
        return fat.get();
    }
//getProperty what dis for lol

    public StringProperty getFoodNameProperty() {
        return foodName;
    }

    public StringProperty getDateProperty() {
        return Date;
    }

    public StringProperty getMealTypeProperty() {
        return mealType;
    }

    public IntegerProperty getCaloriesProperty() {
        return calories;
    }

    public IntegerProperty getFatProperty() {
        return fat;
    }

    public IntegerProperty getProteinProperty() {
        return protein;
    }

    public IntegerProperty getCarbohydrateProperty() {
        return carbohydrate;
    }
//setters

    public void setFoodName(String foodName) {
        this.foodName.set(foodName);
    }

    public void setMealType(String mealType) {
        this.mealType.set(mealType);
    }

    public void setDate(String Date) {
        this.Date.set(Date);
    }

    public void setCalories(int calories) {
        this.calories.set(calories);
    }

    public void setFat(int fat) {
        this.fat.set(fat);
    }

    public void setProtein(int protein) {
        this.protein.set(protein);
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate.set(carbohydrate);
    }

}

//bmi for overall
/*
  try {
            openConnection();
            Statement st = conn.createStatement();
            String selectQuery = "SELECT * FROM BodyMeasurements WHERE date = '" + todayDate + "'";
            ResultSet rs = st.executeQuery(selectQuery);
            int WeightAmt = rs.getInt("WeightAmt");
            while (rs.next()) {

                //  System.out.println(rs.getInt("SUM(caloriesBurnt)"));
                WeightAmt = rs.getInt("WeightAmt");
                System.out.println(WeightAmt);
            }
            weightLabel.setText(Integer.toString(WeightAmt));
            weightLabel.setVisible(true);

        } catch (SQLException ex) {

        }
*/