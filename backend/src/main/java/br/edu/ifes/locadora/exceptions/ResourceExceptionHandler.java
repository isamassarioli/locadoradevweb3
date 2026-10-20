package br.edu.ifes.locadora.exceptions;

import java.time.Instant;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Intercepta as excecoes disparadas pelos controllers e devolve um ErroPadrao
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(AtorNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> atorNaoEncontradoHandle(AtorNaoEncontradoException e, HttpServletRequest req) {
        return montarErro(HttpStatus.NOT_FOUND, "Resource not found", e.getMessage(), req);
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> clienteNaoEncontradoHandle(ClienteNaoEncontradoException e, HttpServletRequest req) {
        return montarErro(HttpStatus.NOT_FOUND, "Resource not found", e.getMessage(), req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> validacaoHandle(MethodArgumentNotValidException e, HttpServletRequest req) {
        String mensagem = e.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.joining(" "));
        return montarErro(HttpStatus.BAD_REQUEST, "Validation error", mensagem, req);
    }

    private ResponseEntity<ErroPadrao> montarErro(HttpStatus status, String error, String message, HttpServletRequest req) {
        ErroPadrao err = new ErroPadrao();
        err.setError(error);
        err.setMessage(message);
        err.setPath(req.getRequestURI());
        err.setStatus(status.value());
        err.setTimestamp(Instant.now());
        return ResponseEntity.status(status).body(err);
    }
}
