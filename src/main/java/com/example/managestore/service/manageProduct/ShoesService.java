package com.example.managestore.service.manageProduct;

import com.example.managestore.entity.dto.ClothesItemDto;
import com.example.managestore.entity.dto.ShoesDto;
import com.example.managestore.entity.dto.ShoesItemDto;
import com.example.managestore.entity.product.shoes.Shoes;
import com.example.managestore.entity.product.shoes.ShoesItem;
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

    public ShoesDto insertShoes(ShoesDto shoesDto){
        Shoes shoes = modelMapper.map(shoesDto, Shoes.class);
        try{
            return modelMapper.map(shoesRepository.save(shoes),ShoesDto.class);
        }catch (DataAccessException e){
            log.debug("Unable save shoes");
            System.out.println(e);
            throw new RepositoryAccessException("Unable save shoes");
        }
    }

    public List<ShoesDto> getAll(){
        return shoesRepository.findAll()
                .stream()
                .map(shoes -> modelMapper.map(shoes,ShoesDto.class))
                .collect(Collectors.toList());
    }

    public void deleteShoes(Long id){
        if(!shoesRepository.existsById(id)){
            log.debug(String.format("Shoes with Id=%f does not exist", id));
            throw new EntityNotFoundException(String.format("Shoes with Id=%f does not exist", id));
        }
        shoesRepository.deleteById(id);
    }

    public ShoesDto updateShoes(ShoesDto shoesDto){
        try{
            return modelMapper.map(shoesRepository.save(modelMapper.map(shoesDto,Shoes.class)),ShoesDto.class);
        }catch (DataAccessException e){
            log.debug("Unable UPDATE shoes");
            System.out.println(e);
            throw new RepositoryAccessException("Unable UPDATE shoes");
        }
    }

    public ShoesItemDto createItem(ShoesItemDto shoesItemDto){
        if (shoesItemRepository.existsByShoesId(shoesItemDto.getShoes().getId())) {
            log.debug(String.format("ShoesItem for Shoes have been already"));
            throw new EntityExistedException(String.format("ShoesItem for Shoes have been already"));
        }
        try {
            ShoesItemDto shoesItemCreated = shoesItemRepository.save(shoesItemDto.toEntity()).toDto();
            return shoesItemCreated;
        } catch (DataAccessException e) {
            System.out.println(e);
            log.debug("Unable save ShoesItem");
            throw new RepositoryAccessException("Unable save ShoesItem");
        }
    }

    public ShoesItemDto updateItem(ShoesItemDto shoesItemDto) {
        try {
            ShoesItemDto shoesItemUpdated = shoesItemRepository.save(shoesItemDto.toEntity()).toDto();
            return shoesItemUpdated;
        } catch (DataAccessException e) {
            log.debug("Unable save ShoesItem");
            throw new RepositoryAccessException("Unable save ShoesItem");
        }
    }

    public void deleteItem(Long clothesItemId) {
        shoesItemRepository.findById(clothesItemId).orElseThrow(() -> {
            log.debug(String.format("ShoesItem does not exist"));
            throw new EntityNotFoundException(String.format("ShoesItem does not exist"));
        });
        shoesItemRepository.deleteById(clothesItemId);
    }

    public List<ShoesItemDto> getAllItem() {
        return shoesItemRepository.findAll()
                .stream()
                .map(x -> x.toDto())
                .collect(Collectors.toList());
    }
}
