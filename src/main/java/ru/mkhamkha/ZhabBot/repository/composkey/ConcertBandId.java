package ru.mkhamkha.ZhabBot.repository.composkey;


import lombok.Data;

import java.io.Serializable;

@Data
public class ConcertBandId implements Serializable {

    private Long concertId;
    private Long bandId;
}