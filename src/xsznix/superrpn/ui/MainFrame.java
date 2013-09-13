package xsznix.superrpn.ui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import xsznix.superrpn.compstack.ComputationStack;
import xsznix.superrpn.ui.elem.OperatorKeyboard;
import xsznix.superrpn.ui.elem.PrefBar;
import xsznix.superrpn.ui.elem.StackView;
import xsznix.superrpn.util.Constant;

/**
 * The main window of SuperRPN.
 * @author Xuming Zeng
 *
 */
public class MainFrame extends JFrame implements KeyListener {

	private static final long serialVersionUID = -4184463015449743888L;
	
	private JPanel contentPane;
	
	private PrefBar prefBar;
	
	private ComputationStack stack;
	
	private StackView stackView;
	
	private OperatorKeyboard keyboard;
	
	/** The displayed precision. */
	private int precision;
	
	/** If true, trigonometric functions are evaluated in degrees. */
	private boolean degrees;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// open the window
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		// initialize the stack
		stack = new ComputationStack();
		stack.push("0");
		
		// other variables
		precision = Constant.INIT_DISP_PRECISION;
		degrees = false;
		
		// window options
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setTitle("SuperRPN");
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		
		// menu bar
		prefBar = new PrefBar(this);
		setJMenuBar(prefBar);
		
		// content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		// stack view
		stackView = new StackView(this);
		contentPane.add(stackView);
		
		// operator keyboard
		keyboard = new OperatorKeyboard(this);
		contentPane.add(keyboard);
	}
	
	// GETTERS / SETTERS
	
	public ComputationStack getCompStack() { return stack; }
	
	public StackView getStackView() { return stackView; }
	
	public int getPrecision() { return precision; }
	
	public void setPrecision(int p) { precision = p; stackView.update(); }
	
	public boolean isDegreesMode() { return degrees; }
	
	public void setDegreesMode(boolean deg) {
		degrees = deg;
		prefBar.updateDegreesModeDisplay();
	}
	
	// EVENT HANDLERS
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT)
			keyboard.setAlt(true);
		else
			keyboard.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT)
			keyboard.setAlt(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// nothing
	}

}
