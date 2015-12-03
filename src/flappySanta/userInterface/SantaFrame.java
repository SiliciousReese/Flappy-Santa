package flappySanta.userInterface;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import flappySanta.FlappySanta;

/* TODO Serialize? */
@SuppressWarnings("serial")
public class SantaFrame extends JFrame implements WindowListener {
	private SantaContentPane contentPane;
	private SantaMenu menu;

	/** The frame that is behind most of the program's gui. */
	public SantaFrame() {
		/* Set title to "Flappy Santa", allow this to handle being closed, set
		 * the size and prevent resizing. */
		/* TODO Move some of this to Swing manager which owns this. */
		super("Flappy Santa");
		addWindowListener(this);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(800, 600);

		contentPane = new SantaContentPane();
		setContentPane(contentPane);
		addKeyListener(contentPane);

		menu = new SantaMenu();
		setJMenuBar(menu);

		/* Start the new Game dialog. */
		/* TODO This should not be made visible until the dialog has closed. */
		new NewGameDialog(this);

		setVisible(true);
	}

	/* TODO Consider using a method for closing window without the need to
	 * implement window listener with all its unused methods. */
	@Override
	public void windowClosed(WindowEvent e) {
		/* Exit cleanly. */
		FlappySanta.exit(false);
	}

	/* Unused window events. */

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
