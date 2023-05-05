import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Scanner;

class pharmacyy {

    private static final int MAX_DRUGS = 20;

    public void addDrug(String type1) {
        try {
            // open Excel file and get worksheet
            FileInputStream file = new FileInputStream(new File("pharmacy.xlsx"));
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
            FileOutputStream fileOut = new FileOutputStream(new File("pharmacy.xlsx"));
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
                FileInputStream file = new FileInputStream(new File("pharmacy.xlsx"));
                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);

                // Get drug ID to remove from the user
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter drug ID to remove: ");
                String idToRemove = scanner.nextLine();

                // Search for drug by ID
                int maxRows = sheet.getLastRowNum();
                boolean drugFound = false;
                for (int i = 1; i <= maxRows; i++) {
                    Row row = sheet.getRow(i);
                    if (row.getCell(1).getCellTypeEnum() == CellType.STRING && row.getCell(1).getStringCellValue().equals(idToRemove)) {
                        //sheet.shiftRows(i + 1, maxRows - 1, -1); // Shift cells upward
                        sheet.removeRow(row);
                        sheet.shiftRows(i + 1, maxRows, -1); // Shift cells upward
                         // Remove the row

                        drugFound = true;
                        System.out.println("Drug removed from the pharmacy successfully.");
                        break;
                    }
                }

                // Handle drug not found case
                if (!drugFound) {
                    System.out.println("Drug not found in the pharmacy.");
                }

                // Save changes to the Excel file
                FileOutputStream fileOut = new FileOutputStream(new File("pharmacy.xlsx"));
                workbook.write(fileOut);
                fileOut.close();

                // Close Excel file
                workbook.close();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
