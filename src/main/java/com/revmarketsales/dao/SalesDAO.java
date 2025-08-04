package com.revmarketsales.dao;

import com.revmarketsales.config.DBConnection;
import com.revmarketsales.util.HelperMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;


public class SalesDAO {

    //-----------------------KPI Methods----------------------
    // KPIs -> 1. total sales
    public double getTotalSales() throws SQLException {
        String total = "SELECT SUM(total_sales) AS total_sales FROM Sales;";
        return HelperMethods.getDoubleValue(total, "total_sales");
    }

    //KPIs -> 2. total gross income
    public double getTotalGrossIncome() throws SQLException {
        String query = "SELECT SUM(gross_income) AS total_gross_income FROM Sales;";
        return HelperMethods.getDoubleValue(query, "total_gross_income");
    }

    //KPIs -> 3. average ratings
    public double getAverageRating() throws SQLException {
        String query = "SELECT AVG(product_rating) AS avg_rating FROM Sales;";
        return HelperMethods.getDoubleValue(query, "avg_rating");
    }

    //---------------------Sales trends over time--------------------------
    //1. monthly sales trends
    public Map<String, Double> getMonthlySalesTrends() throws SQLException {
        String query = "SELECT DATE_FORMAT(transaction_datetime, '%Y-%m') AS month, SUM(total_sales) AS monthly_sales FROM Sales " +
                "GROUP BY month ORDER BY month;";
        return HelperMethods.getLabeledDoubleMap(query, "month", "monthly_sales");
    }

    // hourly sales trends
    public Map<Integer, Double> getHourlySalesTrends() throws SQLException {
        String query = "SELECT HOUR(transaction_datetime) AS hour, SUM(total_sales) AS hourly_sales FROM Sales " +
                "GROUP BY hour ORDER BY hour;";
        return HelperMethods.getIntegerDoubleMap(query, "hour", "hourly_sales");
    }

    // day wise sales trends
    public Map<String, Double> getDayWiseSalesTrends() throws SQLException {
        String query = "SELECT DAYNAME(transaction_datetime) AS day, SUM(total_sales) AS daily_sales FROM Sales " +
                "GROUP BY day " +
                "ORDER BY FIELD (day, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', " +
                "'Friday', 'Saturday', 'Sunday');";
        return HelperMethods.getLabeledDoubleMap(query, "day", "daily_sales");
    }

    // weekly sales trends
    public Map<String, Double> getWeeklySalesTrends() throws SQLException {
        String query = "SELECT DATE_FORMAT(transaction_datetime, '%Y-%u') AS week, SUM(total_sales) AS weekly_sales FROM Sales " +
                "GROUP BY week ORDER BY week;";
        return HelperMethods.getLabeledDoubleMap(query, "week","weekly_sales");
    }

    // quarterly sales trends
    public Map<String, Double> getQuarterlySalesTrends() throws SQLException {
        String query = "SELECT CONCAT(YEAR(transaction_datetime), '-Q', QUARTER(transaction_datetime)) AS quarter, SUM(total_sales) AS quarterly_sales FROM Sales " +
                "GROUP BY quarter ORDER BY quarter;";
        return HelperMethods.getLabeledDoubleMap(query, "quarter", "quarterly_sales");
    }

    //----------------------Branch/ City and Product_line performance--------------------------
    //Sales by Branch using view
    public Map<String, Double> getSalesByBranch() throws SQLException {
        String query = "SELECT branch_code, total_sales FROM view_sales_by_branch;";
        return HelperMethods.getPerformanceMap(query);
    }

    //Sales by city using view
    public Map<String, Double> getSalesByCity() throws SQLException {
        String query = "SELECT city, total_sales FROM view_sales_by_city;";
        return HelperMethods.getPerformanceMap(query);
    }

    //Sales by product line
    public Map<String, Double> getSalesByProductLine() throws SQLException {
        String query = "SELECT product_line, total_sales FROM view_sales_by_product_line;";
        return HelperMethods.getPerformanceMap(query);
    }

    //----------------------------Customer Behaviour analysis--------------------------------
    //sales by customer type
    public Map<String, Double> getSalesByCustomerType() throws SQLException {
        String query = "SELECT customer_type, total_sales FROM view_sales_by_customer_type;";
        return HelperMethods.getPerformanceMap(query);
    }

    //sales by gender
    public Map<String, Double> getSalesByGender() throws SQLException {
        String query = "SELECT gender, total_sales FROM view_sales_by_gender;";
        return HelperMethods.getPerformanceMap(query);
    }

    //sales by payment method
    public Map<String, Double> getSalesByPaymentMethod() throws SQLException {
        String query = "SELECT payment_method, SUM(total_sales) AS total_sales FROM Sales " +
                "GROUP BY payment_method ORDER BY total_sales DESC;";
        return HelperMethods.getLabeledDoubleMap(query, "payment_method", "total_sales");
    }

    //--------------------------Profitability and Discounts-----------------------------
    //gross income by product line
    public Map<String, Double> getGrossIncomeByPL() throws SQLException {
        String query = "SELECT product_line, total_gross_income FROM view_gross_income_by_product_line;";
        return HelperMethods.getPerformanceMap(query);
    }

    //average tax by productLine
    public Map<String, Double> getAvgTaxByPL() throws SQLException {
        String query = "SELECT product_line, avg_tax FROM view_avg_tax_by_product_line;";
        return HelperMethods.getPerformanceMap(query);
    }

    //-------------------------Peak sales periods--------------------------
    //peak hours
    public Map<Integer, Map<String, Double>> getPeakSalesHours() throws SQLException {
        String query = "SELECT HOUR(transaction_datetime) AS Hour, COUNT(*) AS transactions, SUM(total_sales) AS total_sales " +
                "FROM Sales GROUP BY Hour ORDER BY total_sales DESC;";
        Map<Integer, Map<String, Double>> result = new LinkedHashMap<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int hour = resultSet.getInt("Hour");
                double transactions = resultSet.getDouble("transactions");
                double totalSales = resultSet.getDouble("total_sales");

                Map<String, Double> data = new LinkedHashMap<>();
                data.put("transactions", transactions);
                data.put("total_sales", totalSales);

                result.put(hour, data);
            }
        }
        return result;
    }

    //peak sales days
    public Map<String, Map<String, Double>> getPeakSalesDays() throws SQLException {
        String query = "SELECT DAYNAME(transaction_datetime) AS Day, COUNT(*) AS transactions, SUM(total_sales) AS total_sales " +
                "FROM Sales GROUP BY Day ORDER BY total_sales DESC;";
        Map<String, Map<String, Double>> result = new LinkedHashMap<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String day = resultSet.getString("Day");
                double transactions = resultSet.getDouble("transactions");
                double totalSales = resultSet.getDouble("total_sales");

                Map<String, Double> data = new LinkedHashMap<>();
                data.put("transactions", transactions);
                data.put("total_sales", totalSales);

                result.put(day, data);
            }
        }
        return result;
    }
}