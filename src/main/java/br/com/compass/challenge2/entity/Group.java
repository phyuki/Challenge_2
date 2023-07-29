package br.com.compass.challenge2.entity;

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
    private List<Student> students;

    @ManyToMany(mappedBy = "groups")
    private List<Organizer> organizers;

}
