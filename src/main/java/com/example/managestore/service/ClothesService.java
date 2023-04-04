package com.example.managestore.service;

import com.example.managestore.entity.product.clothes.Clothes;
import com.example.managestore.entity.dto.ClothesDto;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.ShirtRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClothesService {
    private final ShirtRepository shirtRepository;
    private final ModelMapper modelMapper;

    public Clothes insert(ClothesDto clothesDto) {
        try {
            Clothes clothesInserted = shirtRepository.save(modelMapper.map(clothesDto, Clothes.class));
            return clothesInserted;
        } catch (DataAccessException e) {
            throw new RepositoryAccessException("Unable save shirt");
        }
    }

    public List<ClothesDto> getAll() {
        return shirtRepository.findAll().stream().map(x -> modelMapper.map(x, ClothesDto.class)).collect(Collectors.toList());
    }

    public void delete(Long id) {
        shirtRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("Shirt does not exist!");
        });
        shirtRepository.deleteById(id);
    }

    public ClothesDto update(ClothesDto clothesDto) {
        Clothes clothes = modelMapper.map(clothesDto, Clothes.class);
        try {
            ClothesDto shirtUpdated = modelMapper.map(shirtRepository.save(clothes), ClothesDto.class);
            return shirtUpdated;
        } catch (DataAccessException e) {
            throw new RepositoryAccessException("Unable update shirt");
        }
    }
}
