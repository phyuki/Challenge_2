package br.com.compass.challenge2.records;

import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Student;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public record GroupRecord(@NotBlank String name, List<Long> studentIDs, List<Long> organizerIDs) {
}
