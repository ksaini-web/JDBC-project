package com.jdbc.demo;

import java.sql.*;
import java.util.Scanner;

public class TestProject {

    private static final String Url = "jdbc:mysql://localhost:3306/mydb";
    private static final String User = "root";
    private static final String Password = "root";

    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(Url, User, Password);
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n----- STUDENT MANAGEMENT SYSTEM -----");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addStudent(connection, sc);
                    break;

                case 2:
                    viewStudent(connection);
                    break;

                case 3:
                    updateStudent(connection, sc);
                    break;

                case 4:
                    deleteStudent(connection, sc);
                    break;

                case 5:
                    System.out.println("Exiting Application...");
                    connection.close();
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // ADD STUDENT
    private static void addStudent(Connection conn, Scanner sc) throws SQLException {

        sc.nextLine(); // clear buffer

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();

        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        String sql = "INSERT INTO student(name, age, marks) VALUES (?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setInt(2, age);
        pstmt.setInt(3, marks);

        int rows = pstmt.executeUpdate();

        if (rows > 0) {
            System.out.println("Student Added Successfully!");
        }
    }

    // VIEW STUDENTS
    private static void viewStudent(Connection conn) throws SQLException {

        String sql = "SELECT * FROM student";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        System.out.println("\n--------------------------------------------------");
        System.out.printf("%-5s %-20s %-5s %-10s%n", "ID", "NAME", "AGE", "MARKS");
        System.out.println("--------------------------------------------------");

        while (rs.next()) {

            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            int marks = rs.getInt("marks");

            System.out.printf("%-5d %-20s %-5d %-10d%n", id, name, age, marks);
        }

        System.out.println("--------------------------------------------------");
    }

    // UPDATE STUDENT
    private static void updateStudent(Connection conn, Scanner sc) throws SQLException {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Age: ");
        int age = sc.nextInt();

        System.out.print("Enter New Marks: ");
        int marks = sc.nextInt();

        String sql = "UPDATE student SET name=?, age=?, marks=? WHERE id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setInt(2, age);
        pstmt.setInt(3, marks);
        pstmt.setInt(4, id);

        int rows = pstmt.executeUpdate();

        if (rows > 0) {
            System.out.println("Student Updated Successfully!");
        } else {
            System.out.println("Student ID Not Found!");
        }
    }

    // DELETE STUDENT
    private static void deleteStudent(Connection conn, Scanner sc) throws SQLException {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM student WHERE id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);

        int rows = pstmt.executeUpdate();

        if (rows > 0) {
            System.out.println("Student Deleted Successfully!");
        } else {
            System.out.println("Student ID Not Found!");
        }
    }
}
