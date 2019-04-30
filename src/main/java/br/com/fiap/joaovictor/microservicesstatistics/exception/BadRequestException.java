package br.com.fiap.joaovictor.microservicesstatistics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestException {
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingParams(MissingServletRequestParameterException ex) {
        String param = ex.getParameterName();
        return new ResponseEntity<Error>(new Error("O parâmetro " + param + "não foi definido", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

}
