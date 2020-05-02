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
public class MedicalRecordsTable {
 private IntegerProperty checkupId;
 private IntegerProperty userID;
 private StringProperty date;
 private StringProperty checkupType;
 private StringProperty doctorName;
 private StringProperty description;
 
 public MedicalRecordsTable (String date, String checkupType, String doctorName, String description){
     this.date = new SimpleStringProperty(date);
     this.checkupType = new SimpleStringProperty(checkupType);
     this.doctorName = new SimpleStringProperty(doctorName);
     this.description = new SimpleStringProperty(description);
 }
 
 public MedicalRecordsTable (String date, String checkupType, String description){
     this.date = new SimpleStringProperty(date);
    
     this.checkupType = new SimpleStringProperty(checkupType);
     this.description = new SimpleStringProperty(description);
 }
 
  public MedicalRecordsTable (String date, String description){
     this.date = new SimpleStringProperty(date);
     this.description = new SimpleStringProperty(description);
 }
 
 //getters
 public String getDate(){
     return date.get();
 }
 
 public String getCheckupType(){
     return checkupType.get();
 }
 
 public String getDoctorName(){
     return doctorName.get();
 }
 
 public String getDescription(){
     return description.get();
 }
 
 public int getCheckupID(){
     return checkupId.get();
 }
 
 public int getUserID(){
     return userID.get();
 }
 
 
 //stringproperty
  
  public StringProperty getDateProperty(){
      return date;
  }
     
  public StringProperty getCheckupTypeProperty(){
      return checkupType;
  }
  
  public IntegerProperty getCheckupIDProperty(){
      return checkupId;
  }
  
  public IntegerProperty getUserIDProperty(){
      return userID;
  }
  
  public StringProperty getDoctorNameProperty(){
      return doctorName;
  }
  
  public StringProperty getDescriptionProperty(){
      return description;
  }
  
  //setters
  public void setCheckupId(int checkupId){
      this.checkupId.set(checkupId);
  }
  
  public void setUserID(int userID){
      this.userID.set(userID);
  }
  
  public void setDate(String date){
      this.date.set(date);
  }
  
  public void setDoctorName(String doctorName){
      this.doctorName.set(doctorName);
  }
  
  public void setDescription(String description){
      this.description.set(description);
  }
  
}