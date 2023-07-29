package br.com.compass.challenge2.entity;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "assessments")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assessment extends RepresentationModel<Assessment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotNull
    @ManyToOne
    private Student student;

    @NotBlank
    @Column(nullable = false)
    private String activityName;

    @NotNull
    @Column(nullable = false)
    private Float grade;
}
