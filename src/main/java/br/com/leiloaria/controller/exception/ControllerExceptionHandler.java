
package br.com.leiloaria.controller.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;



@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
	public ResponseEntity<StandardError> RuntimeException(RuntimeException e,
			HttpServletRequest request) {
		int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();

		StandardError err = new StandardError(
                httpStatus, "Erro interno", e.getMessage(),
                request.getRequestURI());
		e.printStackTrace();
		return ResponseEntity.status(httpStatus).body(err);
	}

    @ExceptionHandler({RecursoNaoEncontradoException.class})
	public ResponseEntity<StandardError> ObjectNotFoundException(RuntimeException e,
			HttpServletRequest request) {
		int httpStatus = HttpStatus.NOT_FOUND.value();
		StandardError err = new StandardError(
                httpStatus, "Não Encontrado", e.getMessage(),
                request.getRequestURI());
		return ResponseEntity.status(httpStatus).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		int httpStatus = HttpStatus.BAD_REQUEST.value();
		ValidationError err = new ValidationError(
                httpStatus, "Erro interno", e.getMessage(),
                request.getRequestURI());
		System.out.println(e.getBindingResult().getFieldErrors());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(httpStatus).body(err);
	}

}