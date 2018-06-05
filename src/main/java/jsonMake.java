import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class jsonMake {
    private static final String FILE_NAME = "/Users/mnaveid/Desktop/automation/AssignProjects/src/main/resources/res1.xlsx";

    public void abc() throws  IOException, InvalidFormatException{

        FileInputStream inp = new FileInputStream(new File(FILE_NAME));

        Workbook workbook = WorkbookFactory.create(inp);

        // Get the first Sheet.
        Sheet sheet = workbook.getSheetAt(0);

        // Start constructing JSON.
        JSONObject json = new JSONObject();

        // Iterate through the rows.
        JSONArray rows = new JSONArray();
        for (Iterator<Row> rowsIT = sheet.rowIterator(); rowsIT.hasNext(); ) {
            Row row = rowsIT.next();
            JSONObject jRow = new JSONObject();

            // Iterate through the cells.
            JSONArray cells = new JSONArray();
            for (Iterator<Cell> cellsIT = row.cellIterator(); cellsIT.hasNext(); ) {
                Cell cell = cellsIT.next();
                cells.put(cell.getStringCellValue());
            }
            jRow.put("empdetails",cells);
            rows.put(jRow);
        }



        // Create the JSON.
        json.put("all", rows);

// Get the JSON text.
        System.out.println( json.toString());
        System.out.print(json.getJSONArray("all").getJSONObject(0));
    }
}
