package br.com.compass.challenge2.records;

import jakarta.persistence.Embeddable;

public record StudentRecord(Long id, String name, String email) {
}
