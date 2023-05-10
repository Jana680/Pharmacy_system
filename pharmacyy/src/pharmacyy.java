import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

class pharmacyy {

    private static final int MAX_DRUGS = 20;
    //private HashMap<K, Integer> drugQuantityMap;

    public void addDrug(String type1) throws InputMismatchException {
        try {
            // open Excel file and get worksheet
            FileInputStream file = new FileInputStream(new File("pharmacyy.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // check if there is room for a new drug
            int maxRows = sheet.getLastRowNum();
            if (maxRows >= MAX_DRUGS) {
                System.out.println("Sorry, the pharmacy is full and cannot add any more drugs.");
                return;
            }

            // get input data from user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter drug name: ");
            String name = scanner.nextLine();
            System.out.print("Enter drug ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter drug price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // consume newline character
            System.out.print("Enter drug quantity: ");
            int quantity = scanner.nextInt();

            // check if drug already exists in sheet
            boolean drugExists = false;
            for (int i = 1; i <= maxRows; i++) {
                Row row = sheet.getRow(i);
                if (row.getCell(1).getCellTypeEnum() == CellType.STRING && row.getCell(1).getStringCellValue().equals(id)) {
                    int currentQuantity = (int) row.getCell(4).getNumericCellValue();
                    row.getCell(4).setCellValue(currentQuantity + quantity);
                    drugExists = true;
                    System.out.println("Drug already exists. Quantity updated.");
                    break;
                }
            }

            // add new row for drug if it doesn't exist
            if (!drugExists) {
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
            FileOutputStream fileOut = new FileOutputStream(new File("pharmacyy.xlsx"));
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
            FileInputStream file = new FileInputStream(new File("pharmacyy.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Get drug ID to remove
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter drug ID to remove: ");
            String idToRemove = scanner.nextLine();
            System.out.println(" 1- remove a quantity:  ");
            System.out.println("2- remove the entire row: ");
            int removeOption = scanner.nextInt();

            // Search for drug by ID
            int maxRows = sheet.getLastRowNum();
            boolean drugFound = false;
            int rowIndexToRemove = -1;
            for (int i = 1; i <= maxRows; i++) {
                Row row = sheet.getRow(i);
                if (row.getCell(1).getCellTypeEnum() == CellType.STRING && row.getCell(1).getStringCellValue().equals(idToRemove)) {
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
                System.out.println("Drug with ID " + idToRemove + " not found in pharmacy.");
            }


            // Save changes to Excel file
            FileOutputStream fileOut = new FileOutputStream(new File("pharmacyy.xlsx"));
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
            FileInputStream file = new FileInputStream(new File("pharmacyy.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter drug ID to check availability: ");
            String idToCheck = scanner.nextLine().trim();

            boolean drugFound = false;
            double totalSales = 0.0;
            int totalQuantity = 0;
            int newQuantity =0;
            Map<String, Integer> drugQuantityMap = new HashMap<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Cell idCell = row.getCell(1);
                String cellValue = "";

                CellType idCellType = idCell.getCellTypeEnum();
                if (idCellType == CellType.STRING) {
                    cellValue = idCell.getStringCellValue().trim();
                } else if (idCellType == CellType.NUMERIC) {
                    cellValue = String.valueOf((int) idCell.getNumericCellValue());
                }

                if (idCellType == CellType.STRING && cellValue.equalsIgnoreCase(idToCheck)) {
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
                            System.out.println("Invalid unit price for drug with ID " + idToCheck);
                            break;
                        }
                    }

                    String category = categoryCell.getStringCellValue();

                    if (category.equals("cosmetics")) {
                        unitPrice *= 1.2;
                    }

                    System.out.println("Drug: " + drugName);
                    System.out.println("Unit Price: " + unitPrice);

                    if (category.equals("prescription")) {
                        System.out.print("Prescription required. Do you have a prescription? (yes/no): ");
                        String prescriptionResponse = scanner.nextLine().trim().toLowerCase();
                        if (prescriptionResponse.equals("no")) {
                            System.out.println("Sorry, you need a prescription to purchase this drug.");
                            break;
                        }
                    }

                    System.out.print("Enter quantity: ");
                    int orderedQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    if (orderedQuantity > quantity) {
                        System.out.println("Insufficient quantity available.");
                        break;
                    }

                    quantity -= orderedQuantity;
                    quantityCell.setCellValue(quantity);

//                    System.out.print("Enter quantity: ");
//                    int quantity = scanner.nextInt();
//                    scanner.nextLine(); // Consume the newline character

                    // Update the quantity for the drug
                   // Map<K, Integer> drugQuantityMap;
                    int previousQuantity = drugQuantityMap.getOrDefault(idToCheck, 0);
                     newQuantity = (int) (previousQuantity + quantity);
                    drugQuantityMap.put(idToCheck, newQuantity);

                    double totalPrice = unitPrice * orderedQuantity;
                    totalSales += totalPrice;

                    System.out.println("Total Price: " + totalPrice);
                    System.out.println("Order placed successfully.");
                    break;
                }
            }

            if (!drugFound) {
                System.out.println("Drug with ID " + idToCheck + " not found in pharmacy.");
            }

           // System.out.println("Total Quantity for the day: " + newQuantity);
            System.out.println("Total Sales for the day: " + totalSales);

            FileOutputStream outFile = new FileOutputStream(new File("pharmacyy.xlsx"));
            workbook.write(outFile);
            outFile.close();

            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
