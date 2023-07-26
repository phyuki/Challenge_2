package br.com.compass.challenge2.entity;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {
    private Long id;
    private Student student;
    private String activityName;
    private Float grade;
}
