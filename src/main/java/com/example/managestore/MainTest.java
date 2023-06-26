package com.example.managestore;

import com.example.managestore.entity.product.Category;
import com.example.managestore.utils.PoiPOJOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainTest {
    public void get() {

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
