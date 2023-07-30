package br.com.compass.challenge2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizers")
@Relation(collectionRelation = "organizers")
public class Organizer extends RepresentationModel<Organizer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "organizers_groups", joinColumns = @JoinColumn(name = "organizer_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups;

    @ElementCollection(targetClass = Role.class)
    @JoinTable(name = "organizers_roles", joinColumns = @JoinColumn(name = "organizer_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private List<Role> roles;
}
