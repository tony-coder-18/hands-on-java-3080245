package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  public static Connection connect() {
    String dbFile = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(dbFile);
      System.out.println("Connected succesfully");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return connection;
  }

  public static Customer getCustomer(String username) {
    String sql = "select * from customers where username = ?";
    Customer customer = null;

    try (
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

      preparedStatement.setString(1, username);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        customer = new Customer(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customer;
  }

  public static void main(String[] args) {
    Customer customer = getCustomer("clillea8@nasa.gov");
    System.out.println(customer.getName());
  }
}
