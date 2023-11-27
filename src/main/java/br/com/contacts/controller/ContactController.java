package br.com.contacts.controller;

import br.com.contacts.model.Contact;
import br.com.contacts.model.dto.ContactDTO;
import br.com.contacts.services.ContactService;
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
@RequestMapping("/contact")
@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Contact>> findAll() {
        return contactService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Contact> findId(@PathVariable("id") Long id) {
        return contactService.findId(id);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Contact> save(@RequestBody ContactDTO contact) {
        return contactService.save(contact);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Contact> update(@PathVariable("id") Long id, @RequestBody ContactDTO contact) {
        return contactService.update(id, contact);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Contact> delete(@PathVariable("id") Long id) {
        return contactService.delete(id);
    }

   }
