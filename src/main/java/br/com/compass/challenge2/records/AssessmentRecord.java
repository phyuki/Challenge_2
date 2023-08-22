package br.com.compass.challenge2.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssessmentRecord(@NotBlank String activityName, @NotNull Float grade,
                               @NotNull Long studentID) {
}
