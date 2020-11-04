import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ConvertPDFToText {

    public void convertPDFToText(String inputPDFPath, String outputTXTPath) throws IOException {
        PDDocument document = PDDocument.load(new File(inputPDFPath));
        PrintWriter printWriter = new PrintWriter(outputTXTPath);
        if (!document.isEncrypted()) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            System.out.println("Text:" + text);
            printWriter.write(text);
            printWriter.close();
        }
        document.close();
    }
}
