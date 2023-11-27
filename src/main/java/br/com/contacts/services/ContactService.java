package br.com.contacts.services;

import br.com.contacts.model.Contact;
import br.com.contacts.model.dto.ContactDTO;
import br.com.contacts.repository.ContactRepository;
import br.com.contacts.services.interfaces.IContactService;
import br.com.contacts.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService implements IContactService {

    @Autowired
    private ContactRepository contactRepository;


    @Override
    public ResponseEntity<List<Contact>> findAll() {
        try {
            List<Contact> p = contactRepository.findAll();

            if (p.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(p, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Contact> findId(Long id) {
        try {
            Optional<Contact> entity = contactRepository.findById(id);
            return entity.map(contact -> new ResponseEntity<>(contact, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Contact> save(ContactDTO contact) {
        try {
            Contact entity = convertDTOToEntity(contact);
            entity.setCreatedDate(LocalDateTime.now());
            entity = contactRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Contact> update(Long id, ContactDTO contact) {
        try {

            Optional<Contact> p = contactRepository.findById(id);
            if (!p.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Contact prod = convertDTOToEntity(contact);

            contactRepository.save(prod);
            return new ResponseEntity<>(prod, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Contact> delete(Long id) {
        try {
            contactRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Contact convertDTOToEntity(ContactDTO contact) {
      return ObjectMapperUtils.map(contact, Contact.class);
    }

}