package com.wandera.hw.notificationcenter.user.infrastructure;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wandera.hw.notificationcenter.user.infrastructure.exception.CSVParseException;
import com.wandera.hw.notificationcenter.user.infrastructure.model.NotificationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationCSVLoader {


    /**
     * Read csv file and map it to the NotificationEntity objects.
     * The path to the CSV file is defined in application properties data -> filepath
     *
     * @return Mapped list of NotificationEntity objects linked to their userID.
     */
    public Map<String, List<NotificationEntity>> loadNotifications(String path) {
        var file = new File(path);
        Map<String, List<NotificationEntity>> notifications = new HashMap<>();

        var entities = readFile(file);

        entities.forEach(notification -> {
            var userId = notification.getUserId();
            var oldEntry = notifications.getOrDefault(userId, new ArrayList<>());
            oldEntry.add(notification);
            notifications.put(userId, oldEntry);
        });

        return notifications;
    }

    /*
      The CSV mapper and schema are local variables as there is no use for them elsewhere.
      If the CSV parsing fail there is no need for recovery as it is crucial step for the application.
     */
    private List<NotificationEntity> readFile(File csvFile) {
        var csvObjectMapper = CsvMapper.builder()
                .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .addModule(new JavaTimeModule())
                .build();

        CsvSchema csvSchema = csvObjectMapper
                .typedSchemaFor(NotificationEntity.class)
                .withColumnSeparator(';');

        try (MappingIterator<NotificationEntity> notificationIterator = csvObjectMapper
                .readerFor(NotificationEntity.class)
                .with(csvSchema)
                .readValues(csvFile)) {

            return notificationIterator.readAll();
        } catch (IOException e) {
            throw new CSVParseException("Failed to load data from CSV", e);
        }

    }
}
