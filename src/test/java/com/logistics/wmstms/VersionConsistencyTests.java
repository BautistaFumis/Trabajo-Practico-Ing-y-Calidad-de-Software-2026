package com.logistics.wmstms;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.junit.jupiter.api.Test;

class VersionConsistencyTests {

    @Test
    void localAndApplicationVersionsMustMatch() throws IOException {
        Properties localConfiguration = loadFromFile(Path.of("config.properties"));
        Properties applicationConfiguration = loadFromClasspath("application.properties");

        assertThat(localConfiguration.getProperty("app.version"))
            .as("La version local debe coincidir con la version publicada por la aplicacion")
            .isEqualTo(applicationConfiguration.getProperty("app.version"));
    }

    private Properties loadFromFile(Path path) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(path)) {
            properties.load(input);
        }
        return properties;
    }

    private Properties loadFromClasspath(String resourceName) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            assertThat(input).as("Debe existir el recurso %s", resourceName).isNotNull();
            properties.load(input);
        }
        return properties;
    }
}
