package cz.wicketstuff.boss.flow.processor;

import cz.wicketstuff.boss.flow.FlowException;

/**
 * Exception is thrown when flow cannot be persisted.
 * The common cause is a JDBC runtime exception.
 * 
 * @author Martin Strejc
 *
 */
public class FlowPersistingException extends FlowException {

	private static final long serialVersionUID = 1L;

	public FlowPersistingException() {
		super();
	}

	public FlowPersistingException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FlowPersistingException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlowPersistingException(String message) {
		super(message);
	}

	public FlowPersistingException(Throwable cause) {
		super(cause);
	}

}
