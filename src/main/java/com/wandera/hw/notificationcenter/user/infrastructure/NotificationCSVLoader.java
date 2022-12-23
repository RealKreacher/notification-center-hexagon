package com.wandera.hw.notificationcenter.user.infrastructure;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wandera.hw.notificationcenter.user.infrastructure.exception.CSVParseException;
import com.wandera.hw.notificationcenter.user.infrastructure.model.NotificationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationCSVLoader {

    @Value("${data.filepath}")
    private String path;

    /**
     * Read csv file and map it to the NotificationEntity objects.
     * The path to the CSV file is defined in application properties data -> filepath
     * @return Mapped list of NotificationEntity objects.
     */
    public List<NotificationEntity> loadNotifications() {
        var file = new File(path);
        return readFile(file);
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
