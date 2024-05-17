package ru.mkhamkha.ZhabBot.omponent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Component
@Slf4j
public class LogCollector {

    // Параметры подключения к базе данных
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public void collectAndSendLog(Map<String, String> logData) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO storage.logs (key, value) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Заполнение параметров для вставки в базу данных
            for (Map.Entry<String, String> entry : logData.entrySet()) {
                statement.setString(1, entry.getKey());
                statement.setString(2, entry.getValue());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            log.error("Ошибка при отправке логов в базу данных: {}", e.getMessage());
        }
    }
}