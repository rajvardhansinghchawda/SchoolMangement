import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student {
    final String dburl = "jdbc:mysql://localhost:3306/schoolmangement";
    final String dbUer = "Admin";
    final String dbPass = "admin123";


    Scanner sc = new Scanner(System.in);
    private String FirstName, LastName, ParentName, Gender, DOB, DateOfAdmission;
    private int  ParentNUm;

    void setVlues() {

        System.out.println("Enter First Name: ");
        FirstName = sc.nextLine();
        System.out.println("Enter Last Name: ");
        LastName = sc.nextLine();
        System.out.println("Enter Parent Name: ");
        ParentName = sc.nextLine();
        System.out.println("Parent number");
        ParentNUm = sc.nextInt();
        System.out.println("Date of birth : format year-month-date");
        sc.nextLine();
        DOB = sc.nextLine();
        System.out.println("Date of Admission");
        DateOfAdmission = sc.nextLine();
        System.out.println("Entre gender M/F");
        Gender = sc.nextLine();
    }

    void addStudent(int id) {
        setVlues();
        try {
            Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
            String query = " insert into student(First_name,Last_name,Parent_name,Parent_num,DOB,Date_admission,gender) values(?,?,?,?,?,?,?)  where Roll_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, FirstName);
            preparedStatement.setString(2, LastName);
            preparedStatement.setString(3, ParentName);
            preparedStatement.setInt(4, ParentNUm);
            preparedStatement.setString(5, DOB);
            preparedStatement.setString(6, DateOfAdmission);
            preparedStatement.setString(7, Gender);
            preparedStatement.setInt(8, id);


            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Student insert failed");
            } else
                System.out.println("Student insert successful");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void ShowGrades(int id) {
        try {
            Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
            String query = "" +
                    "" +
                    "";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Grades are " + resultSet.getString("Grades"));
                System.out.println("Assignment marks are " + resultSet.getString("assignmentMarks"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    void displayStudent(int idd) {

        try {
            Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
            String query = "select * from student where roll_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idd);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("roll number is :" + resultSet.getInt("roll_number"));
                System.out.println("First Name is :" + resultSet.getString("first_name"));

                System.out.println("Last Name is :" + resultSet.getString("last_name"));
                System.out.println("gender is :" + resultSet.getString("gender"));
                System.out.println("ParentName is :" + resultSet.getString("parent_name"));
                System.out.println("Date of birth is :" + resultSet.getString("DOB"));
                System.out.println("Date of admission is :" + resultSet.getString("Date_admission"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    // Scanner sc = new Scanner(System.in);
    public void basisFuncality(int id, String role) {
        while (true) {
            System.out.println("What you want ");
            System.out.println("1.To Know your details");
            System.out.println("2.To Know your who teachs you ");
            System.out.println("3. To know your gardes");

            int input = sc.nextInt();
            switch (input) {
                case 1: {
                    displayStudent(id);
                }
                break;
                case 2: {
                    Teacher teacher = new Teacher();
                    ArrayList<Integer> RollNumbers = new ArrayList<>();
                    try {
                        Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
                        String query = "select  teachers_id from student_has_teachers where roll_number = ?";

                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, id);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            RollNumbers.add(resultSet.getInt("teachers_id"));
                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(RollNumbers);
                    teacher.handlemultipleStatement(teacher::ShowTeacherBasicDeatils, id, role, "view", "teachers_id", "Roll_number");
                    //  teacher.ShowTeacherBasicDeatils(id);
                    break;

                }
                case 3: {
                    ShowGrades(id);
                }
                break;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
            System.out.println("Do you want to continue? (y/n)");
            String answer = sc.next();
            if (answer.toUpperCase().equals("N")) {
                break;
            }
        }
    }

    void UpadteGrades(int id) {
        try {

            System.out.println("entre gardes");
            String gardes = sc.next();
            System.out.println(" assigenment marks ");
            int marks = sc.nextInt();
            Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
            String query = "update  grades set grades = ? , assignmentMarks = ? where roll_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gardes);
            preparedStatement.setInt(2, marks);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated successfully");
            } else {
                System.out.println("Update failed. No matching roll_number found.");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    void deleteGrades(int id) {
        try {
            Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
            String query = "update  grades set grades = null, assignmentMarks = null where roll_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("delete successfully");
            } else {
                System.out.println("delete failed. No matching roll_number found.");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    void deleteStudent(int id) {

        try {
            Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
            String query = "DELETE  from student where roll_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("delete successfully");
            } else {
                System.out.println("delete failed. No matching roll_number found.");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void updateStudent(int rollNumber) {
        try {
            Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
            boolean repeat = true;

            while (repeat) {

                System.out.println("Which detail do you want to update?");
                System.out.println("1. First Name\n2. Last Name\n3. Parent Name\n4. Parent Number");
                System.out.println("5. Date of Birth\n6. Date of Admission\n7. Gender\n8. Exit");
                int choice = sc.nextInt();
                sc.nextLine();

                String field = "";
                String newValue = "";
                boolean isNumber = false;

                switch (choice) {
                    case 1:
                        field = "First_name";
                        System.out.println("Enter new First Name:");
                        newValue = sc.nextLine();
                        break;
                    case 2:
                        field = "Last_name";
                        System.out.println("Enter new Last Name:");
                        newValue = sc.nextLine();
                        break;
                    case 3:
                        field = "Parent_name";
                        System.out.println("Enter new Parent Name:");
                        newValue = sc.nextLine();
                        break;
                    case 4:
                        field = "Parent_num";
                        System.out.println("Enter new Parent Number:");
                        newValue = sc.nextLine();
                        isNumber = true;
                        break;
                    case 5:
                        field = "DOB";
                        System.out.println("Enter new Date of Birth:");
                        newValue = sc.nextLine();
                        break;
                    case 6:
                        field = "Date_admission";
                        System.out.println("Enter new Date of Admission:");
                        newValue = sc.nextLine();
                        break;
                    case 7:
                        field = "gender";
                        System.out.println("Enter new Gender (M/F):");
                        newValue = sc.nextLine();
                        break;
                    case 8:
                        repeat = false;
                        continue;
                    default:
                        System.out.println("Invalid choice! Try again.");
                        continue;
                }
                String query = "UPDATE student SET " + field + " = ? WHERE Roll_number = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                if (isNumber) {
                    preparedStatement.setInt(1, Integer.parseInt(newValue));
                } else {
                    preparedStatement.setString(1, newValue);
                }

                preparedStatement.setInt(2, rollNumber);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println(field + " updated successfully!");
                } else {
                    System.out.println("Update failed.");
                }


                System.out.println("Do you want to update another detail? (y/n)");
                String response = sc.nextLine();
                if (!response.equalsIgnoreCase("y")) {
                    repeat = false;
                }
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}