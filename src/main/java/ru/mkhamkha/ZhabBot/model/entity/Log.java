package ru.mkhamkha.ZhabBot.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs", schema = "zhab")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "level")
    private String level;

    @Column(name = "logger")
    private String logger;

    @Column(name = "message")
    private String message;

    @Column(name = "create_time")
    private LocalDateTime create;

}