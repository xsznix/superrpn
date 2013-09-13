package xsznix.superrpn.ui.elem;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


import xsznix.superrpn.compstack.ComputationStackItem;
import xsznix.superrpn.ui.MainFrame;

/**
 * A GUI representation of the computation stack, including the memory registers.
 * @author Xuming Zeng
 *
 */
public class StackView extends JPanel {

	private static final long serialVersionUID = 1487270099691913157L;
	
	/** The parent <code>MainFrame</code> of this stack view */
	private MainFrame parent;
	
	/** A list of labels in this stack view */
	private LinkedList<StackViewItem> labels;
	
	private JScrollPane scrollPane;
	
	/** Contains the stack */
	private JPanel panel;
	
	/** Displays memory registers */
	private JPanel memDisp;
	private JLabel mem1;
	private JLabel mem2;
	
	/**
	 * Creates a new stack view and updates it to fill in text from the
	 * computation stack of the specified <code>MainFrame</code>.
	 * @param par the parent <code>MainFrame</code> of this stack view
	 */
	public StackView(MainFrame par) {
		parent = par;
		labels = new LinkedList<StackViewItem>();
		scrollPane = new JScrollPane();
		panel = new JPanel();
		memDisp = new JPanel();
		mem1 = new JLabel();
		mem2 = new JLabel();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// stack display
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(new Color(200, 200, 200));
		panel.setMinimumSize(new Dimension(150, 150));
		
		scrollPane.getViewport().setMinimumSize(new Dimension(150, 150));
		scrollPane.getViewport().add(panel);
		
		add(scrollPane);
		
		// memory register display
		// FIXME: non-optimal format
		memDisp.setLayout(new BoxLayout(memDisp, BoxLayout.X_AXIS));
		memDisp.add(new JLabel("M1: "));
		memDisp.add(mem1);
		memDisp.add(Box.createHorizontalGlue());
		memDisp.add(new JLabel("M2: "));
		memDisp.add(mem2);
		
		add(memDisp);
		
		update();
	}
	
	/**
	 * Updates this stack view to reflect the changes made to the computation
	 * stack of the <code>MainFrame</code>
	 */
	public void update() {
		// retrieve items
		ComputationStackItem[] items = parent.getCompStack().getItems();
		
		// reuse labels if possible
		int index = 0;
		Iterator<StackViewItem> it = labels.iterator();
		while (it.hasNext() && index < items.length) {
			it.next().setLabel(items[index].getValStr(parent.getPrecision()));
			index++;
		}
		
		// check if we need to add or delete labels
		if (index < items.length) {
			// make new labels
			do {
				StackViewItem newLabel = new StackViewItem(
						items[index].getValStr(parent.getPrecision())); 
				labels.add(newLabel);
				panel.add(newLabel);
				panel.revalidate();
				index++;
			} while (index < items.length);
		} else if (labels.size() > items.length) {
			// delete labels
			do {
				panel.remove(labels.removeLast());
			} while (labels.size() > items.length);
		}
		
		// memory register display
		mem1.setText(parent.getCompStack().getMem1().toString(true));
		mem2.setText(parent.getCompStack().getMem2().toString(true));
		
		repaint();
		
		JScrollBar sb = scrollPane.getVerticalScrollBar();
		sb.setValue(sb.getMaximum());
	}

}
