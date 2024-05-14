package ru.mkhamkha.ZhabBot.repository.composkey;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "concert_band", schema = "zhab")
@IdClass(ConcertBandId.class)
public class ConcertBand implements Serializable {

    @Id
    @Column(name = "concert_id")
    private Long concertId;

    @Id
    @Column(name = "band_id")
    private Long bandId;
}