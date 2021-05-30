import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.Scanner;

public class Employee {
    private static Connection connection;
    private static Statement state;
    private static PreparedStatement stmt;
    private static ResultSet result;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        createRegistrationTable();
    }

    public static void createRegistrationTable() {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "dbd_user";
        String password = "8_7*06AZ!#y";
        String sqlCommand;
        sqlCommand = "CREATE DATABASE IF NOT EXISTS dbd_employee CHARACTER SET utf8 COLLATE utf8_unicode_ci";

        try  {
            connection = DriverManager.getConnection(url, username, password);
            state = connection.createStatement();
            state.executeUpdate(sqlCommand);
            state.execute("USE dbd_employee");

            stmt = connection.prepareStatement("INSERT INTO salary (firstname,lastname,department,amount)VALUES(?, ?, ?, ?)");

            //createTable();
            //insertRecordsInTable();
            deleteSingleRecordViaTransaction();

            state.close();
            stmt.close();
            connection.close();
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
            //stmt.setInt(1, '?');
            stmt.setString(1, "Max");
            stmt.setString(2, "Mustermann");
            stmt.setString(3, "Engineering");
            stmt.setInt(4, 2000);
            stmt.addBatch();

            //stmt.setInt(1,'?');
            stmt.setString(1, "Katrin");
            stmt.setString(2, "Musterfrau");
            stmt.setString(3, "Production");
            stmt.setInt(4, 2200);
            stmt.addBatch();

            //stmt.setInt(1,'?');
            stmt.setString(1, "John");
            stmt.setString(2, "Doe");
            stmt.setString(3, "Engineering");
            stmt.setInt(4, 2400);
            stmt.addBatch();

            //stmt.setInt(1,);
            stmt.setString(1, "Becker");
            stmt.setString(2, "Heinz");
            stmt.setString(3, "Marketing");
            stmt.setInt(4, 2800);
            stmt.addBatch();

            stmt.executeBatch();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static void deleteSingleRecordViaTransaction(){
       lookingForId(validation());

    }

    public static Long validation(){
        String input;
        Long checkIfNumber= null;

        try {
            System.out.print("Please enter an ID: ");
            input = scan.nextLine();
            checkIfNumber = Long.valueOf(input);
            if (checkIfNumber <= 0){
                deleteSingleRecordViaTransaction();
            }
        }catch (Exception e){
            deleteSingleRecordViaTransaction();
        }
        return checkIfNumber;
    }

    public static void lookingForId(Long validateID){
        String delete;

        try {
            result = state.executeQuery("SELECT id FROM salary");
            while (result.next()) {
               if (validateID == result.getLong("id")){
                   System.out.println("Is it okay to delete?");
                   delete = scan.next();
                   if (delete.equals("y")) {
                       //call delete method
                       deleteRow(validateID);
                   }else {
                       System.out.println("Can not be deleted");
                       //call rollback method !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                   }
                   return;
               }
            }
            //gets only printed when id does not exists
            System.out.println("ID " + validateID + " does not exist");
        } catch (Exception e) {}

    }

    public static void deleteRow(Long idRow){
        try {
            connection.setAutoCommit(false);
            state.executeQuery("DELETE FROM salary WHERE id =" + idRow);
        }catch (Exception e){
            System.out.println("error when try to delete");
        }
    }



}
