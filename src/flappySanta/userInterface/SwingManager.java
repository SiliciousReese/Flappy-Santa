package flappySanta.userInterface;

import javax.swing.SwingUtilities;

public class SwingManager {
	/** Start swing in its own thread. */
	public static void startSwing() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SantaFrame();
			}
		});
	}
}
