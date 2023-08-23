package br.com.compass.challenge2.records;

import jakarta.validation.constraints.NotBlank;

public record OrganizerRecord(@NotBlank String name, @NotBlank String email) {
}
