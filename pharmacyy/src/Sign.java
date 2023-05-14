import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class Sign {
 private int capacity;
 private List<User> users;
 private String excelFilePath = "D://Advanced//signinfo.xlsx";
 public Sign() {
 users = new ArrayList<>();

 }
    public void menusign() {

 Scanner input1 = new Scanner(System.in);

 while (true) {

 System.out.println("1- Register");

 System.out.println("2- Login");

 System.out.println("3- Exit");

 System.out.println("Please enter the number of the option:");



 int option = input1.nextInt();

 input1.nextLine(); // Consume the newline character



 switch (option) {

case 1:

 registerUser();
 continue;

 case 2:

loginUser();

 continue;

 case 3:

 System.out.println("Exiting...");

break;

default:

System.out.println("Invalid option. Please try again.");
 break;

 }

 pharmacyy obj1 = new pharmacyy();

obj1.mainmenuf();

 }

}
public void registerUser() {

 Scanner scanner = new Scanner(System.in);
 System.out.println("User Registration");

 System.out.print("Enter your first name: ");

 String name = scanner.nextLine();

 System.out.print("Enter your last name: ");

 String last_name = scanner.next();

 System.out.print("Enter your Email: ");
 String email = scanner.next();

 if (isEmailExists(email)) {

 System.out.println("Email already exists. Please choose another one.");

return;
 }

System.out.print("Enter your password (minimum 8 characters or numbers): ");

String password = scanner.nextLine();

 while (password.length() < 8) {

 System.out.print("Password must be at least 8 characters or numbers. Please enter a valid password: ");

 password = scanner.nextLine();

 }
 System.out.print("Enter your phone number: ");

 String phoneNumber = scanner.nextLine();

 User newUser = new User(name, password, phoneNumber, email, last_name);

 users.add(newUser);

 System.out.println("Registration successful. You can now log in.");
 saveUserToExcel(newUser);

 }
    public void loginUser() {

 Scanner scanner = new Scanner(System.in);
 System.out.println("User Login");

 System.out.print("Enter your name: ");
 String name = scanner.nextLine();
 System.out.print("Enter your password: ");

String password = scanner.nextLine();

 User user = findUserByName(name);
if (user != null && user.getPassword().equals(password)) {
 System.out.println("Login successful. Welcome, " + user.getName() + "!");

 } else {

 System.out.println("Invalid username or password.");

 }

 }

 private User findUserByName(String name) {

 for (User user : users) {

if (user.getName().equals(name)) {
 return user;

 }

 }

 return null;

 }

 private boolean isEmailExists(String email) {
 for (User user : users) {

if (user.getEmail().equals(email)) {

return true;

 }

}

return false;

}
 private void saveUserToExcel(User user) {

 try {

FileInputStream file = new FileInputStream(new File(excelFilePath));

XSSFWorkbook workbook = new XSSFWorkbook(file); XSSFSheet sheet = workbook.getSheetAt(0);

 int rowCount = sheet.getLastRowNum();

 Row newRow = sheet.createRow(rowCount + 1);

 Cell cellFirstName = newRow.createCell(0);

 cellFirstName.setCellValue(user.getName());
 Cell cellLastName = newRow.createCell(1);

 cellLastName.setCellValue(user.getLastname());
 Cell cellEmail = newRow.createCell(2);

 cellEmail.setCellValue(user.getEmail());
 Cell cellPassword = newRow.createCell(3);

 cellPassword.setCellValue(user.getPassword());

Cell cellPhoneNumber = newRow.createCell(4);

 cellPhoneNumber.setCellValue(user.getPhoneNumber());

 FileOutputStream outputStream = new FileOutputStream(excelFilePath);

workbook.write(outputStream);

 workbook.close();

 outputStream.close();

} catch (IOException e) {

e.printStackTrace();

}

    }

    private static class User {

 private String name;

 private String password;

 private String phoneNumber;

 private String email;

private String lastname;

public User(String name, String password, String phoneNumber, String email, String lastname) {

this.name = name;

this.password = password;

this.phoneNumber = phoneNumber;

 this.email = email;

this.lastname = lastname;

}

        public String getName() {

 return name;

}
        public String getPassword() {

return password;

}

public String getPhoneNumber() {
    return phoneNumber;

 }

        public String getEmail() {

return email;

 }
         public String getLastname() {

 return lastname;

 }
    }}



