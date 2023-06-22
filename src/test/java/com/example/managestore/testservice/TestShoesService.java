package com.example.managestore.testservice;

import com.example.managestore.dataExcel.InitData;
import com.example.managestore.domain.ShoesDto;
import com.example.managestore.entity.product.Category;
import com.example.managestore.entity.product.shoes.Shoes;
import com.example.managestore.repository.manageProduct.ShoesItemRepository;
import com.example.managestore.repository.manageProduct.ShoesRepository;
import com.example.managestore.service.manageProduct.ShoesService;
import com.example.managestore.utils.PoiPOJOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestShoesService {

    @Mock
    private ShoesRepository shoesRepository;
    @Mock
    private ShoesItemRepository shoesItemRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private ShoesService shoesService;
    private List<Shoes> shoesList ;
    @Before
    public void setup() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        FileInputStream file = new FileInputStream(new File("src/test/java/com/example/managestore/dataExcel/Shoes.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        shoesList = PoiPOJOUtils.sheetToPOJO(sheet,Shoes.class);
        Mockito.when(shoesRepository.findAll()).thenReturn(shoesList);
    }
    @Test
    public void testInsertShoes(){
        System.out.println(shoesService.getAll().get(0).getCode());
    }

    @Test
    public void test(){

    }
}
