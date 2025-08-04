package com.revmarketsales.app;

//import com.revmarketsales.dao.SalesDAO;
import com.revmarketsales.service.SalesAnalysisService;
import com.revmarketsales.util.AppLogger;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

import java.util.Scanner;
//import com.revmarketsales.util.ImportCSVFile;
//import java.sql.Connection;

public class MainApp {
    private static final Logger logger = AppLogger.getLogger();
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {

//        String csvPath = "src/main/resources/SuperMarketAnalysis.csv";
//        ImportCSVFile importer = new ImportCSVFile();
//        importer.importSalesData(csvPath);

        SalesAnalysisService salesAnalysis = new SalesAnalysisService();
        Scanner scan = new Scanner(System.in);

        logger.info("Application started");

        String choice = "y";

        while (choice.equalsIgnoreCase("y")) {
            System.out.println("Select an option to perform specified analysis on given dataset");
            System.out.println("1. Get all KPIs Calculations");
            System.out.println("--> Sales Trend Analysis: ");
            System.out.println("2. Get Monthly Sales Trend");
            System.out.println("3. Get Hourly Sales Trend");
            System.out.println("4. Get Day Wise Sales Trend");
            System.out.println("5. Get Weekly Sales Trend");
            System.out.println("6. Get Quarterly Sales Trend");
            System.out.println("--> Branch, City and Product line Performance:");
            System.out.println("7. Get Top Performing Branches");
            System.out.println("8. Get Top performing Cities");
            System.out.println("9. Get Top Performing Product Line");
            System.out.println("--> Customer Behaviour Analysis: ");
            System.out.println("10. Get Sales by Customer type");
            System.out.println("11. Get Sales by Gender");
            System.out.println("12. Get Sales by Payment Method");
            System.out.println("--> Profitability and Discounts: ");
            System.out.println("13. Get Gross Income by Product Line");
            System.out.println("14. Get Average Tax by Product Line");
            System.out.println("--> Peak Sales Period");
            System.out.println("15. Get Peak Sales Hours");
            System.out.println("16. Get Peak Sales Days");

            int choose = 0;
            try {
                choose = scan.nextInt();
                logger.info("User selected option: "+ choose);
            } catch (Exception e) {
                logger.severe("Invalid menu input: "+e.getMessage());
                scan.nextLine();
                continue;
            }

            try {
                switch (choose) {
                    case 1 -> salesAnalysis.printKPIs();
                    case 2 -> salesAnalysis.printMonthlySalesTrend();
                    case 3 -> salesAnalysis.printHourlySalesTrend();
                    case 4 -> salesAnalysis.printDayWiseSalesTrend();
                    case 5 -> salesAnalysis.printWeeklySalesTrend();
                    case 6 -> salesAnalysis.printQuarterlySalesTrend();
                    case 7 -> salesAnalysis.printSalesByBranch();
                    case 8 -> salesAnalysis.printSalesByCity();
                    case 9 -> salesAnalysis.printSalesByProductLine();
                    case 10 ->salesAnalysis.printSalesByCustomerType();
                    case 11 -> salesAnalysis.printSalesByGender();
                    case 12 -> salesAnalysis.printSalesByPaymentMethod();
                    case 13 -> salesAnalysis.printGrossIncomeByProductLine();
                    case 14 -> salesAnalysis.printAvgTaxByProductLine();
                    case 15 -> salesAnalysis.printPeakSalesHours();
                    case 16 -> salesAnalysis.printPeakSalesDays();
                    default -> System.out.println("Invalid Request!!!");
                }
            } catch (Exception e) {
                logger.severe("Error occurred while performing analysis for option "+choose+": "+e.getMessage());
            }

            System.out.println("\nDo you want to get another analysis on dataset? (y/n)");
            scan.nextLine();
            choice = scan.nextLine();
        }
        logger.info("Application terminated by user.");
        System.out.println("ThankYou!!!");
        scan.close();
    }
}
