import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.sql.*;

/**
 * Ryan Bookram
 * Software Development 1 (14877)
 * October 10, 2025
 * VehicleManager.java
 * This class allows the storage and manipulation of Vehicle information in a SQLite database
 */
public class VehicleManager {
    public ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();

    /**
     * Method: addVehicle
     * Parameters: info, stmt
     * Return: String
     * Purpose: Allows the addition of a single vehicle entry to the database
     */
    public String addVehicle(String info, Statement stmt) {

        int vinNumber;
        String make;
        String model;
        double price;
        String trans;
        int miles;
        String color;
        String drivetrain;
        String[] splitInfo  = info.split("-");

        try {
            if (splitInfo.length != 8) {
                return "Invalid format. Ensure 8 attributes are entered and are separated by dashes with no spaces. Format: VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain";
            }
            else {

                vinNumber = Integer.parseInt(splitInfo[0]);
                make = splitInfo[1];
                model = splitInfo[2];
                price = Double.parseDouble(splitInfo[3]);
                trans = splitInfo[4];
                miles = Integer.parseInt(splitInfo[5]);
                color = splitInfo[6];
                drivetrain = splitInfo[7];

                //vehicleList.add(new Vehicle(vinNumber, make, model, price, trans, miles, color, drivetrain));

                stmt.executeUpdate("INSERT INTO vehicles VALUES (" + vinNumber + ", '" + make + "', '" + model + "', " + price + ", '" + trans + "', " + miles + ", '" + color + "', '" + drivetrain + "')");

                return "Vehicle added successfully!";
            }
        }
        catch(Exception e) {
            return "Failed to add vehicle.";
        }
    }

    /**
     * Method: addFromText
     * Parameters: fileName, stmt
     * Return: String
     * Purpose: Allows the addition of multiple vehicles to the database through a text file
     */
    public String addFromText(String fileName, Statement stmt) {

        File inputFile = null;
        BufferedReader br = null;
        String vehicle;

        try {

            inputFile = new File(fileName);
            br = new BufferedReader(new FileReader(inputFile));

            while((vehicle = br.readLine()) != null) {
                addVehicle(vehicle, stmt);
            }

            return "Vehicles added successfully!";
        }
        catch(Exception e){
            return "An error has occurred. Please ensure your file path is correct and your text file is formatted correctly.";
        }
    }

    /**
     * Method: removeVehicle
     * Parameters: vin, stmt
     * Return: String
     * Purpose: Allows the removal of a single vehicle using the VIN number
     */
    public String removeVehicle(String vin, Statement stmt) {

        try{
            stmt.executeUpdate("DELETE FROM vehicles WHERE VIN = '" + vin + "'");
        }
        catch(Exception e){
            return e.getMessage();
        }
        return "Vehicle removed successfully!";
    }

    /**
     * Method: updateVehicle
     * Parameters: info, stmt
     * Return: String
     * Purpose: Updates a single vehicle based on the VIN number entered
     */
    public String updateVehicle(String info, Statement stmt) {

        String vinNumber;
        String make;
        String model;
        String price;
        String trans;
        String miles;
        String color;
        String drivetrain;
        String[] splitInfo = info.split("-");
        String output = "";

        try {

            vinNumber = splitInfo[0];
            make = splitInfo[1];
            model = splitInfo[2];
            price = splitInfo[3];
            trans = splitInfo[4];
            miles = splitInfo[5];
            color = splitInfo[6];
            drivetrain = splitInfo[7];

            stmt.executeUpdate("UPDATE vehicles SET vin = " + vinNumber + ", make = '" + make + "', model = '" + model + "', price = " + price + ", trans = '" + trans + "', miles = " + miles + ", color = '" + color + "', drive = '" + drivetrain + "' WHERE vin = " + vinNumber + ";");

            output = "Vehicle updated successfully!";

        }
        catch (Exception e) {
            output = "Error updating vehicle.";
        }
        return output;
    }

    /**
     * Method: evaluateVehicle
     * Parameters: vehicle, stmt
     * Return: String
     * Purpose: Estimates a vehicle's possible condition
     */
    public String evaluateVehicle(String vehicle, Statement stmt) {

        String eval;
        int vinNumber;
        String make;
        String model;
        double price;
        String trans;
        int miles;
        String color;
        String drivetrain;
        String[] splitInfo  = vehicle.split(", ");

        try{
            vinNumber = Integer.parseInt(splitInfo[0]);
            make = splitInfo[1];
            model = splitInfo[2];
            price = Double.parseDouble(splitInfo[3]);
            trans = splitInfo[4];
            miles = Integer.parseInt(splitInfo[5]);
            color = splitInfo[6];
            drivetrain = splitInfo[7];

            Vehicle tempV = new Vehicle(vinNumber, make, model, price, trans, miles, color, drivetrain);
            eval =  "The condition of the " + tempV.getMake() + " " + tempV.getModel() + " (" + vinNumber + ") is: " + tempV.evaluate();
        }
        catch(Exception e){
            eval = "Error evaluating vehicle.";
        }
        return eval;
    }

    /**
     * Method: displayVehicles
     * Parameters: None
     * Return: None
     * Purpose: Prints the full list of all vehicles in the ArrayList to the screen
     */
    public void displayVehicles() {

        for (Vehicle vehicle : vehicleList) {
            System.out.println(vehicle.toString());
        }
    }

    /**
     * Method: getList
     * Parameters: None
     * Return: ArrayList<Vehicle>
     * Purpose: Returns the ArrayList containing all vehicle objects
     */
    public ArrayList<Vehicle> getList() {
        return vehicleList;
    }
}
