import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;

class pharmacyy {

    private int capacity;

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

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
                mainmenuf();
            }
            if (option == 6) {
                System.out.println("exit");
                break;
            }
        }
    }

    public void  mainmenuf(){
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

    public void addDrug(String type1) throws InputMismatchException {
        try {
            // open Excel file and get worksheet
            FileInputStream file = new FileInputStream(new File("D://Advanced//pharmacy//pharmacy.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // check if there is room for a new drug
            int maxRows = sheet.getLastRowNum();
            if (maxRows >= capacity) {
                System.out.println("Sorry, the pharmacy is full and cannot add any more drugs.");
                return;
            }

            // get input data from user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter drug name: ");
            String name = scanner.nextLine();

            int id;
            double price;
            int quantity;
            String check1;
            // check if drug already exists in sheet
            boolean drugExists = false;

            for (int i = 1; i <= maxRows; i++) {
                Row row = sheet.getRow(i);
                if (row.getCell(0).getCellTypeEnum() == CellType.STRING && row.getCell(0).getStringCellValue().equals(name)) {
                    System.out.println("the drug already exist! it's quantity: " + row.getCell(4));
                    System.out.println("do you want to add more quantity? (yes/no)");
                    check1 = scanner.next();
                    if (check1.equals("yes")) {
                        System.out.print("Enter drug quantity: ");
                        quantity = scanner.nextInt();
                        int currentQuantity = (int) row.getCell(4).getNumericCellValue();
                        row.getCell(4).setCellValue(currentQuantity + quantity);
                        drugExists = true;
                        System.out.println("Drug already exists. Quantity updated.");
                        System.out.println("the quantity is:" + row.getCell(4));
                        break;
                    } else {
                        drugExists = true;
                        break;
                    }
                }

            }
            // add new row for drug if it doesn't exist
            if (!drugExists) {

                System.out.print("Enter drug price: ");
                price = scanner.nextDouble();
                scanner.nextLine(); // consume newline character
                System.out.print("Enter drug quantity: ");
                quantity = scanner.nextInt();
                Random r1 = new Random();
                id = r1.nextInt(100);
                Row row = sheet.createRow(maxRows + 1);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(name);
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(id);
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(price);
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(type1);
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(quantity);
                System.out.println("Drug added to pharmacy successfully.");
            }

            // save changes to Excel file
            FileOutputStream fileOut = new FileOutputStream(new File("D://Advanced//pharmacy//pharmacy.xlsx"));
            workbook.write(fileOut);
            fileOut.close();

            // close Excel file
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeDrug() {
        try {
            // Open Excel file and get worksheet
            FileInputStream file = new FileInputStream(new File("D://Advanced//pharmacy//pharmacy.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Get drug ID to remove
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter drug name to remove: ");
            String nameToRemove = scanner.nextLine();
            System.out.println(" 1- remove a quantity:  ");
            System.out.println("2- remove the entire row: ");
            int removeOption = scanner.nextInt();
            // Search for drug by ID
            int maxRows = sheet.getLastRowNum();
            boolean drugFound = false;
            int rowIndexToRemove = -1;
            for (int i = 1; i <= maxRows; i++) {
                Row row = sheet.getRow(i);
                if (row.getCell(0).getCellTypeEnum() == CellType.STRING && row.getCell(0).getStringCellValue().equals(nameToRemove)) {
                    drugFound = true;
                    rowIndexToRemove = i;
                    break;
                }
            }
            if (drugFound) {
                if (rowIndexToRemove == -1) {
                    System.out.println("Unexpected error occurred while trying to remove the drug.");
                } else {
                    Row rowToRemove = sheet.getRow(rowIndexToRemove);
                    if (rowToRemove == null) {
                        System.out.println("Unexpected error occurred while trying to remove the drug.");
                    } else if (removeOption == 1) {
                        System.out.print("Enter quantity to remove: ");
                        int quantityToRemove = scanner.nextInt();
                        int currentQuantity = (int) rowToRemove.getCell(4).getNumericCellValue();
                        int newQuantity = currentQuantity - quantityToRemove;
                        if (newQuantity < 0) {
                            System.out.println("Error: Quantity to remove is greater than current quantity.");
                        } else if (newQuantity == 0) {
                            sheet.removeRow(rowToRemove);
                            System.out.println("Drug removed from pharmacy successfully.");
                        } else {
                            rowToRemove.getCell(4).setCellValue(newQuantity);
                            System.out.println("Drug quantity updated successfully.");
                        }
                    } else if (removeOption == 2) {
                        sheet.removeRow(rowToRemove);
                        int lastRow = sheet.getLastRowNum();
                        if (rowIndexToRemove >= 0 && rowIndexToRemove < lastRow) {
                            sheet.shiftRows(rowIndexToRemove + 1, lastRow, -1);
                        }
                        System.out.println("Drug removed from pharmacy successfully.");
                    } else {
                        System.out.println("Invalid option selected.");
                    }
                }
            } else {
                System.out.println("Drug with ID " + nameToRemove + " not found in pharmacy.");
            }
            // Save changes to Excel file
            FileOutputStream fileOut = new FileOutputStream(new File("D://Advanced//pharmacy//pharmacy.xlsx"));
            workbook.write(fileOut);
            fileOut.close();
            // Close Excel file
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void placeAnOrder() throws InputMismatchException {
        try {
            FileInputStream file = new FileInputStream(new File("D://Advanced//pharmacy//pharmacy.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Scanner scanner = new Scanner(System.in);
            double totalSalesAllDays = 0.0;
            boolean drugFound = false;
            double totalSales = 0.0;
            int totalQuantity = 0;
            double totalPrice;

            while (true) {
                System.out.print("Enter drug name to check availability (or 'exit' to quit): ");
                String nameToCheck = scanner.nextLine().trim();

                if (nameToCheck.equalsIgnoreCase("exit")) {
                    break;
                }


                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    Cell nameCell1 = row.getCell(0);
                    String cellValue = "";

                    CellType nameCellType = nameCell1.getCellTypeEnum();
                    if (nameCellType == CellType.STRING) {
                        cellValue = nameCell1.getStringCellValue().trim();
                    } else if (nameCellType == CellType.NUMERIC) {
                        cellValue = String.valueOf((int) nameCell1.getNumericCellValue());
                    }

                    if (nameCellType == CellType.STRING && cellValue.equalsIgnoreCase(nameToCheck)) {
                        drugFound = true;
                        Cell nameCell = row.getCell(0);
                        Cell unitPriceCell = row.getCell(2);
                        Cell categoryCell = row.getCell(3);
                        Cell quantityCell = row.getCell(4);

                        String drugName = nameCell.getStringCellValue();
                        double unitPrice = 0.0;
                        double quantity = quantityCell.getNumericCellValue();

                        CellType unitPriceCellType = unitPriceCell.getCellTypeEnum();
                        if (unitPriceCellType == CellType.NUMERIC) {
                            unitPrice = unitPriceCell.getNumericCellValue();
                        } else if (unitPriceCellType == CellType.STRING) {
                            try {
                                unitPrice = Double.parseDouble(unitPriceCell.getStringCellValue());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid unit price for drug with name " + nameToCheck);
                                break;
                            }
                        }

                        String category = categoryCell.getStringCellValue();

                        if (category.equals("cosmetics")) {
                            unitPrice *= 1.2;
                        }

                        System.out.println("Drug: " + drugName);
                        System.out.println("Unit Price: " + unitPrice);
                        System.out.print("Enter quantity: ");
                        int orderedQuantity = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        if (orderedQuantity > quantity) {
                            System.out.println("Insufficient quantity available.");
                            break;
                        }

                        quantity -= orderedQuantity;
                        quantityCell.setCellValue(quantity);
                        totalPrice = unitPrice * orderedQuantity;
                        totalSales += totalPrice;
                        totalQuantity += orderedQuantity;

                        String user = categoryCell.getStringCellValue();
                        if (user.equals("prescription")) {
                            System.out.print("Prescription required. Do you have a prescription? (yes/no): ");
                            String prescriptionResponse = scanner.nextLine().trim().toLowerCase();
                            if (prescriptionResponse.equals("no")) {
                                // System.out.println("Sorry, you need a prescription
                                System.out.println("Sorry, you need a prescription to purchase this drug.");
                                break;
                            }
                        }

                        System.out.println("Total Price: " + totalPrice);
                        System.out.println("Order placed successfully.");
                        break;
                    }
                }

                if (!drugFound) {
                    System.out.println("Drug with name " + nameToCheck + " not found in pharmacy.");
                }

//                totalSalesAllDays += totalSales;
                System.out.println("Total Sales for the day: " + totalSales);
                System.out.println("Total Quantity for the day: " + totalQuantity);

                System.out.print("Do you want to place another order? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();

                if (!response.equals("yes")) {
                    break;
                }
            }

            System.out.println("Total Sales for all days: " + totalSales);

            FileOutputStream outFile = new FileOutputStream(new File("D://Advanced//pharmacy//pharmacy.xlsx"));
            workbook.write(outFile);
            outFile.close();

            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}