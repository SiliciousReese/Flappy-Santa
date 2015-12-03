package flappySanta.userInterface;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import flappySanta.northPolePhysics.PhysicsManager;

@SuppressWarnings("serial")
public class SantaContentPane extends JPanel implements KeyListener {
	private static final int REFRESH_DELAY = 10;
	private PhysicsManager physics;

	private Timer refreshDisplay;

	/** Content pane for santa frame. */
	public SantaContentPane() {
		physics = PhysicsManager.getInstance();

		setRepaintTimer();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ')
			physics.jump();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Toolkit.getDefaultToolkit().sync();
		physics.drawWorld(g);
	}

	/** Creates and starts a timer that refreshes the display. */
	private void setRepaintTimer() {
		ActionListener redraw = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		};
		refreshDisplay = new Timer(REFRESH_DELAY, redraw);
		refreshDisplay.start();
	}

}
