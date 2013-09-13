package xsznix.superrpn.err;

/**
 * An exception that is thrown when an operation goes wrong. Used just like any
 * other Java exception.
 * @author Xuming Zeng
 *
 */
public class OperationException extends Exception {

	private static final long serialVersionUID = 411320757514845861L;
	
	public OperationException() { super(); }
	
	public OperationException(String message) { super(message); }
	
	public OperationException(String message, Throwable cause) { super(message, cause); }
	
	public OperationException(Throwable cause) { super(cause); }

}
