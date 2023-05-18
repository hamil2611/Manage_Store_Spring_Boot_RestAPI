package com.example.managestore.service.manageProduct;

import com.example.managestore.entity.dto.CategoryDto;
import com.example.managestore.entity.product.Category;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.manageProduct.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    public CategoryDto insert(CategoryDto categoryDto){
        try{
            Category category = modelMapper.map(categoryDto, Category.class);
            category.setCreatedDate(LocalDateTime.now());
            category.setLastUpdated(LocalDateTime.now());
            Category categoryInserted = categoryRepository.save(category);
            return modelMapper.map(categoryInserted,CategoryDto.class);
        }catch (DataAccessException e){
            log.debug("Unable save Category");
            throw new RepositoryAccessException("Unable save Category");
        }
    }

    public CategoryDto update(CategoryDto categoryDto){
        try{
            Category category = modelMapper.map(categoryDto,Category.class);
            category.setLastUpdated(LocalDateTime.now());
            Category categoryUpdated = categoryRepository.save(category);
            return modelMapper.map(categoryUpdated,CategoryDto.class);
        }catch (DataAccessException e){
            log.debug("Unable update Category");
            throw new RepositoryAccessException("Unable update Category");
        }
    }

    public List<CategoryDto> getAll(){
        return categoryRepository.findAll().stream().map(x -> modelMapper.map(x,CategoryDto.class)).collect(Collectors.toList());
    }

    public void delete(Long categoryId){
        if(!categoryRepository.existsById(categoryId)){
            log.debug(String.format("Category with Id=%f does not exist", categoryId));
            throw new EntityNotFoundException(String.format("Category with Id=%f does not exist", categoryId));
        }
        categoryRepository.deleteById(categoryId);
    }

}
