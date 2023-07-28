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
    private Group group;

    @ManyToOne
    private Squad squad;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Assessment> assessments;
}
