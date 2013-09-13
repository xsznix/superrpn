package xsznix.superrpn.util;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.Apint;

import xsznix.superrpn.err.OperationException;


/**
 * Additional mathematical functions required for some operations.
 * @author Xuming Zeng
 *
 */
public class Maths {
	/** The value of pi to the required internal precision */
	public static final Apfloat PI = ApfloatMath.pi(Constant.PRECISION);
	
	/** The value of e to the required internal precision */
	public static final Apfloat E
			= ApfloatMath.exp(Apfloat.ONE.precision(Constant.PRECISION));
	
	/** To convert from radians to degrees */
	public static final Apfloat R_TO_D
			= (new Apfloat(180)).divide(PI);
	
	/** To convert from degrees to radians */
	public static final Apfloat D_TO_R
			= PI.divide(new Apfloat(180));
	
	/** Converts an angle from radians to degrees. */
	public static Apfloat radToDeg(Apfloat rad) {
		return rad.multiply(R_TO_D);
	}
	
	/** Converts an angle from degrees to radians */
	public static Apfloat degToRad(Apfloat deg) {
		return deg.multiply(D_TO_R);
	}
	
	/**
	 * Finds and returns a k-combination.<br>
	 * Formula: nPr(n,k) = n(n-1)(n-2)...(n-k+1)/k(k-1)(k-2)...1
	 * @param n the number of items in the set
	 * @param k the number of items to choose
	 * @return the k-combination
	 * @throws OperationException when something goes wrong
	 */
	public static Apint nCr(Apint n, Apint k) throws OperationException {
		Apint numerator = nPr(n, k);
		
		Apint denominator = k, i = k.subtract(Apint.ONE);
		while (i.signum() == 1) {
			denominator = denominator.multiply(i);
			i = i.subtract(Apint.ONE);
		}
		
		return numerator.divide(denominator);
	}
	
	/**
	 * Finds and returns a permutation.<br>
	 * Formula: nCr(n,k) = n(n-1)(n-2)...(n-k+1)
	 * @param n the number of items in the set
	 * @param k the number of items to choose and permute
	 * @return the permutation
	 * @throws OperationException when something goes wrong
	 */
	public static Apint nPr(Apint n, Apint k) throws OperationException {
		// sanity check
		if (n.signum() != 1 || k.signum() != 1)
			throw new OperationException(
				"Cannot take combination/permutation with negative numbers.");
		if (n.compareTo(k) == 0)
			return Apint.ONE;
		else if (n.compareTo(k) < 0)
			return Apint.ZERO;
		
		Apint result = n, i = n.subtract(Apint.ONE);
		
		// multiply
		while (i.compareTo(k) >= 0) {
			result = result.multiply(i);
			i = i.subtract(Apint.ONE);
		}
		
		return result;
	}
	
	/**
	 * Prints an <code>Apfloat</code> in nice format.
	 * @param a the number to print
	 * @param precision the number of digits to print to
	 * @return the formatted string representation of the number
	 */
	public static String toPrettyString(Apfloat a, long precision) {
		if (a == null) {
			return "";
		}
		
		long scale = a.scale();
		
		StringBuilder format = new StringBuilder().append('%');
		
		// only use scientific notation if necessary
		if (Math.abs(scale) < 6)
			format = format.append("#");
		
		format = format.append('.');
		
		// set precision cap
		format = format.append(precision)
		
		// finish the string
		.append('s');
		
		// FIXME: sometimes floating-point roundoff is not properly handled
		return String.format(format.toString(), a);
	}

}
