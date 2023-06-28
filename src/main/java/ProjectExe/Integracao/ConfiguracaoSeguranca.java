package ProjectExe.Integracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {

    //Seguranças são feitas através de filtros
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        authorize -> {
                            authorize.requestMatchers("/public").permitAll();
                            authorize.requestMatchers("/logout").permitAll();
                            authorize.anyRequest().authenticated();
                        })
                .oauth2Login(Customizer.withDefaults())
                .build();
    }
}
