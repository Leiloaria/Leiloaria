package br.com.leiloaria.service.exceptions;

public class GerarVendaInvalidaException extends RuntimeException  {
    public GerarVendaInvalidaException(String message) {
        super(message);
    }
}
