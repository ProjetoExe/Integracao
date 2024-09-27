package ProjectExe.Integracao.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.ServletContext;

@Configuration
public class CookieConfig {

    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) {
                SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
                sessionCookieConfig.setMaxAge(3600); // 60 minutos = 1 hora
                sessionCookieConfig.setHttpOnly(true);
                sessionCookieConfig.setSecure(true);
                sessionCookieConfig.setName("JINTEGRACAOID");
            }
        };
    }
}