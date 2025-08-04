package com.revmarketsales.service;

import com.revmarketsales.dao.SalesDAO;

import java.sql.SQLException;
import java.util.Map;

public class SalesAnalysisService {
    private final SalesDAO salesDAO;

    public SalesAnalysisService() {
        this.salesDAO = new SalesDAO();
    }

    //----------------------KPIs service-----------------------
    public void printKPIs() {
        try {
            double totalSales = salesDAO.getTotalSales();
            double grossIncome = salesDAO.getTotalGrossIncome();
            double averageRating = salesDAO.getAverageRating();

            System.out.println("Key Performance Indicators: ");
            System.out.println("Total Sales    : Rs."+ String.format("%.2f", totalSales));
            System.out.println("Gross Income   : Rs."+ String.format("%.2f", grossIncome));
            System.out.println("Average Rating : "+ String.format("%.2f", averageRating));

        } catch (SQLException e) {
            System.err.println("Failed to fetch KPIs.");
            e.fillInStackTrace();
        }
    }

    //-------------Sales trends over time----------------------------
    //--------------------monthly sales service----------------------
    public void printMonthlySalesTrend() {
        try {
            Map<String, Double> monthlyTrend = salesDAO.getMonthlySalesTrends();
            System.out.println("\nMonthly Sales Trend: ");
            monthlyTrend.forEach((month, sales) ->
                    System.out.println(month + " → Rs. " + String.format("%.2f", sales))
            );
        } catch (Exception e) {
            System.err.println("Failed to fetch monthly trend.");
            e.fillInStackTrace();
        }
    }

    //-----------------------hourly sales service-------------------------------
    public void printHourlySalesTrend() {
        try {
            Map<Integer, Double> hourlyTrend = salesDAO.getHourlySalesTrends();
            System.out.println("\nHourly Sales Trend: ");
            hourlyTrend.forEach((hour, sales) ->
                    System.out.println(hour + ":00 → Rs. " + String.format("%.2f", sales))
            );
        } catch (Exception e) {
            System.err.println(" Failed to fetch hourly trend.");
            e.fillInStackTrace();
        }
    }

    //---------------------------day wise sales service---------------------------------
    public void printDayWiseSalesTrend() {
        try {
            Map<String, Double> dayWiseTrend = salesDAO.getDayWiseSalesTrends();
            System.out.println("\nDay-wise Sales Trend: ");
            dayWiseTrend.forEach((day, sales) ->
                    System.out.printf("%-10s -> Rs. %6.2f\n", day, sales)
            );
        } catch (Exception e) {
            System.err.println("Failed to fetch day-wise trend.");
            e.fillInStackTrace();
        }
    }

    //-------------------------weekly sales service-----------------------------
    public void printWeeklySalesTrend() {
        try {
            Map<String, Double> weeklyTrend = salesDAO.getWeeklySalesTrends();
            System.out.println("\nWeekly Sales Trend: ");
            weeklyTrend.forEach((week, sales) ->
                    System.out.println(week + ":00 → Rs. " + String.format("%.2f", sales))
            );
        } catch (Exception e) {
            System.err.println(" Failed to fetch weekly trend.");
            e.fillInStackTrace();
        }
    }

    //-----------------------------quarterly sales service------------------------------
    public void printQuarterlySalesTrend() {
        try {
            Map<String, Double> quarterlyTrend = salesDAO.getQuarterlySalesTrends();
            System.out.println("\nQuarterly Sales Trend: ");
            quarterlyTrend.forEach((quarter, sales) ->
                    System.out.println(quarter + ":00 → Rs. " + String.format("%.2f", sales))
            );
        } catch (Exception e) {
            System.err.println(" Failed to fetch Quarterly trend.");
            e.fillInStackTrace();
        }
    }

    //----------------------sales by branch service--------------------------------
    public void printSalesByBranch() {
        try {
            Map<String, Double> sales = salesDAO.getSalesByBranch();
            System.out.println("===== Sales By Branch =====");
            System.out.printf("%s : %-10s\n", "Branch Code", "Total Sales");

            for (Map.Entry<String, Double> entry : sales.entrySet()) {
                System.out.printf("%-11s : Rs. %.2f\n", entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sales by Branch: "+e.getMessage());
        }
    }

    //--------------------------sales by city service------------------------------
    public void printSalesByCity() {
        try {
            Map<String, Double> sales = salesDAO.getSalesByCity();
            System.out.println("===== Sales By City =====");
            System.out.printf("%-10s : %10s\n", "City", "Total Sales");
            for (Map.Entry<String, Double> entry : sales.entrySet()) {
                System.out.printf("%-10s : Rs. %.2f\n", entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sales by City: "+e.getMessage());
        }
    }

    //---------------------sales by product line service------------------------
    public void printSalesByProductLine() {
        try {
            Map<String, Double> sales = salesDAO.getSalesByProductLine();
            System.out.println("==== Sales By Product_Line ====");
            System.out.printf("%-23s : %10s\n", "Product Line", "Total Sales");
            for (Map.Entry<String, Double> entry : sales.entrySet()) {
                System.out.printf("%-23s : Rs. %.2f\n", entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sales by Product line: "+e.getMessage());
        }
    }

    //--------------------sales by customer type service-----------------------------
    public void printSalesByCustomerType() {
        try {
            Map<String, Double> sales = salesDAO.getSalesByCustomerType();
            System.out.println("==== Sales By Customer Type ====");
            System.out.printf("%-8s : %10s\n", "Gender", "Total Sales");
            for (Map.Entry<String, Double> entry : sales.entrySet()) {
                System.out.printf("%-8s : Rs. %.2f\n", entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sales by Customer Type: "+e.getMessage());
        }
    }

    //------------------------sales by gender service------------------------------
    public void printSalesByGender() {
        try {
            Map<String, Double> sales = salesDAO.getSalesByGender();
            System.out.println("==== Sales By Gender ====");
            System.out.printf("%-8s : %10s\n", "Gender", "Total Sales");
            for (Map.Entry<String, Double> entry : sales.entrySet()) {
                System.out.printf("%-8s : Rs. %.2f\n", entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sales by Gender: "+e.getMessage());
        }
    }

    //----------------sales by payment method service------------------
    public void printSalesByPaymentMethod() {
        try {
            Map<String, Double> sales = salesDAO.getSalesByPaymentMethod();
            System.out.println("==== Sales By Payment Method ====");
            System.out.printf("%-16s : %10s\n", "Payment Method", "Total Sales");
            for (Map.Entry<String, Double> entry : sales.entrySet()) {
                System.out.printf("%-16s : Rs. %.2f\n", entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Sales By Payment Method: "+e.getMessage());
        }
    }

    //---------------total gross income by product line service--------------
    public void printGrossIncomeByProductLine() {
        try {
            Map<String, Double> income = salesDAO.getGrossIncomeByPL();
            System.out.println("==== Gross Income By Product Line ====");
            System.out.printf("%-23s : %10s\n", "Product Line", "Total Gross Income");
            for (Map.Entry<String, Double> entry : income.entrySet()) {
                System.out.printf("%-23s : Rs. %.2f\n", entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Gross Income By ProductLine: "+e.getMessage());
        }
    }

    //---------------average tax by product line service---------------------
    public void printAvgTaxByProductLine() {
        try {
            Map<String, Double> avgTax = salesDAO.getAvgTaxByPL();
            System.out.println("==== Average Tax By Product Line ====");
            System.out.printf("%-23s : %10s\n", "Product Line", "Average Tax");
            for (Map.Entry<String, Double> entry : avgTax.entrySet()) {
                System.out.printf("%-23s : Rs. %.2f\n",entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Average Tax By ProductLine: "+e.getMessage());
        }
    }

    //--------------------------peak sales hours service-----------------------------
    public void printPeakSalesHours() {
        try {
            Map<Integer, Map<String, Double>> data = salesDAO.getPeakSalesHours();
            System.out.println("==== Peak Sales By Hour ====");
            System.out.printf("%-5s %-15s %-18s%n", "Hour", "Transactions", "Total Sales");
            for (Map.Entry<Integer, Map<String, Double>> entry : data.entrySet()) {
                int hour = entry.getKey();
                double transactions = entry.getValue().get("transactions");
                double totalSales = entry.getValue().get("total_sales");
                System.out.printf("%-10d %-10.0f Rs. %-14.2f%n", hour, transactions, totalSales);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching peak hour sales: " + e.getMessage());
        }
    }

    //---------------------------peak sales days service------------------------
    public void printPeakSalesDays() {
        try {
            Map<String, Map<String, Double>> data = salesDAO.getPeakSalesDays();
            System.out.println("==== Peak Sales By Day ====");
            System.out.printf("%-10s %-15s %-15s%n", "Day", "Transactions", "Total Sales");
            for (Map.Entry<String, Map<String, Double>> entry : data.entrySet()) {
                String day = entry.getKey();
                double transactions = entry.getValue().get("transactions");
                double totalSales = entry.getValue().get("total_sales");
                System.out.printf("%-15s %-10.0f Rs. %-14.2f%n", day, transactions, totalSales);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching peak day sales: " + e.getMessage());
        }
    }
}
