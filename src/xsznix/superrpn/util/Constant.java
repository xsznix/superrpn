package xsznix.superrpn.util;

/**
 * Defines some internal constants in SuperRPN so that they can be easily
 * changed afterwards if necessary.
 * @author Xuming Zeng
 *
 */
public interface Constant {

	/**
	 * the internal precision of stored floats
	 */
	public static final int PRECISION = 36;
	
	/**
	 * the initial displayed precision
	 */
	public static final int INIT_DISP_PRECISION = 30;
	
	/**
	 * the initial length of the array containing the computation stack
	 */
	public static final int INIT_COMPSTACK_LENGTH = 10;

}
