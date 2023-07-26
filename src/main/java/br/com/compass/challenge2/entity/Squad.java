package br.com.compass.challenge2.entity;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Squad {
    private Long id;
    private String squadName;
    private List<Student> students;
}
