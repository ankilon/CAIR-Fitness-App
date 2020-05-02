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
public class SleepTable {
    private IntegerProperty sleepID;
    private StringProperty Date;
    private IntegerProperty HoursSlept;
    private StringProperty SleepQuality;
    private StringProperty wakeUpTime;
    private StringProperty SleepTime;
    
    public SleepTable(String Date, String wakeUpTime, String SleepTime) {
        this.Date = new SimpleStringProperty(Date);
        this.wakeUpTime = new SimpleStringProperty(wakeUpTime);
        this.SleepTime = new SimpleStringProperty(SleepTime);
    }
    
    //getters
    public int getSleepId(){
        return sleepID.get();
}
    public String getDate(){
        return Date.get();
    }
    
    public int getHoursSlept(){
        return HoursSlept.get();
    }
    
    public String getSleepQuality(){
        return SleepQuality.get();
    }
    
    public String getWakeUpTime(){
        return wakeUpTime.get();
    }
    
    public String getSleepTime(){
        return SleepTime.get();
    }
    
    //getproperty
      public IntegerProperty getSleepIDProperty() {
        return sleepID;
    }
      
      public StringProperty getDateProperty(){
          return Date;
      }
      
      public IntegerProperty getHoursSleptProperty(){
          return HoursSlept;
      }
      
      public StringProperty getSleepQualityProperty(){
          return SleepQuality;
      }
      
      public StringProperty getWakeUpTimeProperty(){
          return wakeUpTime;
      }
      
      public StringProperty getSleepTimeProperty(){
          return SleepTime;
      }
      
      //setters
      public void setSleepIDName (int sleepID) {
        this.sleepID.set(sleepID);
    }
      
      public void setDate (String Date){
          this.Date.set(Date);
      }
      
      public void setHoursSlept(int HoursSlept){
          this.HoursSlept.set(HoursSlept);
      }
      
      public void setSleepQuality(String SleepQuality){
          this.SleepQuality.set(SleepQuality);
      }
      
      public void setWakeUpTime(String wakeUpTime){
          this.wakeUpTime.set(wakeUpTime);
      }
      
      public void setSleepTime(String SleepTime){
          this.SleepTime.set(SleepTime);
      }
}
    


