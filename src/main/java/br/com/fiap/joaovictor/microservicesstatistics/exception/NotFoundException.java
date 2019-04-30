package br.com.fiap.joaovictor.microservicesstatistics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5933685185608330868L;

	public NotFoundException(String message) {
        super(message);
    }
}
