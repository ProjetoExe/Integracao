package ProjectExe.Integracao.config;

import ProjectExe.Integracao.repositorios.UsuarioRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SegurancaFiltro extends OncePerRequestFilter {

    @Autowired
    TokenServico tokenServico;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    //Configuração do filtro interno antes de chamar o filtro da classe na SecurityConfig
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            var token = this.retornarToken(request);
            if (token != null) {
                var conexao = tokenServico.validarToken(token);
                UserDetails usuario = usuarioRepositorio.findByLogin(conexao);

                if (usuario != null) {
                    var autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    //Retorna o token padrão substituindo Bearer para pegar somente o valor do token
    private String retornarToken(HttpServletRequest request){
        var cabecalhoAutenticacao = request.getHeader("Authorization");
        if (cabecalhoAutenticacao == null || !cabecalhoAutenticacao.startsWith("Bearer ")) {
            return null;
        }
        return cabecalhoAutenticacao.replace("Bearer ", "");
    }
}
