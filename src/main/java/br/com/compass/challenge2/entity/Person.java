package br.com.compass.challenge2.entity;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {
    private Long id;
    private String name;
    private String email;
}
