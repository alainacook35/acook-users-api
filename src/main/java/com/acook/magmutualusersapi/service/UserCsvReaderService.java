package com.acook.magmutualusersapi.service;

import com.acook.magmutualusersapi.entity.CsvUser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserCsvReaderService {
    private final String filePath;

    public UserCsvReaderService(String filePath) {
        this.filePath = filePath;
    }

    public List<CsvUser> readUsers() throws IOException {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();

        CsvMapper mapper = new CsvMapper();
        mapper.registerModule(new JavaTimeModule());
        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        MappingIterator<CsvUser> iterator = mapper
                .readerFor(CsvUser.class)
                .with(schema)
                .readValues(inputStream);

        return iterator.readAll();
    }
}
