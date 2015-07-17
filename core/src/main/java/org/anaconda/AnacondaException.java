package org.anaconda;

public class AnacondaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -217705911898602719L;
	
	public AnacondaException() {
		super();
	}
	
	public AnacondaException(String message) {
		super(message);
	}
	
	public AnacondaException(String message, Throwable t) {
		super(message, t);
	}

}
