package flappySanta;

import flappySanta.userInterface.SwingManager;

/** A clone of flappy bird.
 * 
 * @author Daniel Darnell
 * @version 1.1.0-beta */
public class FlappySanta {
	/** Non-zero exit status is standard for representing error. */
	private static final int DEFAULT_ERROR_CODE = 1;

	/** Entrance to program. */
	public static void main(String[] args) {
		/* Start swing, which will load all the other pieces of the code itself. */
		SwingManager.startSwing();
	}

	/** Only exit point for the code except for errors. */
	public static void exit(boolean error) {
		/* TODO Allow method to receive different error messages. */
		/* Status code of 0 is standard exit code. */
		int status;

		if (error) {
			System.err.println("Exiting with error.");
			status = DEFAULT_ERROR_CODE;
		} else
			status = 0;

		/* Will run any shutdown hooks, specifically those for closing streams. */
		System.exit(status);
	}

}
