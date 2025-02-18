package se.hedsec.webscraperspring;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Or "/**" for all endpoints
                .allowedOrigins("*") // Add your frontend origins
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify allowed methods
                .allowedHeaders("*"); // Or specify allowed headers
    }
}
