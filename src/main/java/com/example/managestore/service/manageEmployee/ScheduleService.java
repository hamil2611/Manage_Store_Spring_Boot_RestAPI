package com.example.managestore.service.manageEmployee;

import com.example.managestore.entity.dto.ShiftDto;
import com.example.managestore.entity.employee.Shift;
import com.example.managestore.exception.entityException.EntityExistedException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ShiftRepository shiftRepository;
    private final ModelMapper modelMapper;

    public ShiftDto insert(ShiftDto shiftDto){
        if(shiftRepository.existsByTimeShift(shiftDto.getTimeShift())){
            throw new EntityExistedException(String.format("Shift with time %s has already been existed!", shiftDto.getTimeShift().toString()));
        }
        try{
            Shift shift = modelMapper.map(shiftDto, Shift.class);
            shift.setCreatedDate(LocalDateTime.now());
            shift.setLastUpdated(LocalDateTime.now());
            return modelMapper.map(shiftRepository.save(shift), ShiftDto.class);
        }catch (DataAccessException e){
            throw new RepositoryAccessException("Unable save shift");
        }
    }

    public List<ShiftDto> getAll(){
        return shiftRepository.findAll().stream().map(x -> modelMapper.map(x, ShiftDto.class)).collect(Collectors.toList());
    }
}
