package br.com.compass.challenge2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

@Table(name = "students")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends RepresentationModel<Student> {

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

    @ManyToOne
    @JsonIgnore
    private Group group;

    @ManyToOne
    @JsonIgnore
    private Squad squad;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Assessment> assessments;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((squad == null) ? 0 : squad.hashCode());
		result = prime * result + ((assessments == null) ? 0 : assessments.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (squad == null) {
			if (other.squad != null)
				return false;
		} else if (!squad.equals(other.squad))
			return false;
		if (assessments == null) {
			if (other.assessments != null)
				return false;
		} else if (!assessments.equals(other.assessments))
			return false;
		return true;
	}
}
