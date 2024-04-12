package ru.mkhamkha.ZhabBot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users", schema = "zhab")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private  String name;

    @Column(name = "family")
    private String family;

    @Column(name = "create_time")
    private LocalDateTime create;

}
