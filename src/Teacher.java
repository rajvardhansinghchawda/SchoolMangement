import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.function.Consumer;

public class Teacher  {
    Scanner sc = new Scanner(System.in);
    final String dburl = "jdbc:mysql://localhost:3306/schoolmangement";
    final String  dbUer = "Admin";
    final String dbPass = "admin123";

    private String FirstName, LastName, Qulification , phoneNUmber;
    private int age ,subjectCode;

  void setValues(){
      System.out.println("Enter First Name: ");
      FirstName = sc.nextLine();
      System.out.println("Enter Last Name: ");
      LastName = sc.nextLine();
      System.out.println("Enter Qulification: ");
      Qulification = sc.nextLine();

      System.out.println("Enter Phone Number: ");
      phoneNUmber = sc.nextLine();
      System.out.println("Enter Age: ");
      age = sc.nextInt();
      System.out.println("Enter Subject Code: ");
      subjectCode = sc.nextInt();

  }


    void updateTeacher(int id) {
        Scanner sc = new Scanner(System.in);
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(dburl, dbUer, dbPass);
            boolean updateMore = true;

            while (updateMore) {
                System.out.println("Which field do you want to update?");
                System.out.println("1. First Name ");
                System.out.println("2. Last Name ");
                System.out.println("3. Subject Code ");
                System.out.println("4. Phone Number ");
                System.out.println("5. Qualification ");
                System.out.println("6. Age ");
                System.out.println("7. Quit");

                int choice = sc.nextInt();
                sc.nextLine();

                String table = "";
                String column = "";
                String newValue = "";
                int Value = 0;
                boolean isString = true;

                switch (choice) {
                    case 1:
                        table = "teachers";
                        column = "First_name";
                        System.out.print("Enter new First Name: ");
                        newValue = sc.nextLine();
                        break;
                    case 2:
                        table = "teachers";
                        column = "Last_name";
                        System.out.print("Enter new Last Name: ");
                        newValue = sc.nextLine();
                        break;
                    case 3:
                        table = "teachers";
                        column = "SubjectCode";
                        System.out.print("Enter new Subject Code: ");
                        Value = sc.nextInt();
                        isString = false;
                        break;
                    case 4:
                        table = "teacherdetail";
                        column = "phoneNumber";
                        System.out.print("Enter new Phone Number: ");
                        newValue = sc.nextLine();

                        break;
                    case 5:
                        table = "teacherdetail";
                        column = "Qualification";
                        System.out.print("Enter new Qualification: ");
                        newValue = sc.nextLine();
                        break;
                    case 6:
                        table = "teacherdetail";
                        column = "age";
                        System.out.print("Enter new Age: ");
                        Value = sc.nextInt();
                        isString = false;
                        break;
                    case 7:
                        System.out.println("Exiting update process.");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        continue;
                }


                String query = "UPDATE " + table + " SET " + column + " = ? WHERE teachers_id = ?";

                preparedStatement = connection.prepareStatement(query);
                if (isString) {
                    preparedStatement.setString(1, newValue);
                } else {
                    preparedStatement.setInt(1, Value);
                }
                preparedStatement.setInt(2, id);

                int updatedRows = preparedStatement.executeUpdate();
                if (updatedRows > 0) {
                    System.out.println(column + " updated successfully in " + table + " table.");
                } else {
                    System.out.println("Update failed.");
                }


                System.out.print("Do you want to update another field?  ");
                String response = sc.nextLine();
                if (!response.equalsIgnoreCase("yes")) {
                    updateMore = false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating teacher: " + e.getMessage(), e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ShowTeacherDetails(int id ) {

       try{
           Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
           String query = "select * from teachers  join teacherdetail using(teachers_id) where teachers_id = ?" ;
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1, id);
           ResultSet resultSet = preparedStatement.executeQuery();
           while (resultSet.next()) {
               System.out.println("Your id is "+ resultSet.getInt("teachers_id"));
               System.out.println("Your name is "+ resultSet.getString("First_name")+" "+resultSet.getString("last_name"));
               System.out.println("Your Phone number is "+ resultSet.getString("phoneNumber"));
               System.out.println("Quailification is "+ resultSet.getString("Qualification"));
               System.out.println("Your Age is "+ resultSet.getInt("age"));
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }


   }
    public  void ShowTeacherBasicDeatils(int id) {
       try{
           Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
           String query2 =" select * from teachers where teachers_id = ?";
           PreparedStatement  preparedStatement2 = connection.prepareStatement(query2);
                       preparedStatement2.setInt(1, id);
                       ResultSet resultSet2 = preparedStatement2.executeQuery();
                       while (resultSet2.next()) {
                           System.out.println("First Name : " + resultSet2.getString("First_name"));
                           System.out.println("Last Name : " + resultSet2.getString("last_name"));
                           int subjectCode = resultSet2.getInt("SubjectCode");
                           String query3 = "select name from subjects where SubjectCode = ?";
                           PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                           preparedStatement3.setInt(1, subjectCode);
                           ResultSet resultSet3 = preparedStatement3.executeQuery();
                           while (resultSet3.next()) {
                               System.out.println("Subject name : " + resultSet3.getString("name"));
                           }
                       }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }

   }

    public void basisFuncality(int id, String role){
        while(true){
            System.out.println("What you want ");
            System.out.println("1.To Know your details");
            System.out.println("2.To Know student detail  ");
            System.out.println("3. To show grades of student");
            System.out.println("4. To update gardes of student ");
            System.out.println("5. To delete student grades");

            int input = sc.nextInt();
            switch (input) {
                case 1:
                {
                 ShowTeacherDetails(id);
                }
                break;
                case 2:
                {
                   Student s1 = new Student();
                    handlemultipleStatement( rollNumber->s1.displayStudent(rollNumber) ,id,role,"view","Roll_number","teachers_id");
                }
                break;
                case 3:
                {
                    Student s1 = new Student();
                     handlemultipleStatement(rollNumber -> s1.ShowGrades(rollNumber),id,role,"view","Roll_number","teachers_id");
                }
                break;
                case 4:
                {   Student s1 = new Student();
                    handlemultipleStatement(rollNumber -> s1.UpadteGrades(rollNumber),id,role,"Upgrade","Roll_number","teachers_id");

                    break;
                }

                case 5:
                {  Student s1 = new Student();
                    handlemultipleStatement(rollnumber->s1.deleteGrades(rollnumber),id,role,"Delete","Roll_number","teachers_id");

                    break;
                }
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
            System.out.println("Do you want to go to main menu continue? (y/n)");
            String answer = sc.next();
            if (answer.toUpperCase().equals("N")) {
                break;
            }
        }
    }
     ArrayList<Integer> getIds(int id,String S1,String S2){
         ArrayList<Integer> RollNumbers = new ArrayList<>();
         try {
             Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
             String query = "select " + S1 +" from student_has_teachers where "+S2+ "  = ?" ;

             PreparedStatement  preparedStatement = connection.prepareStatement(query);
             preparedStatement.setInt(1, id);
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()) {
                 RollNumbers.add( resultSet.getInt(S1));
             }

         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
        return RollNumbers;
     }




    void handlemultipleStatement( Consumer<Integer> doaction,int id,String role,String operation,String Selectid,String fromThe){
        System.out.println("The list of ids  of  are ");
        ArrayList<Integer> rollNumbers = getIds(id,Selectid,fromThe);
        System.out.println(rollNumbers);
        boolean flag = false;
        while (flag == false) {
            System.out.println("entre the id which details you want to "+operation);
            int SRN = sc.nextInt();
            if (rollNumbers.contains(SRN)) {
                //System.out.println("i am in SRN");
              doaction.accept(SRN);
            } else
                System.out.println("id not found");

            System.out.println("You want to  again "+operation+" y/n");
            String answer = sc.next();
            if (!answer.equalsIgnoreCase("y")) {
                flag = true;
                System.out.println("Exist You entre invaild roll number ");
            }



        }


    }
    void addTeacher(int id) {
      setValues();
      try {

        Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
          connection.setAutoCommit(false);
        String query = " insert into teachers(First_name,last_name,SubjectCode,teachers_id) values(?,?,?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, FirstName);
        preparedStatement.setString(2, LastName);
        preparedStatement.setInt(3, subjectCode);
        preparedStatement.setInt(4, id);
        String query2 = "insert into teacherdetail(teachers_id,phoneNumber,Qualification,age) values(?,?,?,?) ";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setInt(1, id);
        preparedStatement2.setString(2, phoneNUmber);
        preparedStatement2.setString(3, Qulification);
        preparedStatement2.setInt(4, age);
        int affectedRows = preparedStatement.executeUpdate();
        int count = preparedStatement2.executeUpdate();
        if (affectedRows > 0 && count> 0)
        {            connection.commit();
            System.out.println("Teacher inserted successfully.");
        } else {
                       connection.rollback();
                      System.out.println("Teacher insertion failed.");
        }
          connection.setAutoCommit(true);
      }
      catch (SQLException e) {
          System.out.println(e.getMessage());;
    }}

    int deleteTeacher(int id) {
        try {
            Connection connection = DriverManager.getConnection(dburl, dbUer, dbPass);
            connection.setAutoCommit(false);


            String query1 = "DELETE FROM teacherdetail WHERE teachers_id = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            preparedStatement1.setInt(1, id);
            int detailRows = preparedStatement1.executeUpdate();


            String query2 = "DELETE FROM teachers WHERE teachers_id = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setInt(1, id);
            int teacherRows = preparedStatement2.executeUpdate();


            String query3 = "DELETE FROM login WHERE id = ?";
            PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
            preparedStatement3.setInt(1, id);
            int loginRows = preparedStatement3.executeUpdate();

            if (detailRows > 0 && teacherRows > 0 && loginRows > 0) {
                connection.commit();
                System.out.println("Teacher with ID " + id + " deleted successfully from all tables.");
                return id;
            } else {
                connection.rollback(); // Rollback if any deletion fails
                System.out.println("Teacher deletion failed. ID may not exist in all tables.");
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
