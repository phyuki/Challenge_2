package br.com.compass.challenge2.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentDTO {

    private Long studentId;
    private String activityName;
    private Float grade;

}
