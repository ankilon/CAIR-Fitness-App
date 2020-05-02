/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.assignment;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author cathyxu
 */
public class ResistanceExercisesTable {
    private IntegerProperty resistanceExerciseId;
    private StringProperty date;
    private StringProperty activity;
    private IntegerProperty sets;
    private IntegerProperty reps;
    private IntegerProperty weight;
    private IntegerProperty caloriesBurnt;
    
    public ResistanceExercisesTable(String date, String activity, int sets, int reps, int weight, int caloriesBurnt){
        this.date = new SimpleStringProperty(date);
        this.activity = new SimpleStringProperty(activity);
        this.sets = new SimpleIntegerProperty(sets);
        this.reps = new SimpleIntegerProperty(reps);
        this.caloriesBurnt = new SimpleIntegerProperty(caloriesBurnt);
        this.weight = new SimpleIntegerProperty(weight);
    }
    
     public ResistanceExercisesTable(String date, String activity, int sets, int reps, int weight){
        this.date = new SimpleStringProperty(date);
        this.activity = new SimpleStringProperty(activity);
        this.sets = new SimpleIntegerProperty(sets);
        this.reps = new SimpleIntegerProperty(reps);
        this.weight = new SimpleIntegerProperty(weight);
    }
    //getters
    public String getDate(){
        return date.get();
    }
    
    public int getResistanceExerciseId(){
        return resistanceExerciseId.get();
    }
    
    public String getActivity(){
        return activity.get();
    }
    
    public int getSets(){
        return sets.get();
    }
    
    public int getReps(){
        return reps.get();
    }
    
    public int getWeight(){
        return weight.get();
    }
    
    public int getCaloriesBurnt(){
        return caloriesBurnt.get();
    }
    
    //get property??
    public IntegerProperty getResistanceExerciseIdProperty(){
        return resistanceExerciseId;
    }
    
    public StringProperty getDateProperty(){
        return date;
    }
    
    public StringProperty getActivityProperty(){
        return activity;
    }
    
    public IntegerProperty getSetsProperty(){
        return sets;
    }
    
    public IntegerProperty getRepsProperty(){
        return reps;
    }
    
    public IntegerProperty getWeightProperty(){
        return weight;
    }
    
    public IntegerProperty getCaloriesBurntProperty(){
        return caloriesBurnt;
    }
    
    //setters
    public void setResistanceExerciseId(int resistanceExerciseId){
        this.resistanceExerciseId.set(resistanceExerciseId);
    }
    
    public void setDate(String date){
        this.date.set(date);
    }
    
    public void setActivity(String activity){
        this.activity.set(activity);
    }
    
    public void setSets(int sets){
        this.sets.set(sets);
    }
    
    public void setWeight(int weight){
        this.weight.set(weight);
    }
    
    public void setCaloriesBurnt(int caloriesBurnt){
        this.caloriesBurnt.set(caloriesBurnt);
    }
}
