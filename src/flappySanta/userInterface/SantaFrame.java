package flappySanta.userInterface;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import flappySanta.FlappySanta;

public class SantaFrame implements WindowListener {
	private JFrame frame;

	private SantaContentPane contentPane;
	private SantaMenu menu;

	/** The frame that is behind most of the program's gui. */
	public SantaFrame() {
		/* Create a new frame with the title "Flappy Santa", allow it to handle
		 * being closed, set the size and prevent resizing. */
		frame = new JFrame("Flappy Santa");
		frame.addWindowListener(this);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(800, 600);

		contentPane = new SantaContentPane();
		frame.setContentPane(contentPane);
		frame.addKeyListener(contentPane);

		menu = new SantaMenu();
		frame.setJMenuBar(menu);

		new NewGameDialog(frame);

		frame.setVisible(true);
	}

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
