package com.wandera.hw.notificationcenter.user.infrastructure;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wandera.hw.notificationcenter.user.infrastructure.model.NotificationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ObjectMapper jacksonObjectMapper;

    public List<NotificationEntity> loadNotifications() {
        var file = new File(path);
        return readFile(file);
    }

    public List<NotificationEntity> readFile(File csvFile) {

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.registerModule(new JavaTimeModule());
        csvMapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);

        CsvSchema csvSchema = csvMapper
                .typedSchemaFor(NotificationEntity.class)
                .withColumnSeparator(';');


        try (MappingIterator<NotificationEntity> notificationIterator = csvMapper
                .readerFor(NotificationEntity.class)
                .with(csvSchema)
                .readValues(csvFile)) {

            return notificationIterator.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
