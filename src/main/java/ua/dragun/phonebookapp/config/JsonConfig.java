package ua.dragun.phonebookapp.config;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Profile("json")
@Configuration
@PropertySource(value = {"classpath:application.properties"})
public class JsonConfig {

    @Value("${json.path}")
    private String jsonPath;

    @Bean(name = "jsonPropertiesFile")
    @Scope("singleton")
    public File jsonPropertiesFile() throws IOException {
        File file = new File(jsonPath);
        boolean fileExist = file.exists();
        if (!fileExist) {
            fileExist = file.createNewFile();
            return file;
        } else {
            return file;
        }
    }
}