package br.com.contacts.services.interfaces;

import br.com.contacts.model.Contact;
import br.com.contacts.model.dto.ContactDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IContactService {

    ResponseEntity<List<Contact>> findAll();

    ResponseEntity<Contact> findId(Long id);

    ResponseEntity<Contact> save(ContactDTO entity);

    ResponseEntity<Contact> update(Long id, ContactDTO entity);

    ResponseEntity<Contact> delete(Long id);

}
