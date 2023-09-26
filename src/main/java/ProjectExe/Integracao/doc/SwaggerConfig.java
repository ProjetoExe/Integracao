package ProjectExe.Integracao.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customizacaoSwagger(){
        return new OpenAPI()
                .info(new Info()
                        .title("Integração")
                        .description("Desenvolvido por Gustavo Parizzato")
                        .contact(new Contact().name("Gustavo Parizzato").email("projeto.exe.123@gmail.com")
                                ).version("1.0.0")
                );
    }
}
