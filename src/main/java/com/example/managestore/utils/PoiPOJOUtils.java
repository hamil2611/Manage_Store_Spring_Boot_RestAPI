package com.example.managestore.utils;

import io.swagger.models.auth.In;
import jakarta.persistence.Column;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PoiPOJOUtils {
    public static <T> List<T> sheetToPOJO(Sheet sheet, Class<T> beanClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        DataFormatter formatter = new DataFormatter(java.util.Locale.US);
        FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //Get Field Of Class
        int headerRowNum = sheet.getFirstRowNum();
        Row row = sheet.getRow(headerRowNum);
        Map<Integer, String> colHeader = new HashMap<>();
        for (Cell cell : row) {
            int colIndex = cell.getColumnIndex();
            String value = formatter.formatCellValue(cell, evaluator);
            colHeader.put(colIndex, value);
        }
        List<T> result = new ArrayList<>();
        String cellString = "";
        Double cellNum = null;
        Date cellDate = null;
        for (int r = headerRowNum + 1; r <= sheet.getLastRowNum(); r++) {
            row = sheet.getRow(r);
            T bean = beanClass.getDeclaredConstructor().newInstance();
            for (Map.Entry<Integer, String> entry : colHeader.entrySet()) {
                int colIndex = entry.getKey();
                Cell cell = row.getCell(colIndex);
                switch (cell.getCellType()) {
                    case NUMERIC -> cellNum = cell.getNumericCellValue();
                    case STRING -> cellString = cell.getStringCellValue();
                }
                for(Field f: beanClass.getDeclaredFields()){
                    if(!f.isAnnotationPresent(ExcelColumn.class))
                        continue;
                    ExcelColumn ec = f.getAnnotation(ExcelColumn.class);
                    f.setAccessible(true);
                    if(entry.getValue().equals(ec.name()))
                        if(f.getType()==String.class)
                            f.set(bean,cellString);
                        else if(f.getType()==Double.class)
                            f.set(bean,cellNum);
                        else if(f.getType()==Integer.class)
                            f.set(bean,cellNum.intValue());
                        else if(f.getType()==Long.class)
                            f.set(bean,cellNum.longValue());
                        else if(f.getType()==Float.class)
                            f.set(bean, cellNum.floatValue());
                        else if(f.getType()== LocalDateTime.class)
                            f.set(bean,LocalDateTime.parse(cellString,dateTimeFormatter));
                }
            }
            result.add(bean);
        }
        return result;
    }
}
