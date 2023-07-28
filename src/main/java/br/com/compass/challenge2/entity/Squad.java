package br.com.compass.challenge2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Table(name = "squads")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "squad_name", nullable = false)
    private String squadName;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Student> students;
    
    public String getSquadName() {
        return squadName;
    }
    public void setSquadName(String squadName) {
        this.squadName = squadName;
    }
    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
