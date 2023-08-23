package br.com.compass.challenge2.records;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SquadRecord(@NotBlank String squadName, List<Long> studentIDs) {
}
