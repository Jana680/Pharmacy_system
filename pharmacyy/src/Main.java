import java.util.Scanner;

public class Main {

    public void mainMenu(String type1){
        while(true){
            System.out.println("1- Add drug");
            System.out.println("2- Remove drug");
            System.out.println("3- Place an order");
            System.out.println("4- Get the total sales for one day");
            System.out.println("5- Exit");
            Scanner input1= new Scanner(System.in);
            System.out.println("please entre the number of the option:");
            int option=input1.nextInt();
            if(option==1){
                System.out.println("add");
                pharmacyy p =new pharmacyy();
                p.addDrug(type1);
            }
            if(option==2){
                System.out.println("remove");

            }
            if(option==3){
                System.out.println("place an order");

            }
            if (option==4){
                System.out.println("Get the total sales for one day");

            }
            if (option==5){
                System.out.println("exit");
                break;

            }
        }

    }
    public void menuMain(){

        while(true){
            System.out.println("1- Cosmetics");
            System.out.println("2- Prescription drugs");
            System.out.println("3- Others");
            System.out.println("4- put any integer value to terminate:");
            Scanner input1= new Scanner(System.in);
            System.out.println("please entre the number of the option:");
            int option=input1.nextInt();
            if(option==1){
                String type="cosmetics";
                mainMenu(type);

            }
            if(option==2){
                String type="prescription";
                mainMenu(type);
            }
            if(option==3){
                String type ="other";
                mainMenu(type);
            }

            else{
                System.out.println("the system is terminate!");
                break;
            }
        }

    }
    public static void main(String[] args) {
        Main obj1= new Main();
        obj1.menuMain();
}
}
