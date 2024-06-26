package ru.mkhamkha.ZhabBot.service.buisness;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.model.entity.Concert;
import ru.mkhamkha.ZhabBot.service.ConcertService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.mkhamkha.ZhabBot.util.Constants.Formatter.DATE_TIME_FORMAT;


@Service
@RequiredArgsConstructor
public class AnswerConcertService {

    private final ConcertService service;
    private final EntityManager entityManager;

    public List<Concert> upcomingConcerts() {

        LocalDate date = LocalDate.now();

        String query = "SELECT * FROM zhab.concerts " +
                       "WHERE " +
                       "    (end_time IS NOT NULL AND end_time >= :date) OR " +
                       "    (end_time IS NULL AND start_time >= :date) " +
                       "ORDER BY start_time DESC";
        Query nativeQuery = entityManager.createNativeQuery(query, Concert.class);
        nativeQuery.setParameter("date", date);

        return nativeQuery.getResultList();
    }

    public String printConcert(Concert concert) {

        StringBuilder builder = new StringBuilder();

        builder.append(concert.getTitle());
        builder.append(" - (").append(concert.getStart().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));

        if (concert.getEnd() != null) {
            builder.append("-").append(concert.getEnd().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))).append(")");
        } else {
            builder.append(")");
        }
        builder.append("\n");

        builder.append(concert.getDescription()).append("\n");

        builder.append("Место проведения: ");
        if (concert.getPlace() != null) {
            builder.append("\n");
            builder.append(concert.getPlace().getName());
            builder.append(" (").append(concert.getPlace().getCity()).append(")");
        } else {
            builder.append(" Уточняется");
        }
        builder.append("\n");

        builder.append("Состав участников: ");
        if (concert.getBands() == null || concert.getBands().isEmpty()) {
            builder.append(" Будет известен позднее");
        } else {
            builder.append("\n");
            concert.getBands().forEach(band -> {
                builder.append("->");
                builder.append("  ").append(band.getName());
                builder.append(" (").append(band.getGenre()).append(" г. ").append(band.getCity()).append(")\n");
            });
        }
        builder.append("\n");

        if (concert.getLink() != null) {
            builder.append("Ссылка на встречу мероприятия:").append("\n");
            builder.append(concert.getLink());
            builder.append("\n");
        }

        return builder.toString();
    }
}
