package cz.wicketstuff.boss.flow.processor;

import cz.wicketstuff.boss.flow.FlowException;

/**
 * Exception is thrown when flow cannot be restored.
 * The common cause is a JDBC runtime exception.
 * 
 * @author Martin Strejc
 *
 */
public class FlowRestoringException extends FlowException {

	private static final long serialVersionUID = 1L;

	public FlowRestoringException() {
		super();
	}

	public FlowRestoringException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FlowRestoringException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlowRestoringException(String message) {
		super(message);
	}

	public FlowRestoringException(Throwable cause) {
		super(cause);
	}

}
