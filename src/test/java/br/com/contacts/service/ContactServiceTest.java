package br.com.contacts.service;

import br.com.contacts.model.Contact;
import br.com.contacts.model.Professional;
import br.com.contacts.model.dto.ContactDTO;
import br.com.contacts.model.dto.ProfessionalDTO;
import br.com.contacts.repository.ContactRepository;
import br.com.contacts.services.ContactService;
import br.com.contacts.util.ObjectMapperUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository repository;

    @InjectMocks
    private ContactService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mustReturnListSuccessfully() {
        List<Contact> contactList = createContactList();
        when(repository.findAll()).thenReturn(contactList);

        ResponseEntity<List<Contact>> result = service.findAll();
        assertEquals(1, Objects.requireNonNull(result.getBody()).size());
    }

    @Test
    public void mustReturnListEmpty() {
        List<Contact> contactList = new ArrayList<>();
        when(repository.findAll()).thenReturn(contactList);

        ResponseEntity<List<Contact>> result = service.findAll();
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void mustReturnContactByIdEmpty() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Contact> result = service.findId(1L);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void mustReturnContactById() {
        Contact contact = Contact.builder()
                .id(1L)
                .name("Fixo")
                .contact("999999999999")
                .professional(Professional.builder().id(1L).build())
                .createdDate(LocalDateTime.now())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(contact));
        ResponseEntity<Contact> result = service.findId(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test
    public void mustSaveContact() {
        ContactDTO contact = ContactDTO.builder()
                .name("Fixo")
                .contact("999999999999")
                .professional(ProfessionalDTO.builder().id(1L).build())
                .createdDate(LocalDateTime.now())
                .build();
        Contact p = convertDTOToEntity(contact);
        when(repository.save(p)).thenReturn(p);
        ResponseEntity<Contact> result = service.save(contact);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void mustUpdateContact() {
        ContactDTO contact = ContactDTO.builder()
                .id(1L)
                .name("Fixo")
                .contact("999999999999")
                .professional(ProfessionalDTO.builder().id(1L).build())
                .createdDate(LocalDateTime.now())
                .build();
        Contact p = convertDTOToEntity(contact);
        when(repository.findById(1L)).thenReturn(Optional.of(p));
        when(repository.save(p)).thenReturn(p);
        ResponseEntity<Contact> result = service.update(1L, contact);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void removeContact() {
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }


    private List<Contact> createContactList() {
        List<Contact> contactList = new ArrayList<Contact>();
        contactList.add(Contact.builder()
                .id(1L)
                .name("Fixo")
                .contact("999999999999")
                .professional(Professional.builder().id(1L).build())
                .createdDate(LocalDateTime.now())
                .build());
        return contactList;
    }

    private Contact convertDTOToEntity(ContactDTO contact) {
        return ObjectMapperUtils.map(contact, Contact.class);
    }

}