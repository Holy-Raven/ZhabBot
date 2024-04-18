package ru.mkhamkha.ZhabBot.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follower", schema = "zhab")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follower {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private  String name;

    @Column(name = "family")
    private String family;

    @Column(name = "create_time")
    private LocalDateTime create;

    @PrePersist
    void prePersist() {
        if (this.create == null) {
            this.create = LocalDateTime.now();
        }
    }
}
