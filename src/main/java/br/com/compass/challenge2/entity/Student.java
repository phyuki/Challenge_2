package br.com.compass.challenge2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private Group group;

    @ManyToOne
    @JsonBackReference
    private Squad squad;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Assessment> assessments;
}
