package flappySanta;

import flappySanta.userInterface.SwingManager;

/** A clone of flappy bird.
 * 
 * @author Daniel Darnell
 * @version 1.0.0-beta */
public class FlappySanta {
	private static final int ERROR_CODE = 1;

	public static void main(String[] args) {
		SwingManager.getInstance();
	}

	public static void exit(boolean error) {
		/* Status code of 0 is standard exit code. */
		int status = 0;

		if (error) {
			System.err.println("Exiting with error.");
			status = ERROR_CODE;
		}

		/* Will run any shutdown hooks. */
		System.exit(status);
	}

}
