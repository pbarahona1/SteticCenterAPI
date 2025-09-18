package BuSmart.APIBuSmart.Config.App;


import BuSmart.APIBuSmart.Utils.JWTUtils;
import BuSmart.APIBuSmart.Utils.JwtCookieAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JwtCookieAuthFilter jwtCookieAuthFilter(JWTUtils jwtUtils){
        return new JwtCookieAuthFilter(jwtUtils);
    }
}
