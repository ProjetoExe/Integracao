package ProjectExe.Integracao.controladores.excecoes;

import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ManipuladorExcecaoRecurso {

    @ExceptionHandler(ExcecaoRecursoNaoEncontrado.class)
    public ResponseEntity<ErroPadrao> recursoNaoEncontrado(ExcecaoRecursoNaoEncontrado e, HttpServletRequest request){
        String erro = "Recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroPadrao erroPadrao = new ErroPadrao(Instant.now(), status.value(), erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroPadrao);
    }
}
