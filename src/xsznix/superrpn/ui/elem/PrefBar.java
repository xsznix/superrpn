package xsznix.superrpn.ui.elem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import xsznix.superrpn.ui.MainFrame;

/**
 * The menu bar of the MainFrame. Contains a menu to choose precision and
 * a label to display the degrees/radians mode.
 * @author Xuming Zeng
 *
 */
public class PrefBar extends JMenuBar {

	private static final long serialVersionUID = 5836183017843292161L;
	
	private MainFrame parent;
	
	private JLabel degModeDisp;
	
	/**
	 * Creates a new <code>PrefBar</code>.
	 */
	public PrefBar(MainFrame par) {
		super();
		
		parent = par;
		
		// add the menu
		JMenu precMenu = new JMenu("Precision");
		for (int i = 1; i <= 30; i++)
			precMenu.add(new PrecisionSetter(this, i));
		add(precMenu);
		
		add(Box.createHorizontalGlue());
		
		// add the degrees/radians mode display
		degModeDisp = new JLabel();
		updateDegreesModeDisplay();
		add(degModeDisp);
	}
	
	// GETTERS/SETTERS
	
	private MainFrame getFrame() { return parent; }
	
	// METHODS
	
	/**
	 * Updates the text in the degrees mode display to match the current
	 * preference of the user.
	 */
	public void updateDegreesModeDisplay() {
		boolean deg = parent.isDegreesMode();
		
		if (deg)
			degModeDisp.setText("Deg");
		else
			degModeDisp.setText("Rad");
	}
	
	/**
	 * A menu item that sets the precision when clicked.
	 * @author Xuming Zeng
	 *
	 */
	private class PrecisionSetter extends JMenuItem {
		private static final long serialVersionUID = -1519009139731148667L;

		/**
		 * Creates a new <code>PrecisionSetter</code> with the specified parent
		 * and precision
		 * @param par the menu bar that supplies the <code>MainFrame</code> to
		 * set the settings of
		 * @param prec the precision to set to
		 */
		public PrecisionSetter(PrefBar par, int prec) {
			super(Integer.toString(prec));
			
			addActionListener(new PrecSetListener(par, prec));
		}
		
		/**
		 * Listens and performs actions for a precision-setting menu item.
		 * @author Xuming Zeng
		 *
		 */
		private class PrecSetListener implements ActionListener {
			/** The preference bar that supplies the MainFrame */
			private PrefBar parent;
			/** The precision to set to when an action is performed. */
			private int precision;
			
			/**
			 * Creates a new listener for a <code>PrecisionSetter</code>.
			 * @param par the menu bar that supplies the <code>MainFrame</code>
			 * to set the settings of
			 * @param prec the precision to set to
			 */
			public PrecSetListener(PrefBar par, int prec) {
				super();
				
				parent = par;
				precision = prec;
			}
			
			/** Sets the precision of the calculator. */
			public void actionPerformed(ActionEvent e) {
				parent.getFrame().setPrecision(precision);
			}
		}
	}

}
