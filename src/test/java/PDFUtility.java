import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.FileInputStream;

public class PDFUtility {

    public static void main(String[] args) throws Exception {
        FileInputStream Actual_PDF = null;
        PDDocument Actual_PDF_1;

        Actual_PDF = new FileInputStream(new File("src//main//resources//sample-pdf-file.pdf"));
        Actual_PDF_1 = PDDocument.load(Actual_PDF);
        String pdfContent = new PDFTextStripper().getText(Actual_PDF_1);
        System.out.println(pdfContent);
    }
}
