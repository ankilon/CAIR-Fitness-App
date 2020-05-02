 package javafx.assignment;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author cathyxu
 */

public class Database {

    public static Connection conn;

    public static void openConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ResultSet getResultSet(String sqlstatement) throws SQLException {
        openConnection();
        java.sql.Statement statement = conn.createStatement();
        ResultSet RS = statement.executeQuery(sqlstatement);
        return RS;
    }

    public void insertStatement(String insert_query) throws SQLException {
        java.sql.Statement stmt = null;
        openConnection();
        try {
            System.out.println("Database opened successfully");
            stmt = conn.createStatement();
            System.out.println("The query was: " + insert_query);
            stmt.executeUpdate(insert_query);
            stmt.close();
            conn.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            //Rachel adding comment
        }
        stmt.close();
    }

    public static void createNutritionTable() throws SQLException {
        PreparedStatement createCaloriesTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Nutrition table check");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "Nutrition", null);
            if (!rs.next()) {
                createCaloriesTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Nutrition "
                        + "(nutID INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "UserID INTEGER,"
                        + "Date TEXT NOT NULL,"
                        + "Foodname TEXT NOT NULL, "
                        + "MealType TEXT NOT NULL,"
                        + "Calories INTEGER,"
                        + "Fats INTEGER,"
                        + "Carbohydrates INTEGER,"
                        + "Protein INTEGER);");
                createCaloriesTable.execute();
                System.out.println("Nutrition table created");
                insertDemoData = conn.prepareStatement("INSERT INTO Nutrition(nutID, UserID, Date, Foodname, MealType, Calories, Fats, Carbohydrates, Protein) "
                        + "VALUES (1, 1, '2019-03-01', 'Cheeseburger', 'Lunch', 313, 14, 33, 15),"
                        + "(2, 1, '2019-03-01', 'Chicken Pad Thai', 'Dinner', 770, 30, 100, 26),"
                        + "(3, 1, '2019-03-02', 'Salmon Udon', 'Lunch', 260, 2, 52, 16),"
                        + "(4, 1, '2019-03-03', 'Steak', 'Lunch', 135, 5, 0, 24),"
                        + "(5, 1, '2019-03-03, 'Oats', 'Breakfast', 117, 7, 66, 17),"
                        + "(6, 1, '2019-03-03', 'Pasta', 'Dinner', 225, 8, 1, 47),"
                        + "(7, 1, '2019-03-04', 'Apple', 'Lunch', 160, 0, 44,0),"
                        + "(8, 1, '2019-03-05', 'Spinach and Cheese Pie', 'Lunch', 270, 10, 29, 16),"
                        + "(9, 1, '2019-03-06', 'Pancakes', 'Breakfast', 280, 9, 45, 6),"
                        + "(10, 1, '2019-03-06', 'Broccoli', 'Dinner', 31, 0, 6, 3),"
                        + "(11, 1, '2019-03-07', 'Egg', 'Breakfast', 78, 5, 5, 6),"
                        + "(12, 1, '2019-03-07', 'Fried Rice', 'Dinner', 163, 3, 31, 5),"
                        + "(13, 1, '2019-03-07', 'Taco', 'Lunch', 156, 9, 14, 6);");
               
                
                insertDemoData.execute();
            } else {
                System.out.println("Nutrition table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createStepsTable() throws SQLException {
        PreparedStatement createStepsTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("STEPS table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "STEPS", null);
            if (!rs.next()) {
                createStepsTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Steps"
                        + "(stepID INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "totalSteps INTEGER NOT NULL, "
                        + "flightsStairsWalked INTEGER NOT NULL,"
                        + "totalDistance DOUBLE NOT NULL, "
                        + "caloriesBurnt INTEGER NOT NULL, "
                        + "dates TEXT NOT NULL);");
                createStepsTable.execute();
                System.out.println("STEPS Table created");
                insertDemoData = conn.prepareStatement("INSERT INTO Steps(stepID, totalSteps,flightsStairsWalked, totalDistance, caloriesBurnt, dates)"
                        + "VALUES (1, 6478,5, 5, 298, '2019-03-01'),"
                        + "(2, 8233, 6, 6.7, 478,'2019-03-02'),"
                        + "(3, 7103, 5, 6.9, 428, '2019-03-03'),"
                        + "(4, 8129 ,3, 7.2, 465,'2019-03-04'),"
                        + "(5, 5069, 2, 4.2, 201, '2019-03-05'),"
                        + "(6, 6219, 1, 5.4, 245, '2019-03-06'),"
                        + "(7, 6217, 2, 4.4, 243, '2019-03-07');");
                insertDemoData.execute();
                System.out.println("Steps data inserted");
            } else {
                System.out.println("Steps table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void createHeartRateTable() throws SQLException {
        PreparedStatement createHeartRateTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Heartrate table check");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "HEARTRATE", null);
            if (!rs.next()) {
                createHeartRateTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS HeartRate"
                        + "(hrID INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "UserID INTEGER,"
                        + "Date TEXT NOT NULL,"
                        + "Minimum INTEGER, "
                        + "Maximum INTEGER, "
                        + "Average INTEGER);");
                createHeartRateTable.execute();
                System.out.println("HEARTRATE table created");
                insertDemoData = conn.prepareStatement("INSERT INTO HeartRate(hrID, UserID, Date, Minimum, Maximum, Average) "
                        + "VALUES (1, 1, '2019-03-01', 60, 130, 80),"
                        + "(2, 1, '2019-03-02', 68, 220, 90),"
                        + "(3, 1, '2019-03-03', 90, 200, 94),"
                        + "(4, 1, '2019-03-04', 75, 100, 76),"
                        + "(5, 1, '2019-03-05', 77, 103, 83),"
                        + "(6, 1, '2019-03-06', 80, 200, 97),"
                        + "(7, 1, '2019-03-07', 69, 169, 86);");
                insertDemoData.execute();
                System.out.println("Heartrate DATA INSERTED");
            } else {
                System.out.println("HeartRate table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createSleepTable() throws SQLException {
        PreparedStatement createSleepTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Sleep table check");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "SLEEP", null);
            if (!rs.next()) {
                createSleepTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Sleep "
                        + "(sleepID INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "UserID INTEGER,"
                        + "Date TEXT NOT NULL, "
                        + "HoursSlept INTEGER, "
                        + "SleepQuality TEXT NOT NULL,"
                        + "wakeUpTime TEXT NOT NULL,"
                        + "SleepTime TEXT NOT NULL);");
                createSleepTable.execute();
                System.out.println("Sleep table created");
                insertDemoData = conn.prepareStatement("INSERT INTO Sleep(sleepID, UserID, Date, HoursSlept, SleepQuality, wakeUpTime, SleepTime) "
                        + "VALUES (1, 1, '2019-03-01', 9, 'good', '7:00', '22:00'),"
                        + "(2, 1, '2019-03-02', 6, 'moderate', '6:40', '00:30'),"
                        + "(3, 1, '2019-03-03', 9, 'good', '7:30', '22:43'), "
                        + "(4, 1, '2019-03-04', 3, 'bad', '6:00', '3:00'), "
                        + "(5, 1, '2019-03-05', 5, 'moderate', '6:30', '01:30'), "
                        + "(6, 1, '2019-03-06', 6, 'moderate', '6:34', '00:49'), "
                        + "(7, 1, '2019-03-07', 5, 'moderate', '8:20', '3:27);");

                insertDemoData.execute();
                System.out.println("Sleep data inserted");

            } else {
                System.out.println("Sleep table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void createBodyMeasurementsTable() throws SQLException {
        PreparedStatement createBodyMeasurementsTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("BodyMeasurement table check");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "BodyMeasurements", null);
            if (!rs.next()) {
                createBodyMeasurementsTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS BodyMeasurements "
                        + "(bmID INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "Date TEXT, "
                        + "WeightAmt INTEGER,"
                        + "LeanMassPercentage INTEGER,"
                        + "FatMassPercentage INTEGER,"
                        + "Height INTEGER);");
                createBodyMeasurementsTable.execute();
                System.out.println("BodyMeasurements table created");
                    insertDemoData = conn.prepareStatement("INSERT INTO BodyMeasurements(bmID, Date, WeightAmt, LeanMassPercentage, FatMassPercentage, Height) "
                        + "VALUES (1, '2019-03-01', 58, 18, 82, 167),"
                        + "(2, '2019-03-02', 57, 18, 82, 167), "
                        + "(3, '2019-03-03', 57, 18, 82, 167), "
                        + "(4, '2019-03-04', 57, 18, 82, 167), "
                        + "(5, '2019-03-05', 57, 18, 82, 167), "
                        + "(6, '2019-03-06', 58, 17, 83, 167), "
                        + "(7, '2019-03-07', 57, 17, 83, 167); ");

                insertDemoData.execute();
                System.out.println("BodyMeasurements data inserted");

            } else {
                System.out.println("BodyMeasurements table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createActivitiesTable() throws SQLException {
        PreparedStatement createActivitiesTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Activities table check");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "ACTIVITIES", null);
            if (!rs.next()) {
                createActivitiesTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Activities "
                        + "(activitiesID INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "UserID INTEGER,"
                        + "Date TEXT NOT NULL, "
                        + "Activity TEXT NOT NULL,"
                        + "TotalTime INTEGER, "
                        + "TotalCalories INTEGER);");
                createActivitiesTable.execute();
                System.out.println("Activities table created");
                insertDemoData = conn.prepareStatement("INSERT INTO Activities(activitiesID, UserID, Date, Activity, TotalTime, TotalCalories) "
                        + "VALUES (1, 1, '2019-03-01', 'Basketball', 45, 400),"
                        + "(2, 1, '2019-03-02', 'Soccer', 30, 350),"
                        + "(3, 1, '2019-03-03', 'Treadmill', 15, 120), "
                        + "(4, 1, '2019-03-04', 'Weights', 30, 300), "
                        + "(5, 1, '2019-03-05', 'Rowing', 45, 400), "
                        + "(6, 1, '2019-03-06', 'Basketball', 30, 300), "
                        + "(7, 1, '2019-03-07', 'Cardio', 50, 400);");

                insertDemoData.execute();
                System.out.println("Activities data inserted");
            } else {
                System.out.println("Activities table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void createAerobicExercisesTable() throws SQLException {
        PreparedStatement createAerobicExercisesTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking AerobicExercies table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "AerobicExercises", null);
            if (!rs.next()) {
                createAerobicExercisesTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS AerobicExercises "
                        + "(aerobicExerciseId INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "UserID INTEGER,"
                        + "date TEXT NOT NULL,"
                        + "activity TEXT NOT NULL,"
                        + "minsSpent INTEGER,"
                        + "caloriesBurnt INTEGER);");
                createAerobicExercisesTable.execute();
                System.out.println("AerobicExercises table created");
                insertDemoData = conn.prepareStatement("INSERT INTO AerobicExercises(aerobicExerciseId, UserID, date, activity, minsSpent, caloriesBurnt) "
                        + "VALUES (1, 1, '2019-03-01', 'Basketball', 45, 400),"
                        + "(2, 1, '2019-03-02', 'Soccer', 30, 350),"
                        + "(3, 1, '2019-03-04', 'Treadmill', 15, 120),"
                        + "(4, 1, '2019-03-06', 'Netball', 30, 300),"
                        + "(5, 1, '2019-03-07', 'Rowing Machine', 15, 120);");

                insertDemoData.execute();
                System.out.println("AerobicExercise DATA INSERTED");

            } else {
                System.out.println("AerobicExercise table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void createResistanceExercisesTable() throws SQLException {
        PreparedStatement createResistanceExercisesTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking ResistanceEx table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "ResistanceExercises", null);
            if (!rs.next()) {
                createResistanceExercisesTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS ResistanceExercises "
                        + "(ResistanceExerciseId INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "UserID INTEGER,"
                        + "date TEXT NOT NULL,"
                        + "activity TEXT NOT NULL,"
                        + "sets INTEGER,"
                        + "reps INTEGER,"
                        + "weight INTEGER,"
                        + "caloriesBurnt INTEGER);");
                createResistanceExercisesTable.execute();
                System.out.println("ResistanceExercises table created");
                insertDemoData = conn.prepareStatement("INSERT INTO ResistanceExercises(ResistanceExerciseId, UserID, date, activity, sets, reps, weight, caloriesBurnt) "
                        + "VALUES (1, 1, '2019-03-01', 'Dumbbell Curls', 5, 10, 50, 200),"
                        + "(2, 1, '2019-03-02', 'Squats', 5, 10, 20, 200),"
                        + "(3, 1, '2019-03-04', 'Situps', 6, 10, 5, 150),"
                        + "(4, 1, '2019-03-05', 'Barbell Bench Press', 5, 8, 80, 250),"
                        + "(5, 1, '2019-03-07', 'Leg Press', 5, 15, 40, 250);");

                insertDemoData.execute();
                System.out.println("ResistanceExercises DATA INSERTED");

            } else {
                System.out.println("ResistanceExercises table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void createGymAttendanceTable() throws SQLException {
        PreparedStatement createGymAttendanceTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking gymAttendance table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "GymAttendance", null);
            if (!rs.next()) {
                createGymAttendanceTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS GymAttendance "
                        + "(gymAttId INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "UserID INTEGER,"
                        + "date TEXT,"
                        + "attendance TEXT,"
                        + "minsSpent INTEGER);");
                createGymAttendanceTable.execute();
                System.out.println("GymAttendance table created");
                insertDemoData = conn.prepareStatement("INSERT INTO GymAttendance(gymAttId, UserID, date, attendance, minsSpent) "
                        + "VALUES (1, 1, '2019-03-01', 'No', 0),"
                        + "(2, 1, '2019-03-02', 'Yes', 40),"
                        + "(3, 1, '2019-03-03', 'Yes', 50),"
                        + "(4, 1, '2019-03-04', 'Yes', 75),"
                        + "(5, 1, '2019-03-05', 'Yes', 40),"
                        + "(6, 1, '2019-03-06, 'No', 0),"
                        + "(7, 1, '2019-03-07', 'Yes', 80);");

                insertDemoData.execute();
                System.out.println("GymAttendance DATA INSERTED");

            } else {
                System.out.println("GymAttendance table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void createIdealTable() throws SQLException {
        PreparedStatement createIdealTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Ideal table check");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "IDEAL", null);
            if (!rs.next()) {
             
                createIdealTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Ideal "
                        + "(IdealId INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "UserID INTEGER,"
                        + "IdealWeight INTEGER NOT NULL, "
                        + "IdealSteps INTEGER NOT NULL,"
                        + "IdealCaloriesBurnt INTEGER NOT NULL,"
                        + "IdealCaloriesConsumed INTEGER NOT NULL);");
                createIdealTable.execute();
                System.out.println("Ideal table created");
                insertDemoData = conn.prepareStatement("INSERT INTO Ideal(IdealId, UserID, IdealWeight, IdealSteps, IdealCaloriesBurnt, IdealCaloriesConsumed) "
                        + "VALUES (1, 1, 50,10000, 300, 1000);");

                insertDemoData.execute();
                System.out.println("Ideal data inserted");

            } else {
                System.out.println("Ideal table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void createMedicalCheckupsTable() throws SQLException {
        PreparedStatement createMedicalCheckupsTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("MedicalCheckups table check");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "MedicalCheckups", null);
            if (!rs.next()) {
                createMedicalCheckupsTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS MedicalCheckups "
                        + "(checkupId INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "UserID INTEGER,"
                        + "date TEXT NOT NULL,"
                        + "checkupType TEXT NOT NULL,"
                        + "doctorName TEXT NOT NULL,"
                        + "description TEXT NOT NULL);");
                createMedicalCheckupsTable.execute();
                System.out.println("MedicalCheckups table created");
                insertDemoData = conn.prepareStatement("INSERT INTO MedicalCheckups(checkupId, UserID, date, checkupType, doctorName, description) "
                        + "VALUES (1, 1, '2019-03-02', 'Dentist', 'Dr Blair', 'Annual Dentist appointment. Everything is healthy.'),"
                        + "(2, 1, '2018-04-04', 'Standard GP', 'Dr Jacob', 'Felt feverish. Had a runny nose and a cough.'),"
                        + "(3, 1, '2018-07-16', 'Optometrist', 'Dr Phoebe','Annual eye checkup. Healthy eyesight.'),"
                        + "(4, 1, '2019-10-29', 'Standard GP', 'Dr Jacob', 'Wrist pain due to repetitive strain.'),"
                        + "(5, 1, '2019-02-01', 'Dentist', 'Dr Blair', 'Annual dentist appointment. Healthy gums and teeth.'),"
                        + "(6, 1, '2019-03-02', 'Standard GP', 'Dr Jacob', 'Common cold symptoms. Received medication from doctor.');");

                insertDemoData.execute();
                System.out.println("MedicalCheckups data inserted");

            } else {
                System.out.println("MedicalCheckups table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    
            public static void createMentalWellbeingTable() throws SQLException {
        PreparedStatement createMentalWellbeingTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("MentalWellbeing table check");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "MentalWellbeing", null);
            if (!rs.next()) {
                createMentalWellbeingTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS MentalWellbeing "
                        + "(mentalWellbeingId INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "UserID INTEGER,"
                        + "date TEXT NOT NULL,"
                        + "mindfulMins INTEGER,"
                        + "hrsHappy INTEGER,"
                        + "hrsOk INTEGER,"
                        + "hrsSad INTEGER,"
                        + "mood TEXT NOT NULL);");
                createMentalWellbeingTable.execute();
                System.out.println("MentalWellbeing table created");
                insertDemoData = conn.prepareStatement("INSERT INTO MentalWellbeing(mentalWellbeingId, UserID,  date, mindfulMins, hrsHappy, hrsOk, hrsSad) "
                        + "VALUES (1, 1, '2019-03-01', 10, 'Happy'),"
                        + "(2, 1, '2019-03-02', 20, 6, 3, 2),"
                        + "(3, 1, '2019-03-03', 15, 4, 4, 3),"
                        + "(4, 1, '2019-03-04', 10, 8, 3, 2),"
                        + "(5, 1, '2019-03-05', 30, 3, 5, 4),"
                        + "(6, 1, '2019-03-06', 10, 9, 5, 2),"
                        + "(7, 1, '2019-03-07', 15, 7, 3, 5);");
                

                insertDemoData.execute();
                System.out.println("MentalWellbeing data inserted");

            } else {
                System.out.println("MentalWellbeing table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
            
            public static void createUserDatabase() throws SQLException {
        PreparedStatement createUserTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking User table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "User", null);
            if (!rs.next()) {
                createUserTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS User "
                        + "(UserID INTEGER,"
                        + "fName TEXT,"
                        + "lName TEXT,"
                        + "DOB TEXT,"
                        + "email TEXT,"
                        + "address TEXT);");
                createUserTable.execute();
                System.out.println("User table created");
                insertDemoData = conn.prepareStatement("INSERT INTO User(UserID, fName, lName, DOB, email, address) "
                        + "VALUES (1, 'Claire', 'Zhang', '1994-11-03', 'clairezhang@gmail.com', '2605 Kensington St, Randwick');");
                        
                        

                insertDemoData.execute();
                System.out.println("User DATA INSERTED");

            } else {
                System.out.println("User table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}

