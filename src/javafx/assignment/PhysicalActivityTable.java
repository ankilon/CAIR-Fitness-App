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
public class PhysicalActivityTable {
    private IntegerProperty activitiesID;
    private IntegerProperty TotalTime;
    private IntegerProperty TotalCalories;
    private StringProperty Date;
    private StringProperty Activity;
    
    
public PhysicalActivityTable(String Activity, int TotalTime, int TotalCalories){
    this.Activity = new SimpleStringProperty(Activity);
    this.TotalTime = new SimpleIntegerProperty(TotalTime);
    this.TotalCalories = new SimpleIntegerProperty(TotalCalories);
}

//getters
public int getActivitiesID(){
    return activitiesID.get();
}

public String getDate(){
    return Date.get();
}

public String getActivity(){
    return Activity.get();
}

public int getTotalTime(){
    return TotalTime.get();
}

public int getTotalCalories(){
    return TotalCalories.get();
}
 
//stringproperty???
public IntegerProperty getActivitiesIDProperty(){
    return activitiesID;
}

public StringProperty getDateProperty(){
    return Date;
}

public StringProperty getActivityProperty(){
        return Activity;
}

public IntegerProperty getTotalTimeProperty(){
    return TotalTime;
}

public IntegerProperty getTotalCaloriesProperty(){
    return TotalCalories;
}

//setters
public void setActivitiesID(int activitiesID){
    this.activitiesID.set(activitiesID);
}

public void setDate(String Date){
    this.Date.set(Date);
}

public void setActivity(String Activity){
    this.Activity.set(Activity);
}

public void setTotalTime(int TotalTime){
    this.TotalTime.set(TotalTime);
}

public void setTotalCalories(int TotalCalories){
    this.TotalCalories.set(TotalCalories);
}

}

