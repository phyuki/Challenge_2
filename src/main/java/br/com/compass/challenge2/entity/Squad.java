package br.com.compass.challenge2.entity;

import lombok.*;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL)
    private List<Student> students;
}
