package br.com.contacts.service;

import br.com.contacts.model.Professional;
import br.com.contacts.model.dto.ProfessionalDTO;
import br.com.contacts.repository.ProfessionalRepository;
import br.com.contacts.services.ProfessionalService;
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
public class ProfessionalServiceTest {

    @Mock
    private ProfessionalRepository repository;

    @InjectMocks
    private ProfessionalService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mustReturnListSuccessfully() {
        List<Professional> professionalList = createProfessionalList();
        when(repository.findAll()).thenReturn(professionalList);

        ResponseEntity<List<Professional>> result = service.findAll();
        assertEquals(1, Objects.requireNonNull(result.getBody()).size());
    }

    @Test
    public void mustReturnListEmpty() {
        List<Professional> professionalList = new ArrayList<>();
        when(repository.findAll()).thenReturn(professionalList);

        ResponseEntity<List<Professional>> result = service.findAll();
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void mustReturnProfessionalByIdEmpty() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Professional> result = service.findId(1L);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void mustReturnProfessionalById() {
        Professional professional = Professional.builder()
                .id(1L)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(professional));
        ResponseEntity<Professional> result = service.findId(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test
    public void mustSaveProfessional() {
        ProfessionalDTO professional = ProfessionalDTO.builder()
                .name("Teste")
                .responsibility("Desenvolvedor")
                .createdDate(LocalDateTime.now())
                .birth(LocalDateTime.now())
                .build();
        Professional p = convertDTOToEntity(professional);
        when(repository.save(p)).thenReturn(p);
        ResponseEntity<Professional> result = service.save(professional);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
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
        Professional p = convertDTOToEntity(professional);
        when(repository.findById(1L)).thenReturn(Optional.of(p));
        when(repository.save(p)).thenReturn(p);
        ResponseEntity<Professional> result = service.update(1L, professional);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void removeProfessional() {
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }


    private List<Professional> createProfessionalList() {
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