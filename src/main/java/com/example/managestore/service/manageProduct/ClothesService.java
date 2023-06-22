package com.example.managestore.service.manageProduct;

import com.example.managestore.domain.ClothesItemDto;
import com.example.managestore.entity.product.clothes.Clothes;
import com.example.managestore.domain.ClothesDto;
import com.example.managestore.utils.Constants;
import com.example.managestore.exception.entityException.EntityExistedException;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.manageProduct.ClothesItemRepository;
import com.example.managestore.repository.manageProduct.ClothesRepository;
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
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final ClothesItemRepository clothesItemRepository;
    private final ModelMapper modelMapper;

    public ClothesDto insertClothes(ClothesDto clothesDto) {
        try {
            Clothes clothesInserted = clothesRepository.save(modelMapper.map(clothesDto, Clothes.class));
            return modelMapper.map(clothesInserted, ClothesDto.class);
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public List<ClothesDto> getAll() {
        return clothesRepository.findAll()
                .stream()
                .map(x -> modelMapper.map(x, ClothesDto.class))
                .collect(Collectors.toList());
    }

    public void deleteClothes(Long clothesId) {
        if(!clothesRepository.existsById(clothesId)){
            log.error(Constants.CLOTHES_NOT_FOUND + clothesId);
            throw new EntityNotFoundException(Constants.CLOTHES_NOT_FOUND + clothesId);
        }
        clothesRepository.deleteById(clothesId);
    }

    public ClothesDto updateClothes(ClothesDto clothesDto) {
        Clothes clothes = modelMapper.map(clothesDto, Clothes.class);
        try {
            ClothesDto shirtUpdated = modelMapper.map(clothesRepository.save(clothes), ClothesDto.class);
            return shirtUpdated;
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public ClothesItemDto createItem(ClothesItemDto clothesItemDto) {
        if (clothesItemRepository.existsByClothesId(clothesItemDto.getClothes().getId())) {
            log.error(Constants.CLOTHES_EXISTED_FOR_CLOTHES_ITEM + clothesItemDto.getId());
            throw new EntityExistedException(Constants.CLOTHES_EXISTED_FOR_CLOTHES_ITEM + clothesItemDto.getId());
        }
        try {
            ClothesItemDto clothesCreated = clothesItemRepository.save(clothesItemDto.toEntity()).toDto();
            return clothesCreated;
        } catch (DataAccessException e) {
            System.out.println(e);
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public ClothesItemDto updateItem(ClothesItemDto clothesItemDto) {
        try {
            ClothesItemDto clothesCreated = clothesItemRepository.save(clothesItemDto.toEntity()).toDto();
            return clothesCreated;
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public void deleteItem(Long clothesItemId) {
        clothesItemRepository.findById(clothesItemId).orElseThrow(() -> {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new EntityNotFoundException(Constants.UNABLE_SAVE_RECORD);
        });
        clothesItemRepository.deleteById(clothesItemId);
    }

    public List<ClothesItemDto> getAllItem() {
        return clothesItemRepository.findAll()
                .stream()
                .map(x -> x.toDto())
                .collect(Collectors.toList());
    }
}
