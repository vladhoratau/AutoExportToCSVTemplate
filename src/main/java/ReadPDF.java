import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadPDF {
    public static void main(String[] args) throws IOException {
        readFromTxt("faqUTF8.txt");
    }

    public static boolean isQuestionLine(String line) {
        Pattern pattern = Pattern.compile("Q\\d+");
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    public static boolean isAnswerLine(String line) {
        if (line.startsWith("A") || line.startsWith(" A")) {
            Pattern pattern = Pattern.compile("A\\d+");
            Matcher matcher = pattern.matcher(line);
            return matcher.find();
        } else
            return false;
    }

    public static boolean isValidQuestion(String line) {
        return line == null || !line.contains("Input user");
    }

    public static void readFromTxt(String inputPath) throws IOException {
        PrintWriter printWriter = new PrintWriter("output.csv");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputPath));
            String line = reader.readLine();
            while (line != null) {
                try {
                    StringBuilder fullQuestion = null;
                    List<String> alternativeQuestions = new ArrayList<>();
                    StringBuilder fullAnswer = null;

                    if (isQuestionLine(line)) {
                        fullQuestion = new StringBuilder(line);
                        line = reader.readLine();
                        while (!line.contains("Variatii ale intrebarii")) {
                            line = reader.readLine();
                            fullQuestion.append(line);
                        }
                    }

                    if (line.contains("Variatii ale intrebarii")) {
                        line = reader.readLine();
                        while (!line.matches("^\\s*$") && !line.contains("Raspuns chatbot:")) {
                            StringBuilder question = new StringBuilder(line);
                            while (!line.contains("?")) {
                                line = reader.readLine();
                                question.append(line);
                            }
                            if (!question.toString().equals("")) {
                                alternativeQuestions.add(question.toString());
                            }
                            line = reader.readLine();
                        }
                        line = reader.readLine();
                        if (line.contains("Raspuns chatbot:")) {
                            line = reader.readLine();
                        }
                    }

                    if (isAnswerLine(line)) {
                        fullAnswer = new StringBuilder(line);
                        line = reader.readLine();
                        while (line != null && !line.matches("^\\s*$") &&
                                (!isQuestionLine(line) && isValidQuestion(line))) {
                            fullAnswer.append(" " + line);
                            line = reader.readLine();
                        }
                    }

                    while (line != null && !isQuestionLine(line) && isValidQuestion(line)) {
                        line = reader.readLine();
                    }

                    if (isValidQuestion(line)) {
                        printWriter.print("," + "\"" + fullAnswer + "\"");
                        printWriter.print("," + "\"" + fullQuestion + "\"");
                            for (String alternativeQuestion : alternativeQuestions) {
                                printWriter.print("," + "\"" + alternativeQuestion + "\"");
                            }
                        printWriter.println();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (line != null && !isQuestionLine(line)) {
                    line = reader.readLine();
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
