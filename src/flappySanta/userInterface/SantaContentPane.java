package flappySanta.userInterface;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import flappySanta.northPoleMechanics.ElvishLiaison;

@SuppressWarnings("serial")
public class SantaContentPane extends JPanel implements KeyListener {
	/** Delay between repainting screen. */
	private static final int REFRESH_DELAY = 10;

	private static final ElvishLiaison MECHANICS = ElvishLiaison.getInstance();

	private Timer refreshDisplay;

	/** Content pane for santa frame. */
	public SantaContentPane() {
		setRepaintTimer();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ')
			MECHANICS.jump();
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
		MECHANICS.drawNorthPole(g);
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
