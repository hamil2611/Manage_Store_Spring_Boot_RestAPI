package com.example.managestore.service.manageProduct;

import com.example.managestore.domain.CategoryDto;
import com.example.managestore.entity.product.Category;
import com.example.managestore.utils.Constants;
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

    public CategoryDto insert(CategoryDto categoryDto) {
        try {
            Category category = modelMapper.map(categoryDto, Category.class);
            category.setCreatedDate(LocalDateTime.now());
            category.setLastUpdated(LocalDateTime.now());
            Category categoryInserted = categoryRepository.save(category);
            return modelMapper.map(categoryInserted, CategoryDto.class);
        } catch (DataAccessException e) {
            log.debug(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public CategoryDto update(CategoryDto categoryDto) {
        try {
            Category category = modelMapper.map(categoryDto, Category.class);
            category.setLastUpdated(LocalDateTime.now());
            Category categoryUpdated = categoryRepository.save(category);
            return modelMapper.map(categoryUpdated, CategoryDto.class);
        } catch (DataAccessException e) {
            log.debug(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream().map(x -> modelMapper.map(x, CategoryDto.class)).collect(Collectors.toList());
    }

    public void delete(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            log.debug(Constants.CATEGORY_NOT_FOUND + categoryId);
            throw new EntityNotFoundException(Constants.CATEGORY_NOT_FOUND + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

}
