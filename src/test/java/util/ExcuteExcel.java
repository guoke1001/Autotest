package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcuteExcel {

    public static Map<String, String> readExcel(String filepath, int sheet_num, int row_num) {
        Map<String, String> map = new HashMap<String, String>();
        Workbook workbook=null;
        try {
             workbook = new XSSFWorkbook(new FileInputStream(filepath));
            Sheet sheet = workbook.getSheetAt(sheet_num);
            for (int romNum = row_num; romNum <= sheet.getLastRowNum(); romNum++) {
                Row row = sheet.getRow(romNum);
                Cell cell1 = row.getCell(0);
                Cell cell2 = row.getCell(8);
                System.out.println(cell1.toString() + " " + cell2.toString());
                map.put(cell1.toString(), cell2.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;

    }
}
