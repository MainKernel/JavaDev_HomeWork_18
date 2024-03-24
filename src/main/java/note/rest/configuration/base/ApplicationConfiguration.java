package note.rest.configuration.base;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("note.rest")
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

}
