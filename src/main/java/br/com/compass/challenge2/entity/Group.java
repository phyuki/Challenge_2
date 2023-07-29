package br.com.compass.challenge2.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "pb_groups")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Student> students;

    @ManyToMany(mappedBy = "groups")
    @JsonManagedReference
    private List<Organizer> organizers;

}
