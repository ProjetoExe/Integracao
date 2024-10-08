package ProjectExe.Integracao.controladores.excecoes;

import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRegistroExistente;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class ManipuladorExcecao {

    //exceção personalizada para recurso não encontrado
    @ExceptionHandler(ExcecaoRecursoNaoEncontrado.class)
    public ResponseEntity<ErroPadrao> recursoNaoEncontrado(ExcecaoRecursoNaoEncontrado e, HttpServletRequest request){
        String erro = "Recurso solicitado não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroPadrao erroPadrao = new ErroPadrao(Instant.now(), status.value(), erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroPadrao);
    }

    //exceção personalizada para erros de banco de dados
    @ExceptionHandler(ExcecaoBancoDeDados.class)
    public ResponseEntity<ErroPadrao> bancoDeDados(ExcecaoBancoDeDados e, HttpServletRequest request){
        String erro = "Erro banco de dados";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroPadrao erroPadrao = new ErroPadrao(Instant.now(), status.value(), erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroPadrao);
    }

    //exceção personalizada para registros já existentes
    @ExceptionHandler(ExcecaoRegistroExistente.class)
    public ResponseEntity<ErroPadrao> registroExistente(ExcecaoRegistroExistente e, HttpServletRequest request){
        String erro = "Cadastro Existente";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroPadrao erroPadrao = new ErroPadrao(Instant.now(), status.value(), erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroPadrao);
    }

    //exceção personalizada para erros de inserção incorretos - Adicionada para tratar exceção que vem do nível entidade
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroPadrao> dadosIncorretos(IllegalArgumentException e, HttpServletRequest request){
        String erro = "Dados de Inserção Incorretos";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroPadrao erroPadrao = new ErroPadrao(Instant.now(), status.value(), erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroPadrao);
    }

    //execeção personalizada para erros de exportação de arquivo
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErroPadrao> erroExportarArquivo(IOException e, HttpServletRequest request){
        String erro = "Erro ao Exportar Arquivo";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroPadrao erroPadrao = new ErroPadrao(Instant.now(), status.value(), erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroPadrao);
    }

    //exceção personalizada para erros de inserção de dados - Exceção lançada automaticamente pelo Spring, foi apenas tratada o retorno
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> argumentoDeEntradaInvalido(MethodArgumentNotValidException e, HttpServletRequest request){
        String erro = "Dados de cadastro incorreto";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        //a exceção MethodArgumentNotValidException preenche o BindResult como padrão, permitindo buscar erros e tratá-los de forma personalizada
        BindingResult bindingResult = e.getBindingResult();
        List<String> erros = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

        ErroPadrao erroPadrao = new ErroPadrao(Instant.now(), status.value(), erro, erros.toString(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroPadrao);
    }
}