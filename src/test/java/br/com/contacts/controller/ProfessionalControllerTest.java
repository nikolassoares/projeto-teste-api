package br.com.contacts.controller;

import br.com.contacts.model.Professional;
import br.com.contacts.model.dto.ProfessionalDTO;
import br.com.contacts.services.ProfessionalService;
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
public class ProfessionalControllerTest {
    
    @InjectMocks
    private ProfessionalController controller;

    @Mock
    private ProfessionalService service;


    @Test
    public void mustReturnAllProfessional() {
        List<Professional> professionalList = createList();
        ResponseEntity<List<Professional>> p = new ResponseEntity<>(professionalList, HttpStatus.OK);
        Mockito.when(service.findAll()).thenReturn(p);
        ResponseEntity<List<Professional>> result = controller.findAll();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void mustReturnProfessionalById() {
        Professional professional = Professional.builder()
                .id(1L)
                .name("Teste")
                .responsibility("Desenvolvedor")
                .createdDate(LocalDateTime.now())
                .birth(LocalDateTime.now())
                .build();
        ResponseEntity<Professional> p = new ResponseEntity<>(professional, HttpStatus.OK);
        Mockito.when(service.findId(1L)).thenReturn(p);
        ResponseEntity<Professional> result = controller.findId(1L);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test
    public void mustSaveProfessional() {
        ProfessionalDTO professional = ProfessionalDTO.builder()
                .name("Teste")
                .responsibility("Desenvolvedor")
                .createdDate(LocalDateTime.now())
                .birth(LocalDateTime.now())
                .build();
        Professional prod = convertDTOToEntity(professional);
        ResponseEntity<Professional> p = new ResponseEntity<>(prod, HttpStatus.CREATED);
        Mockito.when(service.save(professional)).thenReturn(p);
        ResponseEntity<Professional> result = controller.save(professional);
        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }


    @Test
    public void mustUpdateProfessional() {
        ProfessionalDTO professional = ProfessionalDTO.builder()
                .id(1L)
                .name("Teste")
                .responsibility("Desenvolvedor")
                .createdDate(LocalDateTime.now())
                .birth(LocalDateTime.now())
                .build();
        Professional prod = convertDTOToEntity(professional);
        ResponseEntity<Professional> p = new ResponseEntity<>(prod, HttpStatus.OK);
        Mockito.when(service.update(1L, professional)).thenReturn(p);
        ResponseEntity<Professional> result = controller.update(1L, professional);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void mustDeleteProfessional() {
        ResponseEntity<Professional> p = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(service.delete(1L)).thenReturn(p);
        ResponseEntity<Professional> result = controller.delete(1L);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    private List<Professional> createList() {
        List<Professional> professionalList = new ArrayList<Professional>();
        professionalList.add(Professional.builder()
                .id(1L)
                .name("Teste")
                .responsibility("Desenvolvedor")
                .createdDate(LocalDateTime.now())
                .birth(LocalDateTime.now())
                .build());
        return professionalList;
    }

    private Professional convertDTOToEntity(ProfessionalDTO professionalDTO) {
       return ObjectMapperUtils.map(professionalDTO, Professional.class);
    }

}
