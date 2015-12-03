package flappySanta.northPolePhysics;

import java.awt.Graphics;

public class PhysicsManager {
	private static final PhysicsManager INSTANCE = new PhysicsManager();

	private World world;

	private PhysicsManager() {
		world = new World();
	}

	/** @return Singleton for managing physics */
	public static PhysicsManager getInstance() {
		return INSTANCE;
	}

	/** Draw the game on the given graphics object. */
	public void drawWorld(Graphics g) {
		/* TODO Draw should be done entirely by UI. This code needs to be
		 * restructured to put all calculations in this section but all drawing
		 * go in user interface. Double buffer mayber? */
		world.draw(g);
	}

	/** Restart the world. */
	public void noahsFlood() {
		world = new World();
	}

	/** Tell thje world to jump santa. */
	public void jump() {
		world.santaJump();
	}

}
