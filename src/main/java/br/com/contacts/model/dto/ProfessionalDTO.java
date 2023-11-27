package br.com.contacts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessionalDTO {
    private Long id;
    private String name;
    private String responsibility;
    private LocalDateTime birth;
    private LocalDateTime createdDate;
}