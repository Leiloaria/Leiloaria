package br.com.leiloaria.controller.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class ValidationError extends StandardError {
	private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String error, String message, String path) {
		super(status, error, message, path);
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}