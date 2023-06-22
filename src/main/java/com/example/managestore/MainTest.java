package com.example.managestore;

import com.example.managestore.entity.product.Category;
import com.example.managestore.utils.PoiPOJOUtils;
import io.github.millij.poi.SpreadsheetReadException;
import io.github.millij.poi.ss.reader.XlsReader;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainTest {
    public void get(){

    }
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        FileInputStream file = new FileInputStream(new File("src/test/java/com/example/managestore/dataExcel/Category.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Category> list = PoiPOJOUtils.sheetToPOJO(sheet, Category.class);
        list.forEach(x -> {
            System.out.println(x.getName());
        });
    }
}
