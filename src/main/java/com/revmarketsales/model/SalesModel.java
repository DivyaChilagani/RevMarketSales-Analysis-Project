package com.revmarketsales.model;

import java.time.LocalDateTime;

public class SalesModel {
    private String invoiceId;
    private int branchId;
    private int customerId;
    private int productId;
    private int quantity;
    private double tax, totalSales, cogs, grossMarginPercent, grossIncome, productRating;
    private LocalDateTime transactionDatetime;
    private String paymentMethod;

    public SalesModel() {
    }

    public SalesModel(String invoiceId, int branchId, int customerId, int productId, int quantity, double tax, double totalSales, double cogs, double grossMarginPercent, double grossIncome, double productRating, LocalDateTime transactionDatetime, String paymentMethod) {
        this.invoiceId = invoiceId;
        this.branchId = branchId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.tax = tax;
        this.totalSales = totalSales;
        this.cogs = cogs;
        this.grossMarginPercent = grossMarginPercent;
        this.grossIncome = grossIncome;
        this.productRating = productRating;
        this.transactionDatetime = transactionDatetime;
        this.paymentMethod = paymentMethod;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return totalSales;
    }

    public void setTotal(double totalSales) {
        this.totalSales = totalSales;
    }

    public double getCogs() {
        return cogs;
    }

    public void setCogs(double cogs) {
        this.cogs = cogs;
    }

    public double getGrossMarginPercent() {
        return grossMarginPercent;
    }

    public void setGrossMarginPercent(double grossMarginPercent) {
        this.grossMarginPercent = grossMarginPercent;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public double getProductRating() {
        return productRating;
    }

    public void setProductRating(double productRating) {
        this.productRating = productRating;
    }

    public LocalDateTime getTransactionDatetime() {
        return transactionDatetime;
    }

    public void setTransactionDatetime(LocalDateTime transactionDatetime) {
        this.transactionDatetime = transactionDatetime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    @Override
    public String toString() {
        return "SalesModel{" +
                "invoiceId='" + invoiceId + '\'' +
                ", branchId=" + branchId +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", tax=" + tax +
                ", totalSales=" + totalSales +
                ", cogs=" + cogs +
                ", grossMarginPercent=" + grossMarginPercent +
                ", grossIncome=" + grossIncome +
                ", productRating=" + productRating +
                ", transactionDatetime=" + transactionDatetime +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
