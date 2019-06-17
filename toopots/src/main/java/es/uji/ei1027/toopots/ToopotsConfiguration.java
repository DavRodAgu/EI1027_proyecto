package es.uji.ei1027.toopots;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import nz.net.ultraq.thymeleaf.LayoutDialect;


import javax.sql.DataSource;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Configuration
public class ToopotsConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

/*	@Bean
	public Formatter<LocalTime> localTimeFormatter() {
		return new Formatter<LocalTime>() {
			@Override
			public String print(LocalTime localTime, Locale locale) {
				return localTime.format(DateTimeFormatter.ofPattern("HH:mm", locale));
			}

			@Override
			public LocalTime parse(String s, Locale locale) throws ParseException {
				try {
					return LocalTime.parse(s, DateTimeFormatter.ofPattern("HH:mm", locale));
				} catch (DateTimeParseException ex) {
					throw new ParseException(ex.getMessage(), ex.getErrorIndex());
				}
			}
		};
	}*/

}