package com.acook.magmutualusersapi.service;

import com.acook.magmutualusersapi.entity.User;
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

    public List<User> readUsers() throws IOException {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();

        CsvMapper mapper = new CsvMapper();
        mapper.registerModule(new JavaTimeModule());
        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        MappingIterator<User> iterator = mapper
                .readerFor(User.class)
                .with(schema)
                .readValues(inputStream);

        return iterator.readAll();
    }
}
