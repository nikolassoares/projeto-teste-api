package br.com.contacts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="professional")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professional_sequence")
    @SequenceGenerator(name = "professional_sequence", sequenceName = "PROFESSIONAL_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "responsibility")
    private String responsibility;

    @Column(name = "birth")
    private LocalDateTime birth;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}