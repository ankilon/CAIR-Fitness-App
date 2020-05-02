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
 * @author cathyxu
 */
public class AerobicExercisesTable {

    private IntegerProperty aerobicExerciseId;
    private StringProperty date;
    private StringProperty activity;
    private IntegerProperty minsSpent;
    private IntegerProperty caloriesBurnt;

    //constructor
    public AerobicExercisesTable(String date, String activity, int minsSpent, int caloriesBurnt) {
        this.date = new SimpleStringProperty(date);
        this.activity = new SimpleStringProperty(activity);
        this.minsSpent = new SimpleIntegerProperty(minsSpent);
        this.caloriesBurnt = new SimpleIntegerProperty(caloriesBurnt);

    }
    
    public AerobicExercisesTable(String date, String activity) {
        this.date = new SimpleStringProperty(date);
        this.activity = new SimpleStringProperty(activity);
    }

    //getters
    public int getAerExID(){
        return aerobicExerciseId.get();
    }
    
    public String getDate() {
        return date.get();
    }

    public String getActivity() {
        return activity.get();
    }

    public int getMinsSpent() {
        return minsSpent.get();
    }

    public int getCaloriesBurnt() {
        return caloriesBurnt.get();
    }

    //StringProperty???
    public IntegerProperty getaerobicExerciseIdProperty(){
        return aerobicExerciseId;
    }
    public StringProperty getDateProperty() {
        return date;
    }

    public StringProperty getActivityProperty() {
        return activity;
    }

    public IntegerProperty getMinsSpentProperty() {
        return minsSpent;
    }

    public IntegerProperty getCaloriesBurntProperty() {
        return caloriesBurnt;
    }

    //setters
    public void setDate(String date) {
        this.date.set(date);
    }
    
    public void setAerExID(int aerobicExerciseId){
        this.aerobicExerciseId.set(aerobicExerciseId);
    }
    
    public void setActivity(String activity){
        this.activity.set(activity);
    }
    
    public void setMinsSPent(int minsSpent){
        this.minsSpent.set(minsSpent);
    }
    
    public void setCaloriesBurnt(int caloriesBurnt){
        this.caloriesBurnt.set(caloriesBurnt);
    }

}

