import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Scanner;

public class pharmacyy{

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
                if (row.getCell(1).getStringCellValue().equals(id)) {
                    int currentQuantity = (int) row.getCell(4).getNumericCellValue();
                    row.getCell(4).setCellValue(currentQuantity + quantity);
                    drugExists = true;
                    break;
                }
            }
            System.out.println("Drug is already exsists.");

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
           // System.out.println("Drug added to pharmacy successfully.");

            // close Excel file
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
