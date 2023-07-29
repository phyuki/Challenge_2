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

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;

}
