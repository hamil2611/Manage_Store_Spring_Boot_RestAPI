package com.example.managestore.service.manageProduct;

import com.example.managestore.entity.dto.ClothesItemDto;
import com.example.managestore.entity.product.clothes.Clothes;
import com.example.managestore.entity.dto.ClothesDto;
import com.example.managestore.entity.product.clothes.ClothesItem;
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
            log.debug("Unable save shirt");
            throw new RepositoryAccessException("Unable save shirt");
        }
    }

    public List<ClothesDto> getAll() {
        return clothesRepository.findAll()
                .stream()
                .map(x -> modelMapper.map(x, ClothesDto.class))
                .collect(Collectors.toList());
    }

    public void deleteClothes(Long id) {
        clothesRepository.findById(id).orElseThrow(() -> {
            log.debug(String.format("Clothes with Id=%f does not exist", id));
            throw new EntityNotFoundException(String.format("Clothes with Id=%f does not exist", id));
        });
        clothesRepository.deleteById(id);
    }

    public ClothesDto updateClothes(ClothesDto clothesDto) {
        Clothes clothes = modelMapper.map(clothesDto, Clothes.class);
        try {
            ClothesDto shirtUpdated = modelMapper.map(clothesRepository.save(clothes), ClothesDto.class);
            return shirtUpdated;
        } catch (DataAccessException e) {
            log.debug("Unable update shirt");
            throw new RepositoryAccessException("Unable update shirt");
        }
    }

    public ClothesItemDto createItem(ClothesItemDto clothesItemDto) {
        if (clothesItemRepository.existsByClothesId(clothesItemDto.getClothes().getId())) {
            log.debug(String.format("ClotheItem for Clothes have been already"));
            throw new EntityExistedException(String.format("ClotheItem for Clothes have been already"));
        }
        try {
            ClothesItemDto clothesCreated = clothesItemRepository.save(clothesItemDto.toEntity()).toDto();
            return clothesCreated;
        } catch (DataAccessException e) {
            System.out.println(e);
            log.debug("Unable save ClothesItem");
            throw new RepositoryAccessException("Unable save ClothesItem");
        }
    }

    public ClothesItemDto updateItem(ClothesItemDto clothesItemDto) {
        try {
            ClothesItemDto clothesCreated = clothesItemRepository.save(clothesItemDto.toEntity()).toDto();
            return clothesCreated;
        } catch (DataAccessException e) {
            log.debug("Unable save ClothesItem");
            throw new RepositoryAccessException("Unable save ClothesItem");
        }
    }

    public void deleteItem(Long clothesItemId) {
        clothesItemRepository.findById(clothesItemId).orElseThrow(() -> {
            log.debug(String.format("ClothesItem does not exist"));
            throw new EntityNotFoundException(String.format("ClothesItem does not exist"));
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
