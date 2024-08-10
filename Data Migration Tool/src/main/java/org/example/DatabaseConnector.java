package org.example;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;


public class DatabaseConnector {


    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);

    public static Connection getConnection(String dbUrl, String username, String password) {
        try {
            return DriverManager.getConnection(("jdbc:mysql://localhost:3306/mysql","root", "Shivtejp@1094"););
        } catch (SQLException e) {
            logger.error("Failed to establish connection", e);
            return null;
        }
    }

    public static Properties loadProperties(String Table) {
        Properties props = new Properties();
        try (InputStream input = DatabaseConnector.class.getClassLoader().getResourceAsStream(Table)) {
            if (input == null) {
                logger.error("Sorry, unable to find {}", Table);
                return null;
            }
            props.load(input);
        } catch (Exception e) {
            logger.error("Failed to load properties", e);
        }
        return props;
    }
    public class DataRetriever {

        public List<String[]> retrieveData(Connection connection, String query) {
            List<String[]> dataList = new ArrayList<>();
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String[] row = new String[rs.getMetaData().getColumnCount()];
                    for (int i = 0; i < row.length; i++) {
                        row[i] = rs.getString(i + 1);
                    }
                    dataList.add(row);
                }
            } catch (SQLException e) {
                // Handle SQL Exception
            }
            return dataList;
        }
    }

    public static void main(String[] args) {
        Properties props = loadProperties("config.properties");

        Connection sourceConnection = getConnection(
                props.getProperty("jdbc:mysql://localhost:3306/mysql"),
                props.getProperty("root"),
                props.getProperty("Shivtejp@1094")
        );

        Connection targetConnection = getConnection(
                props.getProperty("jdbc:mysql://localhost:3306/mysql"),
                props.getProperty("Shiv"),
                props.getProperty("pass@123")
        );


    }
}


