import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        RunClass runClass = new RunClass();
        runClass.Steps();
    }
}
