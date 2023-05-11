import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public void mainMenu(String type1) {
        Scanner input1 = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        pharmacyy p = new pharmacyy();
        while (true) {
            System.out.println("1- Add drug");
            System.out.println("2- Remove drug");
            System.out.println("3- Place an order");
            System.out.println("4- Get the total sales for one day");
            System.out.println("5-return to mainmenu :");
            System.out.println("6- Exit");
            System.out.println("please entre the number of the option:");
            int option = input1.nextInt();
            if (option == 1) {
                try {
                    String typ1 = type1;
                    if (Objects.equals(type1, "other")) {
                        System.out.println("enter the type of product");
                        typ1 = input2.nextLine();
                    }
                    type1 = typ1;
                    System.out.println("add");
                    p.addDrug(type1);
                } catch (InputMismatchException e2) {
                    System.out.println(e2.toString());
                    p.addDrug(type1);
                }
            }
            if (option == 2) {
                System.out.println("remove");
                p.removeDrug();

            }
            if (option == 3) {
                try {
                    System.out.println("place an order");

                    p.placeAnOrder();

                } catch (InputMismatchException e) {
                    System.out.println(e.toString());
                    p.placeAnOrder();
                }
            }
            if (option == 4) {
                System.out.println("Get the total sales for one day");

            }
            if(option==5){
                menuMain();
            }
            if (option == 6) {
                System.out.println("exit");
                break;

            }
        }

    }

    public void menuMain() {
        Scanner input1 = new Scanner(System.in);
        System.out.println("Enter the value of the capacity:");
        int capacity=input1.nextInt();
        pharmacyy p = new pharmacyy();
        p.setCapacity(capacity);
        while (true) {
            System.out.println("1- Cosmetics");
            System.out.println("2- Prescription drugs");
            System.out.println("3- Others");
            System.out.println("4- put any integer value to terminate:");

            System.out.println("please entre the number of the option:");
            int option = input1.nextInt();
            if (option == 1) {
                String type = "cosmetics";
                mainMenu(type);

            }
            if (option == 2) {
                String type = "prescription";
                mainMenu(type);
            }
            if (option == 3) {
                String type = "other";
                mainMenu(type);
            } else {
                System.out.println("the system is terminate!");
                break;
            }
        }
    }
    public static void main(String[] args) {
        Main obj1 = new Main();
        obj1.menuMain();
    }
}
