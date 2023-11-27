package br.com.contacts.controller;

import br.com.contacts.model.Contact;
import br.com.contacts.model.Professional;
import br.com.contacts.model.dto.ContactDTO;
import br.com.contacts.model.dto.ProfessionalDTO;
import br.com.contacts.services.ContactService;
import br.com.contacts.util.ObjectMapperUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class ContactControllerTest {
    @InjectMocks
    private ContactController controller;

    @Mock
    private ContactService service;


    @Test
    public void mustReturnAllContact() {
        List<Contact> contactList = createContactList();
        ResponseEntity<List<Contact>> p = new ResponseEntity<>(contactList, HttpStatus.OK);
        Mockito.when(service.findAll()).thenReturn(p);
        ResponseEntity<List<Contact>> result = controller.findAll();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void mustReturnContactById() {
        Contact contact = createContactList().get(0);
        ResponseEntity<Contact> p = new ResponseEntity<>(contact, HttpStatus.OK);
        Mockito.when(service.findId(1L)).thenReturn(p);
        ResponseEntity<Contact> result = controller.findId(1L);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test
    public void mustSaveContact() {
        ContactDTO contact = ContactDTO.builder()
                .name("Fixo")
                .contact("999999999999")
                .professional(ProfessionalDTO.builder().id(1L).build())
                .createdDate(LocalDateTime.now())
                .build();
        Contact prod = convertDTOToEntity(contact);
        ResponseEntity<Contact> p = new ResponseEntity<>(prod, HttpStatus.CREATED);
        Mockito.when(service.save(contact)).thenReturn(p);
        ResponseEntity<Contact> result = controller.save(contact);
        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
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
        Contact prod = convertDTOToEntity(contact);
        ResponseEntity<Contact> p = new ResponseEntity<>(prod, HttpStatus.OK);
        Mockito.when(service.update(1L, contact)).thenReturn(p);
        ResponseEntity<Contact> result = controller.update(1L, contact);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void mustDeleteContact() {
        ResponseEntity<Contact> p = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(service.delete(1L)).thenReturn(p);
        ResponseEntity<Contact> result = controller.delete(1L);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
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
