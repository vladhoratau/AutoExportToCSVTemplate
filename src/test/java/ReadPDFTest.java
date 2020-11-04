import static org.junit.jupiter.api.Assertions.*;

class ReadPDFTest {

    @org.junit.jupiter.api.Test
    void isQuestionLine() {
        boolean result = ReadPDF.isQuestionLine("Q5.Ce efecte juridice produce   calitatea de codebitor sau garant? ");
        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void isQuestionLineQInMiddle() {
        boolean result = ReadPDF.isQuestionLine("   Ce efecte juridice produce  Q5. calitatea de codebitor sau garant? ");
        assertFalse(result);
    }
}