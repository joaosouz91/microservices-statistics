package br.com.fiap.joaovictor.microservicesstatistics.exception;

import static br.com.fiap.joaovictor.microservicesstatistics.exception.Exceptions.toMap;

public class SisxtySecondsReachedException extends Exception{
	
	static final long serialVersionUID = -4760399099703932420L;

	public SisxtySecondsReachedException(String... params) {
        super("The transaction request exceeded the 60 seconds limit to reach the server." + toMap(String.class, String.class, params));
    }

}
