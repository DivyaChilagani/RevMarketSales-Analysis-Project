package com.revmarketsales.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.revmarketsales.config.DBConnection;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ImportCSVFile {

    public void importSalesData (String csvFilePath) {
        String insertSalesSQL = "INSERT INTO Sales (invoice_id, branch_id, customer_id, product_id, quantity, tax, total_sales, " +
                "transaction_datetime, payment_method, cogs, gross_margin_percent, gross_income, product_rating) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();

            CSVReader reader = new CSVReader(new FileReader(csvFilePath));
            String[] rowArray;
            reader.readNext();
            while ((rowArray = reader.readNext()) != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSalesSQL)){
                    String invoiceId = rowArray[0];
                    String branchCode = rowArray[1];
                    String city = rowArray[2];
                    String customer_type = rowArray[3];
                    String gender = rowArray[4];
                    String product_line = rowArray[5];
                    double unit_price = Double.parseDouble(rowArray[6]);
                    int quantity = Integer.parseInt(rowArray[7]);
                    double tax = Double.parseDouble(rowArray[8]);
                    double total_sales = Double.parseDouble(rowArray[9]);

                    String rawDate = rowArray[10].trim();
                    String rawTime = rowArray[11].trim();
                    String payment_method = rowArray[12];
                    double cogs = Double.parseDouble(rowArray[13]);
                    double gross_margin_percent = Double.parseDouble(rowArray[14]);
                    double gross_income = Double.parseDouble(rowArray[15]);
                    double product_rating = Double.parseDouble(rowArray[16]);

                    //parse date+time safely

                    LocalDateTime transaction_date = null;
                    try {
                        LocalDate date;
                        try {
                            DateTimeFormatter dashTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            date = LocalDate.parse(rawDate, dashTimeFormatter);
                        } catch (Exception e) {
                            DateTimeFormatter slashFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                            date = LocalDate.parse(rawDate, slashFormatter);
                        }

                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm:ss a", Locale.US);
                        LocalTime time = LocalTime.parse(rawTime, timeFormatter);

                        transaction_date = LocalDateTime.of(date, time);
                    } catch (Exception e) {
                        System.err.println("Failed to parse date/time: " + rawDate + " " + rawTime);
                        e.fillInStackTrace();
                        continue;
                    }

                    int branch_id = getBranchId(connection, branchCode, city);
                    int customer_id = getCustomerId(connection, customer_type, gender);
                    int product_id = getProductId(connection, product_line, unit_price);

                    preparedStatement.setString(1, invoiceId);
                    preparedStatement.setInt(2, branch_id);
                    preparedStatement.setInt(3, customer_id);
                    preparedStatement.setInt(4, product_id);
                    preparedStatement.setInt(5, quantity);
                    preparedStatement.setDouble(6, tax);
                    preparedStatement.setDouble(7, total_sales);
                    preparedStatement.setTimestamp(8, Timestamp.valueOf(transaction_date));
                    preparedStatement.setString(9, payment_method);
                    preparedStatement.setDouble(10, cogs);
                    preparedStatement.setDouble(11, gross_margin_percent);
                    preparedStatement.setDouble(12, gross_income);
                    preparedStatement.setDouble(13, product_rating);

                    try {
                        preparedStatement.executeUpdate();
                    } catch (SQLIntegrityConstraintViolationException e) {
                        System.err.println("Skipping duplicate invoice_id: " + invoiceId);
                        continue;

                    }
                }
            }

            System.out.println("CSV data imported successfully");
        } catch (SQLException | IOException | CsvValidationException e) {
            throw new RuntimeException("Error importing CSV data: "+ e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed");
                } catch (SQLException e) {
                    System.err.println("Error closing connection.");
                    e.fillInStackTrace();
                }
            }
        }
    }

    private int getBranchId(Connection connection, String branchCode, String city) throws SQLException {
        String select = "SELECT branch_id FROM Branch WHERE branch_code = ?;";
        String insert = "INSERT INTO Branch (branch_code, city) VALUES (?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
            preparedStatement.setString(1, branchCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
           preparedStatement.setString(1, branchCode);
           preparedStatement.setString(2, city);
           preparedStatement.executeUpdate();
           ResultSet resultSet = preparedStatement.getGeneratedKeys();
           if (resultSet.next()) {
               return resultSet.getInt(1);
           }
        }
        return -1;
    }

    private int getCustomerId(Connection connection, String customer_type, String gender) throws SQLException {
        String select = "SELECT customer_id FROM customers WHERE customer_type = ? AND gender = ?;";
        String insert = "INSERT INTO customers (customer_type, gender) VALUES (?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {
            preparedStatement.setString(1, customer_type);
            preparedStatement.setString(2, gender);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, customer_type);
            preparedStatement.setString(2, gender);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return -1;
    }

    private int getProductId(Connection conn, String product_line, double price) throws SQLException {
        String select = "SELECT product_id FROM products WHERE product_line = ? AND unit_price = ?";
        String insert = "INSERT INTO products (product_line, unit_price) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(select)) {
            preparedStatement.setString(1, product_line);
            preparedStatement.setDouble(2, price);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        try (PreparedStatement preparedStatement = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product_line);
            preparedStatement.setDouble(2, price);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return -1;
    }

}
