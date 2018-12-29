import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ss.ExcelComparator;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class CompareExcel {

    public static void main(String[]args) throws Exception {

        FileInputStream Expected_Excel = null;
        FileInputStream Actual_Excel = null;

        Actual_Excel =new FileInputStream(new File("src//main//resources//Financial_Sample_Actual.xlsx"));
        Expected_Excel =new FileInputStream(new File("src//main//resources//Financial_Sample_Expected.xlsx"));

        XSSFWorkbook workbook1= new XSSFWorkbook(Expected_Excel);
        XSSFWorkbook workbook2= new XSSFWorkbook(Actual_Excel);

        XSSFSheet sheet1 = workbook1.getSheetAt(0);
        XSSFSheet sheet2 = workbook1.getSheetAt(0);

        List<String> differences = ExcelComparator.compare(workbook1,workbook2);
        if(differences.isEmpty())
        {
            System.out.println("Both files are identical");
        }

        else
        {
            System.out.println(differences);
        }





    }
}
