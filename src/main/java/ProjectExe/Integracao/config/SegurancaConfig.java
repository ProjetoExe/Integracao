package ProjectExe.Integracao.config;

import ProjectExe.Integracao.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    //Filtros de segurança as URLs --STATEFUL (sessão) para usuários USER, ADMIN e DEV, STATELESS para usuários PROD
    @Bean
    SecurityFilterChain cadeiaFiltrosSeguranca(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/auth/login") // Redireciona para a página de login após expiração da sessão
                        .maximumSessions(1)
                        .expiredUrl("/auth/login")) //Redireciona para o login após a sessão expirar
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
                        .requestMatchers(HttpMethod.DELETE, "/vendas/**").hasRole("DEV")
                        //classes
                        .requestMatchers(HttpMethod.GET, "/classes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/classes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/classes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/classes/**").hasRole("ADMIN")
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
                        //cupons
                        .requestMatchers(HttpMethod.GET, "/cupons/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/cupons/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/cupons/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cupons/**").hasRole("ADMIN")
                        //promoções
                        .requestMatchers(HttpMethod.GET, "/promocoes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/promocoes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/promocoes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/promocoes/**").hasRole("ADMIN")
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
    public PasswordEncoder criptografarSenha() { return new BCryptPasswordEncoder(); }
}