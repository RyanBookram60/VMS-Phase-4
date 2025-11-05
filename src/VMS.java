import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Ryan Bookram
 * Software Development (14877)
 * October 10, 2025
 * VMS.java
 * This class operates as a Vehicle Managment System, allowing users to store and manipulate vehicle attributes
 */
public class VMS {
    public static String jdbcURL;
    public static Connection con;
    public static String selectAll = "SELECT * FROM vehicles";
    public static Statement stmt;
    public static ResultSet rs;

    /**
     * Method: Main
     * Return: None
     * Purpose: Incorporates a GUI system to allow users to interact with the managment system
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                constructGUI();
            }
        });
    }

    /**
     * Method: constructGUI
     * Return: None
     * Purpose: Defines logic and visuals for the GUI system
     */
    public static void constructGUI() {

        VehicleManager vm = new VehicleManager();

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame mainGUI = new JFrame("Vehicle Management System");
        mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGUI.setResizable(false);

        JFrame connectGUI = new JFrame("Vehicle Management System: Connect");
        connectGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connectGUI.setResizable(false);

        BoxLayout layout1 = new BoxLayout(mainGUI.getContentPane(), BoxLayout.Y_AXIS);
        BoxLayout layout2 = new BoxLayout(connectGUI.getContentPane(), BoxLayout.Y_AXIS);
        GridLayout buttonLayout = new GridLayout(1, 10);
        GridLayout singleLayout = new GridLayout(1, 1);
        mainGUI.setLayout(layout1);
        mainGUI.setMaximumSize(new Dimension(1280, 720));
        connectGUI.setMinimumSize(new Dimension(192, 144));
        connectGUI.setLayout(layout2);

        JPanel listPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel buttonPanel = new JPanel(buttonLayout);
        JPanel inputPanel = new JPanel(singleLayout);
        JPanel outputPanel = new JPanel(singleLayout);
        JPanel exitPanel = new JPanel();

        DefaultListModel<String> listModel = new DefaultListModel<String>();
        JList<String> vehicleList = new JList<String>(listModel);
        vehicleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ScrollPane vScroll = new ScrollPane();
        vScroll.add(vehicleList);
        vScroll.setSize(1200, 500);

        JLabel infoLabel = new JLabel("Enter text below. ADD and UPDATE commands require the format: VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain. AFT (Add from texfile) requires a valid file path. Select a list entry for REMOVE and EVALUATE.");
        JLabel outputLabel = new JLabel("");

        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton updateButton = new JButton("Update");
        JButton evaluateButton = new JButton("Evaluate");
        JButton addTextButton = new JButton("AFT");
        JButton mainExitButton = new JButton("Exit");
        JButton disconnectButton = new JButton("Disconnect");
        JButton connectExitButton = new JButton("Exit");

        JTextField inputBox = new JTextField();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputBox.getText().isEmpty()){
                    outputLabel.setText(vm.addVehicle(inputBox.getText(), stmt));
                    inputBox.setText("");
                    listModel.clear();

                    try{
                        rs = stmt.executeQuery(selectAll);
                        while(rs.next()) {
                            listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                        }
                    }
                    catch(Exception x) {
                        outputLabel.setText("Failed to add vehicle.");
                    }
                }
            }
        });

        addTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputBox.getText().isEmpty()){
                    outputLabel.setText(vm.addFromText(inputBox.getText(), stmt));
                    inputBox.setText("");
                    listModel.clear();

                    try{
                        rs = stmt.executeQuery(selectAll);
                        while(rs.next()) {
                            listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                        }
                    }
                    catch(Exception x) {
                        outputLabel.setText("Failed to add vehicles.");
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!vehicleList.isSelectionEmpty()) {
                    String[] splitInfo = vehicleList.getSelectedValue().split(",");
                    String vin = splitInfo[0];
                    outputLabel.setText(vm.removeVehicle(vin, stmt));
                    listModel.clear();

                    try{
                        rs = stmt.executeQuery(selectAll);
                        while(rs.next()) {
                            listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                        }
                    }
                    catch(Exception x) {
                        outputLabel.setText("Failed to remove vehicle.");
                    }

                    vehicleList.clearSelection();
                }
            }
        });

        vehicleList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if(!vehicleList.isSelectionEmpty()) {
                        String[] splitInfo = vehicleList.getSelectedValue().split(",");
                        String vin = splitInfo[0];
                        outputLabel.setText(vm.removeVehicle(vin, stmt));
                        listModel.clear();

                        try{
                            rs = stmt.executeQuery(selectAll);
                            while(rs.next()) {
                                listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                            }
                        }
                        catch(Exception x) {
                            outputLabel.setText("Failed to remove vehicle.");
                        }

                        vehicleList.clearSelection();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputBox.getText().isEmpty()){
                    boolean exists = false;
                    String[] inputInfo = inputBox.getText().split("-");
                    String[] listInfo;
                    String inputVin;
                    String listVin;

                    if(inputInfo.length != 8){
                        outputLabel.setText("Invalid format. Ensure 8 attributes are entered and are separated by dashes with no spaces. Format: VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain");
                    }
                    else {
                        inputVin = inputInfo[0];
                        for(int i = 0; i < listModel.size(); i++) {
                            listInfo = listModel.getElementAt(i).split(",");
                            listVin = listInfo[0];

                            if(listVin.equals(inputVin)){
                                exists = true;
                            }
                        }
                    }

                    outputLabel.setText("Vehicle entry not found. Ensure the VIN number is correct.");

                    if(exists){
                        outputLabel.setText(vm.updateVehicle(inputBox.getText(), stmt));
                    }

                    inputBox.setText("");
                    listModel.clear();

                    try{
                        rs = stmt.executeQuery(selectAll);
                        while(rs.next()) {
                            listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                        }
                    }
                    catch(Exception x) {
                        outputLabel.setText("Failed to update vehicle.");
                    }
                }
            }
        });

        evaluateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!vehicleList.isSelectionEmpty()) {
                    outputLabel.setText(vm.evaluateVehicle(vehicleList.getSelectedValue(), stmt));
                }
            }
        });

        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    con.close();
                    listModel.clear();
                    mainGUI.setVisible(false);
                    connectGUI.setVisible(true);
                }
                catch(Exception x) {
                    outputLabel.setText("Failed to disconnect database. Try again.");
                }
            }
        });

        mainExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.dispose();
                connectGUI.dispose();
            }
        });

        connectExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.dispose();
                connectGUI.dispose();
            }
        });

        mainGUI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() != vehicleList && !vehicleList.getBounds().contains(e.getPoint())) {
                    vehicleList.clearSelection();
                }
            }
        });

        listPanel.add(vScroll);
        infoPanel.add(infoLabel);
        buttonPanel.add(addButton);
        buttonPanel.add(addTextButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(evaluateButton);
        buttonPanel.add(inputBox);
        inputPanel.add(inputBox);
        outputPanel.add(outputLabel);
        exitPanel.add(disconnectButton);
        exitPanel.add(mainExitButton);

        mainGUI.add(listPanel);
        mainGUI.add(infoPanel);
        mainGUI.add(buttonPanel);
        mainGUI.add(inputPanel);
        mainGUI.add(outputPanel);
        mainGUI.add(exitPanel);

        JLabel startUp = new JLabel("Welcome to Vehicle Management System! Enter the file path to your SQLite database file to get started.");
        JLabel connectOutput = new JLabel(" ");
        JTextField connectBox = new JTextField();
        JButton connectButton = new JButton("Connect");
        
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!connectBox.getText().isEmpty()) {
                    jdbcURL = "jdbc:sqlite:\\" + connectBox.getText() + "\\";
                    try {
                        con = DriverManager.getConnection(jdbcURL);
                        stmt = con.createStatement();
                        connectOutput.setText("");
                        connectGUI.setVisible(false);
                        mainGUI.setVisible(true);

                        try{
                            rs = stmt.executeQuery(selectAll);
                            while(rs.next()) {
                                listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                            }
                        }
                        catch(Exception x) {
                            outputLabel.setText("Failed to load database entries. Try to disconnect and reconnect the database.");
                        }
                    } catch (Exception x) {
                        connectOutput.setText("Failed to connect to database. Ensure the file path is correct.");
                    }
                }
            }
        });

        JPanel connectP1 = new JPanel(singleLayout);
        JPanel connectP2 = new JPanel(singleLayout);
        JPanel connectP3 = new JPanel();
        JPanel connectP4 = new JPanel(singleLayout);

        connectP1.add(startUp);
        connectP2.add(connectBox);
        connectP3.add(connectButton);
        connectP3.add(connectExitButton);
        connectP4.add(connectOutput);

        connectGUI.add(connectP1);
        connectGUI.add(connectP2);
        connectGUI.add(connectP3);
        connectGUI.add(connectP4);

        connectGUI.pack();
        mainGUI.pack();
        mainGUI.setVisible(false);
        connectGUI.setVisible(true);

        mainGUI.setBounds(0, 0, 1280, 720);
    }
}