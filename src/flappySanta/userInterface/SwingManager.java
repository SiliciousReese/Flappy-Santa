package flappySanta.userInterface;

import javax.swing.SwingUtilities;

public class SwingManager {
	private static final SwingManager INSTANCE = new SwingManager();
	SantaFrame frame;

	private SwingManager() {
		/* Start swing in it's own thread. */
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame = new SantaFrame();
			}
		});
	}

	/** @return Singleton for managing graphical interface. */
	public static SwingManager getInstance() {
		return INSTANCE;
	}

}
