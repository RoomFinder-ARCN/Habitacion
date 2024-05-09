package arcn.roomfinder.habitacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Swagger {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
               .info(new Info()
                    .title("RoomFinder API")
                    .version("1.0.0")
                    .description("Habitacion API"));
    }
    
}
