package xsznix.superrpn.ui.elem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import xsznix.superrpn.compstack.ComputationStack;
import xsznix.superrpn.err.OperationException;

/**
 * A button that listen to its own actions and defines two of its own operations.
 * @author Xuming Zeng
 *
 */
public abstract class OperationButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 8219700390956540255L;
	
	/**
	 * The parent <code>OperatorKeyboard</code> that supplies the computation
	 * stack for this button.
	 */
	protected OperatorKeyboard parent;
	
	/**
	 * The key on the keyboard that this button corresponds to. For example,
	 * if this operator is assigned to the Q key, <code>key</code> will contain
	 * "Q".
	 */
	private String key;
	
	/**
	 * The text for the operation that this button performs. For example, if
	 * this operator is assigned to the logarithm function, <code>op</code> will
	 * contain "log(x)".
	 */
	private String operation;
	
	/**
	 * The alternate text for this operation, shown if the Shift key is held
	 * down.
	 */
	private String altOp;
	
	/**
	 * <code>true</code> if the alternate operations of the button should be
	 * shown.
	 */
	private boolean isAlt;

	/**
	 * Performs an operation on the computation stack. This method is overridden
	 * in any class that extends this one.
	 */
	protected abstract void operate(ComputationStack stack)
			throws OperationException;
	
	/**
	 * Performs this button's alternate operation.
	 * @param stack
	 */
	protected abstract void altOp(ComputationStack stack)
			throws OperationException;
	
	/**
	 * Instantiates a new <code>OperationButton</code> with a specified
	 * parent <code>OperatorKeyboard</code>.
	 * @param par the parent <code>OperatorKeyboard</code> of this button
	 * @param k the letter on the keyboard corresponding to this button
	 * @param op a short string representing the operation this button does
	 * @param alt a short string representing the alternate operation of this
	 * button
	 */
	public OperationButton(OperatorKeyboard par, char k, String op, String alt) {
		this(par, "" + k, op, alt);
	}
	
	/**
	 * Instantiates a new <code>OperationButton</code> with a specified
	 * parent <code>OperatorKeyboard</code>.
	 * @param par the parent <code>OperatorKeyboard</code> of this button
	 * @param k the key on the keyboard corresponding to this button
	 * @param op a short string representing the operation this button does
	 * @param alt a short string representing the alternate operation of this
	 * button
	 */
	public OperationButton(OperatorKeyboard par, String k, String op, String alt) {
		// set private values
		parent = par;
		key = k;
		operation = op;
		altOp = alt;
		isAlt = false;
		
		// buttons can not be focused because the MainFrame captures all
		// keyboard events
		setFocusable(false);
		
		// add the action listener
		addActionListener(this);
	}
	
	/**
	 * Sets the operation display mode of this button.
	 * @param alt whether to display the alternate operation or not
	 */
	public void setAlt(boolean alt) {
		isAlt = alt;
		repaint();
	}
	
	/**
	 * Handles any action performed on this button.
	 */
	public final void actionPerformed(ActionEvent e) {
		ComputationStack stack = parent.getFrame().getCompStack();
		
		try {
			// perform the operation
			if (isAlt)
				altOp(stack);
			else
				operate(stack);
		} catch (Exception err) {
			// show error to user
			JOptionPane.showMessageDialog(parent.getFrame(),
			    err.getMessage(),
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
			
			// print error to console
			err.printStackTrace();
			
			// undo stack operations
			stack.addMarker();
			try { stack.undo(); }
			catch (OperationException ex) { /* lol */ }
		}
		
		stack.addMarker();
		
		parent.getFrame().getStackView().update();
	}
	
	/**
	 * Paints the text in this button.
	 */
	@Override
	protected void paintChildren(Graphics g) {
		Dimension size = getSize();
		
		// set font
		int fontSize = (int) size.getHeight() / 4;
		int textOffset = (int) size.getHeight() / 6;
		Font f = new Font("Arial", Font.PLAIN, fontSize);
		g.setFont(f);
		FontMetrics fm = g.getFontMetrics();
		
		// draw operation
		g.drawString((isAlt ? altOp : operation), textOffset,
				fm.getHeight() + textOffset / 2);
		
		// draw key
		g.setColor(new Color(200, 0, 0));
		g.drawString(key, size.width - fm.stringWidth(key) - textOffset,
				size.height - textOffset - textOffset / 2);
	}

}
