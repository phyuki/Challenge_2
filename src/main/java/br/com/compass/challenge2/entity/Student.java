package br.com.compass.challenge2.entity;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Squad squad;
    private Group group;
    private List<Assessment> assessments;
}

