package br.com.contacts.services;

import br.com.contacts.model.Professional;
import br.com.contacts.model.dto.ProfessionalDTO;
import br.com.contacts.repository.ProfessionalRepository;
import br.com.contacts.services.interfaces.IProfessionalService;
import br.com.contacts.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService implements IProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;


    @Override
    public ResponseEntity<List<Professional>> findAll() {
        try {
            List<Professional> entity = professionalRepository.findAll();

            if (entity.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(entity, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Professional> findId(Long id) {
        try {
            Optional<Professional> entity = professionalRepository.findById(id);
            return entity.map(professional -> new ResponseEntity<>(professional, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Professional> save(ProfessionalDTO professional) {
        try {

            Professional entity = convertDTOToEntity(professional);
            entity.setCreatedDate(LocalDateTime.now());
            entity = professionalRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Professional> update(Long id, ProfessionalDTO professional) {
        try {

            Optional<Professional> entityOptional = professionalRepository.findById(id);
            if (!entityOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Professional entity = convertDTOToEntity(professional);

            professionalRepository.save(entity);
            return new ResponseEntity<>(entity, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Professional> delete(Long id) {
        try {
            professionalRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private Professional convertDTOToEntity(ProfessionalDTO professional) {
        return ObjectMapperUtils.map(professional, Professional.class);
    }

}