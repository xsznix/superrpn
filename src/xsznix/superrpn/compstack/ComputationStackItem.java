package xsznix.superrpn.compstack;

import org.apfloat.Apfloat;

import xsznix.superrpn.util.Constant;
import xsznix.superrpn.util.Maths;

/**
 * An item in the computation stack. The item is stored both as a float and a
 * string so that it is possible to enter in digits in decimal without any loss
 * of precision.
 * @author Xuming Zeng
 *
 */
public class ComputationStackItem {

	/**
	 * True if this stack item is currently being stored as a string.
	 * False if this stack item is currently being stored as a float.
	 */
	private boolean isStr;
	
	/**
	 * The float representation of the value of this item.
	 */
	private Apfloat value;
	
	/**
	 * The string representation of the value of this item.
	 */
	private String valStr;
	
	/* CONSTRUCTORS */
	
	/**
	 * Constructs a new blank stack item.<br>
	 * 
	 * Postcondition: <code>value</code> has been initialized to zero and
	 * <code>valStr</code> has been initialized to an empty string.
	 */
	public ComputationStackItem() {
		value = Apfloat.ZERO.precision(Constant.PRECISION);
	}
	
	/**
	 * Constructs a new stack item with an initial float value.<br>
	 * @param val the value of this stack item
	 */
	public ComputationStackItem(Apfloat val) {
		value = (val.precision() == Constant.PRECISION ?
				val : val.precision(Constant.PRECISION));
	}
	
	/**
	 * Constructs a new stack item with an initial string value.<br>
	 * @param str the string value of this stack item
	 */
	public ComputationStackItem(String str) {
		valStr = str;
		isStr = true;
	}
	
	/* GET/SET */
	
	/**
	 * Whether the stack item is being stored primarily as a float or a string
	 * @return <code>true</code> if the current stack item is being stored
	 * as a string primarily
	 */
	public boolean storedAsString() { return isStr; }
	
	/**
	 * Gets the value of the stack item as a float
	 * @return the value
	 */
	public Apfloat getValue() {
		if (isStr)
			return toFloat();
		else
			return value;
	}
	
	/**
	 * Sets the value of the stack item
	 * @param val the value of the stack item to set to
	 */
	public void setValue(Apfloat val) {
		isStr = false;
		value = (val.precision() == Constant.PRECISION ?
				val : val.precision(Constant.PRECISION));
		valStr = null;
	}
	
	/**
	 * Gets the value of the stack item as a string
	 * @return the value
	 */
	public String getValStr(int precision) {
		if (isStr)
			return valStr;
		else
			return toPrettyString(precision);
	}
	
	/**
	 * Sets the value of the stack item
	 * @param str the value of the stack item to set to
	 */
	public void setValue(String str) {
		isStr = true;
		valStr = str;
		value = null;
	}
	
	/**
	 * Appends a character to the end of the value string.<br>
	 * The character is only appended if it creates a valid number afterwards. 
	 * @param c the character to append
	 * @return <code>true</code> if the operation was successful
	 */
	public boolean strAppend(char c, int precision) {
		// check if the character can be appended
		if (!(
				// numbers are valid
				Character.isDigit(c) ||
				// decimal point is valid only once in a number
				(c == '.' && valStr.indexOf('.') == -1) ||
				// 'e' is also valid only once in a number
				(c == 'e' && valStr.indexOf('e') == -1)
				))
			return false;
		
		// append the character, setting store mode if necessary
		// FIXME: sometimes leading zero is not handled properly
		if (isStr) {
			if (valStr == "0")
				valStr = "" + c;
			else
				valStr += c;
		}
		else {
			String oldStr = toPrettyString(precision);
			if (oldStr == "0") {
				valStr = "" + c;
			}
			else
				valStr = oldStr + c;
			isStr = true;
			value = null;
		}
		
		return true;
	}
	
	public String toString() {
		return toPrettyString(Constant.INIT_DISP_PRECISION);
	}
	
	/**
	 * Prints a pretty string from the float representation of this stack item.
	 * @return the formatted string
	 */
	private String toPrettyString(int precision) {
		return Maths.toPrettyString(value, precision);
	}
	
	/**
	 * Finds the value of the string representation of this stack item,
	 * removing unnecessary characters at the end of the string if necessary.
	 * @return the float value
	 */
	private Apfloat toFloat() {
		String str;
		char lastChar = valStr.charAt(valStr.length() - 1); 
		if ((lastChar == 'e') || (lastChar == '.') || (lastChar == '-'))
			str = valStr.substring(0, valStr.length() - 1);
		else
			str = valStr;
		
		return new Apfloat(str, Constant.PRECISION);
	}

}
