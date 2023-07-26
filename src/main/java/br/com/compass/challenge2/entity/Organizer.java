package br.com.compass.challenge2.entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organizer {
    private List<Group> groups;
    private List<Profile> profiles;
}
