import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WriteToCSV {


    public void writeFirstQuestionToCSV(String inputLine, String CSVFile) throws IOException {

        FileWriter fileWriter = null;
        try {
             fileWriter = new FileWriter(CSVFile,true);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder =  new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append(","+",");
        stringBuilder.append(inputLine);
        fileWriter.append(stringBuilder.toString());
        fileWriter.close();
    }
}
