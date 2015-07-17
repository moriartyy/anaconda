package org.anaconda.transport;

import org.anaconda.AnacondaException;

public class TransportException extends AnacondaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2792932426612051230L;
	
	public TransportException() {
		super();
	}
	
	public TransportException(String message) {
		super(message);
	}
	
	public TransportException(String message, Throwable t) {
		super(message, t);
	}

}
