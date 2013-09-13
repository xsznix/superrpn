package xsznix.superrpn.ui.elem;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A panel containing a single right-aligned label whose value can be set
 * externally.
 * @author Xuming Zeng
 *
 */
public class StackViewItem extends JPanel {

	private static final long serialVersionUID = -8074760414365777724L;
	
	/**
	 * The label that contains the text in this item.
	 */
	private JLabel label;
	
	/**
	 * Creates a new stack view item with empty text.
	 */
	public StackViewItem() {
		this("");
	}
	
	/**
	 * Creates a new stack view item with the specified text.
	 * @param txt the text of this stack view item
	 */
	public StackViewItem(String txt) {
		// create right aligned label
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(Box.createHorizontalGlue());
		label = new JLabel(txt);
		add(label);
		
		// formatting
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		label.setFont(new Font("Courier New", Font.PLAIN, 14));
	}
	
	/**
	 * Gets the text of this stack item.
	 * @return the text of this stack item
	 */
	public String getLabel() { return label.getText(); }
	
	/**
	 * Sets the text of this stack item.
	 * @param txt the text to set this item to
	 */
	public void setLabel(String txt) { label.setText(txt); }

}
