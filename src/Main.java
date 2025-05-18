import java.sql.*;
import java.util.Scanner;

public class Main {

          private static  final  String url = "jdbc:mysql://localhost:3306/schoolmangement";
    private static   final String AdminUserName = "Admin";
    private static   final String AdminPassword = "admin123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String userName, UserPassword;
        System.out.println("Please enter your username: ");
        userName = sc.nextLine();
        System.out.println("Please enter your password: ");
        UserPassword = sc.nextLine();
         try{
             Connection connection = DriverManager.getConnection(url,AdminUserName,AdminPassword);
             String query = "SELECT id, role FROM login  WHERE username = ? AND password = ?";
             PreparedStatement preparedStatement = connection.prepareStatement(query);

             preparedStatement.setString(1,userName);
             preparedStatement.setString(2,UserPassword);
             ResultSet resultSet = preparedStatement.executeQuery();

             if (resultSet.next()) {
                 int id = resultSet.getInt("id");
                 String role = resultSet.getString("role");
                 System.out.println("login Successful"+ " id is  "+ id + " role is  "+ role);
                  if(role.equals("student")){
                      Student s1 = new Student();
                      s1.basisFuncality(id,role);
                    //  s1.displayStudent(id);
                  }
                  if(role.equals("teacher")){

                      Teacher t1 = new Teacher();
                      t1.basisFuncality(id,role);
                     // t1.ShowTeacherDetails(id);
                  }
                  if(role.equals("admin")){
                      Admin a1 = new Admin();
                      a1.functionality();
                  }

             }
             else
                 System.out.println("login Failed");

         } catch (SQLException e) {
             throw new RuntimeException(e);
         }


        System.out.println("");
    }
}
