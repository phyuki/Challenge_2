package br.com.compass.challenge2.records;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record StudentRecord(@NotBlank String name, @NotBlank String email) {
}
