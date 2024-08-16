package vn.phuocloc.jobhunter.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // cho phep cac url ket noi toi backend
        corsConfiguration.setAllowedHeaders(
                Arrays.asList("http://localhost:3000", "http://localhost:4173", "http://localhost:5173"));
        // cac method nao duoc ket noi
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS")); // allowed method
        // cac phan header duoc phep gui len
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "x-no-retry"));
        // gui kem cookie hay khong
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // apply this configuration
        return source;
    }

}
