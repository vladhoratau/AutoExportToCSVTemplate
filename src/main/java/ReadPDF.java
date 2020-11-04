import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadPDF {
    public static void main(String[] args) throws IOException {
            readFromTxt("faq.txt");
    }

    public static boolean isQuestionLine(String line) {
        if(line.startsWith("Q") || line.startsWith(" Q")) {
            Pattern pattern = Pattern.compile("Q\\d+");
            Matcher matcher = pattern.matcher(line);
            boolean matchFound = matcher.find();
            return matchFound;
        }
        else
            return false;
    }

    public static void readFromTxt(String inputPath) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter("output.csv");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputPath));
            String line;
            while ((line = reader.readLine())!=null) {
                try {
                    if (isQuestionLine(line)) {
                        String questionStart = line;
                        String nextLine;
                        String fullQuestion = questionStart;
                        while(!(nextLine = reader.readLine()).startsWith("Variatii ale intrebarii")) {
                             fullQuestion+= nextLine;
                        }
                        printWriter.println(","+"," +"\"" + fullQuestion + "\"");
                    }
//                    else if(line.startsWith("Variatii ale intrebarii"))
//                    {
//                        String questionStart = reader.readLine();
//                        String fullQuestion;
//                        if((questionStart.contains("?"))
//                        {
//                            fullQuestion=questionStart;
//                        }
//                        else if()
//
//
//                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.out.println(line);
                }
            }
            reader.close();
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
