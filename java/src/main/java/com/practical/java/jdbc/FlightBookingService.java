package com.practical.java.jdbc;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

@Service
public class FlightBookingService {

    // IMPORTANT: Update USER and PASSWORD if needed
    private static final String URL = "jdbc:mysql://localhost:3306/airlinedb";
    private static final String USER = "root";
    private static final String PASSWORD = "Isha_1405";

    public void processBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Flight Booking System ---");
        System.out.print("Enter flight ID: ");
        int flightId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter passenger name: ");
        String passengerName = scanner.nextLine();
        System.out.print("Enter seats requested: ");
        int seatsRequested = scanner.nextInt();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);

            // 1. Fetch available seats
            String checkSeatsSql = "SELECT available_seats, price_per_seat FROM flights WHERE flight_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSeatsSql);
            checkStmt.setInt(1, flightId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int availableSeats = rs.getInt("available_seats");
                double pricePerSeat = rs.getDouble("price_per_seat");

                // 2. Check if seats are available
                if (availableSeats >= seatsRequested) {
                    // Deduct seats
                    String updateFlightsSql = "UPDATE flights SET available_seats = available_seats - ? WHERE flight_id = ?";
                    PreparedStatement updateStmt = connection.prepareStatement(updateFlightsSql);
                    updateStmt.setInt(1, seatsRequested);
                    updateStmt.setInt(2, flightId);
                    updateStmt.executeUpdate();

                    // Insert booking
                    double totalAmount = seatsRequested * pricePerSeat;
                    String insertBookingSql = "INSERT INTO bookings (passenger_name, flight_id, seats_booked, total_amount) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertStmt = connection.prepareStatement(insertBookingSql);
                    insertStmt.setString(1, passengerName);
                    insertStmt.setInt(2, flightId);
                    insertStmt.setInt(3, seatsRequested);
                    insertStmt.setDouble(4, totalAmount);
                    insertStmt.executeUpdate();

                    connection.commit();
                    System.out.println("Booking Successful!");
                } else {
                    System.out.println("Booking Failed: Not enough seats available");
                    connection.rollback();
                }
            } else {
                System.out.println("Booking Failed: Flight not found");
                connection.rollback();
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
