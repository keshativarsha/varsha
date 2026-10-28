
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    static final String URL = "jdbc:mysql://localhost:3306/varsha";
    static final String USER = "root";
    static final String PASSWORD = "root";

    // Connect to MySQL
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // CREATE: Insert student
    public static void insertStudent(
            String name, int age, String gender, String aadhar)
            throws Exception {

        String sql = "INSERT INTO student (name, age, gender, Aadhar) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection cn = getConnection();
             PreparedStatement stmt = cn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, aadhar);

            stmt.executeUpdate();
            System.out.println("Student inserted successfully!");
        }
    }

    // READ: Display all students
    public static void displayStudents() throws Exception {

        String sql = "SELECT * FROM student";

        try (Connection cn = getConnection();
             PreparedStatement stmt = cn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id")
                                + ", Name: " + rs.getString("name")
                                + ", Age: " + rs.getInt("age")
                                + ", Gender: " + rs.getString("gender")
                                + ", Aadhar: " + rs.getString("Aadhar")
                );
            }
        }
    }

    // UPDATE: Update student age using ID
    public static void updateStudent(int id, int age) throws Exception {

        String sql = "UPDATE student SET age = ? WHERE id = ?";

        try (Connection cn = getConnection();
             PreparedStatement stmt = cn.prepareStatement(sql)) {

            stmt.setInt(1, age);
            stmt.setInt(2, id);

            int rows = stmt.executeUpdate();
            System.out.println(rows + " student record(s) updated.");
        }
    }

    // DELETE: Delete student using ID
    public static void deleteStudent(int id) throws Exception {

        String sql = "DELETE FROM student WHERE id = ?";

        try (Connection cn = getConnection();
             PreparedStatement stmt = cn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            System.out.println(rows + " student record(s) deleted.");
        }
    }

    public static void main(String[] args) {

        try {
            // 1. Insert a student
            insertStudent("varsha", 20, "Female", "123456784449");

            // 2. Display all students
            System.out.println("\\nStudent Details:");
            displayStudents();

            // 3. Update the student with ID 1
            updateStudent(1, 22);

            // 4. Display updated records
            System.out.println("\\nAfter Update:");
            displayStudents();

            // 5. Delete only if you really want to remove ID 1
            // deleteStudent(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}