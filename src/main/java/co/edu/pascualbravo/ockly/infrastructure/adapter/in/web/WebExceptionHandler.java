package co.edu.pascualbravo.ockly.infrastructure.adapter.in.web;

import co.edu.pascualbravo.ockly.domain.exception.DatosInvalidosException;
import co.edu.pascualbravo.ockly.domain.exception.RegistroNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(RegistroNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noEncontrado() { return "error/404"; }
    @ExceptionHandler(DatosInvalidosException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String datosInvalidos() { return "error/400"; }
}
