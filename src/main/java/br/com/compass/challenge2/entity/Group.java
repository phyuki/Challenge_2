package br.com.compass.challenge2.entity;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private Long id;
    private String groupName;
    private List<Student> students;
    private List<Organizer> organizers;
}

