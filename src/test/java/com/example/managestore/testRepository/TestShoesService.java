package com.example.managestore.testRepository;

import com.example.managestore.entity.dto.ShoesDto;
import com.example.managestore.entity.product.shoes.Shoes;
import com.example.managestore.repository.manageProduct.ShoesItemRepository;
import com.example.managestore.repository.manageProduct.ShoesRepository;
import com.example.managestore.service.manageProduct.ShoesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class TestShoesService {

//    @Test
//    public void testInsertShoes(){
//        Shoes shoes = modelMapper.map(shoesDto,Shoes.class);
//        System.out.println(shoesDto.getName());
//        System.out.println(shoes.getName());
//        System.out.println(shoesRepository.save(shoes).getName());
//    }
}
