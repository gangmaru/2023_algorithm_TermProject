package Algorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Destination {
    String name;

    Destination(String name) {
        this.name = name;
    }
}

public class algorithm2 extends JFrame {

    // Serialization ID for JFrame
    private static final long serialVersionUID = 1L;

    // JDBC database connection details
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/algorithm";
    static final String USER = "root";
    static final String PASS = "****";

    // Variables for selected city and locations
    private static String cityName = "";
    private static String string1 = null;
    private static String string2 = null;

    // Swing components
    private JComboBox<String> cityComboBox;
    private JComboBox<String> locationComboBox1;
    private JComboBox<String> locationComboBox2;
    private static JTextArea resultArea;

    // Database connection
    private Connection connection;

    // Floyd-Warshall algorithm variables
    static final int Count_Vertice = 7;
    static int[][] Seoul = new int[Count_Vertice][Count_Vertice];
    static int[][] Busan = new int[Count_Vertice][Count_Vertice];
    static int[][] Gyeongju = new int[Count_Vertice][Count_Vertice];
    static int[][] D = new int[Count_Vertice][Count_Vertice];
    static int[][] P = new int[Count_Vertice][Count_Vertice];

    // Floyd-Warshall algorithm to find the shortest paths
    public static void floyd(int array[][]) {
        for (int i = 0; i < Count_Vertice; i++)
            for (int j = 0; j < Count_Vertice; j++) {
                P[i][j] = -1;
                D[i][j] = array[i][j];
            }
        for (int k = 0; k < Count_Vertice; k++)
            for (int i = 0; i < Count_Vertice; i++)
                for (int j = 0; j < Count_Vertice; j++)
                    if (D[i][j] > D[i][k] + D[k][j]) {
                        D[i][j] = D[i][k] + D[k][j];
                        P[i][j] = k;
                    }
    }

    // Print the shortest path between two vertices
    public static void printPath(int a, int b, int city) {
        StringBuilder path = new StringBuilder();

        if (city == 1) {
            if (P[a][b] != -1) {
                printPath(a, P[a][b], city);
                path.append(getNodeName(P[a][b])).append("->");
                printPath(P[a][b], b, city);
            }
            resultArea.setText("최단거리 : " + D[a][b] + " 최단경로 : " + getNodeName(a) + "->" + path.toString());
        } else if (city == 2) {
            if (P[a][b] != -1) {
                printPath(a, P[a][b], city);
                path.append(getNodeName(P[a][b] + 7)).append("->");
                printPath(P[a][b], b, city);
            }
            System.out.println("최단거리 : " + D[a][b] + " 최단경로 : " + getNodeName(a + 7) + "->" + path.toString());
            resultArea.setText("최단거리 : " + D[a][b] + " 최단경로 : " + getNodeName(a + 7) + "->" + path.toString());
        } else {
            if (P[a][b] != -1) {
                printPath(a, P[a][b], city);
                path.append(getNodeName(P[a][b] + 14)).append("->");
                printPath(P[a][b], b, city);
            }
            resultArea.setText("최단거리 : " + D[a][b] + " 최단경로 : " + getNodeName(a + 14) + "->" + path.toString());
        }

    }

    // getNodeName method to retrieve node name from database
    private static String getNodeName(int nodeId) {
        String nodeName = null;
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstm = con.prepareStatement("SELECT node_name FROM node_inf WHERE n_id = ?")) {

            pstm.setInt(1, nodeId);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    nodeName = rs.getString("node_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nodeName;
    }

    // Constructor for the JFrame
    public algorithm2() {

        // Set up the JFrame
        setTitle("Travel Recommendation App");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Create components
        JLabel cityLabel = new JLabel("Select City:");
        cityComboBox = new JComboBox<>();
        JLabel locationLabel1 = new JLabel("Select Location:");
        locationComboBox1 = new JComboBox<>();
        JLabel locationLabel2 = new JLabel("Select Location:");
        locationComboBox2 = new JComboBox<>();
        JButton recommendButton = new JButton("Recommend Destination");
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
        inputPanel.add(locationLabel1);
        inputPanel.add(locationComboBox1);
        inputPanel.add(locationLabel2);
        inputPanel.add(locationComboBox2);

        buttonPanel.add(recommendButton);
        buttonPanel.add(clearButton);

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultArea, BorderLayout.SOUTH);

        // Add action listeners
        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recommendDestination();
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
            Class.forName(JDBC_DRIVER);
            // Connect to the database
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
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

    // Method to update location list based on the selected city
    private void updateLocationList() {
        // Clear previous items
        locationComboBox1.removeAllItems();
        locationComboBox2.removeAllItems();

        // Get selected city
        String selectedCity = (String) cityComboBox.getSelectedItem();

        // Example locations data from the database based on the selected city
        List<Destination> locations = fetchLocationsFromDatabase(selectedCity);

        // Display locations
        for (Destination location : locations) {
            locationComboBox1.addItem(location.name);
            locationComboBox2.addItem(location.name);
        }
    }

    // Method to fetch locations from the database based on the selected city
    private List<Destination> fetchLocationsFromDatabase(String selectedCity) {
        List<Destination> locations = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM node_inf WHERE main_main_course = ?");
            preparedStatement.setString(1, selectedCity);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("node_name");

                locations.add(new Destination(name));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locations;
    }

    // Method to recommend a destination using the Floyd-Warshall algorithm
    private void recommendDestination() {
        String selectedCity = (String) cityComboBox.getSelectedItem();
        String selectedLocation1 = (String) locationComboBox1.getSelectedItem();
        String selectedLocation2 = (String) locationComboBox2.getSelectedItem();
        string1 = selectedLocation1;
        string2 = selectedLocation2;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM node_inf WHERE main_main_course = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, selectedCity);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                             PreparedStatement pstm = con.prepareStatement("SELECT n_id FROM node_inf WHERE node_name = ?")) {
                            pstm.setString(1, string1);
                            try (ResultSet rs1 = pstm.executeQuery()) {
                                int a;
                                int b;
                                int city;
                                if (rs1.next()) {
                                    a = rs1.getInt("n_id");
                                    if (a >= 7 && a < 14) {
                                        a = a - 7;
                                        floyd(Busan);
                                        city = 2;
                                    } else if (a >= 14 && a < 21) {
                                        a = a - 14;
                                        floyd(Gyeongju);
                                        city = 3;
                                    } else {
                                        floyd(Seoul);
                                        city = 1;
                                    }
                                    pstm.setString(1, string2);
                                    try (ResultSet rs2 = pstm.executeQuery()) {
                                        if (rs2.next()) {
                                            b = rs2.getInt("n_id");
                                            if (b >= 7 && b < 14) {
                                                b = b - 7;
                                            } else if (b >= 14 && b < 21) {
                                                b = b - 14;
                                            }
                                            System.out.println("a: " + a + " b: " + b);

                                            // Print the shortest path
                                            printPath(a, b, city);

                                            if (D[a][b] != 0) {
                                                // Append the destination node to the label
                                                if (city == 1) {
                                                    resultArea.setText(resultArea.getText() + getNodeName(b));
                                                } else if (city == 2) {
                                                    resultArea.setText(resultArea.getText() + getNodeName(b + 7));
                                                } else {
                                                    resultArea.setText(resultArea.getText() + getNodeName(b + 14));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to clear user selection
    private void clearSelection() {
        // Clear selected city, locations, and result area
        cityComboBox.setSelectedIndex(-1);
        locationComboBox1.removeAllItems();
        locationComboBox2.removeAllItems();
        resultArea.setText("");
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	try {
					FILE window=new FILE();
					window.LOGIN();
					window.frame.setVisible(true);
				}catch(Exception e) {
					e.printStackTrace();
				}
            }
        });

        // Database connection variables
        Connection con = null;
        try {
            // Load JDBC driver
            Class.forName(JDBC_DRIVER);
            // Connect to the database
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println(con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        PreparedStatement pstm = null;
        int i = 0;
        int j = 0;
        int count = 0;

        try {
            // Select distances from the database and populate the arrays
            String selectSql = "SELECT result FROM distance WHERE d_id = ?";
            pstm = con.prepareStatement(selectSql);
            for (i = 0; i < 7; i++) {
                for (j = 0; j < 7; j++) {
                    pstm.setInt(1, count);
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        int distance = rs.getInt("result");
                        Seoul[i][j] = distance;
                        count++;
                    }
                }
            }
            for (i = 7; i < 14; i++) {
                for (j = 7; j < 14; j++) {
                    pstm.setInt(1, count);
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        int distance = rs.getInt("result");
                        Busan[i - 7][j - 7] = distance;
                        count++;
                    }
                }
            }
            for (i = 14; i < 21; i++) {
                for (j = 14; j < 21; j++) {
                    pstm.setInt(1, count);
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        int distance = rs.getInt("result");
                        Gyeongju[i - 14][j - 14] = distance;
                        count++;
                    }
                }
		    }
		    pstm.close();		    

        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            // Close the ResultSet and PreparedStatement
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null && !con.isClosed()) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // database operations ...
        try {
            if (con != null && !con.isClosed()) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}