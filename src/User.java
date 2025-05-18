    import java.sql.*;
    import java.util.Scanner;
    import java.util.function.Consumer;

    class Admin  {
        final String dburl = "jdbc:mysql://localhost:3306/schoolmangement";
        final String  dbUer = "Admin";
        final String dbPass = "admin123";
        Scanner sc = new Scanner(System.in);

        int AddMember (String role){

            System.out.println("Enter Username");
            sc.nextLine();
            String username = sc.nextLine();
            System.out.println("Enter Password");
            String password = sc.nextLine();

            String insertQuery = "INSERT INTO login(UserName, password, role) VALUES (?, ?, ?)";
            try {
                Connection connection = DriverManager.getConnection(dburl,dbUer,dbPass);
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
               preparedStatement.setString(1,username);
               preparedStatement.setString(2,password);
               preparedStatement.setString(3,role);
              int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("insert  successfully");
                    try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                        if (rs.next()) {
                            int id = rs.getInt(1);
                            System.out.println("Generated ID: " + id);
                            return id;
                        }
                    }
                }

                 else {
                    System.out.println("add failed.");
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return 0 ;
        }
        public void functionality(){
            while (true) {
                System.out.println("Admin functionality");
                System.out.println("1.Add student");
                System.out.println("2.View student");
                System.out.println("3.Update student");
                System.out.println("4.Delete student");
                System.out.println("5.Add teacher");
                System.out.println("6.View teacher");
                System.out.println("7.Update teacher");
                System.out.println("8.Delete teacher");
                System.out.println("9.view marks of student");
                System.out.println("10.Update marks of students");
                System.out.println("11.Delete marks of students");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1: {
                        Student s = new Student();
                        int id = AddMember("student");
                        System.out.println("id return is " + id);
                        s.addStudent(id);
                        // s.addStudent("himanshu",2,"dubey","hemant dubey",23,87878787,"male","2005-06-20","2023-06-20");
                        break;
                    }
                    case 2: {
                        Student s = new Student();
                        System.out.println("entre student id");
                        int id = sc.nextInt();
                        s.displayStudent(id);
                        break;
                    }
                    case 3: {
                        Student s = new Student();
                        System.out.println("entre student id");
                        int id = sc.nextInt();
                        s.updateStudent(id);
                        break;
                    }
                    case 4: {
                        Student s = new Student();
                        System.out.println("entre id which student you want to delete");
                        int id = sc.nextInt();
                        removeMember(id);
                        System.out.println("student deleted successfully from login table");
                        s.deleteStudent(id);
                        System.out.println("student deleted successfully from student table");
                        break;
                    }
                    case 5: {
                        Teacher t = new Teacher();
                        int id = AddMember("Teacher");
                        System.out.println("id return is " + id);
                        t.addTeacher(id);
                        System.out.println("add successfully");
                        break;
                    }
                    case 6: {
                        Teacher t = new Teacher();
                        System.out.println("entre id which teacher you want to see");
                        int id = sc.nextInt();
                        t.ShowTeacherDetails(id);
                        System.out.println("teacher details show successfully");
                        break;
                    }
                    case 7: {
                        Teacher t = new Teacher();
                        System.out.println("entre id which teacher you want to update");
                        int id = sc.nextInt();
                        t.updateTeacher(id);

                    }
                    case 8: {
                        Teacher t = new Teacher();
                        System.out.println("entre id which teacher you want to delete");
                        int id = sc.nextInt();
                        t.deleteTeacher(id);
                        System.out.println("teacher deleted successfully");
                        break;
                    }
                    case 9 :{
                        Student student = new Student();
                        System.out.println("entre id which student score you want to view ");
                        int id = sc.nextInt();
                        student.ShowGrades(id);
                        break;

                    }
                    case 10: {
                        Student student = new Student();
                        System.out.println("entre id which teacher score you want to view");
                        int id = sc.nextInt();
                        student.UpadteGrades(id);
                        break;

                    }
                    case 11: {
                       Student student = new Student();
                        System.out.println("entre id which teacher score you want to view");
                        int id = sc.nextInt();
                        student.deleteGrades(id);
                        break;
                    }

                }
                System.out.println("Do you want to continue? (y/n)");
                String answer = sc.next();
                if (answer.toUpperCase().equals("N")) {
                    break;
                }

            }
        }

        void removeMember(int id){
            String insertQuery = "delete from login where id = ?";
            try {
                Connection connection = DriverManager.getConnection(dburl,dbUer,dbPass);
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1,id);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("delete successfully");

                }

                else {
                    System.out.println("delete failed.");
                }


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }
