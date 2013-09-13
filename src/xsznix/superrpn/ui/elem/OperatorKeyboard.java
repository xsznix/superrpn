package xsznix.superrpn.ui.elem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.Apint;
import org.apfloat.ApintMath;


import xsznix.superrpn.compstack.ComputationStack;
import xsznix.superrpn.compstack.ComputationStackItem;
import xsznix.superrpn.err.OperationException;
import xsznix.superrpn.ui.MainFrame;
import xsznix.superrpn.util.Maths;

/**
 * A keyboard containing a bunch of operators.
 * @author Xuming Zeng
 *
 */
public class OperatorKeyboard extends JPanel {

	private static final long serialVersionUID = -5829314300061016504L;
	
	/** An array of the buttons that make up this keyboard. */
	private OperationButton[] buttons;
	
	/**
	 * The parent frame that provides the computation stack for all
	 * computations.
	 */
	private MainFrame parent;
	
	/** A map of key bindings */
	private HashMap<Integer, OperationButton> keyMap;
	
	/**
	 * Creates a new <code>OperatorKeyboard</code>.
	 * @param par the parent <code>MainFrame</code>
	 */
	public OperatorKeyboard(MainFrame par) {
		setLayout(new GridBagLayout());
		setBorder(new EmptyBorder(8, 8, 8, 16));
		setFocusable(false);
		
		parent = par;
		
		createButtons();
	}
	
	public MainFrame getFrame() { return parent; }
	
	/**
	 * Sets whether the alternate operations of the buttons in this keyboard
	 * should be displayed.
	 * @param alt if the alternate operations should be displayed
	 */
	public void setAlt(boolean alt) {
		for (OperationButton b : buttons) {
			if (b != null)
				b.setAlt(alt);
		}
	}
	
	/**
	 * Handles key press events sent from the main frame
	 * @param e the key event to handle
	 */
	public void keyPressed(KeyEvent e) {
        OperationButton op = keyMap.get(e.getKeyCode());
        if (op != null)
        	op.doClick();
	}
	
	/**
	 * Fills in this keyboard.<br>
	 * Postcondition: <code>buttons</code> is set to contain an array of buttons
	 * and the same buttons have been added in the correct order and layout onto
	 * this keyboard. 
	 */
	@SuppressWarnings("serial") // Eclipse complains about anonymous classes
	private void createButtons() {
		// initialize data
		buttons = new OperationButton[54];
		keyMap = new HashMap<Integer, OperationButton>();
		
		// common constraints options
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 20;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.insets = new Insets(1, 1, 1, 1);
		
		// spacers
		c.gridy = 0;
		for (int i = 0; i < 27; i++) {
			c.gridx = i;
			add(Box.createGlue(), c);
		}
		
		// negate
		buttons[0] = new OperationButton(this, '`', "+/-", "+/-") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					String val = item.getValStr(
							parent.getFrame().getPrecision());
					int indexOfE = val.indexOf('e');
					
					// invert value
					if (indexOfE == -1) {
						if (val.charAt(0) == '-')
							stack.push(val.substring(1, val.length()));
						else
							stack.push("-" + val);
					}
					// invert exponent
					else if (val.charAt(indexOfE + 1) == '-') {
						stack.push(val.substring(0, indexOfE) + "e"
								+ val.substring(indexOfE + 2, val.length()));
					} else {
						stack.push(val.substring(0, indexOfE) + "e-"
								+ val.substring(indexOfE + 1, val.length()));
					}
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 0;
		c.gridy = 1;
		add(buttons[0], c);
		keyMap.put(new Integer(KeyEvent.VK_BACK_QUOTE), buttons[0]);
		
		// 1
		buttons[1] = new OperationButton(this, '1', "1", "1") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					int precision = parent.getFrame().getPrecision();
					ComputationStackItem newItem =
							new ComputationStackItem(item.getValStr(precision));
					newItem.strAppend('1', precision);
					stack.push(newItem);
				} else {
					stack.push("1");
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 2;
		c.gridwidth = 2;
		add(buttons[1], c);
		keyMap.put(new Integer(KeyEvent.VK_1), buttons[1]);
		
		// 2
		buttons[2] = new OperationButton(this, '2', "2", "2") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					int precision = parent.getFrame().getPrecision();
					ComputationStackItem newItem =
							new ComputationStackItem(item.getValStr(precision));
					newItem.strAppend('2', precision);
					stack.push(newItem);
				} else {
					stack.push("2");
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 4;
		c.gridwidth = 2;
		add(buttons[2], c);
		keyMap.put(new Integer(KeyEvent.VK_2), buttons[2]);

		// 3
		buttons[3] = new OperationButton(this, '3', "3", "3") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					int precision = parent.getFrame().getPrecision();
					ComputationStackItem newItem =
							new ComputationStackItem(item.getValStr(precision));
					newItem.strAppend('3', precision);
					stack.push(newItem);
				} else {
					stack.push("3");
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 6;
		c.gridwidth = 2;
		add(buttons[3], c);
		keyMap.put(new Integer(KeyEvent.VK_3), buttons[3]);

		// 4
		buttons[4] = new OperationButton(this, '4', "4", "4") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					int precision = parent.getFrame().getPrecision();
					ComputationStackItem newItem =
							new ComputationStackItem(item.getValStr(precision));
					newItem.strAppend('4', precision);
					stack.push(newItem);
				} else {
					stack.push("4");
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 8;
		c.gridwidth = 2;
		add(buttons[4], c);
		keyMap.put(new Integer(KeyEvent.VK_4), buttons[4]);

		// 5
		buttons[5] = new OperationButton(this, '5', "5", "5") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					int precision = parent.getFrame().getPrecision();
					ComputationStackItem newItem =
							new ComputationStackItem(item.getValStr(precision));
					newItem.strAppend('5', precision);
					stack.push(newItem);
				} else {
					stack.push("5");
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 10;
		c.gridwidth = 2;
		add(buttons[5], c);
		keyMap.put(new Integer(KeyEvent.VK_5), buttons[5]);

		// 6
		buttons[6] = new OperationButton(this, '6', "6", "6") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					int precision = parent.getFrame().getPrecision();
					ComputationStackItem newItem =
							new ComputationStackItem(item.getValStr(precision));
					newItem.strAppend('6', precision);
					stack.push(newItem);
				} else {
					stack.push("6");
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 12;
		c.gridwidth = 2;
		add(buttons[6], c);
		keyMap.put(new Integer(KeyEvent.VK_6), buttons[6]);

		// 7
		buttons[7] = new OperationButton(this, '7', "7", "7") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					int precision = parent.getFrame().getPrecision();
					ComputationStackItem newItem =
							new ComputationStackItem(item.getValStr(precision));
					newItem.strAppend('7', precision);
					stack.push(newItem);
				} else {
					stack.push("7");
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 14;
		c.gridwidth = 2;
		add(buttons[7], c);
		keyMap.put(new Integer(KeyEvent.VK_7), buttons[7]);

		// 8
		buttons[8] = new OperationButton(this, '8', "8", "8") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					int precision = parent.getFrame().getPrecision();
					ComputationStackItem newItem =
							new ComputationStackItem(item.getValStr(precision));
					newItem.strAppend('8', precision);
					stack.push(newItem);
				} else {
					stack.push("8");
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 16;
		c.gridwidth = 2;
		add(buttons[8], c);
		keyMap.put(new Integer(KeyEvent.VK_8), buttons[8]);

		// 9
		buttons[9] = new OperationButton(this, '9', "9", "9") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					int precision = parent.getFrame().getPrecision();
					ComputationStackItem newItem =
							new ComputationStackItem(item.getValStr(precision));
					newItem.strAppend('9', precision);
					stack.push(newItem);
				} else {
					stack.push("9");
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 18;
		c.gridwidth = 2;
		add(buttons[9], c);
		keyMap.put(new Integer(KeyEvent.VK_9), buttons[9]);

		// 0
		buttons[10] = new OperationButton(this, '0', "0", "0") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					int precision = parent.getFrame().getPrecision();
					ComputationStackItem newItem =
							new ComputationStackItem(item.getValStr(precision));
					newItem.strAppend('0', precision);
					stack.push(newItem);
				} else {
					stack.push("0");
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 20;
		c.gridwidth = 2;
		add(buttons[10], c);
		keyMap.put(new Integer(KeyEvent.VK_0), buttons[10]);

		// subtraction
		buttons[11] = new OperationButton(this, '-', "-", "-") {
			protected final void operate(ComputationStack stack) {
				if (stack.height() >= 2) {
					Apfloat bottom = stack.pop().getValue();
					Apfloat top = stack.pop().getValue();
					stack.push(top.subtract(bottom));
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 22;
		c.gridwidth = 2;
		add(buttons[11], c);
		keyMap.put(new Integer(KeyEvent.VK_MINUS), buttons[11]);
		
		// addition
		buttons[12] = new OperationButton(this, '=', "+", "+") {
			protected final void operate(ComputationStack stack) {
				if (stack.height() >= 2) {
					Apfloat bottom = stack.pop().getValue();
					Apfloat top = stack.pop().getValue();
					stack.push(top.add(bottom));
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 24;
		c.gridwidth = 2;
		add(buttons[12], c);
		keyMap.put(new Integer(KeyEvent.VK_EQUALS), buttons[12]);
		
		// backspace
		buttons[13] = new OperationButton(this, "Bksp", "Bksp", "Bksp") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					String value = stack.pop().getValStr(
							parent.getFrame().getPrecision());
					
					// trim last character
					if (value.length() > 0) {
						value = value.substring(0,value.length() - 1);
						
						// trim an additional character if necessary
						if (value.length() > 0) {
							char lastChar = value.charAt(value.length() - 1);
							if ((lastChar == 'e') || (lastChar == '.') ||
									(lastChar == '-'))
								value = value.substring(0, value.length() - 1);
						} else {
							value = "0";
						}
					}
					
					stack.push(value);
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 26;
		c.gridwidth = 3;
		add(buttons[13], c);
		keyMap.put(new Integer(KeyEvent.VK_BACK_SPACE), buttons[13]);
		
		// swap
		buttons[14] = new OperationButton(this, "Tab", "Swap", "Swap") {
			protected final void operate(ComputationStack stack) {
				if (stack.height() >= 2) {
					ComputationStackItem item1 = stack.pop();
					ComputationStackItem item2 = stack.pop();
					
					stack.push(item1);
					stack.push(item2);
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		add(buttons[14], c);
		keyMap.put(new Integer(KeyEvent.VK_TAB), buttons[14]);
		
		// sin/sinh
		buttons[15] = new OperationButton(this, "Q", "sin(x)", "sinh(x)") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat in = item.getValue();
					if (parent.getFrame().isDegreesMode())
						in = Maths.degToRad(in);
					stack.push(ApfloatMath.sin(in));
				}
			}
			protected final void altOp(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat in = item.getValue();
					if (parent.getFrame().isDegreesMode())
						in = Maths.degToRad(in);
					stack.push(ApfloatMath.sinh(in));
				}
			}
		};
		c.gridx = 3;
		c.gridwidth = 2;
		add(buttons[15], c);
		keyMap.put(new Integer(KeyEvent.VK_Q), buttons[15]);
		
		// cos/cosh
		buttons[16] = new OperationButton(this, "W", "cos(x)", "cosh(x)") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat in = item.getValue();
					if (parent.getFrame().isDegreesMode())
						in = Maths.degToRad(in);
					stack.push(ApfloatMath.cos(in));
				}
			}
			protected final void altOp(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat in = item.getValue();
					if (parent.getFrame().isDegreesMode())
						in = Maths.degToRad(in);
					stack.push(ApfloatMath.cosh(in));
				}
			}
		};
		c.gridx = 5;
		c.gridwidth = 2;
		add(buttons[16], c);
		keyMap.put(new Integer(KeyEvent.VK_W), buttons[16]);
		
		// tan/tanh
		buttons[17] = new OperationButton(this, "E", "tan(x)", "tanh(x)") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat in = item.getValue();
					if (parent.getFrame().isDegreesMode())
						in = Maths.degToRad(in);
					stack.push(ApfloatMath.tan(in));
				}
			}
			protected final void altOp(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat in = item.getValue();
					if (parent.getFrame().isDegreesMode())
						in = Maths.degToRad(in);
					stack.push(ApfloatMath.tanh(in));
				}
			}
		};
		c.gridx = 7;
		c.gridwidth = 2;
		add(buttons[17], c);
		keyMap.put(new Integer(KeyEvent.VK_E), buttons[17]);
		
		// ln/LCM
		buttons[18] = new OperationButton(this, "R", "ln(x)", "LCM") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(ApfloatMath.log(item.getValue()));
				}
			}
			protected final void altOp(ComputationStack stack) {
				if (stack.height() >= 2) {
					ComputationStackItem a = stack.pop();
					ComputationStackItem b = stack.pop();
					
					Apint a1 = a.getValue().truncate();
					Apint b1 = b.getValue().truncate();
					
					stack.push(ApintMath.lcm(a1, b1));
				}
			}
		};
		c.gridx = 9;
		c.gridwidth = 2;
		add(buttons[18], c);
		keyMap.put(new Integer(KeyEvent.VK_R), buttons[18]);
		
		// logb/GCD
		buttons[19] = new OperationButton(this, "T", "logy(x)", "GCD") {
			protected final void operate(ComputationStack stack) {
				if (stack.height() >= 2) {
					ComputationStackItem base = stack.pop();
					ComputationStackItem item = stack.pop();
					stack.push(
							ApfloatMath.log(item.getValue(), base.getValue()));
				}
			}
			protected final void altOp(ComputationStack stack) {
				if (stack.height() >= 2) {
					ComputationStackItem a = stack.pop();
					ComputationStackItem b = stack.pop();
					
					Apint a1 = a.getValue().truncate();
					Apint b1 = b.getValue().truncate();
					
					stack.push(ApintMath.gcd(a1, b1));
				}
			}
		};
		c.gridx = 11;
		c.gridwidth = 2;
		add(buttons[19], c);
		keyMap.put(new Integer(KeyEvent.VK_T), buttons[19]);
		
		// log10/nCr
		buttons[20] = new OperationButton(this, "Y", "log(x)", "nCr") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(
							ApfloatMath.log(item.getValue(), new Apfloat(10)));
				}
			}
			protected final void altOp(ComputationStack stack)
					throws OperationException {
				if (stack.height() >= 2) {
					ComputationStackItem a = stack.pop();
					ComputationStackItem b = stack.pop();
					
					Apint a1 = a.getValue().truncate();
					Apint b1 = b.getValue().truncate();
					
					 stack.push(Maths.nCr(b1, a1));
				}
			}
		};
		c.gridx = 13;
		c.gridwidth = 2;
		add(buttons[20], c);
		keyMap.put(new Integer(KeyEvent.VK_Y), buttons[20]);
		
		// 1/x / nPr
		buttons[21] = new OperationButton(this, "U", "1/x", "nPr") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(
							ApfloatMath.inverseRoot(item.getValue(), 1L));
				}
			}
			protected final void altOp(ComputationStack stack)
					throws OperationException {
				if (stack.height() >= 2) {
					ComputationStackItem a = stack.pop();
					ComputationStackItem b = stack.pop();
					
					Apint a1 = a.getValue().truncate();
					Apint b1 = b.getValue().truncate();
					
					stack.push(Maths.nPr(b1, a1));
				}
			}
		};
		c.gridx = 15;
		c.gridwidth = 2;
		add(buttons[21], c);
		keyMap.put(new Integer(KeyEvent.VK_U), buttons[21]);
		
		// x^2
		buttons[22] = new OperationButton(this, "I", "x\u00B2", "") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(
							ApfloatMath.pow(item.getValue(), 2L));
				}
			}
			protected final void altOp(ComputationStack stack) {}
		};
		c.gridx = 17;
		c.gridwidth = 2;
		add(buttons[22], c);
		keyMap.put(new Integer(KeyEvent.VK_I), buttons[22]);
		
		// x^3
		buttons[23] = new OperationButton(this, "O", "x\u00B3", "") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(
							ApfloatMath.pow(item.getValue(), 3L));
				}
			}
			protected final void altOp(ComputationStack stack) {}
		};
		c.gridx = 19;
		c.gridwidth = 2;
		add(buttons[23], c);
		keyMap.put(new Integer(KeyEvent.VK_O), buttons[23]);
		
		// x^y
		buttons[24] = new OperationButton(this, "P", "x\u02B8", "") {
			protected final void operate(ComputationStack stack) {
				if (stack.height() >= 2) {
					ComputationStackItem pow = stack.pop();
					ComputationStackItem item = stack.pop();
					stack.push(
							ApfloatMath.pow(item.getValue(), pow.getValue()));
				}
			}
			protected final void altOp(ComputationStack stack) {}
		};
		c.gridx = 21;
		c.gridwidth = 2;
		add(buttons[24], c);
		keyMap.put(new Integer(KeyEvent.VK_P), buttons[24]);
		
		// division
		buttons[25] = new OperationButton(this, '[', "/", "/") {
			protected final void operate(ComputationStack stack) {
				if (stack.height() >= 2) {
					Apfloat bottom = stack.pop().getValue();
					Apfloat top = stack.pop().getValue();
					stack.push(top.divide(bottom));
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 23;
		c.gridwidth = 2;
		add(buttons[25], c);
		keyMap.put(new Integer(KeyEvent.VK_OPEN_BRACKET), buttons[25]);
		
		// multiplication
		buttons[26] = new OperationButton(this, ']', "*", "*") {
			protected final void operate(ComputationStack stack) {
				if (stack.height() >= 2) {
					Apfloat bottom = stack.pop().getValue();
					Apfloat top = stack.pop().getValue();
					stack.push(top.multiply(bottom));
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 25;
		c.gridwidth = 2;
		add(buttons[26], c);
		keyMap.put(new Integer(KeyEvent.VK_CLOSE_BRACKET), buttons[26]);
		
		// modulo
		buttons[27] = new OperationButton(this, '\\', "%", "%") {
			protected final void operate(ComputationStack stack) {
				if (stack.height() >= 2) {
					Apfloat bottom = stack.pop().getValue();
					Apfloat top = stack.pop().getValue();
					stack.push(top.mod(bottom));
				}
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 27;
		c.gridwidth = 2;
		add(buttons[27], c);
		keyMap.put(new Integer(KeyEvent.VK_BACK_SLASH), buttons[27]);
		
		// blank
		buttons[28] = new OperationButton(this, "Caps Lock", "", "") {
			protected final void operate(ComputationStack stack) {}
			protected final void altOp(ComputationStack stack) {}
		};
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 4;
		add(buttons[28], c);
		keyMap.put(new Integer(KeyEvent.VK_CAPS_LOCK), buttons[28]);
		
		// asin/asinh
		buttons[29] = new OperationButton(this, 'A', "asin(x)", "asinh(x)") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat out = ApfloatMath.asin(item.getValue());
					if (parent.getFrame().isDegreesMode())
						out = Maths.radToDeg(out);
					stack.push(out);
				}
			}
			protected final void altOp(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat out = ApfloatMath.asinh(item.getValue());
					if (parent.getFrame().isDegreesMode())
						out = Maths.radToDeg(out);
					stack.push(out);
				}
			}
		};
		c.gridx = 4;
		c.gridwidth = 2;
		add(buttons[29], c);
		keyMap.put(new Integer(KeyEvent.VK_A), buttons[29]);
		
		// acos/acosh
		buttons[30] = new OperationButton(this, 'S', "acos(x)", "acosh(x)") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat out = ApfloatMath.acos(item.getValue());
					if (parent.getFrame().isDegreesMode())
						out = Maths.radToDeg(out);
					stack.push(out);
				}
			}
			protected final void altOp(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat out = ApfloatMath.acosh(item.getValue());
					if (parent.getFrame().isDegreesMode())
						out = Maths.radToDeg(out);
					stack.push(out);
				}
			}
		};
		c.gridx = 6;
		c.gridwidth = 2;
		add(buttons[30], c);
		keyMap.put(new Integer(KeyEvent.VK_S), buttons[30]);
		
		// atan/atanh
		buttons[31] = new OperationButton(this, 'D', "atan(x)", "atanh(x)") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat out = ApfloatMath.atan(item.getValue());
					if (parent.getFrame().isDegreesMode())
						out = Maths.radToDeg(out);
					stack.push(out);
				}
			}
			protected final void altOp(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					Apfloat out = ApfloatMath.atanh(item.getValue());
					if (parent.getFrame().isDegreesMode())
						out = Maths.radToDeg(out);
					stack.push(out);
				}
			}
		};
		c.gridx = 8;
		c.gridwidth = 2;
		add(buttons[31], c);
		keyMap.put(new Integer(KeyEvent.VK_D), buttons[31]);
		
		// e^x/atan2(x,y)
		buttons[32] = new OperationButton(this, 'F', "e^x", "atan2") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(ApfloatMath.exp(item.getValue()));
				}
			}
			protected final void altOp(ComputationStack stack) {
				if (stack.height() >= 2) {
					ComputationStackItem b = stack.pop();
					ComputationStackItem a = stack.pop();
					Apfloat out = ApfloatMath.atan2(a.getValue(), b.getValue());
					if (parent.getFrame().isDegreesMode())
						out = Maths.radToDeg(out);
					stack.push(out);
				}
			}
		};
		c.gridx = 10;
		c.gridwidth = 2;
		add(buttons[32], c);
		keyMap.put(new Integer(KeyEvent.VK_F), buttons[32]);
		
		// 2^x
		buttons[33] = new OperationButton(this, 'G', "2^x", "") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(ApfloatMath.pow(
							new Apfloat(2), item.getValue()));
				}
			}
			protected final void altOp(ComputationStack stack) {}
		};
		c.gridx = 12;
		c.gridwidth = 2;
		add(buttons[33], c);
		keyMap.put(new Integer(KeyEvent.VK_G), buttons[33]);
		
		// 10^x
		buttons[34] = new OperationButton(this, 'H', "10^x", "") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(ApfloatMath.pow(
							new Apfloat(10), item.getValue()));
				}
			}
			protected final void altOp(ComputationStack stack) {}
		};
		c.gridx = 14;
		c.gridwidth = 2;
		add(buttons[34], c);
		keyMap.put(new Integer(KeyEvent.VK_H), buttons[34]);
		
		// inv sqrt
		buttons[35] = new OperationButton(this, 'J', "1/\u221Ax", "") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(ApfloatMath.inverseRoot(item.getValue(), 2L));
				}
			}
			protected final void altOp(ComputationStack stack) {}
		};
		c.gridx = 16;
		c.gridwidth = 2;
		add(buttons[35], c);
		keyMap.put(new Integer(KeyEvent.VK_J), buttons[35]);
		
		// sqrt
		buttons[36] = new OperationButton(this, 'K', "\u221Ax", "") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(ApfloatMath.sqrt(item.getValue()));
				}
			}
			protected final void altOp(ComputationStack stack) {}
		};
		c.gridx = 18;
		c.gridwidth = 2;
		add(buttons[36], c);
		keyMap.put(new Integer(KeyEvent.VK_K), buttons[36]);
		
		// cbrt
		buttons[37] = new OperationButton(this, 'L', "\u00B3\u221Ax", "") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.pop();
					stack.push(ApfloatMath.cbrt(item.getValue()));
				}
			}
			protected final void altOp(ComputationStack stack) {}
		};
		c.gridx = 20;
		c.gridwidth = 2;
		add(buttons[37], c);
		keyMap.put(new Integer(KeyEvent.VK_L), buttons[37]);
		
		// arbitrary root
		buttons[38] = new OperationButton(this, ';', "\u02B8\u221Ax", "") {
			protected final void operate(ComputationStack stack) {
				if (stack.height() >= 2) {
					ComputationStackItem pow = stack.pop();
					ComputationStackItem base = stack.pop();
					stack.push(ApfloatMath.pow(base.getValue(),
							ApfloatMath.inverseRoot(pow.getValue(), 1L)));
				}
			}
			protected final void altOp(ComputationStack stack) {}
		};
		c.gridx = 22;
		c.gridwidth = 2;
		add(buttons[38], c);
		keyMap.put(new Integer(KeyEvent.VK_SEMICOLON), buttons[38]);
		
		// EE
		buttons[39] = new OperationButton(this, '\'', "EE", "EE") {
			protected final void operate(ComputationStack stack) {
				String val = stack.pop().getValStr(
						parent.getFrame().getPrecision());
				int posOfE = val.indexOf('e');
				if (posOfE == -1) {
					val += 'e';
				} else {
					val = val.substring(0, posOfE);
				}
				stack.push(val);
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 24;
		c.gridwidth = 2;
		add(buttons[39], c);
		keyMap.put(new Integer(KeyEvent.VK_QUOTE), buttons[39]);
		
		// enter
		buttons[40] = new OperationButton(this, "Return", "Enter", "Enter") {
			protected final void operate(ComputationStack stack) {
				stack.push("0");
			}
			protected final void altOp(ComputationStack stack) {
				operate(stack);
			}
		};
		c.gridx = 26;
		c.gridwidth = 3;
		add(buttons[40], c);
		keyMap.put(new Integer(KeyEvent.VK_ENTER), buttons[40]);
		
		// left shift
		buttons[41] = new OperationButton(this, "Shift", "Alt", "Alt") {
			protected final void operate(ComputationStack stack) {
				parent.setAlt(true);
			}
			protected final void altOp(ComputationStack stack) {
				parent.setAlt(false);
			}
		};
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 5;
		add(buttons[41], c);
		
		// undo
		buttons[42] = new OperationButton(this, 'Z', "undo", "undo") {
			protected final void operate(ComputationStack stack)
					throws OperationException {
				stack.undo();
			}
			protected final void altOp(ComputationStack stack)
					throws OperationException {
				operate(stack);
			}
		};
		c.gridx = 5;
		c.gridwidth = 2;
		add(buttons[42], c);
		keyMap.put(new Integer(KeyEvent.VK_Z), buttons[42]);
		
		// redo
		buttons[43] = new OperationButton(this, 'X', "redo", "redo") {
			protected final void operate(ComputationStack stack)
					throws OperationException {
				stack.redo();
			}
			protected final void altOp(ComputationStack stack)
					throws OperationException {
				operate(stack);
			}
		};
		c.gridx = 7;
		c.gridwidth = 2;
		add(buttons[43], c);
		keyMap.put(new Integer(KeyEvent.VK_X), buttons[43]);
		
		// clear memory
		buttons[44] = new OperationButton(this, 'C', "M1C", "M2C") {
			protected final void operate(ComputationStack stack) {
				stack.setMem1(Apfloat.ZERO);
			}
			protected final void altOp(ComputationStack stack){
				stack.setMem2(Apfloat.ZERO);
			}
		};
		c.gridx = 9;
		c.gridwidth = 2;
		add(buttons[44], c);
		keyMap.put(new Integer(KeyEvent.VK_C), buttons[44]);
		
		// add memory
		buttons[45] = new OperationButton(this, 'V', "M1+", "M2+") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty())
					stack.setMem1(stack.getMem1().add(stack.peek().getValue()));
			}
			protected final void altOp(ComputationStack stack){
				if (!stack.empty())
					stack.setMem2(stack.getMem2().add(stack.peek().getValue()));
			}
		};
		c.gridx = 11;
		c.gridwidth = 2;
		add(buttons[45], c);
		keyMap.put(new Integer(KeyEvent.VK_V), buttons[45]);
		
		// sub memory
		buttons[46] = new OperationButton(this, 'B', "M1-", "M2-") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty())
					stack.setMem1(
							stack.getMem1().subtract(stack.peek().getValue()));
			}
			protected final void altOp(ComputationStack stack){
				if (!stack.empty())
					stack.setMem2(
							stack.getMem2().subtract(stack.peek().getValue()));
			}
		};
		c.gridx = 13;
		c.gridwidth = 2;
		add(buttons[46], c);
		keyMap.put(new Integer(KeyEvent.VK_B), buttons[46]);
		
		// recall memory
		buttons[47] = new OperationButton(this, 'N', "M1R", "M2R") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) stack.pop();
				stack.push(stack.getMem1());
			}
			protected final void altOp(ComputationStack stack){
				if (!stack.empty()) stack.pop();
				stack.push(stack.getMem2());
			}
		};
		c.gridx = 15;
		c.gridwidth = 2;
		add(buttons[47], c);
		keyMap.put(new Integer(KeyEvent.VK_N), buttons[47]);
		
		// pi
		buttons[48] = new OperationButton(this, 'M', "\u03C0", "\u03C0") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) stack.pop();
				stack.push(Maths.PI);
			}
			protected final void altOp(ComputationStack stack){
				operate(stack);
			}
		};
		c.gridx = 17;
		c.gridwidth = 2;
		add(buttons[48], c);
		keyMap.put(new Integer(KeyEvent.VK_M), buttons[48]);
		
		// e
		buttons[49] = new OperationButton(this, ',', "e", "e") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) stack.pop();
				stack.push(Maths.E);
			}
			protected final void altOp(ComputationStack stack){
				operate(stack);
			}
		};
		c.gridx = 19;
		c.gridwidth = 2;
		add(buttons[49], c);
		keyMap.put(new Integer(KeyEvent.VK_COMMA), buttons[49]);
		
		// decimal point
		buttons[50] = new OperationButton(this, '.', ".", ".") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) {
					ComputationStackItem item = stack.peek();
					int precision = parent.getFrame().getPrecision();
					String value = item.getValStr(precision);
					if ((value.indexOf('.') == -1) &&
							(value.indexOf('e') == -1)) {
						ComputationStackItem newItem =
								new ComputationStackItem(value + '.');
						stack.pop();
						stack.push(newItem);
					}
				} else {
					stack.push("0.");
				}
			}
			protected final void altOp(ComputationStack stack){
				operate(stack);
			}
		};
		c.gridx = 21;
		c.gridwidth = 2;
		add(buttons[50], c);
		keyMap.put(new Integer(KeyEvent.VK_PERIOD), buttons[50]);
		
		// deg/rad
		buttons[51] = new OperationButton(this, '/', "D/R", "D/R") {
			protected final void operate(ComputationStack stack) {
				MainFrame par = parent.getFrame();
				par.setDegreesMode(!par.isDegreesMode());
			}
			protected final void altOp(ComputationStack stack){
				operate(stack);
			}
		};
		c.gridx = 23;
		c.gridwidth = 2;
		add(buttons[51], c);
		keyMap.put(new Integer(KeyEvent.VK_SLASH), buttons[51]);
		
		// right shift
		buttons[52] = new OperationButton(this, "Shift", "Alt", "Alt") {
			protected final void operate(ComputationStack stack) {
				parent.setAlt(true);
			}
			protected final void altOp(ComputationStack stack) {
				parent.setAlt(false);
			}
		};
		c.gridx = 25;
		c.gridwidth = 5;
		add(buttons[52], c);
		
		buttons[53] = new OperationButton(this, "Space", "Drop", "Clear") {
			protected final void operate(ComputationStack stack) {
				if (!stack.empty()) stack.pop();
			}
			protected final void altOp(ComputationStack stack) {
				while (!stack.empty()) stack.pop();
				stack.push("0");
			}
		};
		c.gridx = 6;
		c.gridy = 5;
		c.gridwidth = 17;
		add(buttons[53], c);
		keyMap.put(new Integer(KeyEvent.VK_SPACE), buttons[53]);
		
		// Phew.
	}

}
