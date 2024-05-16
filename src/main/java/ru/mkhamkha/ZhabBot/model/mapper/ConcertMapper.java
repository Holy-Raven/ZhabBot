package ru.mkhamkha.ZhabBot.model.mapper;

import org.springframework.stereotype.Component;
import ru.mkhamkha.ZhabBot.model.dto.ConcertDTO;
import ru.mkhamkha.ZhabBot.model.entity.Concert;

import java.util.stream.Collectors;

@Component
public class ConcertMapper {

    public static Concert toEntity(ConcertDTO concertDTO) {

        Concert concert = Concert.builder()
                .id(concertDTO.getId())
                .title(concertDTO.getTitle())
                .description(concertDTO.getDescription())
                .price(concertDTO.getPrice())
                .start(concertDTO.getStart())
                .end(concertDTO.getEnd())
                .link(concertDTO.getLink())
                .bands(concertDTO.getBandDTO().stream().map(BandMapper::toEntity).collect(Collectors.toList()))
                .build();

        if (concertDTO.getPlaceDTO() != null) {
            concert.setPlace(PlaceMapper.toEntity(concertDTO.getPlaceDTO()));
        }

        if (concertDTO.getBandDTO() != null) {
            concert.setBands(concertDTO.getBandDTO().stream().map(BandMapper::toEntity).collect(Collectors.toList()));
        }

        return concert;
    }

    public static ConcertDTO toDTO(Concert concert) {

        ConcertDTO concertDTO = ConcertDTO.builder()
                .id(concert.getId())
                .title(concert.getTitle())
                .placeDTO(PlaceMapper.toDTO(concert.getPlace()))
                .description(concert.getDescription())
                .price(concert.getPrice())
                .start(concert.getStart())
                .end(concert.getEnd())
                .link(concert.getLink())
                .bandDTO(concert.getBands().stream().map(BandMapper::toDTO).collect(Collectors.toList()))
                .build();

        if (concert.getPlace() != null) {
            concertDTO.setPlaceDTO(PlaceMapper.toDTO(concert.getPlace()));
        }

        if (concert.getBands() != null) {
            concertDTO.setBandDTO(concert.getBands().stream().map(BandMapper::toDTO).collect(Collectors.toList()));
        }

        return concertDTO;
    }
}