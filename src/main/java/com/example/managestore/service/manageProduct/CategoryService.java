package com.example.managestore.service.manageProduct;

import com.example.managestore.entity.dto.CategoryDto;
import com.example.managestore.entity.product.Category;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.manageProduct.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    public CategoryDto insert(CategoryDto categoryDto){
        try{
            Category categoryInserted = categoryRepository.save(modelMapper.map(categoryDto,Category.class));
            return modelMapper.map(categoryInserted,CategoryDto.class);
        }catch (DataAccessException e){
            log.debug("Unable save Category");
            System.out.println(e);
            throw new RepositoryAccessException("Unable save Category");
        }
    }
}
