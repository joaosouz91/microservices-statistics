package br.com.fiap.joaovictor.microservicesstatistics.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {

    private HttpStatus httpStatus;
    private String msg;

    public Error(String msg, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

}
