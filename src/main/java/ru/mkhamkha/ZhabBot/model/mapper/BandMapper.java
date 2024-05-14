package ru.mkhamkha.ZhabBot.model.mapper;

import org.springframework.stereotype.Component;
import ru.mkhamkha.ZhabBot.model.dto.BandDTO;
import ru.mkhamkha.ZhabBot.model.entity.Band;

@Component
public class BandMapper {

    public static Band toEntity(BandDTO bandDTO) {

        return Band.builder()
                .id(bandDTO.getId())
                .name(bandDTO.getName())
                .city(bandDTO.getCity())
                .genre(bandDTO.getGenre())
                .description(bandDTO.getDescription())
                .link(bandDTO.getLink())
                .build();
    }

    public static BandDTO toDTO(Band band) {

        return BandDTO.builder()
                .id(band.getId())
                .name(band.getName())
                .city(band.getCity())
                .description(band.getDescription())
                .genre(band.getGenre())
                .link(band.getLink())
                .build();
    }
}
