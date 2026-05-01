import java.sql.*;
import java.util.Scanner;

public class index {

    static final String URL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create Connection
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            int choice;

            do {
                System.out.println("\n===== CRUD OPERATIONS =====");
                System.out.println("1. Insert Data");
                System.out.println("2. Read Data");
                System.out.println("3. Update Data");
                System.out.println("4. Delete Data");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");

                choice = sc.nextInt();

                switch (choice) {

                    // INSERT
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();

                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();

                        String insertQuery =
                                "INSERT INTO students(id, name, age) VALUES (?, ?, ?)";

                        PreparedStatement psInsert =
                                con.prepareStatement(insertQuery);

                        psInsert.setInt(1, id);
                        psInsert.setString(2, name);
                        psInsert.setInt(3, age);

                        int insertResult = psInsert.executeUpdate();

                        if (insertResult > 0) {
                            System.out.println("Data Inserted Successfully!");
                        }

                        break;

                    // READ
                    case 2:

                        String selectQuery = "SELECT * FROM students";

                        Statement st = con.createStatement();

                        ResultSet rs = st.executeQuery(selectQuery);

                        System.out.println("\nID\tNAME\tAGE");

                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("id") + "\t" +
                                    rs.getString("name") + "\t" +
                                    rs.getInt("age")
                            );
                        }

                        break;

                    // UPDATE
                    case 3:

                        System.out.print("Enter ID to Update: ");
                        int updateId = sc.nextInt();

                        sc.nextLine();

                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter New Age: ");
                        int newAge = sc.nextInt();

                        String updateQuery =
                                "UPDATE students SET name=?, age=? WHERE id=?";

                        PreparedStatement psUpdate =
                                con.prepareStatement(updateQuery);

                        psUpdate.setString(1, newName);
                        psUpdate.setInt(2, newAge);
                        psUpdate.setInt(3, updateId);

                        int updateResult = psUpdate.executeUpdate();

                        if (updateResult > 0) {
                            System.out.println("Data Updated Successfully!");
                        } else {
                            System.out.println("Record Not Found!");
                        }

                        break;

                    // DELETE
                    case 4:

                        System.out.print("Enter ID to Delete: ");
                        int deleteId = sc.nextInt();

                        String deleteQuery =
                                "DELETE FROM students WHERE id=?";

                        PreparedStatement psDelete =
                                con.prepareStatement(deleteQuery);

                        psDelete.setInt(1, deleteId);

                        int deleteResult = psDelete.executeUpdate();

                        if (deleteResult > 0) {
                            System.out.println("Data Deleted Successfully!");
                        } else {
                            System.out.println("Record Not Found!");
                        }

                        break;

                    case 5:
                        System.out.println("Program Ended");
                        break;

                    default:
                        System.out.println("Invalid Choice!");
                }

            } while (choice != 5);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}