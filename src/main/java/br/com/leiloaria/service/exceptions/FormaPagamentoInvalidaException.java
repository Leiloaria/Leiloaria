package br.com.leiloaria.service.exceptions;

public class FormaPagamentoInvalidaException extends RuntimeException {
    public FormaPagamentoInvalidaException(String message) {
        super(message);
    }
}
