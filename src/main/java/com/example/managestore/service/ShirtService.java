package com.example.managestore.service;

import com.example.managestore.entity.product.clothes.Shirt;
import com.example.managestore.entity.dto.ShirtDto;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.repositoryException.RepositoryAccessException;
import com.example.managestore.repository.ShirtRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShirtService {
    private final ShirtRepository shirtRepository;
    private final ModelMapper modelMapper;

    public Shirt insert(ShirtDto shirtDto) {
        try {
            Shirt shirtInserted = shirtRepository.save(modelMapper.map(shirtDto, Shirt.class));
            return shirtInserted;
        } catch (DataAccessException e) {
            throw new RepositoryAccessException("Unable save shirt");
        }
    }

    public List<ShirtDto> getAll() {
        return shirtRepository.findAll().stream().map(x -> modelMapper.map(x, ShirtDto.class)).collect(Collectors.toList());
    }

    public void delete(Long id) {
        shirtRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("Shirt does not exist!");
        });
        shirtRepository.deleteById(id);
    }

    public ShirtDto update(ShirtDto shirtDto) {
        Shirt shirt = modelMapper.map(shirtDto, Shirt.class);
        try {
            ShirtDto shirtUpdated = modelMapper.map(shirtRepository.save(shirt), ShirtDto.class);
            return shirtUpdated;
        } catch (DataAccessException e) {
            throw new RepositoryAccessException("Unable update shirt");
        }
    }
}
