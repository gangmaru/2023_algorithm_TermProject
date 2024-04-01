package Algorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Class representing a travel destination with associated attributes
class TravelDestination {
    String name;         // Name of the travel destination
    int cost;            // Cost of traveling to the destination
    int distance;        // Distance to the destination
    int safety;          // Safety rating of the destination
    double totalWeight;  // Total weighted score for recommendation
    double totalCost = 0;       // Total cost of all destinations for normalization
    double totalDistance = 0;   // Total distance of all destinations for normalization
    double totalSafety = 0;     // Total safety of all destinations for normalization

    // Constructor to initialize a travel destination
    TravelDestination(String name, int cost, int distance, int safety) {
        this.name = name;
        this.cost = cost;
        this.distance = distance;
        this.safety = safety;
        totalCost = totalCost + cost;
        totalDistance = totalDistance + distance;
        totalSafety = totalSafety + safety;
    }
}

// Main class representing the Travel Recommendation Application
public class TravelRecommendationApp extends JFrame {

    // GUI components
    private JComboBox<String> cityComboBox;
    private JComboBox<String> locationComboBox;
    private JTextField costWeightField;
    private JTextField distanceWeightField;
    private JTextField safetyWeightField;
    private JTextArea resultArea;

    private Connection connection;  // Database connection

    // Constructor for the Travel Recommendation Application
    public TravelRecommendationApp() {
        // Set up the JFrame
        setTitle("Travel Recommendation App");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel cityLabel = new JLabel("Select City:");
        cityComboBox = new JComboBox<>();
        JLabel locationLabel = new JLabel("Select Location:");
        locationComboBox = new JComboBox<>();
        JLabel costWeightLabel = new JLabel("Weight for Cost:");
        JLabel distanceWeightLabel = new JLabel("Weight for Distance:");
        JLabel safetyWeightLabel = new JLabel("Weight for Safety:");
        costWeightField = new JTextField(10);
        distanceWeightField = new JTextField(10);
        safetyWeightField = new JTextField(10);
        JButton recommendButton = new JButton("Recommend Travel Destination ");
        JButton clearButton = new JButton("Clear Selection");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        // Set layout
        setLayout(new BorderLayout());

        // Create panels
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        JPanel buttonPanel = new JPanel();

        // Add components to the panels
        inputPanel.add(cityLabel);
        inputPanel.add(cityComboBox);
        inputPanel.add(locationLabel);
        inputPanel.add(locationComboBox);
        inputPanel.add(costWeightLabel);
        inputPanel.add(costWeightField);
        inputPanel.add(distanceWeightLabel);
        inputPanel.add(distanceWeightField);
        inputPanel.add(safetyWeightLabel);
        inputPanel.add(safetyWeightField);

        buttonPanel.add(recommendButton);
        buttonPanel.add(clearButton);

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultArea, BorderLayout.SOUTH);

        // Add action listener
        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recommendTravelDestination();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelection();
            }
        });

        cityComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update location list based on selected city
                updateLocationList();
            }
        });

        // Initialize database connection
        initializeDatabaseConnection();
        // Load city options from the database
        loadCityOptions();
        // Update location list based on the initial city selection
        updateLocationList();
    }

    // Method to initialize the database connection
    private void initializeDatabaseConnection() {
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            String url = "jdbc:mysql://localhost:3306/algorithm";
            String username = "root";
            String password = "****";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to load city options from the database
    private void loadCityOptions() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT DISTINCT main_course FROM main";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String city = resultSet.getString("main_course");
                cityComboBox.addItem(city);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the location list based on the selected city
    private void updateLocationList() {
        // Clear previous items
        locationComboBox.removeAllItems();

        // Get selected city
        String selectedCity = (String) cityComboBox.getSelectedItem();

        // Fetch locations data from the database based on the selected city
        List<TravelDestination> locations = fetchLocationsFromDatabase(selectedCity);

        // Display locations
        for (TravelDestination location : locations) {
            locationComboBox.addItem(location.name);
        }
    }

    // Method to fetch locations data from the database
    private List<TravelDestination> fetchLocationsFromDatabase(String selectedCity) {
        List<TravelDestination> locations = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM node_inf WHERE main_main_course = ?");
            preparedStatement.setString(1, selectedCity);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("node_name");
                int cost = resultSet.getInt("node_cost");
                int distance = resultSet.getInt("node_x");
                int safety = resultSet.getInt("node_security");

                locations.add(new TravelDestination(name, cost, distance, safety));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locations;
    }

    // Method to recommend a travel destination based on user input
    private void recommendTravelDestination() {
        // Get user input from text fields
        double weightCost = Double.parseDouble(costWeightField.getText());
        double weightDistance = Double.parseDouble(distanceWeightField.getText());
        double weightSafety = Double.parseDouble(safetyWeightField.getText());

        // Get selected city and location
        String selectedCity = (String) cityComboBox.getSelectedItem();
        String selectedLocation = (String) locationComboBox.getSelectedItem();

        // Fetch locations data from the database based on the selected city
        List<TravelDestination> locations = fetchLocationsFromDatabase(selectedCity);

        // Remove the current location from the list
        locations.removeIf(location -> location.name.equals(selectedLocation));

        // Get recommended location
        String recommendedLocation = recommendLocation(weightCost, weightDistance, weightSafety, locations);

        // Display the recommended location
        resultArea.setText("Recommended location: " + recommendedLocation);
    }

    // Method to recommend a travel destination based on user input weights
    private String recommendLocation(double weightCost, double weightDistance, double weightSafety,
                                     List<TravelDestination> locations) {
        // Calculate total weight for each location based on user input weights and normalization factors
        for (TravelDestination location : locations) {
            location.totalWeight = (location.cost * weightCost) / location.totalCost
                    + (location.distance * weightDistance) / location.totalDistance
                    + (location.safety * weightSafety) / location.totalSafety;
        }

        // Sort locations based on total weight in descending order
        locations.sort(Comparator.comparingDouble(d -> d.totalWeight));
        Collections.reverse(locations);

        // Return the name of the recommended location
        return locations.get(0).name;
    }

    // Method to clear user selections
    private void clearSelection() {
        // Clear selected city, location, and weights
        cityComboBox.setSelectedIndex(-1);
        locationComboBox.removeAllItems();
        costWeightField.setText("");
        distanceWeightField.setText("");
        safetyWeightField.setText("");
        resultArea.setText("");
    }

    // Main method to launch the Travel Recommendation App
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TravelRecommendationApp().setVisible(true);
            }
        });
    }
}