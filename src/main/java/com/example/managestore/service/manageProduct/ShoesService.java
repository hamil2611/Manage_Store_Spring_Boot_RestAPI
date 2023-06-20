package com.example.managestore.service.manageProduct;

import com.example.managestore.domain.ShoesDto;
import com.example.managestore.domain.ShoesItemDto;
import com.example.managestore.entity.product.shoes.Shoes;
import com.example.managestore.enums.Constants;
import com.example.managestore.exception.entityException.EntityExistedException;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.manageProduct.ShoesItemRepository;
import com.example.managestore.repository.manageProduct.ShoesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ShoesService {
    private final ShoesRepository shoesRepository;
    private final ShoesItemRepository shoesItemRepository;
    private final ModelMapper modelMapper;

    public ShoesDto insertShoes(ShoesDto shoesDto) {
        Shoes shoes = modelMapper.map(shoesDto, Shoes.class);
        try {
            return modelMapper.map(shoesRepository.save(shoes), ShoesDto.class);
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public List<ShoesDto> getAll() {
        return shoesRepository.findAll()
                .stream()
                .map(shoes -> modelMapper.map(shoes, ShoesDto.class))
                .collect(Collectors.toList());
    }

    public void deleteShoes(Long shoesId) {
        if (!shoesRepository.existsById(shoesId)) {
            log.error(Constants.SHOES_NOT_FOUND + shoesId);
            throw new EntityNotFoundException(Constants.SHOES_NOT_FOUND + shoesId);
        }
        shoesRepository.deleteById(shoesId);
    }

    public ShoesDto updateShoes(ShoesDto shoesDto) {
        try {
            return modelMapper.map(shoesRepository.save(modelMapper.map(shoesDto, Shoes.class)), ShoesDto.class);
        } catch (DataAccessException e) {
            log.debug(Constants.UNABLE_SAVE_RECORD);
            System.out.println(e);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public ShoesItemDto createItem(ShoesItemDto shoesItemDto) {
        if (shoesItemRepository.existsByShoesId(shoesItemDto.getShoes().getId())) {
            log.debug(Constants.SHOES_EXISTED_FOR_SHOES_ITEM + shoesItemDto.getId());
            throw new EntityExistedException(Constants.SHOES_EXISTED_FOR_SHOES_ITEM + shoesItemDto.getId());
        }
        try {
            ShoesItemDto shoesItemCreated = shoesItemRepository.save(shoesItemDto.toEntity()).toDto();
            return shoesItemCreated;
        } catch (DataAccessException e) {
            System.out.println(e);
            log.debug(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public ShoesItemDto updateItem(ShoesItemDto shoesItemDto) {
        try {
            ShoesItemDto shoesItemUpdated = shoesItemRepository.save(shoesItemDto.toEntity()).toDto();
            return shoesItemUpdated;
        } catch (DataAccessException e) {
            log.debug(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public void deleteItem(Long shoesItemId) {
        shoesItemRepository.findById(shoesItemId).orElseThrow(() -> {
            log.debug(Constants.SHOES_ITEM_NOT_FOUND + shoesItemId);
            throw new EntityNotFoundException(Constants.SHOES_ITEM_NOT_FOUND + shoesItemId);
        });
        shoesItemRepository.deleteById(shoesItemId);
    }

    public List<ShoesItemDto> getAllItem() {
        return shoesItemRepository.findAll()
                .stream()
                .map(x -> x.toDto())
                .collect(Collectors.toList());
    }
}
