package br.com.compass.challenge2.entity;

import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Getter
@Table(name = "organizers")
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organizer extends Person{

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "organizers_groups", joinColumns = @JoinColumn(name = "organizer_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups;

    @ElementCollection(targetClass = Role.class)
    @JoinTable(name = "organizers_roles", joinColumns = @JoinColumn(name = "organizer_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private List<Role> roles;
}
