package br.com.compass.challenge2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Table(name = "students")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Person {

    @ManyToOne
    @JsonIgnore
    private Group group;

    @ManyToOne
    @JsonIgnore
    private Squad squad;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Assessment> assessments;
}
