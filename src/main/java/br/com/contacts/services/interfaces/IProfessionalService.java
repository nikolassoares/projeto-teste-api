package br.com.contacts.services.interfaces;

import br.com.contacts.model.Professional;
import br.com.contacts.model.dto.ProfessionalDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProfessionalService {

    ResponseEntity<List<Professional>> findAll();

    ResponseEntity<Professional> findId(Long id);

    ResponseEntity<Professional> save(ProfessionalDTO entity);

    ResponseEntity<Professional> update(Long id, ProfessionalDTO entity);

    ResponseEntity<Professional> delete(Long id);
}
