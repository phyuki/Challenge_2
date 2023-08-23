package br.com.compass.challenge2.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record WebinarRecord(@NotBlank String name, @NotNull Date datetime) {
}
