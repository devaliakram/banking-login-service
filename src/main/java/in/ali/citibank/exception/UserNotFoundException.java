package in.ali.citibank.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3066238640446101843L;

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

}
