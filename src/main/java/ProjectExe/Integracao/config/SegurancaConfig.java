package ProjectExe.Integracao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SegurancaConfig {

    @Autowired
    SegurancaFiltro segurancaFiltro;

    //Filtros de segurança as URLs
    @Bean
    SecurityFilterChain cadeiaFiltrosSeguranca(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/registro").permitAll()
                        //swagger
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/v3/api-docs/**").permitAll()
                        //lojas
                        .requestMatchers(HttpMethod.GET, "/lojas/**").hasRole("DEV")
                        .requestMatchers(HttpMethod.POST, "/lojas/**").hasRole("DEV")
                        .requestMatchers(HttpMethod.PUT, "/lojas/**").hasRole("DEV")
                        .requestMatchers(HttpMethod.DELETE, "/lojas/**").hasRole("DEV")
                        //produtos
                        .requestMatchers(HttpMethod.GET, "/produtos/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/produtos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN")
                        //vendas
                        .requestMatchers(HttpMethod.GET, "/vendas/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/vendas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/vendas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/vendas/**").hasRole("ADMIN")
                        //categorias
                        .requestMatchers(HttpMethod.GET, "/categorias/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("ADMIN")
                        //marcas
                        .requestMatchers(HttpMethod.GET, "/marcas/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/marcas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/marcas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/marcas/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(segurancaFiltro, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //Configuração de autenticação para pegar a instância do método usado na AuthenticationController
    @Bean
    public AuthenticationManager gerenciadorAutenticacao(AuthenticationConfiguration configuracaoAutenticacao) throws Exception {
        return configuracaoAutenticacao.getAuthenticationManager();
    }

    //Faz a criptografia da senha para salvá-la
    @Bean
    public PasswordEncoder criptografarSenha(){ return new BCryptPasswordEncoder(); }
}
