package ru.mkhamkha.ZhabBot.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "news", schema = "zhab")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "create_time")
    private LocalDateTime time;

    @PrePersist
    void prePersist() {
        if (this.time == null) {
            this.time = LocalDateTime.now();
        }
    }
}
