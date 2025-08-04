package com.revmarketsales.util;

import com.revmarketsales.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class HelperMethods {
    public static double getDoubleValue(String query, String columnLabel) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getDouble(columnLabel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    public static Map<String, Double> getLabeledDoubleMap(String query, String keyColumn, String valueColumn) {
        Map<String, Double> result = new LinkedHashMap<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.put(resultSet.getString(keyColumn), resultSet.getDouble(valueColumn));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Map<Integer, Double> getIntegerDoubleMap (String query, String keyColumn, String valueColumn) {
        Map<Integer, Double> result = new LinkedHashMap<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.put(resultSet.getInt(keyColumn), resultSet.getDouble(valueColumn));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Map<String, Double> getPerformanceMap(String query) {
        Map<String, Double> result = new LinkedHashMap<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.put(resultSet.getString(1), resultSet.getDouble(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
