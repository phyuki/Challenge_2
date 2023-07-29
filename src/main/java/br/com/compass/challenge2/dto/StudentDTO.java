package br.com.compass.challenge2.dto;
import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Squad;
import lombok.*;
import java.util.List;
@Getter
@Setter
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private Group group;
    private Squad squad;
    private List<Assessment> assessments;
}
