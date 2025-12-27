package br.com.leiloaria.service.exceptions;

public class AtualizarLeilaoInvalidoException  extends RuntimeException{
    public AtualizarLeilaoInvalidoException(String message) {
        super(message);
    }
}
