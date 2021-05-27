import java.sql.*;

public class Employee {
    private static Statement state;
    private static PreparedStatement stmt;

    public static void main(String[] args) {
        createRegistrationTable();
    }

    public static void createRegistrationTable() {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "dbd_user";
        String password = "8_7*06AZ!#y";
        String sqlCommand;
        sqlCommand = "CREATE DATABASE IF NOT EXISTS dbd_employee CHARACTER SET utf8 COLLATE utf8_unicode_ci";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            state = connection.createStatement();
            state.executeUpdate(sqlCommand);
            state.execute("USE dbd_employee");

            stmt = connection.prepareStatement("INSERT INTO salary VALUES(?, ?, ?, ?, ?)");

            createTable();
            insertRecordsInTable();
           /* readRecordsFromTable(state);
            readSortedRecordsFromTable(state);
            */
            state.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createTable() {

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS salary (id INT(11) NOT NULL AUTO_INCREMENT,"
                + " firstname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "lastname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "department VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "amount INT(11) NOT NULL , PRIMARY KEY (id))";
        try {
            state.executeUpdate(createTable);
        } catch (Exception e) {

        }
    }

    private static void insertRecordsInTable() {
        try {
            stmt.setInt(1,101);
            stmt.setString(2, "Max");
            stmt.setString(3, "Mustermann");
            stmt.setString(4, "Engineering");
            stmt.setInt(5, 2000);
            stmt.executeUpdate();

            stmt.setInt(1,102);
            stmt.setString(2, "Katrin");
            stmt.setString(3, "Musterfrau");
            stmt.setString(4, "Production");
            stmt.setInt(5, 2200);
            stmt.executeUpdate();

            stmt.setInt(1,103);
            stmt.setString(2, "John");
            stmt.setString(3, "Doe");
            stmt.setString(4, "Engineering");
            stmt.setInt(5, 2400);
            stmt.executeUpdate();

            stmt.setInt(1,104);
            stmt.setString(2, "Becker");
            stmt.setString(3, "Heinz");
            stmt.setString(4, "Marketing");
            stmt.setInt(5, 2800);
            stmt.executeUpdate();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

}
