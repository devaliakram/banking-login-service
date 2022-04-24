package in.ali.citibank.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.ali.citibank.exception.UserNotFoundException;

@RestControllerAdvice
public class ValidationApi {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException es) {
		Map<String, String> errorMap = new HashMap<>();
		es.getBindingResult().getAllErrors().forEach(error -> {
			errorMap.put(error.getCode(), error.getDefaultMessage());
		});
		return errorMap;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(UserNotFoundException.class)
	public Map<String, String> handleUserNotFooundException(UserNotFoundException exception) {
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("ex", exception.getMessage());
		return errorMap;
	}
	
	
}
