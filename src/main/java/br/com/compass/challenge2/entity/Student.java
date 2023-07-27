package br.com.compass.challenge2.entity;

import lombok.*;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "students")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Person {

    @ManyToOne
    private Group group;

    @ManyToOne
    private Squad squad;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Assessment> assessments;
}
