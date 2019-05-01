package br.com.fiap.joaovictor.microservicesstatistics.exception;

import static br.com.fiap.joaovictor.microservicesstatistics.exception.Exceptions.toMap;

public class ServerException extends Exception {

	private static final long serialVersionUID = -9074783500133873622L;

	public ServerException(String... searchParamsMap) {
        super("Internal server error " + toMap(String.class, String.class, searchParamsMap));
    }
}