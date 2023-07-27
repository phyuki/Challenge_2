package br.com.compass.challenge2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "assessments")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    private Student student;

    @Column(name = "activity_name", nullable = false)
    private String activityName;

    @Column(name = "grade", nullable = false)
    private Float grade;
}
