/**
 * Ryan Bookram
 * Software Development 1 (14877)
 * October 10, 2025
 * Vehicle.java
 * This class stores and allows the manipulation/retrieval of vehicle attributes.
 */
public class Vehicle {
    private int vinNumber;
    private String make;
    private String model;
    private double price;
    private String trans;
    private int miles;
    private String color;
    private String drivetrain;
    private String condition;

    public  Vehicle(int vinNumber, String make, String model, double price, String trans, int miles, String color, String drivetrain) {

        String tempValue = String.format("%.2f", price);

        this.vinNumber = vinNumber;
        this.make = make;
        this.model = model;
        this.price = Double.parseDouble(tempValue);
        this.trans = trans;
        this.miles = miles;
        this.color = color;
        this.drivetrain = drivetrain;
    }

    public void setVinNumber(int vinNumber) {
        this.vinNumber = vinNumber;
    }

    public int getVinNumber() {
        return vinNumber;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getMake() {
        return make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public int getMiles() {
        return miles;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }

    public String getDrivetrain() {
        return drivetrain;
    }

    /**
     * Method: evaluate
     * Parameters: None
     * Return: String
     * Purpose: Uses the vehicle's mileage to estimate its possible condition
     */
    public String evaluate() {

        if(miles >= 0 && miles <= 10000) {
            condition = "New";
        }
        else if(miles > 10000 && miles <= 50000) {
            condition = "Maintained";
        }
        else if (miles > 50000 && miles <= 100000) {
            condition = "Used";
        }
        else if (miles > 100000 && miles <= 200000) {
            condition = "Worn";
        }
        else if (miles > 200000 && miles <= 300000) {
            condition = "Old";
        }
        else {
            condition = "Unavailable (Invalid Mileage)";
        }
        return condition;
    }

    /**
     * Method: toString
     * Parameters: None
     * Return: String
     * Purpose: Overrides the toString method to return the attributes of this vehicle
     */
    @Override
    public String toString() {
        return vinNumber + ", " + make + ", " + model + ", " + price + ", " + trans + ", " + miles + ", " + color + ", " + drivetrain;
    }
}
