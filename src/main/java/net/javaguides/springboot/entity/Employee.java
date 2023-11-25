package net.javaguides.springboot.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
