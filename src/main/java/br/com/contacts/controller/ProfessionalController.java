package br.com.contacts.controller;

import br.com.contacts.model.Professional;
import br.com.contacts.model.dto.ProfessionalDTO;
import br.com.contacts.services.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/professional")
@RestController
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Professional>> findAll() {
        return professionalService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Professional> findId(@PathVariable("id") Long id) {
        return professionalService.findId(id);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Professional> save(@RequestBody ProfessionalDTO professionalDTO) {
        return professionalService.save(professionalDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Professional> update(@PathVariable("id") Long id, @RequestBody ProfessionalDTO professionalDTO) {
        return professionalService.update(id, professionalDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Professional> delete(@PathVariable("id") Long id) {
        return professionalService.delete(id);
    }

}
