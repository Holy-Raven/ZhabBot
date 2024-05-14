package ru.mkhamkha.ZhabBot.model.mapper;

import org.springframework.stereotype.Component;
import ru.mkhamkha.ZhabBot.model.dto.BandDTO;
import ru.mkhamkha.ZhabBot.model.dto.ConcertDTO;
import ru.mkhamkha.ZhabBot.model.dto.PlaceDTO;
import ru.mkhamkha.ZhabBot.model.entity.Band;
import ru.mkhamkha.ZhabBot.model.entity.Concert;
import ru.mkhamkha.ZhabBot.model.entity.Place;
import ru.mkhamkha.ZhabBot.util.exception.exception.NotFoundException;

import java.util.stream.Collectors;

@Component
public class ConcertMapper {

    public static Concert toEntity(ConcertDTO concertDTO) {

        Concert concert = Concert.builder()
                .id(concertDTO.getId())
                .title(concertDTO.getTitle())
                .description(concertDTO.getDescription())
                .price(concertDTO.getPrice())
                .date(concertDTO.getDate())
                .link(concertDTO.getLink())
                .bands(concertDTO.getBandDTO().stream().map(BandMapper::toEntity).collect(Collectors.toList()))
                .build();

        if (concertDTO.getPlaceDTO() != null) {
            concert.setPlace(PlaceMapper.toEntity(concertDTO.getPlaceDTO()));
        } else {
            throw new NotFoundException(PlaceDTO.class, "Значение PlaceDTO не может быть null");
        }

        if (concertDTO.getBandDTO() != null) {
            concert.setBands(concertDTO.getBandDTO().stream().map(BandMapper::toEntity).collect(Collectors.toList()));
        } else {
            throw new NotFoundException(BandDTO.class, "Значение BandDTO не может быть null");
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
                .date(concert.getDate())
                .link(concert.getLink())
                .bandDTO(concert.getBands().stream().map(BandMapper::toDTO).collect(Collectors.toList()))
                .build();

        if (concert.getPlace() != null) {
            concertDTO.setPlaceDTO(PlaceMapper.toDTO(concert.getPlace()));
        } else {
            throw new NotFoundException(Place.class, "Значение Place не может быть null");
        }

        if (concert.getBands() != null) {
            concertDTO.setBandDTO(concert.getBands().stream().map(BandMapper::toDTO).collect(Collectors.toList()));
        } else {
            throw new NotFoundException(Band.class, "Значение Band не может быть null");
        }

        return concertDTO;
    }
}