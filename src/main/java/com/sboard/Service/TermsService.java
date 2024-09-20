package com.sboard.Service;

import com.sboard.dto.TermsDTO;
import com.sboard.entity.Terms;
import com.sboard.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TermsService {

    private final TermsRepository termsRepository;

    public List<TermsDTO> selectAll(){
        List<Terms> terms = termsRepository.findAll();
        List<TermsDTO> dtos = terms.stream().map(entity -> entity.toDTO()).collect(Collectors.toList());

        return dtos;
    }

}
